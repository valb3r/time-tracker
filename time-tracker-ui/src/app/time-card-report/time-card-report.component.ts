import {ChangeDetectionStrategy, Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {CalendarEvent, CalendarEventAction, CalendarMonthViewDay, CalendarView} from 'angular-calendar';
import {Subject} from 'rxjs';

import {endOfMonth, isSameDay, isSameMonth, parseISO, startOfMonth} from 'date-fns';
import {MatDialog} from '@angular/material/dialog';
import {TimeCardEditComponent} from '../time-card-edit/time-card-edit.component';
import {TimeLogUpload} from '../service/timecard-api/time-card-api.service';
import {MediaMatcher} from '@angular/cdk/layout';
import {AdminApiService, ManagedTimeLog, ProjectDto} from '../service/admin-api/admin-api-service';
import {TimeCardImagesListComponent} from '../dialogs/time-card-images-list/time-card-images-list.component';
import {FormControl} from "@angular/forms";

const colors: any = {
  blue: {
    primary: '#3f51b5',
    secondary: '#D1E8FF'
  }
};

@Component({
  selector: 'timecard-reports',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './time-card-report.component.html',
  styleUrls: ['./time-card-report.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TimeCardReportComponent implements OnInit {

  public ALL = 'ALL';

  @Input() project: ProjectDto;

  mobileQuery: MediaQueryList;

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  excludeDays: number[] = [0, 6];
  loading = true;
  users: UserDto[] = [];
  selectedUserId = new FormControl(this.ALL);

  actions: CalendarEventAction[] = [
    {
      label: '<i class="material-icons">preview</i>',
      a11yLabel: 'Details',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.dialog.open(TimeCardImagesListComponent, {
          data: [this.project, event.meta.src],
          width: '70%',
          height: '70%'
        });
      }
    },
  ];

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [];

  activeDayIsOpen = true;

  constructor(private media: MediaMatcher, private dialog: MatDialog, private api: AdminApiService) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      this.activeDayIsOpen = !((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0);
      this.viewDate = date;
    }
  }

  viewDateChanged() {
    this.activeDayIsOpen = false;
    this.loadTimeLogs();
  }

  private loadTimeLogs() {
    this.loading = true;
    const resp = this.ALL === this.selectedUserId.value ?
      this.api.getManagedTimelogs([this.project.id], startOfMonth(this.viewDate), endOfMonth(this.viewDate))
      : this.api.getManagedTimelogsOfUsers([this.project.id], [+this.selectedUserId.value], startOfMonth(this.viewDate), endOfMonth(this.viewDate));

    resp
      .subscribe(res => {
        this.loading = false;
        this.updateTimeCards(res);
      });
  }

  beforeMonthViewRender({ body }: { body: CalendarMonthViewDay[] }): void {
    body.forEach(day => {
      day.badgeTotal = day.events.map(
        event => event.meta ? event.meta.hoursValue : 0
      ).reduce((a, b) => a + b, 0);
    });
    body.forEach(day => day.badgeTotal = +this.round(day.badgeTotal));
  }

  ngOnInit(): void {
    this.loadTimeLogs();
  }

  changeUser() {
    this.loadTimeLogs();
  }

  private updateTimeCards(updates: ManagedTimeLog[]) {
    this.events = [];
    if (this.ALL === this.selectedUserId.value) {
      const userMap = new Map();
      updates.forEach(it => userMap.set(it.userid, new UserDto(it.userid, this.userName(it))));
      this.users = [...userMap.values()];
    }

    updates.sort((a, b) => parseISO(a.timestamp).valueOf() - parseISO(b.timestamp).valueOf()).forEach(card => {
      const timeValue = this.getHoursValue(card);
      const event = {
        start: parseISO(card.timestamp),
        end: parseISO(card.timestamp),
        title: `[${this.userName(card)}], ${timeValue}h ${card.description}`,
        color: colors.blue,
        actions: this.actions,
        allDay: true,
        meta: {
          hoursValue: +timeValue,
          src: card
        },
        draggable: !this.mobileQuery.matches
      };

      this.events.push(event);
    });
    this.refresh.next();
  }

  private userName(card: ManagedTimeLog) {
    return `${card.userfullname ? card.userfullname + ',' : ''}${card.username}`;
  }

  private getHoursValue(card: ManagedTimeLog) {
    return this.round(card.durationminutes / 60.0 );
  }

  private round(value: number) {
    return (Math.round((value + Number.EPSILON) * 100) / 100).toFixed(2);
  }
}

class UserDto {
  constructor(public id: number, public name: string) {
  }
}


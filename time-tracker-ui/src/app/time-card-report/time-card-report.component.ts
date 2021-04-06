import {ChangeDetectionStrategy, Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {CalendarEvent, CalendarEventAction, CalendarMonthViewDay, CalendarView} from "angular-calendar";
import {Subject} from "rxjs";

import {endOfMonth, isSameDay, isSameMonth, parseISO, startOfMonth} from 'date-fns';
import {MatDialog} from "@angular/material/dialog";
import {TimeCardEditComponent} from "../time-card-edit/time-card-edit.component";
import {TimeLogUpload} from "../service/timecard-api/time-card-api.service";
import {MediaMatcher} from "@angular/cdk/layout";
import {AdminApiService, ManagedTimeLog, ProjectDto} from "../service/admin-api/admin-api-service";
import {TimeCardListComponent} from "../dialogs/time-card-list/time-card-list.component";

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

  @Input() project: ProjectDto;

  mobileQuery: MediaQueryList;

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  excludeDays: number[] = [0, 6];

  actions: CalendarEventAction[] = [
    {
      label: '<i class="material-icons">preview</i>',
      a11yLabel: 'Details',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.dialog.open(TimeCardListComponent, {
          data: [this.project, event.meta.src],
          width: "70%",
          height: "70%"
        });
      }
    },
  ];

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [];

  activeDayIsOpen: boolean = true;

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
    this.api.getManagedTimelogs([this.project.id], startOfMonth(this.viewDate), endOfMonth(this.viewDate))
      .subscribe(res => this.updateTimeCards(res));
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
    this.api.getManagedTimelogs([this.project.id], startOfMonth(this.viewDate), endOfMonth(this.viewDate))
      .subscribe(res => this.updateTimeCards(res));
  }

  private updateTimeCards(updates: ManagedTimeLog[]) {
    this.events = [];
    updates.sort((a, b) => parseISO(a.timestamp).valueOf() - parseISO(b.timestamp).valueOf()).forEach(card => {
      const timeValue = this.getHoursValue(card);
      let event = {
        start: parseISO(card.timestamp),
        end: parseISO(card.timestamp),
        title: `[${card.userfullname ? card.userfullname + ',': ''}${card.username}], ${timeValue}h ${card.description}`,
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

  private getHoursValue(card: ManagedTimeLog) {
    return this.round(card.durationminutes / 60.0 );
  }

  private round(value: number) {
    return (Math.round((value + Number.EPSILON) * 100) / 100).toFixed(2);
  }
}


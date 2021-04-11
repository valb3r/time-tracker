import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarMonthViewDay,
  CalendarView
} from "angular-calendar";
import {Subject} from "rxjs";

import {endOfMonth, isSameDay, isSameMonth, parseISO, startOfMonth} from 'date-fns';
import {MatDialog} from "@angular/material/dialog";
import {TimeCardEditComponent} from "../time-card-edit/time-card-edit.component";
import {TimeCardApiService, TimeLogUpload} from "../service/timecard-api/time-card-api.service";
import {MediaMatcher} from "@angular/cdk/layout";
import {filter, flatMap, map} from "rxjs/operators";
import {ManagedTimeLog} from "../service/admin-api/admin-api-service";

const colors: any = {
  blue: {
    primary: '#3f51b5',
    secondary: '#D1E8FF'
  }
};

@Component({
  selector: 'my-timecards',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './time-card-calendar.component.html',
  styleUrls: ['./time-card-calendar.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TimeCardCalendarComponent implements OnInit {

  mobileQuery: MediaQueryList;

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  excludeDays: number[] = [0, 6];
  loading = true;

  actions: CalendarEventAction[] = [
    {
      label: '<i class="material-icons">edit</i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        const dialogRef = this.dialog.open(TimeCardEditComponent, {
          data: event.meta.src
        });

        dialogRef.afterClosed().pipe(
          filter(result => result !== undefined),
          flatMap((result: TimeLogUpload) => this.api.updateTimeCard(event.meta.src.id, result)),
          flatMap(() => this.doLoadTimecards())
        ).subscribe(res => this.updateTimeCards(res));
      }
    },
    {
      label: '<i class="material-icons">delete</i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter(iEvent => iEvent !== event);
        this.api.deleteTimeCard(event.meta.src.id)
          .pipe(
            flatMap(() => this.doLoadTimecards())
          ).subscribe(res => this.updateTimeCards(res))
      }
    }
  ];

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [];

  activeDayIsOpen: boolean = true;

  constructor(private media: MediaMatcher, private dialog: MatDialog, private api: TimeCardApiService) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      this.activeDayIsOpen = !((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0);
      this.viewDate = date;
    }

    const dialogRef = this.dialog.open(TimeCardEditComponent, {
      data: {timestamp: this.viewDate}
    });

    dialogRef.afterClosed().pipe(
      filter(result => result !== undefined),
      flatMap((result: TimeLogUpload) => this.api.uploadTimeCard(result)),
      flatMap(() => this.doLoadTimecards())
    ).subscribe(res => this.updateTimeCards(res));
  }

  private doLoadTimecards() {
    this.loading = true;
    return this.api.listTimeCards(startOfMonth(this.viewDate), endOfMonth(this.viewDate)).pipe(
      map(it => {
        this.loading = false;
        return it;
      })
    )
  }

  eventTimesChanged({
                      event,
                      newStart,
                      newEnd
                    }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map(iEvent => {
      if (iEvent === event) {
        event.meta.src.timestamp = newStart;
        this.api.updateTimeCard(event.meta.src.id, event.meta.src)
          .pipe(
            flatMap(() => this.doLoadTimecards())
          ).subscribe(res => this.updateTimeCards(res));
        return {
          ...event,
          start: newStart,
          end: newEnd
        };
      }
      return iEvent;
    });
  }

  viewDateChanged() {
    this.activeDayIsOpen = false;
    this.doLoadTimecards()
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
    this.doLoadTimecards()
      .subscribe(res => this.updateTimeCards(res));
  }

  private updateTimeCards(updates: TimeLogUpload[]) {
    this.events = [];
    updates.forEach(card => {
      let event = {
        start: parseISO(card.timestamp),
        end: parseISO(card.timestamp),
        title: card.description,
        color: colors.blue,
        actions: this.actions,
        allDay: true,
        meta: {
          hoursValue: +this.getHoursValue(card),
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


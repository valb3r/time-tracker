import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarMonthViewDay,
  CalendarView
} from "angular-calendar";
import {Subject} from "rxjs";

import {addDays, addHours, endOfDay, endOfMonth, isSameDay, isSameMonth, startOfDay, subDays} from 'date-fns';
import {MatDialog} from "@angular/material/dialog";
import {TimeCardEditComponent} from "../time-card-edit/time-card-edit.component";
import {TimeCardApiService} from "../service/timecard-api/time-card-api.service";

const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3'
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF'
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA'
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

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  excludeDays: number[] = [0, 6];

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  actions: CalendarEventAction[] = [
    {
      label: '<i class="material-icons">edit</i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      }
    },
    {
      label: '<i class="material-icons">delete</i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter(iEvent => iEvent !== event);
        this.handleEvent('Deleted', event);
      }
    }
  ];

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [
    {
      start: subDays(startOfDay(new Date()), 1),
      end: addDays(new Date(), 1),
      title: 'A 3 day event',
      color: colors.red,
      actions: this.actions,
      allDay: true,
      resizable: {
        beforeStart: true,
        afterEnd: true
      },
      meta: {
        hoursValue: 0.5
      },
      draggable: true
    },
    {
      start: startOfDay(new Date()),
      title: 'An event with no end date',
      color: colors.yellow,
      actions: this.actions,
      meta: {
        hoursValue: 1.5
      },
    },
    {
      start: subDays(endOfMonth(new Date()), 3),
      end: addDays(endOfMonth(new Date()), 3),
      title: 'A long event that spans 2 months',
      color: colors.blue,
      allDay: true
    },
    {
      start: addHours(startOfDay(new Date()), 2),
      end: addHours(new Date(), 2),
      title: 'A draggable and resizable event',
      color: colors.yellow,
      actions: this.actions,
      resizable: {
        beforeStart: true,
        afterEnd: true
      },
      meta: {
        hoursValue: 2.3
      },
      draggable: true
    }
  ];

  activeDayIsOpen: boolean = true;

  constructor(private dialog: MatDialog, private api: TimeCardApiService) {}

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }

    const dialogRef = this.dialog.open(TimeCardEditComponent, {
      data: {date: this.viewDate, apiUrl: "122", username: "user", password: "pass"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
      }
    });
  }

  eventTimesChanged({
                      event,
                      newStart,
                      newEnd
                    }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map(iEvent => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: 'New event',
        start: startOfDay(new Date()),
        end: endOfDay(new Date()),
        color: colors.red,
        draggable: true,
        resizable: {
          beforeStart: true,
          afterEnd: true
        }
      }
    ];
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter(event => event !== eventToDelete);
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  beforeMonthViewRender({ body }: { body: CalendarMonthViewDay[] }): void {
    body.forEach(day => {
      day.badgeTotal = day.events.map(
        event => event.meta ? event.meta.hoursValue : 0
      ).reduce((a, b) => a + b, 0);
    });
  }

  ngOnInit(): void {
  }
}


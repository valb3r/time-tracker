import {Component, Input, OnInit} from '@angular/core';
import {TimeLogUpload} from "../service/timecard-api/time-card-api.service";
import {
  endOfISOWeek,
  endOfMonth,
  formatISO,
  parseISO,
  startOfDay,
  startOfISOWeek,
  startOfMonth,
  subDays
} from "date-fns";
import {ManagedTimeLog} from "../service/admin-api/admin-api-service";

@Component({
  selector: 'app-time-card-aggregate-stats',
  templateUrl: './time-card-aggregate-stats.component.html',
  styleUrls: ['./time-card-aggregate-stats.component.scss']
})
export class TimeCardAggregateStatsComponent implements OnInit {

  logs: TimeLogUpload[];
  viewDate: Date;

  @Input() set updates(value: TimeLogUpload[]) {
    this.logs = value;
    this.viewDate = new Date();
    this.computeAggregateStats(this.logs, this.viewDate);
  }

  @Input() set date(value: Date) {
    this.viewDate = value;
    this.computeAggregateStats(this.logs, this.viewDate);
  }

  sevenDaysH = 0;
  fourteenDaysH = 0;
  thirtyDaysH = 0;
  weeklyH = 0;
  biWeeklyH = 0;
  monthlyH = 0;

  sevenDaysDates = [];
  fourteenDaysDates = [];
  thirtyDaysDates = [];
  weeklyDates = [];
  biWeeklyDates = [];
  monthlyDates = [];

  constructor() { }

  ngOnInit(): void {
  }

  isoDate(date: Date) {
    if (!date) {
      return '';
    }

    return formatISO(date, {representation: 'date'});
  }

  private computeAggregateStats(updates: TimeLogUpload[], date: Date) {
    if (!updates) {
      return;
    }

    const weekStart = startOfISOWeek(date);
    const weekEnd = endOfISOWeek(date);
    const biWeekStart = startOfISOWeek(subDays(weekStart, 1));
    const monthStart = startOfMonth(date);
    const monthEnd = endOfMonth(date);
    const cardData = updates.map(it => new CardDuration(parseISO(it.timestamp), it.durationminutes));
    const sevenDaysStart = subDays(date, 6);
    const fourteenDaysStart = subDays(date, 13);
    const thirtyDaysStart = subDays(date, 29);

    this.sevenDaysDates = [sevenDaysStart, date];
    this.fourteenDaysDates = [fourteenDaysStart, date];
    this.thirtyDaysDates = [thirtyDaysStart, date];
    this.weeklyDates = [weekStart, weekEnd];
    this.biWeeklyDates = [biWeekStart, weekEnd];
    this.monthlyDates = [monthStart, monthEnd];

    this.sevenDaysH = this.hoursBetweenDates(cardData, this.sevenDaysDates[0], this.sevenDaysDates[1]);
    this.fourteenDaysH = this.hoursBetweenDates(cardData, this.fourteenDaysDates[0], this.fourteenDaysDates[1]);
    this.thirtyDaysH = this.hoursBetweenDates(cardData, this.thirtyDaysDates[0], this.thirtyDaysDates[1]);
    this.weeklyH = this.hoursBetweenDates(cardData, this.weeklyDates[0], this.weeklyDates[1]);
    this.biWeeklyH = this.hoursBetweenDates(cardData, this.biWeeklyDates[0], this.biWeeklyDates[1]);
    this.monthlyH = this.hoursBetweenDates(cardData, this.monthlyDates[0], this.monthlyDates[1]);
  }

  private hoursBetweenDates(cardData: CardDuration[], start: Date, end: Date) {
    return +this.round(cardData.filter(it => startOfDay(it.at) >= startOfDay(start) && startOfDay(it.at) <= startOfDay(end)).map(it => it.durationminutes).reduce((a, b) => a + b, 0) / 60.0);
  }

  private twoWeekStart(weekStart: Date) {
    let twoWeekStart = new Date(weekStart);
    twoWeekStart.setDate(weekStart.getDate() - 1);
    return this.monday(twoWeekStart);
  }

  private getHoursValue(card: ManagedTimeLog) {
    return this.round(card.durationminutes / 60.0 );
  }

  private round(value: number) {
    return (Math.round((value + Number.EPSILON) * 100) / 100).toFixed(2);
  }

  private monday(d: Date) {
    d = new Date(d);
    let day = d.getDay();
    let diff = d.getDate() - day + (day === 0 ? -6 : 1);
    return new Date(d.setDate(diff));
  }
}

class CardDuration {
  constructor(public at, public durationminutes) {
  }
}


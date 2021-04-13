import {Component, Input, OnInit} from '@angular/core';
import {TimeLogUpload} from "../service/timecard-api/time-card-api.service";
import {endOfISOWeek, endOfMonth, formatISO, parseISO, startOfISOWeek, startOfMonth, subDays} from "date-fns";
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

  weeklyH = 0;
  biWeeklyH = 0;
  monthlyH = 0;

  constructor() { }

  ngOnInit(): void {
  }

  isoViewDate() {
    return formatISO(this.viewDate, {representation: 'date'});
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
    this.weeklyH = +this.round(cardData.filter(it => it.at >= weekStart && it.at <= weekEnd).map(it => it.durationminutes).reduce((a, b) => a + b, 0) / 60.0);
    this.biWeeklyH = +this.round(cardData.filter(it => it.at >= biWeekStart && it.at <= weekEnd).map(it => it.durationminutes).reduce((a, b) => a + b, 0) / 60.0);
    this.monthlyH = +this.round(cardData.filter(it => it.at >= monthStart && it.at <= monthEnd).map(it => it.durationminutes).reduce((a, b) => a + b, 0) / 60.0);
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


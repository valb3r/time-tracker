import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {
  LocationCode,
  ProjectWithId,
  TimeCardApiService,
  TimeLogUpload
} from "../service/timecard-api/time-card-api.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {endOfDay} from "date-fns";

@Component({
  selector: 'app-time-card-edit',
  templateUrl: './time-card-edit.component.html',
  styleUrls: ['./time-card-edit.component.scss']
})
export class TimeCardEditComponent implements OnInit {

  locations = Object.keys(LocationCode);

  defaultActivity = '';
  activities = new Set<string>();
  projects: ProjectWithId[];

  loading = true;

  durations: Duration[] = Array
    .from(Array(20).keys())
    .map(it => + 30 * (it + 1))
    .map(it => new Duration(it, TimeCardEditComponent.parseTime(it)));

  filteredDurations: Observable<Duration[]>;

  durationControl = new FormControl( "", [Validators.required]);
  projectControl = new FormControl("", [Validators.required]);
  activityControl = new FormControl("", [Validators.required]);
  locationControl = new FormControl(this.data.location, [Validators.required]);
  descriptionControl = new FormControl(this.data.description, [Validators.minLength(3), Validators.required]);
  dateControl = new FormControl(new Date(this.data.timestamp), [Validators.minLength(10), Validators.required]);

  addTimecard = this.fb.group({
    durationControl: this.durationControl,
    projectControl: this.projectControl,
    activityControl: this.activityControl,
    locationControl: this.locationControl,
    descriptionControl: this.descriptionControl,
    dateControl: this.dateControl
  });

  constructor(
    private api: TimeCardApiService,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<TimeCardEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TimeLogUpload
  ) {}

  ngOnInit() {
    this.loading = true;
    this.api.listAvailableProjects()
      .subscribe(resp => {
        this.projects = resp;
        this.loading = false;
        this.prefillProjectAndActivityAndDuration();
    });

    this.filteredDurations = this.durationControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.durations.slice())
      );
  }

  handleAddTimecardClick() {
    if (!this.addTimecard.valid) {
      return
    }

    this.dialogRef.close({
      projectid: this.projectControl.value.id,
      description: this.descriptionControl.value,
      duration:
        this.durationControl.value instanceof Duration ? "PT" + this.durationControl.value.minutes + "M"
          : `PT${this.durationControl.value.replace(' ', '').toUpperCase()}`,
      location: this.locationControl.value,
      timestamp: endOfDay(this.dateControl.value instanceof Date ? this.dateControl.value : this.dateControl.value.toDate()).toISOString(),
      tags: [this.activityControl.value]}
      );
  }

  handleCancelClick() {
    this.dialogRef.close();
  }

  private prefillProjectAndActivityAndDuration() {
    if (this.data.durationminutes) {
      this.durationControl.setValue(new Duration(this.data.durationminutes, TimeCardEditComponent.parseTime(this.data.durationminutes)))
    }

    if (this.data.projectid) {
      this.projectControl.setValue(this.projects.filter(it => it.id == this.data.projectid).pop());
    }

    // TODO: Not working
    if (this.data.tags && this.data.tags[0]) {
      this.defaultActivity = this.data.tags[0];
    }
  }

  private _filter(value: string): Duration[] {
    const filterValue = value.toLowerCase();

    return this.durations.filter(option => option.name.toLowerCase().includes(filterValue));
  }

  displayFn(duration?: Duration): string | undefined {
    return duration ? duration.name : undefined;
  }

  private static parseTime(minutes: number) {
    let hours = Math.floor(minutes / 60);
    if (0 == hours) {
      return minutes + 'm';
    }

    let partMinutes = minutes - hours * 60;
    if (0 == partMinutes) {
      return hours + 'h';
    }

    return hours + 'h ' + partMinutes + 'm';
  }
}

class Duration {
  constructor(public minutes: number, public name: string) {}
}

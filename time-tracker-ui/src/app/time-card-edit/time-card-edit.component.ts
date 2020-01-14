import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LocationCode, ProjectWithId, TimeCardApiService} from "../service/timecard-api/time-card-api.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {SelectableDto} from "../management/dialogs/add-user-or-group-to-project-dialog/add-user-or-group-to-project-dialog.component";

export interface TimeCardUpload {
  date: Date;
}

@Component({
  selector: 'app-time-card-edit',
  templateUrl: './time-card-edit.component.html',
  styleUrls: ['./time-card-edit.component.scss']
})
export class TimeCardEditComponent implements OnInit {

  locations = LocationCode;

  activities = new Set<string>();
  projects: ProjectWithId[];

  durations: Duration[] = Array
    .from(Array(20).keys())
    .map(it => + 30 * (it + 1))
    .map(it => new Duration(it, TimeCardEditComponent.parseTime(it)));

  filteredDurations: Observable<Duration[]>;

  durationControl = new FormControl("", [Validators.required]);
  projectControl = new FormControl("", [Validators.required]);
  activityControl = new FormControl("", [Validators.required]);
  locationControl = new FormControl("", [Validators.required]);
  descriptionControl = new FormControl("", [Validators.minLength(3), Validators.required]);
  dateControl = new FormControl(this.data.date, [Validators.minLength(10), Validators.required]);

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
    @Inject(MAT_DIALOG_DATA) public data: TimeCardUpload
  ) {}

  ngOnInit() {
    this.api.listAvailableProjects()
      .subscribe(resp => {
        this.projects = resp;
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

  }

  handleCancelClick() {
    this.dialogRef.close();
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

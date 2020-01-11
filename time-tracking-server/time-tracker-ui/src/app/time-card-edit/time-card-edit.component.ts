import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProjectWithId, TimeCardApiService} from "../service/timecard-api/time-card-api.service";
import {classToClass, plainToClass} from "class-transformer";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

export interface ApiConfigData {
  date: Date;
  apiUrl: string;
  username: string;
  password: string;
}

@Component({
  selector: 'app-time-card-edit',
  templateUrl: './time-card-edit.component.html',
  styleUrls: ['./time-card-edit.component.scss']
})
export class TimeCardEditComponent implements OnInit {

  projects: ProjectWithId[];
  tags: Tag[] = [new Tag("WORK")];
  durations: string[] = Array.from(Array(10).keys())
    .map(it => '' + 30 * (it + 1));
  filteredDurations: Observable<string[]>;

  duration = new FormControl();
  date = new FormControl(this.data.date);

  constructor(
    private api: TimeCardApiService,
    public dialogRef: MatDialogRef<TimeCardEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ApiConfigData
  ) {}

  ngOnInit() {
    this.api.listAvailableProjects()
      .subscribe(resp => {
        this.projects = resp.body;
      });

    this.filteredDurations = this.duration.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  handleAddTimecardClick() {
    this.dialogRef.close();
  }

  handleCancelClick() {
    this.dialogRef.close();
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.durations.filter(option => option.toLowerCase().includes(filterValue));
  }
}

class Tag {

  constructor(public id: string) {};
}

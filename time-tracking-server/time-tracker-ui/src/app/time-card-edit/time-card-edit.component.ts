import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TimeCardApiService} from "../service/timecard-api/time-card-api.service";

export interface ApiConfigData {
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

  constructor(
    private api: TimeCardApiService,
    public dialogRef: MatDialogRef<TimeCardEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ApiConfigData) {}

  ngOnInit() {
    this.api.listAvailableProjects()
      .subscribe(resp => console.log(resp))
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

class ProjectWithId {

  id: number;
  name: string;
  code: string;
  description: string;
}

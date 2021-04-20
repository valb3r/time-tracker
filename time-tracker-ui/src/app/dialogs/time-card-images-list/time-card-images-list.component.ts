import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AdminApiService, ManagedTimeLog, ProjectDto} from "../../service/admin-api/admin-api-service";

@Component({
  selector: 'app-time-card-list',
  templateUrl: './time-card-images-list.component.html',
  styleUrls: ['./time-card-images-list.component.scss']
})
export class TimeCardImagesListComponent implements OnInit {

  selected: ManagedTimeLogData;
  images: ManagedTimeLogData[] = [];
  timeOnCardM: number;
  timeOnScreenshotsM: number;
  loading = true;
  byIncrementTags = [];

  constructor(@Inject(MAT_DIALOG_DATA) public log: [ProjectDto, ManagedTimeLog], public api: AdminApiService, public dialogRef: MatDialogRef<TimeCardImagesListComponent>) { }

  ngOnInit(): void {
    this.loading = true;
    this.api.getManagedTimelogCards([this.log[0].id], [this.log[1].id])
      .subscribe(res => {
        this.images = res.map(it => new ManagedTimeLogData(this.api.getLinkManagedTimelogCardImages(it.imageurl), it.timestamp, it.duration));
        this.timeOnCardM = +this.round(this.log[1].durationseconds / 60.0);
        this.timeOnScreenshotsM = +this.round(res.map(it => it.durationseconds).reduce((a, b) => a + b, 0) / 60.0);
        this.loading = false;
        if (this.log[1].incrementtagsminutes) {
          new Map(Object.entries(this.log[1].incrementtagsminutes)).forEach((v, k) => {
            this.byIncrementTags.push(`${k} : ${v} minutes`);
          });
        }
      });
  }

  onDoneClick() {
    this.dialogRef.close();
  }

  round(value: number) {
    return (Math.round((value + Number.EPSILON) * 10) / 10).toFixed(1);
  }
}

export class ManagedTimeLogData {

  constructor(public url: string, public timestamp: string, public duration: string) {
  }
}

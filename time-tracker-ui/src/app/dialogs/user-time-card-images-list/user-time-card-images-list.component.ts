import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ManagedTimeLog, ProjectDto} from "../../service/admin-api/admin-api-service";
import {TimeCardApiService} from "../../service/timecard-api/time-card-api.service";

@Component({
  selector: 'app-user-time-card-list',
  templateUrl: './user-time-card-images-list.component.html',
  styleUrls: ['./user-time-card-images-list.component.scss']
})
export class UserTimeCardImagesListComponent implements OnInit {

  selected: UserTimeLogData;
  images: UserTimeLogData[] = [];
  loading = true;

  constructor(@Inject(MAT_DIALOG_DATA) public log: UserTimeCardDialogData, public api: TimeCardApiService, public dialogRef: MatDialogRef<UserTimeCardImagesListComponent>) { }

  ngOnInit(): void {
    this.loadTimeCards();
  }

  onDoneClick() {
    this.dialogRef.close();
  }

  round(value: number) {
    return (Math.round((value + Number.EPSILON) * 10) / 10).toFixed(1);
  }

  doDelete(data: UserTimeLogData) {
    this.loading = true;
    this.api.deleteTimeCardImage(data.relUrl).subscribe(_ => this.loadTimeCards());
  }

  private loadTimeCards() {
    this.loading = true;
    this.api.getTimelogCards([this.log.cardId])
      .subscribe(res => {
        this.images = res.map(it => new UserTimeLogData(this.api.getLinkManagedTimelogCardImages(it.imageurl), it.imageurl, it.timestamp, it.duration));
        this.loading = false;
      })
  }
}

export class UserTimeLogData {

  constructor(public url: string, public relUrl, public timestamp: string, public duration: string) {
  }
}

export class UserTimeCardDialogData {

  constructor(public cardId: number) {
  }
}


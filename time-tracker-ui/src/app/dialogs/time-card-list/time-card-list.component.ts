import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AdminApiService, ManagedTimeLog, ProjectDto} from "../../service/admin-api/admin-api-service";

@Component({
  selector: 'app-time-card-list',
  templateUrl: './time-card-list.component.html',
  styleUrls: ['./time-card-list.component.scss']
})
export class TimeCardListComponent implements OnInit {

  selected: ManagedTimeLogData;
  images: ManagedTimeLogData[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public log: [ProjectDto, ManagedTimeLog], public api: AdminApiService, public dialogRef: MatDialogRef<TimeCardListComponent>) { }

  ngOnInit(): void {
    this.api.getManagedTimelogCards([this.log[0].id], [this.log[1].id])
      .subscribe(res => this.images = res.map(it => new ManagedTimeLogData(this.api.getLinkManagedTimelogCardImages(it.imageurl), it.timestamp, it.duration)))
  }

  onDoneClick() {
    this.dialogRef.close();
  }
}

export class ManagedTimeLogData {

  constructor(public url: string, public timestamp: string, public duration: string) {
  }
}

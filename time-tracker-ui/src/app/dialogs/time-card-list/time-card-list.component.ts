import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AdminApiService, ManagedTimeLog, ProjectDto} from "../../service/admin-api/admin-api-service";

@Component({
  selector: 'app-time-card-list',
  templateUrl: './time-card-list.component.html',
  styleUrls: ['./time-card-list.component.scss']
})
export class TimeCardListComponent implements OnInit {

  selected: string;
  images: string[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public log: [ProjectDto, ManagedTimeLog], public api: AdminApiService, public dialogRef: MatDialogRef<TimeCardListComponent>) { }

  ngOnInit(): void {
    this.api.getManagedTimelogCards([this.log[0].id], [this.log[1].id])
      .subscribe(res => this.images = res.map(it => this.api.getLinkManagedTimelogCardImages(it.imageurl)));
  }

  onDoneClick() {
    this.dialogRef.close();
  }
}

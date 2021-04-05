import {Component, Inject, OnInit} from '@angular/core';
import {ProjectDto} from "../../../service/admin-api/admin-api-service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-review-project',
  templateUrl: './review-project.component.html',
  styleUrls: ['./review-project.component.scss']
})
export class ReviewProjectComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public project: ProjectDto, private dialog: MatDialog, public dialogRef: MatDialogRef<ReviewProjectComponent>) { }

  ngOnInit(): void {
  }

  onDoneClick() {
    this.dialogRef.close();
  }
}

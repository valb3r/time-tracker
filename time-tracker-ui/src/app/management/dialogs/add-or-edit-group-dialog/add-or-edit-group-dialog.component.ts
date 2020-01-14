import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FieldErrorStateMatcher} from "../../../app.component";
import {FormBuilder, FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-group-dialog',
  templateUrl: './add-or-edit-group-dialog.component.html',
  styleUrls: ['./add-or-edit-group-dialog.component.scss']
})
export class AddOrEditGroupDialogComponent implements OnInit {

  projectNameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  fieldMatcher = new FieldErrorStateMatcher();

  constructor(
    public dialogRef: MatDialogRef<AddOrEditGroupDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddNewGroup
  ) {}

  ngOnInit() {
  }

  onAddClick(): void {
    if (!this.projectNameControl.valid) {
      return
    }

    this.dialogRef.close(this.data.name);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

export interface AddNewGroup {
  name: string;
}

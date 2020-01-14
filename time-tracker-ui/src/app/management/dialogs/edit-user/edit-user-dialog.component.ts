import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AddNewOrEditUserDto} from "../../../service/admin-api/admin-api-service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {FieldErrorStateMatcher, ParentOrFieldErrorStateMatcher} from "../../../app.component";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user-dialog.component.html',
  styleUrls: ['./edit-user-dialog.component.scss']
})
export class EditUserDialogComponent implements OnInit {

  userNameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  fullNameControl = new FormControl('', []);

  hourlyRateControl = new FormControl('', [
    Validators.required,
    Validators.min(0)
  ]);

  registerForm = this.fb.group({
    username: this.userNameControl,
    fullname: this.fullNameControl,
    rate: this.hourlyRateControl
  });


  fieldMatcher = new FieldErrorStateMatcher();
  parentOrFieldMatcher = new ParentOrFieldErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<EditUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddNewOrEditUserDto
  ) {
    this.userNameControl.setValue(data.username);
    this.fullNameControl.setValue(data.fullname);
    this.hourlyRateControl.setValue(data.rate);
  }

  ngOnInit() {
  }

  onSaveClick(): void {
    if (!this.registerForm.valid) {
      return
    }

    this.dialogRef.close(
      {
        username: this.userNameControl.value,
        fullname: this.fullNameControl.value,
        rate: this.hourlyRateControl.value
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}



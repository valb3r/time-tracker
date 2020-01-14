import {Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, Validators} from "@angular/forms";
import {FieldErrorStateMatcher, ParentOrFieldErrorStateMatcher} from "../../../app.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProjectCreateOrUpdateDto} from "../../../service/admin-api/admin-api-service";

@Component({
  selector: 'app-add-project-dialog',
  templateUrl: './add-or-edit-project-dialog.component.html',
  styleUrls: ['./add-or-edit-project-dialog.component.scss']
})
export class AddOrEditProjectDialogComponent implements OnInit {

  projectCodeControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  projectNameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  activitiesControl = new FormArray<ActivityControl>(
    [],
    [Validators.minLength(1)]
  );

  newProjectForm = this.fb.group({
    username: this.projectCodeControl,
    fullname: this.projectNameControl,
  });

  fieldMatcher = new FieldErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddOrEditProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProjectCreateOrUpdateDto
  ) {}

  ngOnInit() {
  }

  addProjectActivity() {
    let control = new ActivityControl("", [
      Validators.required,
      Validators.minLength(3)]
    );

    this.activitiesControl.controls.push(control);
  }

  onCreateClick(): void {
    if (!this.projectNameControl.valid) {
      return
    }

    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

export class ActivityControl extends FormControl {

  value: string;
}

import {Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {FieldErrorStateMatcher} from "../../../app.component";
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

  activitiesControl: FormArray;

  projectDescriptionControl = new FormControl('', []);

  newProjectForm = this.fb.group({
    username: this.projectCodeControl,
    fullname: this.projectNameControl,
    activities: this.fb.array([]),
  }, {validator: AddOrEditProjectDialogComponent.checkActivities});

  fieldMatcher = new FieldErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddOrEditProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProjectCreateOrUpdateDto
  ) {
    this.activitiesControl = this.newProjectForm.controls.activities;

    this.projectCodeControl.setValue(data.code);
    this.projectNameControl.setValue(data.name);

    if (data.activities) {
      data.activities.forEach(it => {
        this.addProjectActivityWithValue(it);
      });
    }

    this.projectDescriptionControl.setValue(data.description)
  }

  ngOnInit() {
  }

  addProjectActivityWithValue(value: string) {
    let control = this.fb.control("", [
      Validators.required,
      Validators.minLength(3)]
    );

    control.setValue(value);
    this.activitiesControl.controls.push(control);
    this.projectNameControl.updateValueAndValidity();
  }

  addProjectActivity() {
    this.addProjectActivityWithValue("");
  }

  removeProjectActivity(control: FormControl) {
    const index = this.activitiesControl.controls.indexOf(control, 0);
    if (index > -1) {
      this.activitiesControl.controls.splice(index, 1);
    }
    this.projectNameControl.updateValueAndValidity();
  }

  onCreateClick(): void {
    if (!this.newProjectForm.valid || this.activitiesControl.controls.filter(it => !it.valid).length > 0) {
      return
    }

    this.dialogRef.close(
      {
        code: this.projectCodeControl.value,
        name: this.projectNameControl.value,
        activities: this.activitiesControl.controls.map(it => it.value),
        description: this.projectDescriptionControl.value}
      );
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  private static checkActivities(group: FormGroup) {
    let matchControls = <FormArray>group.controls.activities;
    return (matchControls.controls.length > 0) ? null : {noActivities: true}
  }
}



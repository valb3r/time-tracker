import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GroupNode, ProjectUserRelation} from "../../../common-types/common-types";
import {FieldErrorStateMatcher} from "../../../app.component";
import {RoleDetailsDto} from "../../../service/admin-api/admin-api-service";


@Component({
  selector: 'app-edit-project-role',
  templateUrl: './edit-project-role.component.html',
  styleUrls: ['./edit-project-role.component.scss']
})
export class EditProjectRoleComponent implements OnInit {
  fromDate = new FormControl(new Date(), [Validators.minLength(10), Validators.required]);
  toDate = new FormControl(new Date(), [Validators.minLength(10), Validators.required]);

  userName: string;
  roleName: string;
  parent: GroupNode;
  fieldMatcher = new FieldErrorStateMatcher();

  hourlyRateControl = new FormControl('', [
    Validators.required,
    Validators.min(0)
  ]);

  editToProjectForm = this.fb.group({
    rate: this.hourlyRateControl,
    from: this.fromDate,
    to: this.toDate
  });

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<EditProjectRoleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProjectUserRelation
  ) {
    this.parent = data.parent;
    this.userName = data.userName;
    this.roleName = data.roleType;
  }

  ngOnInit() {
    this.hourlyRateControl.setValue(this.data.roleDetails.rate);
    this.fromDate.setValue(this.data.roleDetails.from);
    this.toDate.setValue(this.data.roleDetails.to);
  }

  onAddClick() {
    if (!this.editToProjectForm.valid) {
      return
    }

    let details = new RoleDetailsDto();
    details.rate = this.hourlyRateControl.value;
    details.from = this.fromDate.value;
    details.to = this.toDate.value;

    this.dialogRef.close(details);
  }

  onCancelClick() {
    this.dialogRef.close();
  }
}

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AddNewUser} from "../../../service/admin-api/admin-api-service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {FieldErrorStateMatcher, ParentOrFieldErrorStateMatcher} from "../../../app.component";

class PasswordsMatchControl extends FormControl {

  constructor(private hidden: boolean) {
    super('', [])
  }

  get Hidden(): boolean {
    return this.hidden;
  }

  visible(): boolean {
    return !this.hidden;
  }

  set Hidden(hidden: boolean) {
    this.hidden = hidden;
  }
}

@Component({
  selector: 'app-add-user-dialog',
  templateUrl: './add-user-dialog.component.html',
  styleUrls: ['./add-user-dialog.component.scss']
})
export class AddUserDialogComponent implements OnInit {

  userNameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  fullNameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  passwordControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  passwordMatchControl = new PasswordsMatchControl(false);

  registerForm = this.fb.group({
    username: this.userNameControl,
    fullname: this.fullNameControl,
    passwords: this.passwordControl,
    matchPasswords: this.passwordMatchControl
  }, {validator: AddUserDialogComponent.checkPasswords});


  fieldMatcher = new FieldErrorStateMatcher();
  parentOrFieldMatcher = new ParentOrFieldErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddNewUser
  ) {}

  ngOnInit() {
  }

  onCreateClick(): void {
    if (!this.registerForm.valid) {
      return
    }

    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  private static checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    let matchControl = <PasswordsMatchControl>group.controls.matchPasswords;
    let pass = group.controls.passwords.value;
    let confirmPass = matchControl.value;

    return (matchControl.Hidden || pass === confirmPass) ? null : {notSame: true}
  }
}



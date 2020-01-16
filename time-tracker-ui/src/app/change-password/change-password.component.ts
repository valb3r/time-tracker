import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {Component, OnInit} from "@angular/core";
import {ErrorMessageUtil, FieldErrorStateMatcher} from "../app.component";
import {AdminApiService} from "../service/admin-api/admin-api-service";
import {Router} from "@angular/router";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  responseError: string;

  oldPasswordControl = new FormControl('', [
    Validators.required
  ]);

  passwordControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  passwordMatchControl = new PasswordsMatchControl(false);

  passwordUpdateForm = this.fb.group({
    oldpassword: this.oldPasswordControl,
    passwords: this.passwordControl,
    matchPasswords: this.passwordMatchControl
  }, {validator: ChangePasswordComponent.checkPasswords});

  fieldMatcher = new FieldErrorStateMatcher();
  parentOrFieldMatcher = new ParentOrFieldErrorStateMatcher();

  constructor(private api: AdminApiService, private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
  }

  handleSaveClick() {
    if (!this.passwordUpdateForm.valid) {
      return
    }

    this.api.updatePassword({current: this.oldPasswordControl.value, newpassword: this.passwordControl.value})
      .subscribe(
        success => {
          this.router.navigate(['/main-screen/my-profile']);
        },
        error => {
          this.responseError = ErrorMessageUtil.extract(error);
        });
  }

  private static checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    let matchControl = <PasswordsMatchControl>group.controls.matchPasswords;
    let pass = group.controls.passwords.value;
    let confirmPass = matchControl.value;

    return (matchControl.Hidden || pass === confirmPass) ? null : {notSame: true}
  }
}

export class ParentOrFieldErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    return (invalidCtrl || invalidParent);
  }
}

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

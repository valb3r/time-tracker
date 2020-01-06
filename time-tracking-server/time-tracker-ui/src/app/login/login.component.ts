import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {FieldErrorStateMatcher} from "../app.component";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  hide = true;

  userNameControl = new FormControl('', [
    Validators.required
  ]);

  passwordControl = new FormControl('', [
    Validators.required
  ]);

  loginForm = this.fb.group({
    username: this.userNameControl,
    passwords: this.passwordControl,
  });

  fieldMatcher = new FieldErrorStateMatcher();

  constructor(private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
  }

  handleLoginClick() {
    if (!this.loginForm.valid) {
      return
    }

    this.router.navigate(['/time-card-input']);
  }
}

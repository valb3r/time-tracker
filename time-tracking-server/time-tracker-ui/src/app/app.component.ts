import { Component } from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'time-tracker-ui';
}

export class FieldErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

export class ErrorMessageUtil {

  static extract(error): string {
    let errMsg = "Failed " + error.message;
    if (error && error.error && error.error.message) {
      errMsg = error.error.message;
    }

    return errMsg.substring(0, 32) + (errMsg.length >= 32 ? "..." : "");
  }
}

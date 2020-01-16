import { Component } from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm} from "@angular/forms";
import {NavigationStart, Router} from "@angular/router";
import {AdminApiService} from "./service/admin-api/admin-api-service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router, private api: AdminApiService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        if (!this.router.navigated) {
          this.api.detectManager();
        }
      }
    });
  }
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

export class ParentOrFieldErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    return (invalidCtrl || invalidParent);
  }
}

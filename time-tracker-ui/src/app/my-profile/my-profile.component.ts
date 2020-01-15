import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {FieldErrorStateMatcher} from "../app.component";
import {AdminApiService} from "../service/admin-api/admin-api-service";

@Component({
  selector: 'my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {

  id: number;
  userNameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ]);

  fullNameControl = new FormControl('', []);

  registerForm = this.fb.group({
    username: this.userNameControl,
    fullname: this.fullNameControl,
  });


  fieldMatcher = new FieldErrorStateMatcher();

  constructor(private fb: FormBuilder, private api: AdminApiService) {}

  ngOnInit() {
    this.api.getMyself().subscribe(res => {
      this.id = res.id;
      this.userNameControl.setValue(res.username);
      this.fullNameControl.setValue(res.fullname);
    });
  }

  onSaveClick(): void {
    if (!this.registerForm.valid) {
      return
    }

    this.api.updateSelf(
      this.id,
      {
        password: null,
        rate: null,
        username: this.userNameControl.value,
        fullname: this.fullNameControl.value
      }).subscribe(res => {
      this.userNameControl.setValue(res.username);
      this.fullNameControl.setValue(res.fullname);
    });
  }
}



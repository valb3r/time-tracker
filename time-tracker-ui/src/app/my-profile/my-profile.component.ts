import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {

  username: string;
  fullName: string;
  joinDate: Date;

  constructor() { }

  ngOnInit() {
  }

  handleSaveChanges() {
  }
}

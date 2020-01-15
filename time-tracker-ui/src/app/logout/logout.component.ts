import { Component, OnInit } from '@angular/core';
import {AdminApiService} from "../service/admin-api/admin-api-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private api: AdminApiService, private router: Router) { }

  ngOnInit() {
    this.api.logout().subscribe(res => this.router.navigate(["main-screen/my-profile"]));
  }
}

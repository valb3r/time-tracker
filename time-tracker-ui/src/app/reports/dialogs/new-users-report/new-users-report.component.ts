import {Component, OnInit} from '@angular/core';
import {AdminApiService, UserDto} from "../../../service/admin-api/admin-api-service";

@Component({
  selector: 'app-new-users-report',
  templateUrl: './new-users-report.component.html',
  styleUrls: ['./new-users-report.component.scss']
})
export class NewUsersReportComponent implements OnInit {

  users: UserDto[];

  constructor(private api: AdminApiService) { }

  ngOnInit() {
    this.api.ownOwnedGroups().subscribe(res => {
      res.forEach(it => this.users.push(...it.entry.users))
    });
  }

  onSaveClick() {
  }

  onCancelClick() {
  }
}

import { Component, OnInit } from '@angular/core';
import {AdminApiService, ProjectDto} from "../../../service/admin-api/admin-api-service";

@Component({
  selector: 'app-new-projects-report',
  templateUrl: './new-projects-report.component.html',
  styleUrls: ['./new-projects-report.component.scss']
})
export class NewProjectsReportComponent implements OnInit {

  projects: ProjectDto[];

  constructor(private api: AdminApiService) { }

  ngOnInit() {
    this.api.ownOwnedGroups().subscribe(res => {
      res.forEach(it => this.projects.push(...it.entry.projects))
    });
  }

  onAddClick() {
  }

  onCancelClick() {
  }
}

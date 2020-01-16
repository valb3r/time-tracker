import { Component, OnInit } from '@angular/core';
import {AdminApiService, ReportDto} from "../service/admin-api/admin-api-service";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  reports: ReportDto[];

  constructor(private api: AdminApiService) { }

  ngOnInit() {
    this.api.getAllMineReports().subscribe(reports => this.reports = reports)
  }
}


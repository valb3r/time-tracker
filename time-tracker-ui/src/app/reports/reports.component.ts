import {Component, OnInit} from '@angular/core';
import {AdminApiService, ReportDto} from "../service/admin-api/admin-api-service";
import {MatDialog} from "@angular/material/dialog";
import {NewProjectsReportComponent} from "./dialogs/new-projects-report/new-projects-report.component";
import {interval} from "rxjs";
import {startWith, switchMap} from "rxjs/operators";
import {NewUsersReportComponent} from "./dialogs/new-users-report/new-users-report.component";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  reports: ReportDto[];
  loading = true;

  constructor(private api: AdminApiService, private dialog: MatDialog) { }

  ngOnInit() {
    this.loading = true;
    this.api.getAllMineReports().subscribe(reports => {
      this.loading = false;
      this.reports = reports
    });

    // Await for updates
    interval(5000)
      .pipe(
        startWith(0),
        switchMap(() => {
          this.loading = true;
          return this.api.getAllMineReports();
        })
      )
      .subscribe(res => {
        this.loading = false;
        this.reports = res;
      });
  }


  onReportDownload(report: ReportDto) {
    this.api.downloadReport(report.id);
  }

  onReportDelete(report: ReportDto) {
    this.api.deleteReport(report.id).subscribe(res => {
      this.api.getAllMineReports().subscribe(reports => this.reports = reports);
    })
  }

  onUserReportAdd() {
    const dialogRef = this.dialog.open(NewUsersReportComponent, {});

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.api.getAllMineReports().subscribe(reports => this.reports = reports);
      }
    });
  }

  onProjectReportAdd() {
    const dialogRef = this.dialog.open(NewProjectsReportComponent, {});

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.api.getAllMineReports().subscribe(reports => this.reports = reports);
      }
    });
  }
}


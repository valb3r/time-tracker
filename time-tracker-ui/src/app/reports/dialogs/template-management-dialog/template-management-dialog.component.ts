import {Component, OnInit} from '@angular/core';
import {AdminApiService, ReportTemplateDto} from "../../../service/admin-api/admin-api-service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-template-management-dialog',
  templateUrl: './template-management-dialog.component.html',
  styleUrls: ['./template-management-dialog.component.scss']
})
export class TemplateManagementDialogComponent implements OnInit {

  templates: ReportTemplateDto[];

  constructor(private api: AdminApiService, public dialogRef: MatDialogRef<TemplateManagementDialogComponent>) { }

  ngOnInit() {
    this.api.getAllReportTemplates().subscribe(reports => this.templates = reports)
  }

  onTemplateDownload(template: ReportTemplateDto) {
    this.api.downloadReportTemplate(template.id)
  }

  onTemplateDelete(template: ReportTemplateDto) {
    this.api.deleteReportTemplate(template.id).subscribe(res =>
      this.api.getAllReportTemplates().subscribe(reports => this.templates = reports)
    )
  }

  onTemplateUpload(event) {
    this.api.createReportTemplate(event.target.files[0], event.target.files[0].name).subscribe(res => {
      this.api.getAllReportTemplates().subscribe(reports => this.templates = reports)
    })
  }

  onCancel() {
    this.dialogRef.close();
  }
}

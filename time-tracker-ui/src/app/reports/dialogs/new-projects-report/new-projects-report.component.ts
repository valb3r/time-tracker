import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AdminApiService, ProjectDto, ReportTemplateDto} from "../../../service/admin-api/admin-api-service";
import {Observable} from "rxjs";
import {flatMap, map, startWith} from "rxjs/operators";
import {FormControl, Validators} from "@angular/forms";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatAutocomplete, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {endOfDay, endOfMonth, startOfDay, startOfMonth} from "date-fns";
import {TemplateManagementDialogComponent} from "../template-management-dialog/template-management-dialog.component";

@Component({
  selector: 'app-new-projects-report',
  templateUrl: './new-projects-report.component.html',
  styleUrls: ['./new-projects-report.component.scss']
})
export class NewProjectsReportComponent implements OnInit {

  projectsSelected: ProjectDto[] = [];
  separatorKeysCodes: number[] = [ENTER, COMMA];

  projectsControl = new FormControl("", []);
  projects: ProjectDto[] = [];
  filteredProjects: Observable<ProjectDto[]>;

  fromDateControl = new FormControl(startOfMonth(new Date()), [Validators.minLength(10), Validators.required]);
  toDateControl = new FormControl(endOfMonth(new Date()), [Validators.minLength(10), Validators.required]);

  templatesControl = new FormControl("", []);
  templates: ReportTemplateDto[] = [];
  filteredTemplates: Observable<ReportTemplateDto[]>;
  loading = true;


  @ViewChild('projectInput') projectInput: ElementRef<HTMLInputElement>;
  @ViewChild('autoProject') matAutocomplete: MatAutocomplete;

  constructor(private api: AdminApiService, private dialog: MatDialog, public dialogRef: MatDialogRef<NewProjectsReportComponent>) { }

  ngOnInit() {
    this.api.ownOwnedGroups().subscribe(res => {
      let ids = new Set<number>();
      res.forEach(it => this.projects.push(...it.entry.projects.filter(project => {
        let contains = !ids.has(project.id);
        ids.add(project.id);
        this.loading = false;
        return contains;
      })));
    });

    this.api.getAllReportTemplates().subscribe(res => {
      this.templates = res;
    });

    this.filteredProjects = this.projectsControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filterProject(name) : this.projects.slice())
      );

    this.filteredTemplates = this.templatesControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filterTemplate(name) : this.templates.slice())
      );
  }

  remove(project: ProjectDto): void {
    const index = this.projectsSelected.indexOf(project);

    if (index >= 0) {
      this.projectsSelected.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (this.projectsSelected.indexOf(event.option.value) < 0) {
      this.projectsSelected.push(event.option.value);
    }

    this.projectInput.nativeElement.value = '';
    this.projectsControl.setValue('');
  }

  onManageTemplatesClick() {
    const dialogRef = this.dialog.open(TemplateManagementDialogComponent, {});

    dialogRef.afterClosed()
      .pipe(
        flatMap(() => this.api.getAllReportTemplates())
      )
      .subscribe(res => {
        this.templates = res;
      });
  }

  onSaveClick() {
    this.loading = true;
    this.api.createProjectBasedReport(
      this.projectsSelected.map(it => it.id),
      {
        templateid: this.templatesControl.value.id,
        from: NewProjectsReportComponent.fromUtcDateToDateStart(this.fromDateControl.value),
        to: NewProjectsReportComponent.fromUtcDateToDateEnd(this.toDateControl.value)
      }
    ).subscribe(res => {
      this.loading = false;
      this.dialogRef.close(true);
    })
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  private _filterProject(value: string): ProjectDto[] {
    const filterValue = value.toLowerCase();

    return this.projects.filter(option => option.name.toLowerCase().includes(filterValue));
  }

  private _filterTemplate(value: string): ReportTemplateDto[] {
    const filterValue = value.toLowerCase();

    return this.templates.filter(option => option.description.toLowerCase().includes(filterValue));
  }

  private static fromUtcDateToDateStart(value) {
    return startOfDay(new Date(value.toISOString()));
  }

  private static fromUtcDateToDateEnd(value) {
    return endOfDay(new Date(value.toISOString()));
  }

  displayProjectFn(project?: ProjectDto): string | undefined {
    return project ? project.name : undefined;
  }

  displayTemplateFn(template?: ReportTemplateDto): string | undefined {
    return template ? template.description : undefined;
  }
}

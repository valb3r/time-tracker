import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {
  AdminApiService,
  GroupDto,
  ProjectDto,
  ReportTemplateDto,
  UserDto
} from "../../../service/admin-api/admin-api-service";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {FormControl, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {endOfMonth, startOfMonth} from "date-fns";
import {MatAutocomplete, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {flatMap, map, startWith} from "rxjs/operators";
import {TemplateManagementDialogComponent} from "../template-management-dialog/template-management-dialog.component";

@Component({
  selector: 'app-new-users-report',
  templateUrl: './new-users-report.component.html',
  styleUrls: ['./new-users-report.component.scss']
})
export class NewUsersReportComponent implements OnInit {

  selectedUsers: UserDto[] = [];
  separatorKeysCodes: number[] = [ENTER, COMMA];

  usersControl = new FormControl("", []);
  users: UserDto[] = [];
  filteredUsers: Observable<UserDto[]>;

  fromDateControl = new FormControl(startOfMonth(new Date()), [Validators.minLength(10), Validators.required]);
  toDateControl = new FormControl(endOfMonth(new Date()), [Validators.minLength(10), Validators.required]);

  templatesControl = new FormControl("", []);
  templates: ReportTemplateDto[] = [];
  filteredTemplates: Observable<ReportTemplateDto[]>;


  @ViewChild('usersInput', {static: false}) usersInput: ElementRef<HTMLInputElement>;
  @ViewChild('autoUsers', {static: false}) matAutocomplete: MatAutocomplete;

  constructor(private api: AdminApiService, private dialog: MatDialog, public dialogRef: MatDialogRef<NewUsersReportComponent>) { }

  ngOnInit() {
    this.api.ownOwnedGroups().subscribe(res => {
      let ids = new Set<number>();
      res.forEach(it => this.users.push(...it.entry.users.filter(user => {
        let contains = !ids.has(user.id);
        ids.add(user.id);
        return contains;
      })));
    });

    this.api.getAllReportTemplates().subscribe(res => {
      this.templates = res;
    });

    this.filteredUsers = this.usersControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filterUser(name) : this.users.slice())
      );

    this.filteredTemplates = this.templatesControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filterTemplate(name) : this.templates.slice())
      );
  }

  remove(project: UserDto): void {
    const index = this.selectedUsers.indexOf(project);

    if (index >= 0) {
      this.selectedUsers.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (this.selectedUsers.indexOf(event.option.value) < 0) {
      this.selectedUsers.push(event.option.value);
    }

    this.usersInput.nativeElement.value = '';
    this.usersControl.setValue('');
  }

  onManageTemplatesClick() {
    const dialogRef = this.dialog.open(TemplateManagementDialogComponent, {});

    dialogRef.afterClosed()
      .pipe(
        flatMap(() => this.api.getAllReportTemplates())
      ).subscribe(res => this.templates = res);
  }

  onSaveClick() {
    this.api.createUserBasedReport(
      this.selectedUsers.map(it => it.id),
      {templateid: this.templatesControl.value.id, from: this.fromDateControl.value, to: this.toDateControl.value}
    ).subscribe(res => {
      this.dialogRef.close(true);
    })
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  private _filterUser(value: string): UserDto[] {
    const filterValue = value.toLowerCase();

    return this.users.filter(option => option.username.toLowerCase().includes(filterValue));
  }

  private _filterTemplate(value: string): ReportTemplateDto[] {
    const filterValue = value.toLowerCase();

    return this.templates.filter(option => option.description.toLowerCase().includes(filterValue));
  }

  displayUserFn(user?: UserDto): string | undefined {
    return user ? user.username : undefined;
  }

  displayTemplateFn(template?: ReportTemplateDto): string | undefined {
    return template ? template.description : undefined;
  }
}

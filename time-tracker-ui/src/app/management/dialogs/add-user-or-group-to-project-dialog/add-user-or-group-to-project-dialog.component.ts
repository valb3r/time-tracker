import {Component, Inject, OnInit} from '@angular/core';
import {Role} from "../../../service/admin-api/admin-api-service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {GroupNode, GroupNodesWithParentName, Kind} from "../../../common-types/common-types";
import {FieldErrorStateMatcher} from "../../../app.component";

export enum SelectableType {
  GROUP,
  USER
}

@Component({
  selector: 'app-add-user-or-group-to-project-dialog',
  templateUrl: './add-user-or-group-to-project-dialog.component.html',
  styleUrls: ['./add-user-or-group-to-project-dialog.component.scss']
})
export class AddUserOrGroupToProjectDialogComponent implements OnInit {

  kind = Kind;
  type = SelectableType;

  roles: Role[] = [Role.DEVELOPER, Role.MANAGER];

  usersAndGroups = new FormControl("", [Validators.required]);
  roleControl = new FormControl("", [Validators.required]);
  fromDate = new FormControl(new Date(), [Validators.minLength(10), Validators.required]);
  toDate = new FormControl(new Date(), [Validators.minLength(10), Validators.required]);

  parent: GroupNode;
  existing = new Set<string>();
  selectables: SelectableDto[] = [];
  filteredSelectables: Observable<SelectableDto[]>;
  fieldMatcher = new FieldErrorStateMatcher();

  hourlyRateControl = new FormControl('', [
    Validators.required,
    Validators.min(0)
  ]);

  addToProjectForm = this.fb.group({
    userOrGroup: this.usersAndGroups,
    role: this.roleControl,
    rate: this.hourlyRateControl,
    from: this.fromDate,
    to: this.toDate
  });

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddUserOrGroupToProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: GroupNodesWithParentName
  ) {
    this.parent = data.parent;
    this.populateSelectables(data.nodes);

    this.filteredSelectables = this.usersAndGroups.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.selectables.slice())
      );
  }

  ngOnInit() {
  }

  onAddClick() {
    if (!this.addToProjectForm.valid) {
      return
    }

    this.dialogRef.close(
      {
        id: this.usersAndGroups.value.id,
        role: this.roleControl.value,
        rate: this.hourlyRateControl.value,
        from: this.fromDate.value,
        to: this.toDate.value
      });
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  private _filter(value: string): SelectableDto[] {
    const filterValue = value.toLowerCase();

    return this.selectables.filter(option => option.name.toLowerCase().includes(filterValue));
  }

  displayFn(user?: SelectableDto): string | undefined {
    return user ? user.name : undefined;
  }

  private populateSelectables(roots: GroupNode[]) {
    roots.forEach(root => {
      this.populateSelectable(root);
      if (root.childrenChange.value.length > 0) {
        this.populateSelectables(root.childrenChange.value);
      }
    });

    this.selectables.sort((s1, s2) => {
      if (s1.type == SelectableType.USER && s2.type == SelectableType.GROUP) {
        return -1;
      }

      if (s2.type == SelectableType.USER && s1.type == SelectableType.GROUP) {
        return 1;
      }

      return s1.name.localeCompare(s2.name)
    })
  }

  private populateSelectable(node: GroupNode) {
    let key = "";
    if (node.kind == Kind.GROUP && !node.isSurrogate) {
      key = "G:" + node.name;
      if (!this.existing.has(key)) {
        this.existing.add(key);
        this.selectables.push({id: node.id, type: SelectableType.GROUP, name: node.name});
      }
    }

    if (node.kind == Kind.USER && node.childrenChange.value.length == 0) {
      key = "U:" + node.name;
      if (!this.existing.has(key)) {
        this.existing.add(key);
        this.selectables.push({id: node.id, type: SelectableType.USER, name: node.name});
      }
    }
  }
}

export interface SelectableDto {

  id: number;
  type: SelectableType;
  name: string;
}

export interface AddUserOrProjectToGroupDto {

  role: Role;
  userOrGroupIdsToAdd: number[];
}

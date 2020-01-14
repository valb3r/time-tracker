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
  selector: 'app-add-user-or-group-to-group-dialog',
  templateUrl: './add-user-or-group-to-group-dialog.component.html',
  styleUrls: ['./add-user-or-group-to-group-dialog.component.scss']
})
export class AddUserOrGroupToGroupDialogComponent implements OnInit {

  kind = Kind;
  type = SelectableType;

  usersAndGroups = new FormControl();

  parent: GroupNode;
  existing = new Set<string>();
  selectables: SelectableDto[] = [];
  filteredSelectables: Observable<SelectableDto[]>;
  fieldMatcher = new FieldErrorStateMatcher();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: GroupNodesWithParentName
  ) {
    this.parent = data.parent;
    this.populateSelectables(data.nodes);

    this.filteredSelectables = this.usersAndGroups.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  ngOnInit() {
  }

  onAddClick() {
  }

  onCancelClick() {
  }

  private _filter(value: string): SelectableDto[] {
    const filterValue = value.toLowerCase();

    return this.selectables.filter(option => option.name.toLowerCase().includes(filterValue));
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

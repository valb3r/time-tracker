import {NestedTreeControl, TreeControl} from '@angular/cdk/tree';
import {Component, Injectable, OnInit} from '@angular/core';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {
  AddNewOrEditUserDto,
  AdminApiService,
  GroupDto,
  GroupDtoWithPath,
  ProjectCreateOrUpdateDto,
  ProjectDto,
  RoleDetailsDto,
  UserDto
} from "../service/admin-api/admin-api-service";
import {BehaviorSubject, Observable} from "rxjs";
import {SelectionChange} from "@angular/cdk/collections";
import {MatDialog} from "@angular/material/dialog";
import {AddOrEditGroupDialogComponent} from "./dialogs/add-or-edit-group-dialog/add-or-edit-group-dialog.component";
import {AddOrEditProjectDialogComponent} from "./dialogs/add-or-edit-project-dialog/add-or-edit-project-dialog.component";
import {GroupNode, GroupNodesWithParentName, Kind, ProjectUserRelation} from "../common-types/common-types";
import {AddUserOrGroupToGroupDialogComponent} from "./dialogs/add-user-or-group-to-group-dialog/add-user-or-group-to-group-dialog.component";
import {EditUserDialogComponent} from "./dialogs/edit-user/edit-user-dialog.component";
import {CreateNewUserDialogComponent} from "./dialogs/create-new-user/create-new-user-dialog.component";
import {
  AddUserOrGroupDetails,
  AddUserOrGroupToProjectDialogComponent
} from "./dialogs/add-user-or-group-to-project-dialog/add-user-or-group-to-project-dialog.component";
import {EditProjectRoleComponent} from "./dialogs/edit-project-role/edit-project-role.component";
import {filter, flatMap} from "rxjs/operators";

@Injectable()
export class GroupDatabase {
  private expandedMemoize = new Set<string>();
  dataChange = new BehaviorSubject<GroupNode[]>([]);

  constructor(private treeControl: TreeControl<GroupNode>) {
    this.treeControl.expansionModel.changed.subscribe(change => {
      if ((change as SelectionChange<GroupNode>).added ||
        (change as SelectionChange<GroupNode>).removed) {
        this.handleTreeControl(change as SelectionChange<GroupNode>);
      }
    });
  }

  keepExpandedNodesState(roots: GroupNode[]) {
    roots.forEach(it => {
      if (this.expandedMemoize.has(it.path)) {
        this.treeControl.expansionModel.select(it)
      }
      this.keepExpandedNodesState(it.childrenChange.value);
    })
  }

  /** Handle expand/collapse behaviors */
  handleTreeControl(change: SelectionChange<GroupNode>) {
    if (change.added) {
      change.added.forEach(node => this.expandedMemoize.add(node.path));
    }
    if (change.removed) {
      change.removed.forEach(node => this.expandedMemoize.delete(node.path));
    }
  }
}

@Component({
  selector: 'management-component',
  templateUrl: 'management.component.html',
  styleUrls: ['management.component.scss'],
})
export class ManagementComponent implements OnInit {
  getChildren = (node: GroupNode): Observable<GroupNode[]> => node.childrenChange;
  hasChild = (_: number, node: GroupNode): boolean => node.expandable;

  treeControl = new NestedTreeControl<GroupNode>(this.getChildren);
  dataSource = new MatTreeNestedDataSource<GroupNode>();
  database: GroupDatabase = new GroupDatabase(this.treeControl);

  public kind = Kind;

  constructor(private api: AdminApiService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.database.dataChange.subscribe(data => {
      this.dataSource.data = data;
    });

    this.api.ownOwnedGroups().subscribe(res => this.updateOwnedGroups(res));
  }

  buildGroupTree(groups: GroupDtoWithPath[]): GroupNode[] {
    let roots: string[] = [];
    let rootNodes: GroupNode[] = [];
    let fromShortest = groups.sort((s1 ,s2) => {
      let l1 = s1.path.split("/").length;
      let l2 = s2.path.split("/").length;

      if (l1 == l2) {
        return s1.path.localeCompare(s2.path)
      }

      return l1 - l2
    });

    let children: Map<GroupNode, GroupDtoWithPath[]> = new Map<GroupNode, GroupDtoWithPath[]>();

    fromShortest.forEach(grp => {
      // if it is not root:
      let rootIndex = ManagementComponent.findRoot(roots, grp.path);
      if (rootIndex >= 0) {
        let parent = rootNodes[rootIndex];
        if (!children.has(parent)) {
          children.set(parent, []);
        }

        children.get(parent).push(grp);
      } else {
        // is a root
        roots.push(grp.path);
        let rootNode = new GroupNode(grp.entry.id, grp.path, grp.entry.name, Kind.GROUP, true);
        rootNodes.push(rootNode);
        rootNode.childrenChange.next(this.buildInternalGroups(grp.path, grp.entry));
      }
    });

    children.forEach((values, parent) => {
      parent.expandable = true;
      let children = this.buildGroupTree(values);
      children.forEach(child => child.parent = parent);
      parent.childrenChange.value.push(...children);
      parent.childrenChange.next(parent.childrenChange.value);
    });

    return rootNodes;
  };

  addGroup(parent: GroupNode) {
    const dialogRef = this.dialog.open(AddOrEditGroupDialogComponent, {
      data: {name: ""}
    });

    dialogRef.afterClosed().pipe(
      filter(result => result !== undefined),
      flatMap((result: GroupDto) => this.api.addGroup(parent.id, result)),
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  editGroup(target: GroupNode) {
    this.api.getGroup(target.id).subscribe(res => {
      const dialogRef = this.dialog.open(AddOrEditGroupDialogComponent, {
        data: res
      });

      dialogRef.afterClosed().pipe(
        filter(result => result !== undefined),
        flatMap((result: GroupDto) => this.api.updateGroup(target.id, result)),
        flatMap(() => this.api.ownOwnedGroups())
      ).subscribe(res => this.updateOwnedGroups(res));
    });
  }


  removeGroup(target: GroupNode) {
    this.api.removeGroup(target.id)
      .pipe(
        flatMap(() => this.api.ownOwnedGroups())
      ).subscribe(res => this.updateOwnedGroups(res));
  }

  createNewUser(parent: GroupNode) {
    const dialogRef = this.dialog.open(CreateNewUserDialogComponent, {
      data: {}
    });

    dialogRef.afterClosed().pipe(
      filter(result => result !== undefined),
      flatMap((result: AddNewOrEditUserDto) => this.api.addUser(parent.id, result)),
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  editUser(target: GroupNode) {
    this.api.getUser(target.id).subscribe(res => {
      const dialogRef = this.dialog.open(EditUserDialogComponent, {
        data: res
      });

      dialogRef.afterClosed().pipe(
        filter(result => result !== undefined),
        flatMap((result: AddNewOrEditUserDto) => this.api.updateUser(target.id, result)),
        flatMap(() => this.api.ownOwnedGroups())
      ).subscribe(res => this.updateOwnedGroups(res));
    });
  }

  removeUserCompletely(target: GroupNode) {
    this.api.removeUserCompletely(target.id).pipe(
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  addExistingUserOrGroupToGroup(parent: GroupNode) {
    const dialogRef = this.dialog.open(AddUserOrGroupToGroupDialogComponent, {
      data: new GroupNodesWithParentName(parent, this.dataSource.data)
    });

    dialogRef.afterClosed().pipe(
      filter(result => result !== undefined),
      flatMap(result => this.api.addUsersOrGroupsToGroup(parent.id, result.id)),
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  addExistingUserOrGroupToProject(parent: GroupNode) {
    const dialogRef = this.dialog.open(AddUserOrGroupToProjectDialogComponent, {
      data: new GroupNodesWithParentName(parent, this.dataSource.data)
    });

    dialogRef.afterClosed().pipe(
      filter(result => result !== undefined),
      flatMap((result: AddUserOrGroupDetails) => this.api.addUsersOrGroupsToProject(result.role, [result.id], parent.id, result.details)),
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  editUserRoleOnProject(target: GroupNode) {
    // no graph update for this
    this.api.getRoleDetailsWithType(target.roleId)
      .pipe(
        flatMap(details => {
          const dialogRef = this.dialog.open(EditProjectRoleComponent, {
            data: new ProjectUserRelation(details.type, details, target.name, target.parent)
          });
          return dialogRef.afterClosed()
        }),
        filter(result => result !== undefined),
        flatMap(result => this.api.updateUsersRoleInProject(target.roleId, result as RoleDetailsDto)),
      ).subscribe();
  }

  removeUserFromProject(target: GroupNode) {
    this.api.removeUserOrGroupFromProject(target.id, target.parent.id).pipe(
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  removeInheritedFromProject(target: GroupNode) {
    this.api.removeUserOrGroupFromProject(target.sourceId, target.parent.id).pipe(
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  addProject(parent: GroupNode) {
    const dialogRef = this.dialog.open(AddOrEditProjectDialogComponent, {
      data: {}
    });

    dialogRef.afterClosed().pipe(
      filter(result => result !== undefined),
      flatMap((result: ProjectCreateOrUpdateDto) => this.api.addProject(parent.id, result)),
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  editProject(target: GroupNode) {
    this.api.getProject(target.id).subscribe(res => {
      const dialogRef = this.dialog.open(AddOrEditProjectDialogComponent, {
        data: res
      });

      dialogRef.afterClosed().pipe(
        filter(result => result !== undefined),
        flatMap((result: ProjectCreateOrUpdateDto) => this.api.updateProject(target.id, result)),
        flatMap(() => this.api.ownOwnedGroups())
      ).subscribe(res => this.updateOwnedGroups(res));
    });
  }

  removeProject(target: GroupNode) {
    this.api.removeProject(target.id).pipe(
      flatMap(() => this.api.ownOwnedGroups())
    ).subscribe(res => this.updateOwnedGroups(res));
  }

  private updateOwnedGroups(res: GroupDtoWithPath[]) {
    const data = this.buildGroupTree(res);
    this.database.dataChange.next(data);
    this.database.keepExpandedNodesState(data);
  }

  private buildInternalGroups(path: string, node: GroupDto): GroupNode[] {
    let res: GroupNode[] = [];

    if (!!node.users && node.users.length > 0) {
      let users = new GroupNode(node.id, path + "/" + node.id, "Users", Kind.USER, true);
      res.push(users);
      users.childrenChange.next(ManagementComponent.buildUsers(users.path, node.users));
    }

    if (!!node.projects && node.projects.length > 0) {
      let projects = new GroupNode(node.id, path + "/" + node.id, "Projects", Kind.PROJECT, true);
      res.push(projects);
      projects.childrenChange.next(this.buildProjects(projects.path, node.projects));
    }

    return res;
  }

  private static buildUsers(path: string, nodes: UserDto[]): GroupNode[] {
    let res: GroupNode[] = [];
    nodes.forEach(it => {
      let user = new GroupNode(it.id, path + "/" + it.id, it.username, Kind.USER, false);
      res.push(user)
    });
    return res;
  }

  private buildProjects(path: string, nodes: ProjectDto[]): GroupNode[] {
    let res: GroupNode[] = [];
    nodes.forEach(it => {
      let project = new GroupNode(it.id, path + "/" + it.id, it.name, Kind.PROJECT, true, true);
      res.push(project);

      this.api.projectActors(project.id).subscribe(actors => {
        let mappedActors = actors.map(
          actor => {
            if (!!actor.source) {
              return new GroupNode(
                actor.user.id,
                path + "/" + actor.user.id,
                actor.source.name + ": -> " + actor.user.username,
                Kind.INHERITED_USER,
                false,
                true,
                project,
                actor.source.id
              )
            } else {
              return new GroupNode(
                actor.user.id,
                path + "/" + actor.user.id,
                actor.user.username,
                Kind.USER,
                false,
                true,
                project,
                null,
                actor.roleid
              )
            }
          }
        );
        project.expandable = true;
        project.childrenChange.next(mappedActors);
        this.database.dataChange.next(this.database.dataChange.value);
      });
    });

    return res;
  }

  private static findRoot(roots: string[], path: string): number {
    for (let pos = 0; pos < roots.length; pos++) {
      if (path.startsWith(roots[pos])) {
        return pos;
      }
    }

    return -1;
  }
}

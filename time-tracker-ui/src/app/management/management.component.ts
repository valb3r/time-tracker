import {NestedTreeControl, TreeControl} from '@angular/cdk/tree';
import {Component, Injectable, OnInit} from '@angular/core';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {AdminApiService, GroupDto, GroupDtoWithPath, ProjectDto, UserDto} from "../service/admin-api/admin-api-service";
import {BehaviorSubject, Observable} from "rxjs";

export enum Kind {
  GROUP, PROJECT, USER
}

class GroupNode {
  childrenChange = new BehaviorSubject<GroupNode[]>([]);
  constructor(public id: number, public name: string, public kind: Kind, public expandable: boolean) {
  }
}

@Injectable()
export class GroupDatabase {
  dataChange = new BehaviorSubject<GroupNode[]>([]);
  constructor() {}
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
  database: GroupDatabase = new GroupDatabase();

  public kind = Kind;

  constructor(private api: AdminApiService) {
  }

  ngOnInit(): void {
    this.database.dataChange.subscribe(data => {
      this.dataSource.data = data;
    });

    this.api.ownOwnedGroups().subscribe(res => {
      const data = this.buildFileTree(res);
      this.database.dataChange.next(data);
    });
  }

  buildFileTree(groups: GroupDtoWithPath[]): GroupNode[] {
    let roots: string[] = [];
    let rootNodes: GroupNode[] = [];
    let fromShortest = groups.sort(grp => -grp.path.length);
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
        let rootNode = new GroupNode(grp.entry.id, grp.entry.name, Kind.GROUP, true);
        rootNodes.push(rootNode);
        rootNode.childrenChange.next(this.buildChildren(grp.entry));
      }
    });

    children.forEach((values, parent) => {
      parent.expandable = true;
      parent.childrenChange.next(this.buildFileTree(values))
    });
    return rootNodes;
  };

  addGroup(parent: GroupNode) {

  }

  removeGroup(target: GroupNode) {

  }

  addUser(parent: GroupNode) {

  }

  removeUser(target: GroupNode) {

  }

  addProject(parent: GroupNode) {

  }

  removeProject(target: GroupNode) {

  }

  private buildChildren(node: GroupDto): GroupNode[] {
    let res: GroupNode[] = [];

    if (!!node.users && node.users.length > 0) {
      let users = new GroupNode(node.id, "Users", Kind.USER, true);
      res.push(users);
      users.childrenChange.next(ManagementComponent.buildUsers(node.users));
    }

    if (!!node.projects && node.projects.length > 0) {
      let projects = new GroupNode(node.id, "Projects", Kind.PROJECT, true);
      res.push(projects);
      projects.childrenChange.next(this.buildProjects(node.projects));
    }

    return res;
  }

  private static buildUsers(nodes: UserDto[]): GroupNode[] {
    let res: GroupNode[] = [];
    nodes.forEach(it => {
      let user = new GroupNode(it.id, it.name, Kind.USER, false);
      res.push(user)
    });
    return res;
  }

  private buildProjects(nodes: ProjectDto[]): GroupNode[] {
    let res: GroupNode[] = [];
    nodes.forEach(it => {
      let project = new GroupNode(it.id, it.name, Kind.PROJECT, true);
      res.push(project);

      this.api.projectActors(project.id).subscribe(actors => {
        let mappedActors = actors.map(actor => new GroupNode(actor.id, actor.name, Kind.USER, false));
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

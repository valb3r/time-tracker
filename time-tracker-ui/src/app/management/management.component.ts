import {NestedTreeControl} from '@angular/cdk/tree';
import {Component, OnInit} from '@angular/core';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {AdminApiService, GroupDto, GroupDtoWithPath, ProjectDto, UserDto} from "../service/admin-api/admin-api-service";

export enum Kind {
  GROUP, PROJECT, USER
}

interface GroupNode {
  id: number;
  name: string;
  children?: GroupNode[];
  kind: Kind;
}

@Component({
  selector: 'management-component',
  templateUrl: 'management.component.html',
  styleUrls: ['management.component.scss'],
})
export class ManagementComponent implements OnInit {
  treeControl = new NestedTreeControl<GroupNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<GroupNode>();

  public kind = Kind;

  constructor(private api: AdminApiService) {
  }

  ngOnInit(): void {
    this.api.ownOwnedGroups().subscribe(res => {
      this.dataSource.data = this.buildFileTree(res);
    })
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
        rootNodes.push({
          id: grp.entry.id,
          name: grp.entry.name,
          children: this.buildChildren(grp.entry),
          kind: Kind.GROUP
        })
      }
    });

    children.forEach((values, key) => key.children = key.children.concat(this.buildFileTree(values)));
    return rootNodes;
  };

  hasChild = (_: number, node: GroupDto) => !!node.children && node.children.length > 0;

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
      res.push({id: node.id, name: "Users", children: ManagementComponent.buildUsers(node.users), kind: Kind.USER});
    }

    if (!!node.projects && node.projects.length > 0) {
      res.push({id: node.id, name: "Projects", children: this.buildProjects(node.projects), kind: Kind.PROJECT});
    }

    return res;
  }

  private static buildUsers(nodes: UserDto[]): GroupNode[] {
    let res: GroupNode[] = [];
    nodes.forEach(it => res.push({id: it.id, name: it.name, children: [], kind: Kind.USER}));
    return res;
  }

  private buildProjects(nodes: ProjectDto[]): GroupNode[] {
    let res: GroupNode[] = [];
    nodes.forEach(it => {
      let project = {id: it.id, name: it.name, children: [], kind: Kind.PROJECT};
      res.push(project);

      this.api.projectActors(project.id).subscribe(actors => {
        console.log("Received actors for : " + project.id);
        console.log(actors);
        actors.forEach(actor => project.children.push({id: actor.id, name: actor.name, children: [], kind: Kind.USER}))
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

import {BehaviorSubject} from "rxjs";
import {RoleDetailsDto} from "../service/admin-api/admin-api-service";

export enum Kind {
  GROUP, PROJECT, USER, INHERITED_USER
}

export class GroupNode {
  childrenChange = new BehaviorSubject<GroupNode[]>([]);

  public isSurrogate: boolean = false;

  constructor(public id: number, public path: string, public name: string, public kind: Kind,
              public expandable: boolean, isSurrogate?: boolean, public parent?: GroupNode,
              public sourceId?: number, public roleId?: number) {
    if (isSurrogate) {
      this.isSurrogate = isSurrogate;
    }
  }
}

export class GroupNodesWithParentName {

  constructor(public parent: GroupNode, public nodes: GroupNode[]) {
  }
}

export class ProjectUserRelation {

  constructor(public roleType: string,
              public roleDetails: RoleDetailsDto,
              public userName: string,
              public parent: GroupNode) {
  }
}

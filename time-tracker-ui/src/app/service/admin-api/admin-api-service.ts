import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {forkJoin, Observable} from "rxjs";

export enum Role {
  DEVELOPER = "DEVELOPER",
  MANAGER = "MANAGER"
}

@Injectable({
  providedIn: 'root'
})
export class AdminApiService {

  private base = environment.adminBaseUri;
  private loginUri = this.base + "login";

  private ownOwnedGroupsUri = this.base + "resources/owned-resources";
  private projectActorsUri = this.base + "resources/roles/in/project/";

  private baseGroupUri = this.base + "resources/groups/";
  private baseUserUri = this.base + "resources/users/";
  private baseProjectUri = this.base + "resources/projects/";
  private baseRolesUri = this.base + "resources/roles/";

  constructor(private httpClient: HttpClient) { }

  login(username: string, password: string) {
    return this.httpClient.post(
      this.loginUri,
      {"username": username, "password": password},
      {observe: 'response'}
    );
  }

  ownOwnedGroups() {
    return this.httpClient.get<GroupDtoWithPath[]>(this.ownOwnedGroupsUri);
  }

  projectActors(projectId: number) {
    return this.httpClient.get<ProjectActorDto[]>(this.projectActorsUri + projectId);
  }

  addGroup(parentId: number, group: GroupDto) {
    return this.httpClient.put<GroupDto>(this.baseGroupUri + parentId + "/children", group);
  }

  getGroup(groupId: number) {
    return this.httpClient.get<GroupDto>(this.baseGroupUri + groupId);
  }

  updateGroup(groupId: number, group: GroupDto) {
    return this.httpClient.post<GroupDto>(this.baseGroupUri + groupId, group);
  }

  removeGroup(groupId: number) {
    return this.httpClient.delete(this.baseGroupUri + groupId);
  }

  addUser(parentId: number, user: AddNewOrEditUserDto) {
    return this.httpClient.put<UserDto>(this.baseUserUri + "of_group/" + parentId, user);
  }

  getUser(userId: number) {
    return this.httpClient.get<UserDto>(this.baseUserUri + userId);
  }

  updateUser(userId: number, user: AddNewOrEditUserDto) {
    return this.httpClient.post<UserDto>(this.baseUserUri + userId, user);
  }

  addUsersOrGroupsToProject(role: Role, userGroupsToAddIds: number[], projectId: number, details: RoleDetailsDto) {
    return this.httpClient.post<UserDto>(
      this.baseRolesUri + `${role}/actors/${userGroupsToAddIds}/in/${projectId}`, details
    );
  }

  addUsersOrGroupsToGroup(groupId: number, userOrGroupToAddIds: number[]) {
    return this.httpClient.post<GroupDto>(this.baseGroupUri + `${groupId}/children/users-and-groups/${userOrGroupToAddIds}`, {})
  }

  removeUserCompletely(userId: number) {
    return this.httpClient.delete(this.baseUserUri + userId);
  }

  addProject(parentId: number, project: ProjectCreateOrUpdateDto) {
    return this.httpClient.put<ProjectDto>(this.baseProjectUri + "of_group/" + parentId, project);
  }

  getProject(projectId: number) {
    return this.httpClient.get<ProjectDto>(this.baseProjectUri + projectId);
  }

  updateProject(projectId: number, project: ProjectCreateOrUpdateDto) {
    return this.httpClient.post<ProjectDto>(this.baseProjectUri + projectId, project);
  }

  removeProject(projectId: number) {
    return this.httpClient.delete(this.baseProjectUri + projectId);
  }

  removeUserFromProject(userId: number, projectId: number) {
    let devUri = this.base + `resources/roles/DEVELOPER/actors/${userId}/in/${projectId}`;
    let managerUri = this.base + `resources/roles/MANAGER/actors/${userId}/in/${projectId}`;

    return forkJoin(this.httpClient.delete(devUri), this.httpClient.delete(managerUri), (x, y) => ({x, y}))
  }
}

export class GroupDto {

  id: number;
  name: string;
  children: GroupDto[];
  users: UserDto[];
  projects: ProjectDto[];
}

export class UserDto {
  id: number;
  username: string;
  fullname: string;
}

export interface AddNewOrEditUserDto {
  username: string;
  fullname: string;
  password: string;
  rate: string;
}

export class ProjectDto {
  id: number;
  name: string;
}

export class GroupDtoWithPath {

  path: string;
  entry: GroupDto;
}

export class ProjectActorDto {
  user: UserDto;
  source: GroupDto;
}

export interface ProjectCreateOrUpdateDto {

  code: string;
  name: string;
  activities: Set<string>;
  description: string;
}

export class RoleDetailsDto {
  from: Date;
  to: Date;
  rate: string;
}

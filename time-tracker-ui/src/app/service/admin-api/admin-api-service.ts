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

  removeGroup(groupId: number) {
    return this.httpClient.delete(this.baseGroupUri + groupId);
  }

  addUser(parentId: number, user: AddNewOrEditUserDto) {
    return this.httpClient.put<UserDto>(this.baseUserUri + "of_group/" + parentId, user);
  }

  addUsersOrGroupsToProjectOrGroup(role: Role, userGroupsToAddIds: number[], projectOrGroupId: number) {
    return this.httpClient.post<UserDto>(
      this.baseRolesUri + `${role}/actors/${userGroupsToAddIds}/in/${projectOrGroupId}`, {}
    );
  }

  removeUserCompletely(userId: number) {
    return this.httpClient.delete(this.baseUserUri + userId);
  }

  addProject(parentId: number, project: ProjectCreateOrUpdateDto) {
    return this.httpClient.put<ProjectDto>(this.baseProjectUri + "of_group/" + parentId, project);
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

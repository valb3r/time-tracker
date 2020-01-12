import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {forkJoin, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminApiService {

  private base = environment.adminBaseUri;
  private loginUri = this.base + "login";
  private ownOwnedGroupsUri = this.base + "resources/owned-resources";
  private projectActorsUri = this.base + "resources/roles/in/project/";
  private removeGroupUri = this.base + "resources/groups/";
  private removeUserUri = this.base + "resources/users/";
  private removeProjectUri = this.base + "resources/projects/";

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

  removeGroup(groupId: number) {
    return this.httpClient.delete(this.removeGroupUri + groupId);
  }

  removeUserCompletely(userId: number) {
    return this.httpClient.delete(this.removeUserUri + userId);
  }

  removeProject(projectId: number) {
    return this.httpClient.delete(this.removeProjectUri + projectId);
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
  name: string;
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

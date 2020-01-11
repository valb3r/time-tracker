import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminApiService {

  private base = environment.adminBaseUri;
  private loginUri = this.base + "login";
  private ownOwnedGroupsUri = this.base + "resources/owned-resources";


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


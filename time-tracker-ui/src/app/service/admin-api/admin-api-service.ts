import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {forkJoin, Observable} from "rxjs";
import {Globals} from "../../Globals";

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

  private baseReportsUri = this.base + "resources/reports/";
  private baseReportTemplatesUri = this.base + "resources/reports/templates/";

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  login(username: string, password: string) {
    return this.httpClient.post(
      this.loginUri,
      {"username": username, "password": password},
      {observe: 'response'}
    );
  }

  updatePassword(update: UpdatePasswordDto) {
    return this.httpClient.post(
      this.baseUserUri + "update-password",
      update,
      {observe: 'response'}
    );
  }

  detectManager() {
    this.ownOwnedGroups().subscribe(res => {
      this.globals.isManagerSubject.next(res.length > 0);
    });
  }

  logout() {
    return this.httpClient.delete(this.loginUri, {observe: 'response'});
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

  getMyself() {
    return this.httpClient.get<UserDto>(this.baseUserUri + "me");
  }

  updateUser(userId: number, user: AddNewOrEditUserDto) {
    return this.httpClient.post<UserDto>(this.baseUserUri + userId, user);
  }

  updateSelf(userId: number, user: AddNewOrEditUserDto) {
    return this.httpClient.post<UserDto>(this.baseUserUri + "self-update/" + userId, user);
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

  getRoleDetailsWithType(roleId: number) {
    return this.httpClient.get<RoleDetailsDto>(this.baseRolesUri + roleId);
  }

  updateUsersRoleInProject(roleId: number, details: RoleDetailsDto) {
    return this.httpClient.post<ProjectDto>(this.baseRolesUri + roleId, details);
  }

  removeUserOrGroupFromProject(userOrGroupId: number, projectId: number) {
    let devUri = this.base + `resources/roles/DEVELOPER/actors/${userOrGroupId}/in/${projectId}`;
    let managerUri = this.base + `resources/roles/MANAGER/actors/${userOrGroupId}/in/${projectId}`;

    return forkJoin(this.httpClient.delete(devUri), this.httpClient.delete(managerUri), (x, y) => ({x, y}))
  }

  createReportTemplate(document, description: string) {
    let formData: FormData = new FormData();
    formData.append('file', document);

    return this.httpClient.put(
      this.baseReportTemplatesUri + description,
      formData,
      {
        responseType: 'blob' as 'json'
      })
  }

  getAllReportTemplates() {
    return this.httpClient.get<ReportTemplateDto[]>(this.baseReportTemplatesUri);
  }

  downloadReportTemplate(templateId: number) {
    this.httpClient.get(
      this.baseReportTemplatesUri + templateId,
      {
        observe: 'response',
        responseType: 'blob' as 'json'
      }
    ).subscribe(
      (response: any) => {
        let contentDisposition = response.headers.get('content-disposition');
        let filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
        let dataType = response.type;
        let binaryData = [];
        binaryData.push(response.body);
        let downloadLink = document.createElement('a');
        downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, {type: dataType}));
        downloadLink.setAttribute('download', filename);
        document.body.appendChild(downloadLink);
        downloadLink.click();
      });
  }

  deleteReportTemplate(templateId: number) {
    return this.httpClient.delete(this.baseReportTemplatesUri + templateId);
  }

  createUserBasedReport(userIds: number[], report: CreateReportDto) {
    return this.httpClient.put<ReportDto>(this.baseReportsUri + `users/${userIds}`, report);
  }

  createProjectBasedReport(projectIds: number[], report: CreateReportDto) {
    return this.httpClient.put<ReportDto>(this.baseReportsUri + `projects/${projectIds}`, report);
  }

  downloadReport(reportId: number) {
    this.httpClient.get(
      this.baseReportsUri + reportId,
      {
        observe: 'response',
        responseType: 'blob' as 'json'
      }
    ).subscribe(
      (response: any) => {
        let contentDisposition = response.headers.get('content-disposition');
        let filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
        let dataType = response.type;
        let binaryData = [];
        binaryData.push(response.body);
        let downloadLink = document.createElement('a');
        downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, {type: dataType}));
        downloadLink.setAttribute('download', filename);
        document.body.appendChild(downloadLink);
        downloadLink.click();
      });
  }

  getAllMineReports() {
    return this.httpClient.get<ReportDto[]>(this.baseReportsUri);
  }

  deleteReport(reportId: number) {
    return this.httpClient.delete(this.baseReportsUri + reportId);
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
  roleid?: number;
  roletype?: string;
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
  type?: string;
}

export interface ReportDto {
  id: number;
  job: string;
  createdat: Date;
  from: Date;
  to: Date;
  status: string;
}

export interface ReportTemplateDto {
  id: number;
  description: string;
}

export interface CreateReportDto {
  templateid: number;
  from: Date;
  to: Date;
}

export interface UpdatePasswordDto {
  current: string;
  newpassword: string;
}

import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {plainToClass} from "class-transformer";

@Injectable({
  providedIn: 'root'
})
export class TimeCardApiService {

  private base = environment.trackerBaseUri;
  private listProjects = this.base + "resources/timelogs/projects";

  constructor(private httpClient: HttpClient) { }

  listAvailableProjects() {
    return this.httpClient.get<ProjectWithId[]>(
      this.listProjects,
      {
        observe: 'response',
        withCredentials: true
      }
    )
  }
}


export class ProjectWithId {

  id: number;
  name: string;
  code: string;
  description: string;
}

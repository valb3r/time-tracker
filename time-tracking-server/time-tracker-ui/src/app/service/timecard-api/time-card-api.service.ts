import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TimeCardApiService {

  private base = environment.trackerBaseUri;
  private listProjects = this.base + "resources/timelogs/projects";

  constructor(private httpClient: HttpClient) { }

  listAvailableProjects() {
    return this.httpClient.get(
      this.listProjects,
      {
        observe: 'response',
        withCredentials: true
      }
    );
  }
}

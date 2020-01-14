import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {plainToClass} from "class-transformer";

@Injectable({
  providedIn: 'root'
})
export class TimeCardApiService {

  private base = environment.trackerBaseUri;
  private listProjectsUri = this.base + "resources/timelogs/projects";
  private timeLogsUri = this.base + "resources/timelogs";

  constructor(private httpClient: HttpClient) { }

  listAvailableProjects() {
    return this.httpClient.get<ProjectWithId[]>(this.listProjectsUri);
  }

  uploadTimeCard(timecard: TimeLogUpload) {
    return this.httpClient.put<TimeLogUpload>(this.timeLogsUri, timecard);
  }
}

export enum LocationCode {
  OFFICE,
  HOME_OFFICE,
  ON_CLIENT_SITE,
  EN_ROUTE,
  REMOTE_FIRST,
  UNKNOWN
}

export class ProjectWithId {

  id: number;
  name: string;
  code: string;
  activities: Set<string>;
}

export class LocationUpload {
  code: LocationCode;
}

export class TimeLogUpload {
  id: number;
  tags: string[];
  duration: string;
  description: string;
  location: LocationUpload;
  timestamp: Date;
}

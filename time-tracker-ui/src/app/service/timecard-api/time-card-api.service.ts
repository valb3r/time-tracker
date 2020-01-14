import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
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

  listTimeCards(from: Date, to: Date) {
    return this.httpClient.get<TimeLogUpload[]>(
      this.timeLogsUri,
      {params: new HttpParams().append("from", from.toISOString()).append("to", to.toISOString()) }
      );
  }

  uploadTimeCard(timecard: TimeLogUpload) {
    return this.httpClient.put<TimeLogUpload>(this.timeLogsUri, timecard);
  }
}

export enum LocationCode {
  OFFICE = "OFFICE",
  HOME_OFFICE = "HOME_OFFICE",
  ON_CLIENT_SITE = "ON_CLIENT_SITE",
  EN_ROUTE = "EN_ROUTE",
  REMOTE_FIRST = "REMOTE_FIRST",
  UNKNOWN = "UNKNOWN"
}

export interface ProjectWithId {

  id: number;
  name: string;
  code: string;
  activities: Set<string>;
}

export interface LocationUpload {
  code: LocationCode;
}

export interface TimeLogUpload {
  id?: number;
  projectid: number;
  tags: string[];
  duration: string;
  durationminutes?: number;
  description: string;
  location: string;
  timestamp: string;
}

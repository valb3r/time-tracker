import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {plainToClass} from "class-transformer";
import {ManagedTimeLogCard} from "../admin-api/admin-api-service";

@Injectable({
  providedIn: 'root'
})
export class TimeCardApiService {

  private base = environment.trackerBaseUri;
  private listProjectsUri = this.base + "resources/timelogs/projects";
  private timeLogsUri = this.base + "resources/timelogs";

  private baseManagedTimelogCardsUri = this.base + "resources/timelogs/cards/"
  private baseManagedTimelogImagesUri = this.base + "resources/timelogs/cards/images/"

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

  updateTimeCard(id: number, timecard: TimeLogUpload) {
    return this.httpClient.post<TimeLogUpload>(this.timeLogsUri + "/" + id, timecard);
  }

  deleteTimeCard(id: number) {
    return this.httpClient.delete<TimeLogUpload>(this.timeLogsUri + "/" + id);
  }

  uploadTimeCard(timecard: TimeLogUpload) {
    return this.httpClient.put<TimeLogUpload>(this.timeLogsUri, timecard);
  }

  getTimelogCards(timelogIds: number[]) {
    return this.httpClient.get<TimeLogCard[]>(this.baseManagedTimelogCardsUri + timelogIds);
  }

  getLinkManagedTimelogCardImages(path: string) {
    return this.baseManagedTimelogImagesUri + path;
  }

  deleteTimeCardImage(path: string) {
    return this.httpClient.delete(this.baseManagedTimelogImagesUri + "/" + path);
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

export interface TimeLogCard {
  id: number;
  imageurl: string;
  duration: string;
  timestamp: string;
  durationminutes?: number;
  durationseconds?: number;
}

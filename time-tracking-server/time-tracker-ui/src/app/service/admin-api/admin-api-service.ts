import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TimeCardApiService {

  private base = environment.adminBaseUri;
  private login = this.base + "/login";

  constructor() { }
}

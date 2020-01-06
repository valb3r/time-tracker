import { Injectable } from '@angular/core';
import Env = jasmine.Env;
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private uri = environment.baseUri;

  constructor() { }
}

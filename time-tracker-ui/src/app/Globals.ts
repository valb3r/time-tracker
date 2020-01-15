import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable()
export class Globals {
  isManagerSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  isManager = this.isManagerSubject.asObservable();
}

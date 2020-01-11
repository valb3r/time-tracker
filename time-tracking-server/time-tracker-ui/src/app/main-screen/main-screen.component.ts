import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";

@Component({
  selector: 'main-screen',
  templateUrl: './main-screen.component.html',
  styleUrls: ['./main-screen.component.scss']
})
export class MainScreenComponent implements OnInit {

  mobileQuery: MediaQueryList;

  fillerNav: Nav[] = [
    new Nav("My profile", "my-profile"),
    new Nav("My timecards", "my-timecards"),
    new Nav("Management", "management"),
    new Nav("Reporting", "reporting"),
    new Nav("Change password", "change-password"),
    new Nav("Logout", "logout")
  ];

  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addEventListener("change", this._mobileQueryListener);
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeEventListener("change", this._mobileQueryListener);
  }
}

class Nav {

  public destination: string;
  constructor(public label: string, destination: string) {
    this.destination = "./" + destination;
  }
}

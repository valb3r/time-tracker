import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {Globals} from "../Globals";
import {NavigationStart, Router} from "@angular/router";
import {AdminApiService} from "../service/admin-api/admin-api-service";

@Component({
  selector: 'main-screen',
  templateUrl: './main-screen.component.html',
  styleUrls: ['./main-screen.component.scss']
})
export class MainScreenComponent implements OnInit {

  mobileQuery: MediaQueryList;

  fillerNav: Nav[] = [
    new Nav("My profile", "my-profile", false),
    new Nav("My timecards", "my-timecards", false),
    new Nav("Management", "management", true),
    new Nav("Reporting", "reports", true),
    new Nav("Change password", "change-password", false),
    new Nav("Logout", "logout", false)
  ];

  filteredNav: Nav[];

  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
              private globals: Globals, private api: AdminApiService) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addEventListener("change", this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.globals.isManager.subscribe(res =>
      this.filteredNav = res ? this.fillerNav : this.fillerNav.filter(res => !res.managerRoleRequired)
    );
  };

  ngOnDestroy(): void {
    this.mobileQuery.removeEventListener("change", this._mobileQueryListener);
  }
}

class Nav {
  public destination: string;
  constructor(public label: string, destination: string, public managerRoleRequired: boolean) {
    this.destination = "./" + destination;
  }
}

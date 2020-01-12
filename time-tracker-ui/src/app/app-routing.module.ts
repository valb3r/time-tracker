import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {TimeCardCalendarComponent} from "./time-card-calendar/time-card-calendar.component";
import {MainScreenComponent} from "./main-screen/main-screen.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {ReportsComponent} from "./reports/reports.component";
import {LogoutComponent} from "./logout/logout.component";
import {ManagementComponent} from "./management/management.component";


const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'main-screen', component: MainScreenComponent, children: [
      {path: 'my-timecards', component: TimeCardCalendarComponent},
      {path: 'my-profile', component: MyProfileComponent},
      {path: 'management', component: ManagementComponent},
      {path: 'reports', component: ReportsComponent},
      {path: 'change-password', component: ChangePasswordComponent},
      {path: 'logout', component: LogoutComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

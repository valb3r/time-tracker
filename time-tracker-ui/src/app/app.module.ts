import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/moment';
import * as moment from 'moment';
import {TimeCardCalendarComponent} from './time-card-calendar/time-card-calendar.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatDividerModule} from "@angular/material/divider";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {LoginComponent} from './login/login.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ErrorInterceptorService} from "./service/interceptor/error-interceptor-service";
import {TimeCardEditComponent} from './time-card-edit/time-card-edit.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatSelectModule} from "@angular/material/select";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatMomentDateModule} from "@angular/material-moment-adapter";
import {MAT_DATE_FORMATS} from "@angular/material/core";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MainScreenComponent} from './main-screen/main-screen.component';
import {MyProfileComponent} from './my-profile/my-profile.component';
import {ChangePasswordComponent} from './change-password/change-password.component';
import {ReportsComponent} from './reports/reports.component';
import {LogoutComponent} from './logout/logout.component';
import {ManagementComponent} from './management/management.component';
import {MatTreeModule} from "@angular/material/tree";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import { AddGroupDialogComponent } from './management/dialogs/add-group-dialog/add-group-dialog.component';

export function momentAdapterFactory() {
  return adapterFactory(moment);
}

export const AppDateFormats = {
  parse: {
    dateInput: 'YYYY-MM-DD',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  }
};

@NgModule({
  declarations: [
    AppComponent,
    TimeCardCalendarComponent,
    LoginComponent,
    TimeCardEditComponent,
    MainScreenComponent,
    MyProfileComponent,
    ChangePasswordComponent,
    ReportsComponent,
    LogoutComponent,
    ManagementComponent,
    AddGroupDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FlexLayoutModule,
    HttpClientModule,
    MatMomentDateModule,
    CalendarModule.forRoot({provide: DateAdapter, useFactory: momentAdapterFactory}),
    MatDividerModule,
    MatIconModule,
    MatCardModule,
    MatTreeModule,
    MatCheckboxModule,
    MatProgressBarModule,
    MatButtonModule,
    MatMenuModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    FormsModule
  ],
  entryComponents: [AddGroupDialogComponent],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true},
    {provide: MAT_DATE_FORMATS, useValue: AppDateFormats}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

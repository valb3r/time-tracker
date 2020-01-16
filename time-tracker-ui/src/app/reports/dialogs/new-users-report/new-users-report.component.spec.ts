import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewUsersReportComponent } from './new-users-report.component';

describe('NewUsersReportComponent', () => {
  let component: NewUsersReportComponent;
  let fixture: ComponentFixture<NewUsersReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewUsersReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewUsersReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

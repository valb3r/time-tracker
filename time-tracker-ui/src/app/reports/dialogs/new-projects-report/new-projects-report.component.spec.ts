import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewProjectsReportComponent } from './new-projects-report.component';

describe('NewProjectsReportComponent', () => {
  let component: NewProjectsReportComponent;
  let fixture: ComponentFixture<NewProjectsReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewProjectsReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewProjectsReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

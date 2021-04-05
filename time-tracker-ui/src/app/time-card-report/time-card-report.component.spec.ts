import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCardReportComponent } from './time-card-report.component';

describe('TimeCardReportComponent', () => {
  let component: TimeCardReportComponent;
  let fixture: ComponentFixture<TimeCardReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCardReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCardReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

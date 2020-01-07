import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCardCalendarComponent } from './time-card-calendar.component';

describe('TimeCardInputComponent', () => {
  let component: TimeCardCalendarComponent;
  let fixture: ComponentFixture<TimeCardCalendarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCardCalendarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCardCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCardInputComponent } from './time-card-input.component';

describe('TimeCardInputComponent', () => {
  let component: TimeCardInputComponent;
  let fixture: ComponentFixture<TimeCardInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCardInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCardInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

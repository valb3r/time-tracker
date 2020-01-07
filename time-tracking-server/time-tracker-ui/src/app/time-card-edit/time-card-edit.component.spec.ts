import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCardEditComponent } from './time-card-edit.component';

describe('TimeCardEditComponent', () => {
  let component: TimeCardEditComponent;
  let fixture: ComponentFixture<TimeCardEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCardEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCardEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

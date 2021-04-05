import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCardListComponent } from './time-card-list.component';

describe('TimeCardListComponent', () => {
  let component: TimeCardListComponent;
  let fixture: ComponentFixture<TimeCardListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCardListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

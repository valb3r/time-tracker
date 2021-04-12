import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCardAggregateStatsComponent } from './time-card-aggregate-stats.component';

describe('TimeCardAggregateStatsComponent', () => {
  let component: TimeCardAggregateStatsComponent;
  let fixture: ComponentFixture<TimeCardAggregateStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCardAggregateStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCardAggregateStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

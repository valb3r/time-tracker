import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewProjectComponent } from './review-project.component';

describe('ReviewProjectComponent', () => {
  let component: ReviewProjectComponent;
  let fixture: ComponentFixture<ReviewProjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewProjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

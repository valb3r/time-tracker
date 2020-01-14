import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrEditProjectDialogComponent } from './add-or-edit-project-dialog.component';

describe('AddProjectDialogComponent', () => {
  let component: AddOrEditProjectDialogComponent;
  let fixture: ComponentFixture<AddOrEditProjectDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddOrEditProjectDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOrEditProjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

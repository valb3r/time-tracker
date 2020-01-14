import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserOrGroupToProjectDialogComponent } from './add-user-or-group-to-project-dialog.component';

describe('AddUserOrGroupToProjectDialogComponent', () => {
  let component: AddUserOrGroupToProjectDialogComponent;
  let fixture: ComponentFixture<AddUserOrGroupToProjectDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserOrGroupToProjectDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserOrGroupToProjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserOrProjectToProjectOrGroupDialogComponent } from './add-user-or-project-to-project-or-group-dialog.component';

describe('AddUserOrProjectToProjectOrGroupDialogComponent', () => {
  let component: AddUserOrProjectToProjectOrGroupDialogComponent;
  let fixture: ComponentFixture<AddUserOrProjectToProjectOrGroupDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserOrProjectToProjectOrGroupDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserOrProjectToProjectOrGroupDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

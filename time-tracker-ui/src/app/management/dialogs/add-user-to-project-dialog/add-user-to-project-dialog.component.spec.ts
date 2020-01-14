import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserToProjectDialogComponent } from './add-user-to-project-dialog.component';

describe('AddUserToProjectDialogComponent', () => {
  let component: AddUserToProjectDialogComponent;
  let fixture: ComponentFixture<AddUserToProjectDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserToProjectDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserToProjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

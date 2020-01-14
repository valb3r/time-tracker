import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrEditUserDialogComponent } from './add-or-edit-user-dialog.component';

describe('AddUserDialogComponent', () => {
  let component: AddOrEditUserDialogComponent;
  let fixture: ComponentFixture<AddOrEditUserDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddOrEditUserDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOrEditUserDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

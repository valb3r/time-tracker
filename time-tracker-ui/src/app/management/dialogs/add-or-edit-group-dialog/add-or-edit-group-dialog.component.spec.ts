import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrEditGroupDialogComponent } from './add-or-edit-group-dialog.component';

describe('AddGroupDialogComponent', () => {
  let component: AddOrEditGroupDialogComponent;
  let fixture: ComponentFixture<AddOrEditGroupDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddOrEditGroupDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOrEditGroupDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

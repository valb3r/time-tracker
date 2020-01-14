import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserOrGroupToGroupDialogComponent } from './add-user-or-group-to-group-dialog.component';

describe('AddUserOrGroupToGroupDialogComponent', () => {
  let component: AddUserOrGroupToGroupDialogComponent;
  let fixture: ComponentFixture<AddUserOrGroupToGroupDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserOrGroupToGroupDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserOrGroupToGroupDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

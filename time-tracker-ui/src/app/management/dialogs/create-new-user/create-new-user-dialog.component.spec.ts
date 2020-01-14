import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewUserDialogComponent } from './create-new-user-dialog.component';

describe('CreateNewUserComponent', () => {
  let component: CreateNewUserDialogComponent;
  let fixture: ComponentFixture<CreateNewUserDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewUserDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewUserDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

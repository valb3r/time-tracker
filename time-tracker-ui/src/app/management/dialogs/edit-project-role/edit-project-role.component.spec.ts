import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProjectRoleComponent } from './edit-project-role.component';

describe('EditProjectRoleComponent', () => {
  let component: EditProjectRoleComponent;
  let fixture: ComponentFixture<EditProjectRoleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditProjectRoleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProjectRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

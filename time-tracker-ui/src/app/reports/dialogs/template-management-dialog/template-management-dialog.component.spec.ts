import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateManagementDialogComponent } from './template-management-dialog.component';

describe('TemplateManagementDialogComponent', () => {
  let component: TemplateManagementDialogComponent;
  let fixture: ComponentFixture<TemplateManagementDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateManagementDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateManagementDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

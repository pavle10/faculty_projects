import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperaterMenuComponent } from './operater-menu.component';

describe('OperaterMenuComponent', () => {
  let component: OperaterMenuComponent;
  let fixture: ComponentFixture<OperaterMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperaterMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperaterMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveAirplaneComponent } from './remove-airplane.component';

describe('RemoveAirplaneComponent', () => {
  let component: RemoveAirplaneComponent;
  let fixture: ComponentFixture<RemoveAirplaneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveAirplaneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveAirplaneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveFlightComponent } from './remove-flight.component';

describe('RemoveFlightComponent', () => {
  let component: RemoveFlightComponent;
  let fixture: ComponentFixture<RemoveFlightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveFlightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

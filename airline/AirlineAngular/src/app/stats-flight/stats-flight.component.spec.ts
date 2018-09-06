import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatsFlightComponent } from './stats-flight.component';

describe('StatsFlightComponent', () => {
  let component: StatsFlightComponent;
  let fixture: ComponentFixture<StatsFlightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatsFlightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatsFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

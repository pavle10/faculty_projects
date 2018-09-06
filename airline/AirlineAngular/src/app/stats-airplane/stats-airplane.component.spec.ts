import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatsAirplaneComponent } from './stats-airplane.component';

describe('StatsAirplaneComponent', () => {
  let component: StatsAirplaneComponent;
  let fixture: ComponentFixture<StatsAirplaneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatsAirplaneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatsAirplaneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

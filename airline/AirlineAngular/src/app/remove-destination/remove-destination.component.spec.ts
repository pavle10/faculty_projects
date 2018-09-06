import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveDestinationComponent } from './remove-destination.component';

describe('RemoveDestinationComponent', () => {
  let component: RemoveDestinationComponent;
  let fixture: ComponentFixture<RemoveDestinationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveDestinationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveDestinationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

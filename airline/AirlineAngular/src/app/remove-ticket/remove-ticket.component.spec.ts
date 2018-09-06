import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveTicketComponent } from './remove-ticket.component';

describe('RemoveTicketComponent', () => {
  let component: RemoveTicketComponent;
  let fixture: ComponentFixture<RemoveTicketComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveTicketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

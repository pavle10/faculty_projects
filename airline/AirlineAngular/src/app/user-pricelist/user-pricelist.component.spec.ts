import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPricelistComponent } from './user-pricelist.component';

describe('UserPricelistComponent', () => {
  let component: UserPricelistComponent;
  let fixture: ComponentFixture<UserPricelistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPricelistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPricelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

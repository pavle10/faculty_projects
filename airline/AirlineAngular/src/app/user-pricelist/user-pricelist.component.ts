import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location} from '@angular/common';
import { Price } from '../Price';

@Component({
  selector: 'app-user-pricelist',
  templateUrl: './user-pricelist.component.html',
  styleUrls: ['./user-pricelist.component.css']
})
export class UserPricelistComponent implements OnInit {

  prices: Price[] = [];

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
    this.adminService.currentPrices().subscribe(data => {
      let i = 0;
      for (const price in data) {
        this.prices[i++] = data[price];
      }
    });
  }


  back() {
    this.location.back();
  }

}

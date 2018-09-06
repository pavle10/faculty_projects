import { Component, OnInit } from '@angular/core';
import { Flight } from '../Flight';
import { Price } from '../Price';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent implements OnInit {

  flights: Flight[] = [];
  prices: Price[] = [];

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getFlights().subscribe(data => {
      let i = 0;
      for (const flight in data) {
        this.flights[i++] = data[flight];
      }
    });
    this.adminService.getPrices().subscribe(data => {
      let i = 0;
      for (const price in data) {
        this.prices[i++] = data[price];
      }
    });
  }

  changePrice(fnumber: any) {
    const price = document.getElementById(fnumber) as HTMLInputElement;
    if (this.checkField(price.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    if (this.checkValue(price.value)) {
      alert('Nevalidan unos cene!');
      return ;
    }
    this.adminService.changePrice(fnumber, price.value).subscribe(data => {
      if (data['status'] === 'FAIL') {
        alert('Neuspesna promena cene!');
      } else {
        alert('Uspesna promena cene!');
      }
    });
  }

  checkField(price: string): boolean {
    return price === '';
  }

  checkValue(price: string): boolean {
    return Number.isNaN(Number(price));
  }

  back() {
    this.location.back();
}

}

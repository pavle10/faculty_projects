import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Flight } from '../Flight';
import { Location } from '@angular/common';
import {fn} from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-buy-ticket',
  templateUrl: './buy-ticket.component.html',
  styleUrls: ['./buy-ticket.component.css']
})
export class BuyTicketComponent implements OnInit {

  flights: Flight[] = [];

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
    this.adminService.currentFlights().subscribe(data => {
      let i = 0;
      for (const flight in data) {
          this.flights[i++] = data[flight];
      }
    });
  }

  buy(fnumber: any) {
    const cardNum = document.getElementById(fnumber) as HTMLInputElement;
    if (this.checkField(cardNum.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    if (this.checkValid(cardNum.value)) {
      alert('Nevalidan unos!');
      return ;
    }
    this.adminService.buyTicket(fnumber, cardNum.value).subscribe(data => {
      if (data['status'] === 'FAIL') {
        alert('Neuspesn kupovina!');
      } else {
        alert('Uspesna kupovina!');
      }
    });
  }

  checkField(fnumber: string): boolean {
    return fnumber === '';
  }

  checkValid(fnumber: string): boolean {
    return Number.isNaN(Number(fnumber));
  }

  back() {
    this.location.back();
  }

}

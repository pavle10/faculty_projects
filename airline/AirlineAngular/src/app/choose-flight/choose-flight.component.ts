import { Component, OnInit } from '@angular/core';
import { Ticket } from '../Ticket';
import { Location } from '@angular/common';
import { Router} from '@angular/router';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-choose-flight',
  templateUrl: './choose-flight.component.html',
  styleUrls: ['./choose-flight.component.css']
})
export class ChooseFlightComponent implements OnInit {

  tickets: Ticket[] = [];

  constructor(private adminService: AdminService, private location: Location,
              private router: Router) { }

  ngOnInit() {
    this.adminService.getBTickets().subscribe(data => {
      let i = 0;
      for (const ticket in data) {
        if (data[ticket].number !== 0) {
          this.tickets[i++] = data[ticket];
        }

      }
    });
  }

  chooseTicket(id: string) {
    sessionStorage.setItem('ticket', id);
    this.router.navigate(['./chooseSeat']);
  }

  back() {
    this.location.back();
  }

}

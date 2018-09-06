import { Component, OnInit } from '@angular/core';
import { Location} from '@angular/common';
import { AdminService } from '../admin.service';
import { Ticket } from '../Ticket';

@Component({
  selector: 'app-remove-ticket',
  templateUrl: './remove-ticket.component.html',
  styleUrls: ['./remove-ticket.component.css']
})
export class RemoveTicketComponent implements OnInit {

  tickets: Ticket[] = [];

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
    this.adminService.getTickets().subscribe(data => {
      let i = 0;
      for (const ticket in data) {
        this.tickets[i++] = data[ticket];
      }
    });
  }

  returnTicket(id: String) {
    this.adminService.returnTickets(id).subscribe(data => {
      alert(data['status']);
      this.back();
    });
  }

  back() {
    this.location.back();
  }

}

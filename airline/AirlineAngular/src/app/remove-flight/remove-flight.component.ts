import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-remove-flight',
  templateUrl: './remove-flight.component.html',
  styleUrls: ['./remove-flight.component.css']
})
export class RemoveFlightComponent implements OnInit {

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
  }

  removeFlight() {
    const fnumber = document.getElementById('fnumber') as HTMLInputElement;
    if (this.checkField(fnumber.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    this.adminService.removeFlight(fnumber.value).subscribe(data => {
      if (data['status'] === 'FAIL') {
        alert('Neuspesno brisanje leta!');
      } else {
        alert('Uspesno brisanje leta!');
      }
    });
  }

  checkField(fnumber: string): boolean {
    return fnumber === '';
  }

  back() {
    this.location.back();
  }

}

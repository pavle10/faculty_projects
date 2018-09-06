import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location} from '@angular/common';

@Component({
  selector: 'app-add-destination',
  templateUrl: './add-destination.component.html',
  styleUrls: ['./add-destination.component.css']
})
export class AddDestinationComponent implements OnInit {

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
  }

  addDest() {
    const code = document.getElementById('code') as HTMLInputElement;
    const city = document.getElementById('city') as HTMLInputElement;
    const country = document.getElementById('country') as HTMLInputElement;
    const timezone = document.getElementById('timezone') as HTMLInputElement;
    if (!this.checkFields(code.value, city.value, country.value, timezone.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    if (!this.checkTimeZone(timezone.value)) {
      alert('Ne validan unos vremenske zone!');
      return ;
    }
    this.adminService.addDest(code.value, city.value, country.value, timezone.value)
      .subscribe(data => {
        if (data['status'] === 'FAIL') {
          alert('Neuspesno dodavanje!');
        } else if (data['status'] === 'CODE') {
          alert('Postoji destinacija sa unetim kodom!');
        } else {
          alert('Uspesno dodavanje!');
        }
      });
  }

  checkFields(code: string, city: string, country: string, timezone: string): boolean {
    if (code === '' || city === '' || country === '' || timezone === '') {
      return false;
    }
    return true;
  }

  checkTimeZone(timezone: string): boolean {
    if (Number.isNaN(Number(timezone))) {
      return false;
    }
    if (Number(timezone) > 14 || Number(timezone) < -12) {
      return false;
    }
    return true;
  }

  back() {
    this.location.back();
  }

}

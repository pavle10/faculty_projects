import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-remove-airplane',
  templateUrl: './remove-airplane.component.html',
  styleUrls: ['./remove-airplane.component.css']
})
export class RemoveAirplaneComponent implements OnInit {

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
  }

  removeAirplane() {
    const regnum = document.getElementById('regnum') as HTMLInputElement;
    if (this.checkField(regnum.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    this.adminService.removeAirplane(regnum.value)
      .subscribe(data => {
        if (data['status'] === 'FAIL') {
          alert('Neuspesno brisanje!');
        } else {
          alert('Uspesno brisanje!');
        }
      });
  }

  checkField(regnum: string): boolean {
    return regnum === '';
  }

  back() {
    this.location.back();
  }

}

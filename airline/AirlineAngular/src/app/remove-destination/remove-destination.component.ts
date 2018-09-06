import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-remove-destination',
  templateUrl: './remove-destination.component.html',
  styleUrls: ['./remove-destination.component.css']
})
export class RemoveDestinationComponent implements OnInit {

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
  }

  removeDestination() {
    const code = document.getElementById('code') as HTMLInputElement;
    if (this.checkField(code.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    this.adminService.removeDest(code.value)
      .subscribe(data => {
        if (data['status'] === 'FAIL') {
          alert('Neuspesno brisanje!');
        } else {
          alert('Uspesno brisanje!');
        }
      });
  }

  checkField(code: string): boolean {
    return code === '';
  }

  back() {
    this.location.back();
  }

}

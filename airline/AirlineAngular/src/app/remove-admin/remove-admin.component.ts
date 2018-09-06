import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-remove-admin',
  templateUrl: './remove-admin.component.html',
  styleUrls: ['./remove-admin.component.css']
})
export class RemoveAdminComponent implements OnInit {

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
  }

  removeAdmin() {
    const username = document.getElementById('username') as HTMLInputElement;
    if (!this.checkField(username.value)) {
      alert('Sva polja moraju biti popunjena');
      return ;
    }
    this.adminService.removeAdmin(username.value)
      .subscribe(data => {
        if (data['status'] === 'FAIL') {
          alert('Ne postoji uneti korisnik');
        } else if (data['status'] === 'TYPE') {
          alert('Mogu se brisati samo admini i operateri!');
        } else {
          alert('Uspesno brisanje!');
        }
      });
  }

  checkField(username: string): boolean {
    if (username === '') {
      return false;
    }
    return true;
  }

  back() {
    this.location.back();
  }

}

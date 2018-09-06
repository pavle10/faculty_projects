import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-airplane',
  templateUrl: './add-airplane.component.html',
  styleUrls: ['./add-airplane.component.css']
})
export class AddAirplaneComponent implements OnInit {

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
  }

  addAirplane() {
    const regnum = document.getElementById('regnum') as HTMLInputElement;
    const brand = document.getElementById('brand') as HTMLInputElement;
    const model = document.getElementById('model') as HTMLInputElement;
    const seatnum = document.getElementById('seatnum') as HTMLInputElement;
    const cols = document.getElementById('cols') as HTMLInputElement;
    const rows = document.getElementById('rows') as HTMLInputElement;
    const colsnum = document.getElementById('colsnum') as HTMLInputElement;
    const distance = document.getElementById('distance') as HTMLInputElement;
    const speed = document.getElementById('speed') as HTMLInputElement;
    if (!this.checkFields(regnum.value, brand.value, model.value, seatnum.value, cols.value,
        rows.value, colsnum.value, distance.value, speed.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    if (!this.checkNums(seatnum.value, cols.value, rows.value, colsnum.value, distance.value, speed.value)) {
      alert('Nevalidan unos brojcanih polja!');
      return ;
    }
    if (!this.checkSeatsNum(seatnum.value, cols.value, rows.value, colsnum.value)) {
      alert('Nevalidan unos broja sedista!');
      return ;
    }
    this.adminService.addAirplane(regnum.value, brand.value, model.value,
                                  seatnum.value, cols.value, rows.value,
                                  colsnum.value, distance.value, speed.value)
      .subscribe(data => {
        if (data['status'] === 'FAIL') {
          alert('Neuspesno dodavanje!');
        } else if (data['status'] === 'REG') {
          alert('Postoji avion sa unetom registracijom!');
        } else {
          alert('Uspesno dodavanje!');
        }
      });
  }

  checkFields(regnum: string, brand: string, model: string, seatnum: string,
              cols: string, rows: string, colsnum: string, distance: string, speed: string): boolean {
    if (regnum === '') {
      return false;
    } else if (brand === '') {
      return false;
    } else  if (model === '') {
      return false;
    } else if (seatnum === '') {
      return false;
    } else if (cols === '') {
      return false;
    } else if (rows === '') {
      return false;
    } else  if (colsnum === '') {
      return false;
    } else if (distance === '') {
      return false;
    } else if (speed === '') {
      return false;
    }
    return true;
  }

  checkNums(seatnum: string, cols: string, rows: string,
            colsnum: string, distance: string, speed: string): boolean {
    if (Number.isNaN(Number(seatnum))) {
      return false;
    } else if (Number.isNaN(Number(cols))) {
      return false;
    } else if (Number.isNaN(Number(rows))) {
      return false;
    } else if (Number.isNaN(Number(colsnum))) {
      return false;
    } else if (Number.isNaN(Number(distance))) {
      return false;
    } else if (Number.isNaN(Number(speed))) {
      return false;
    }
    return true;
  }

  checkSeatsNum(seatnum: string, cols: string, rows: string, colsnum: string): boolean {
    const sn = Number(seatnum);
    const c = Number(cols);
    const r = Number(rows);
    const cs = Number(colsnum);
    return ((cs * c) * r === sn);
  }

  back() {
    this.location.back();
  }
}

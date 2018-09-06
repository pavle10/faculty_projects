import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  constructor(private location: Location, private router: Router, private adminService: AdminService) { }

  ngOnInit() {
  }

  getHistory() {
    const id = document.getElementById('userId') as HTMLInputElement;
    if (this.checkField(id.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    if (this.checkId(id.value)) {
      alert('Nevalidan id!');
      return ;
    }
    sessionStorage.setItem('choosenId', id.value);
    this.router.navigate(['./history']);
  }

  getFlightStat() {
    const fnumber = document.getElementById('fnumber') as HTMLInputElement;
    if (this.checkField(fnumber.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    sessionStorage.setItem('fnumberId', fnumber.value);
    this.router.navigate(['./flightStats']);
  }

  getAirplaneStat() {
    const anumber = document.getElementById('anumber') as HTMLInputElement;
    if (this.checkField(anumber.value)) {
      alert('Polje mora biti popunjeno!');
      return ;
    }
    sessionStorage.setItem('anumber', anumber.value);
    this.router.navigate(['./airplaneStats']);
  }

  checkField(temp: string): boolean {
    return temp === '';
  }

  checkId(id: string): boolean {
    return Number.isNaN(Number(id));
  }

  back() {
    this.location.back();
  }

}

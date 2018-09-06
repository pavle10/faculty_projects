import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AdminService } from '../admin.service';
import { FlightStats } from '../FlightStats';

@Component({
  selector: 'app-stats-flight',
  templateUrl: './stats-flight.component.html',
  styleUrls: ['./stats-flight.component.css']
})
export class StatsFlightComponent implements OnInit {

  flightStats: FlightStats[] = [];
  fnumberId: string;

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
    this.fnumberId = sessionStorage.getItem('fnumberId');
    this.adminService.getFlightStats(this.fnumberId).subscribe(data => {
      if (data === null) {
        this.back();
      }
      let i = 0;
      for (const f in data) {
        this.flightStats[i++] = data[f];
      }
    });
  }

  back() {
    sessionStorage.removeItem('fnumberId');
    this.location.back();
  }

}

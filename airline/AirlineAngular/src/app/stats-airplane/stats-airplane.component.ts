import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AdminService } from '../admin.service';
import { AirplaneStats } from '../AirplaneStats';

@Component({
  selector: 'app-stats-airplane',
  templateUrl: './stats-airplane.component.html',
  styleUrls: ['./stats-airplane.component.css']
})
export class StatsAirplaneComponent implements OnInit {

  airplaneStats: AirplaneStats[] = [];

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
    const anumber = sessionStorage.getItem('anumber');
    this.adminService.getAirplaneStats(anumber).subscribe(data => {
      if (data === null) {
        this.back();
      }
      let i = 0;
      for (const air in data) {
        this.airplaneStats[i++] = data[air];
      }
    });
  }

  back() {
    sessionStorage.removeItem('anumber');
    this.location.back();
  }

}

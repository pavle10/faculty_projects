import { Component, OnInit } from '@angular/core';
import { History } from '../History';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  totalDistance: number;
  history: History[] = [];
  name: string;
  email: string;

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
    const type = Number(sessionStorage.getItem('type'));
    this.totalDistance = 0;
    if (type === 3) {
      this.name = sessionStorage.getItem('name');
      this.email = sessionStorage.getItem('user');
      const userId = Number(sessionStorage.getItem('id'));
      this.adminService.getHistory(userId).subscribe(data => {
        let i = 0;
        for (const h in data) {
          this.history[i++] = data[h];
          this.totalDistance += data[h].distance;
        }
      });
    } else {
      const id = Number(sessionStorage.getItem('choosenId'));
      this.adminService.getUser(id).subscribe(data => {
        if (data['type'] !== '3') {
          this.back();
        }
        if (data['status'] !== 'FAIL') {
          this.name = data['name'];
          this.email = data['email'];
        }
        this.adminService.getHistory(id).subscribe(history => {
          let i = 0;
          for (const h in history) {
            this.history[i++] = history[h];
            this.totalDistance += history[h].distance;
          }
        });
      });
    }
  }

  back() {
    sessionStorage.removeItem('choosenId');
    this.location.back();
  }

}

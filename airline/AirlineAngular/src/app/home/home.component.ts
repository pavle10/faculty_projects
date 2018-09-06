import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    if (sessionStorage.getItem('remembered') != null) {
      const type = sessionStorage.getItem('type');
      if (type === '1') {
        this.router.navigate(['/adminMenu']);
      } else if (type === '2') {
        this.router.navigate(['/operaterMenu']);
      } else {
        this.router.navigate(['/userMenu']);
      }
    }
  }
}

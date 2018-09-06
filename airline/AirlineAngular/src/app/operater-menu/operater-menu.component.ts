import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-operater-menu',
  templateUrl: './operater-menu.component.html',
  styleUrls: ['./operater-menu.component.css']
})
export class OperaterMenuComponent implements OnInit {

  currUser: String;

  constructor(private router: Router) { }

  ngOnInit() {
    if ((sessionStorage.getItem('user') == null)) {
      this.router.navigate(['./']);
    }
    this.currUser = sessionStorage.getItem('user');
  }

  logout() {
    this.currUser = '';
    sessionStorage.removeItem('remembered');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('id');
    sessionStorage.removeItem('type');
    this.router.navigate(['./']);
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css']
})
export class UserMenuComponent implements OnInit {

  currUser: String;

  constructor(private router: Router) { }

  ngOnInit() {
    if ((sessionStorage.getItem('user') == null)) {
      this.router.navigate(['./']);
    }
    this.currUser = sessionStorage.getItem('name');
  }

  logout() {
    this.currUser = '';
    sessionStorage.removeItem('remembered');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('id');
    sessionStorage.removeItem('type');
    sessionStorage.removeItem('name');
    this.router.navigate(['./']);
  }
}

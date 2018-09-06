import { Component, OnInit } from '@angular/core';
import {LoginService } from '../login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) { }

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

  login() {
    const usernameField = document.getElementById('username') as HTMLInputElement;
    const passwordField = document.getElementById('password') as HTMLInputElement;
    const cb = document.getElementById('checkBox') as HTMLInputElement;
    if (!this.checkFields(usernameField.value, passwordField.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    this.loginService.login(usernameField.value, passwordField.value)
      .subscribe(data => {
        console.log(data);
        const type = Number(data['type']);
        const reg = Number(data['reg']);
        if (cb.checked === true) {
          sessionStorage.setItem('remembered', '1');
        }
        if (type === 1) {
          alert('Zdravo admin!');
          sessionStorage.setItem('user', data['user']);
          sessionStorage.setItem('id', data['id']);
          sessionStorage.setItem('type', data['type']);
          if (reg === 1) {
            this.router.navigate(['./adminMenu']);
          } else {
            this.router.navigate(['./changePass']);
          }
        } else if (type === 2) {
          alert('Zdravo operater!');
          sessionStorage.setItem('user', data['user']);
          sessionStorage.setItem('id', data['id']);
          sessionStorage.setItem('type', data['type']);
          if (reg === 1) {
            this.router.navigate(['./operaterMenu']);
          } else {
            this.router.navigate(['./changePass']);
          }
        } else if (type === 3) {
          alert('Zdravo korisnik!');
          sessionStorage.setItem('user', data['user']);
          sessionStorage.setItem('id', data['id']);
          sessionStorage.setItem('type', data['type']);
          sessionStorage.setItem('name', data['name']);
          if (reg === 1) {
            this.router.navigate(['./userMenu']);
          } else {
            alert('Niste aktivirali nalog! Molimo Vas proverite mail!');
          }
        } else {
          alert('Greska pri logovanju!');
        }
      });
  }

  checkFields(username: string, password: string): boolean {
    if (username === '' || password === '') {
      return false;
    }
    return true;
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-change-pass',
  templateUrl: './change-pass.component.html',
  styleUrls: ['./change-pass.component.css']
})
export class ChangePassComponent implements OnInit {

  constructor(private router: Router, private adminService: AdminService) { }

  ngOnInit() {
  }

  changePass() {
    const password = document.getElementById('password') as HTMLInputElement;
    const cpassword = document.getElementById('cpassword') as HTMLInputElement;
    const id = sessionStorage.getItem('id');
    if (!this.checkFields(password.value, cpassword.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    if (!this.checkPasswords(password.value, cpassword.value)) {
      alert('Niste dobro ponovili password!');
      return ;
    }
    this.adminService.changePass(password.value, id)
      .subscribe(data => {
        console.log(data['status']);
        if (data['status'] === 'OK') {
          alert('Uspesna promena password-a!');
          const type = Number(sessionStorage.getItem('type'));
          if (type === 1) {
            this.router.navigate(['./adminMenu']);
          } else if (type === 2) {
            this.router.navigate(['./operaterMenu']);
          }
        } else {
          alert('Neuspesna promena password-a! Probaj ponovo!');
        }
      });
  }

  checkFields(password: string, cpassword: string): boolean {
    if (password === '' || cpassword === '') {
      return false;
    }
    return true;
  }

  checkPasswords(password: string, cpassword: string): boolean {
    if (password === cpassword) {
      return true;
    }
    return false;
  }

}

import { Component, OnInit } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }
  register() {
    const fname = document.getElementById('fname') as HTMLInputElement;
    const lname = document.getElementById('lname') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;
    const cpassword = document.getElementById('cpassword') as HTMLInputElement;
    if (!this.checkFields(fname.value, lname.value, email.value, password.value, cpassword.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    if (!this.checkPasswords(password.value, cpassword.value)) {
      alert('Niste dobro ponovili password!');
      return ;
    }
    const json = {'fname': fname.value, 'lname': lname.value, 'email': email.value, 'password': password.value };
    this.http.post('http://localhost:8080/user/register', JSON.stringify(json))
      .subscribe(data => {
        console.log(data['status']);
        if (data['status'] === 'FAIL') {
          alert('Neuspesna registracija!');
        } else if (data['status'] === 'EXIST') {
          alert('Postoji korisnik sa odabranim email-om!');
        } else {
          alert('Uspesna registracija! Poslat Vam je aktivacioni mail!');
        }
      });
  }

  checkFields(fname: string, lname: string, email: string, password: string, cpassword: string): boolean {
    if (fname === '' || lname === '' || email === '' || password === '' || cpassword === '') {
      return false;
    }
    return true;
  }

  checkPasswords(password: String, cpassword: String): boolean {
    if (password === cpassword) {
      return true;
    }
    return false;
  }

}

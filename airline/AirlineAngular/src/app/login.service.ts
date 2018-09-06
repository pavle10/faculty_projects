import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) { }

  login(username: String, password: String): Observable<any> {
    const json = {'username': username, 'password': password };
    return this.http.post('http://localhost:8080/user/login', JSON.stringify(json));
  }

}

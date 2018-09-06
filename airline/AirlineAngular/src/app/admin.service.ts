import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AdminService {

  constructor(private http: HttpClient) { }

  addAdmin(fname: String, lname: String, username: String, type: String): Observable<any> {
    const json = {'fname': fname, 'lname': lname, 'username': username, 'type': type};
    return this.http.post('http://localhost:8080/user/addAdmin', JSON.stringify(json));
  }

  changePass(password: String, id: String): Observable<any> {
    const json = {'id': id, 'password': password};
    return this.http.post('http://localhost:8080/user/changePass', JSON.stringify(json));
  }

  removeAdmin(username: String): Observable<any> {
    const json = {'username': username};
    return this.http.post('http://localhost:8080/user/removeAdmin', JSON.stringify(json));
  }

  addAirplane(regnum: String, brand: String, model: String,
              seatnum: String, cols: String, rows: String,
              colsnum: String, distance: String, speed: String): Observable<any> {
    const json = {'regnum': regnum, 'brand': brand, 'model': model,
                  'seatnum': seatnum, 'cols': cols, 'rows': rows,
                  'colsnum': colsnum, 'distance': distance, 'speed': speed};
    return this.http.post('http://localhost:8080/airplane/add', JSON.stringify(json));
  }

  removeAirplane(regnum: String): Observable<any> {
    const json = {'regnum': regnum};
    return this.http.post('http://localhost:8080/airplane/remove', JSON.stringify(json));
  }

  addDest(code: String, city: String, country: String, timezone: String): Observable<any> {
    const json = {'code': code, 'city': city, 'country': country, 'timezone': timezone};
    return this.http.post('http://localhost:8080/destination/add', JSON.stringify(json));
  }

  removeDest(code: String): Observable<any> {
    const json = {'code': code};
    return this.http.post('http://localhost:8080/destination/remove', JSON.stringify(json));
  }

  getDests() {
    return this.http.get('http://localhost:8080/destination/getAll');
  }

  getAirplanes() {
    return this.http.get('http://localhost:8080/airplane/getAll');
  }

  addFlight(fnumber: String, ddest: String, adest: String,
            datetime: String, distance: String, airplaneid: String): Observable<any> {
    const json = {'fnumber': fnumber, 'ddest': ddest, 'adest': adest, 'datetime': datetime,
                  'distance': distance, 'airplaneid': airplaneid};
    return this.http.post('http://localhost:8080/flight/add', JSON.stringify(json));
  }

  removeFlight(fnumber: String): Observable<any> {
    const json = {'fnumber': fnumber};
    return this.http.post('http://localhost:8080/flight/remove', JSON.stringify(json));
  }

  getFlights() {
    return this.http.get('http://localhost:8080/flight/getAll');
  }

  getPrices() {
    return this.http.get('http://localhost:8080/flight/getPrices');
  }

  changePrice(fnumber: String, newprice: String): Observable<any> {
    const json = {'fnumber': fnumber, 'newprice': newprice};
    return this.http.post('http://localhost:8080/flight/changePrice', JSON.stringify(json));
  }

  currentFlights(): Observable<any> {
    return this.http.get('http://localhost:8080/flight/currentFlights');
  }

  buyTicket(fnumber: String, cardNum: String): Observable<any> {
    const id = sessionStorage.getItem('id');
    const json = {'id': id, 'fnumber': fnumber, 'cardNum': cardNum};
    return this.http.post('http://localhost:8080/tickets/buy', JSON.stringify(json));
  }

  getTickets() {
    const id = sessionStorage.getItem('id');
    return this.http.get('http://localhost:8080/tickets/getTickets/' + id);
  }

  getBTickets() {
    const id = sessionStorage.getItem('id');
    return this.http.get('http://localhost:8080/tickets/getBTickets/' + id);
  }

  returnTickets(id: String): Observable<any> {
    const json = {'tid': id};
    return this.http.post('http://localhost:8080/tickets/returnTicket', JSON.stringify(json));
  }

  getSeats(ticketid: string): Observable<any> {
    const json = {'ticketid': ticketid};
    return this.http.post('http://localhost:8080/flight/seats', JSON.stringify(json));
  }

  getBoardingTicketsByTicket(ticketId: string) {
    const json = {'ticketId': ticketId};
    return this.http.post('http://localhost:8080/boardingTickets/byTicket', JSON.stringify(json));
  }

  buyBoardingTicket(ticketId: string, fname: string, lname: string,
                    jmbg: string, row: number, col: number): Observable<any> {
    const json = {'ticketId': ticketId, 'fname': fname, 'lname': lname,
                  'jmbg': jmbg, 'row': row, 'col': col};
    return this.http.post('http://localhost:8080/boardingTickets/buy', JSON.stringify(json));
  }

  getHistory(userId: number) {
    const json = {'userId': userId};
    return this.http.post('http://localhost:8080/tickets/history', JSON.stringify(json));
  }

  currentPrices() {
    return this.http.get('http://localhost:8080/flight/currentPrices');
  }

  getUser(id: number): Observable<any> {
    const json = {'userId': id};
    return this.http.post('http://localhost:8080/user/getUser', JSON.stringify(json));
  }

  getFlightStats(fnumber: string): Observable<any> {
    const json = {'fnumber': fnumber};
    return this.http.post('http://localhost:8080/flight/stats', JSON.stringify(json));
  }

  getAirplaneStats(anumber: string): Observable<any> {
    const json = {'anumber': anumber};
    return this.http.post('http://localhost:8080/airplane/stats', JSON.stringify(json));
  }
}

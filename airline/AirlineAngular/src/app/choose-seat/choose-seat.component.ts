import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AdminService } from '../admin.service';
import { Row } from '../Row';
import { Seat } from '../Seat';
import { BoardingTicket } from '../BoardingTicket';

@Component({
  selector: 'app-choose-seat',
  templateUrl: './choose-seat.component.html',
  styleUrls: ['./choose-seat.component.css']
})
export class ChooseSeatComponent implements OnInit {

  rows: number;
  cols: number;
  colsnum: number;
  rowsA: Row[] = [];
  colsA: Row[] = [];
  seats: Seat[][] = [];
  btickets: BoardingTicket[] = [];

  constructor(private location: Location, private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getSeats(sessionStorage.getItem('ticket'))
      .subscribe(data => {
        this.rows = 1 * data['rows'];
        this.cols = 1 * data['cols'];
        this.colsnum = 1 * data['colsnum'];
        this.makeGrid();
        this.addTakenSeats();
      });

  }

  changeColor(seatId: String) {
    document.getElementById('' + seatId).className = 'taken';
  }

  addTakenSeats() {
    this.adminService.getBoardingTicketsByTicket(sessionStorage.getItem('ticket'))
      .subscribe(data => {
      let i = 0;
      for (const t in data) {
        this.btickets[i++] = data[t];
        const tr = data[t].row;
        const tc = data[t].col;
        const c = this.cols + (this.cols / this.colsnum ) - 1;
        const id = tr * c + tc;
        document.getElementById('' + id).className = 'taken';
      }
    });
  }


  board(seatId: string) {
    const sId = Number(seatId);
    const fname = document.getElementById('fname') as HTMLInputElement;
    const lname = document.getElementById('lname') as HTMLInputElement;
    const jmbg = document.getElementById('jmbg') as HTMLInputElement;
    const element = document.getElementById(seatId) as HTMLElement;
    if (!this.checkFields(fname.value, lname.value, jmbg.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    if (!this.validJMBG(jmbg.value)) {
      alert('Nevalidan jmbg!');
      return ;
    }
    if (element.getAttribute('class') === 'taken') {
      alert('Zauzeto mesto!');
      return ;
    }
    const ticketId = sessionStorage.getItem('ticket');
    const c = this.cols + (this.cols / this.colsnum ) - 1;
    let row;
    let col;
    for (let i = 0; i < this.rows; i++) {
      for (let j = 0; j < c; j++) {
        if (this.seats[i][j].id === sId) {
            row = i;
            col = j;
            break;
        }
      }
    }
    this.adminService.buyBoardingTicket(ticketId, fname.value, lname.value, jmbg.value, row, col)
      .subscribe(data => {
        alert(data['status']);
        if (data['status'] === 'OK') {
          sessionStorage.removeItem('ticket');
          this.location.back();
        }
      });

  }

  checkFields(fname: string, lname: string, jmbg: string): boolean {
    if (fname === '' || lname === '' || jmbg === '') {
      return false;
    }
    return true;
  }

  validJMBG(value) {
    return /^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{9}$/.test(value);
  }

  back() {
    sessionStorage.removeItem('ticket');
    this.location.back();
  }

  makeGrid() {
    let p = this.colsnum;
    const c = this.cols + (this.cols / this.colsnum ) - 1;
    for (let i = 0; i < c; i++) {
      const temp = new Row();
      temp.i = i;
      this.colsA[i] = temp;
    }
    for (let i = 0; i < this.rows; i++) {
      const temp = new Row();
      temp.i = i;
      this.seats[i] = [];
      this.rowsA[i] = temp;
      for (let j = 0; j < c; j++) {
        const seat = new Seat();
        seat.row = i;
        seat.col = j;
        seat.id = i * c + j;
        if (j % p === 0) {
          if (j === 0) {
            seat.cls = 'valid';
          }else {
            seat.cls = 'notvalid';
            p += this.colsnum + 1;
          }
        } else {
          seat.cls = 'valid';
        }
        this.seats[i][j] = seat;
      }
      p = this.colsnum;
    }
  }

}

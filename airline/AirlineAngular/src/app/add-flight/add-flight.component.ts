import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Location } from '@angular/common';
import { Destination } from '../Destination';
import { Airplane } from '../Airplane';

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.css']
})
export class AddFlightComponent implements OnInit {

  destinations: Destination[] = [];
  airplanes: Airplane[] = [];

  constructor(private adminService: AdminService, private location: Location) { }

  ngOnInit() {
    this.adminService.getDests().subscribe(data => {
      let i = 0;
      for (const dest in data) {
        this.destinations[i++] = data[dest];
      }
    });
    this.adminService.getAirplanes().subscribe(data => {
      let i = 0;
      for (const air in data) {
        this.airplanes[i++] = data[air];
      }
    });
  }

  addFlight() {
    const flyNumber = document.getElementById('number') as HTMLInputElement;
    const ddest = document.getElementById('ddest') as HTMLOptionElement;
    const adest = document.getElementById('adest') as HTMLOptionElement;
    const date = document.getElementById('date') as HTMLInputElement;
    const time = document.getElementById('time') as HTMLInputElement;
    const distance = document.getElementById('distance') as HTMLInputElement;
    const airplane = document.getElementById('airplane') as HTMLOptionElement;
    if (!this.checkFields(flyNumber.value, ddest.value, adest.value, date.value,
                          time.value, distance.value, airplane.value)) {
      alert('Sva polja moraju biti popunjena!');
      return ;
    }
    if (this.checkDests(ddest.value, adest.value)) {
      alert('Destinacije se moraju razlikovati!');
      return ;
    }
    if (!this.checkTime(time.value)) {
      alert('Nevalidan format vremena!');
      return ;
    }
    if (this.checkDistance(distance.value)) {
      alert('Nevalidan unost distance!');
      return ;
    }
    this.adminService.addFlight(flyNumber.value, ddest.value, adest.value,
                                date.value + ' ' + time.value,
                                distance.value, airplane.value)
      .subscribe(data => {
        if (data['status'] === 'TIME') {
          alert('Let se poklapa sa drugim letovima aviona!');
        } else if (data['status'] === 'DISTANCE') {
          alert('Ovaj avion ne moze da predje toliku distancu!');
        } else {
          alert('Uspesno dodavanje leta! Proverite cenu!');
        }
      });
  }

  checkFields(flyNumber: string, ddest: string, adest: string, date: string,
              time: string, distance: string, airplane: string): boolean {
    if (flyNumber === '' || ddest === '' || adest === '' || date === '' ||
        time === '' || distance === '' || airplane === '') {
      return false;
    }
    return true;
  }

  checkDests(ddest: string, adest: string): boolean {
    return ddest === adest;
  }

  checkTime(time: string): boolean {
    const regexp = new RegExp('^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$');
    return regexp.test(time);
  }

  checkDistance(distance: string): boolean {
    return Number.isNaN(Number(distance));
  }

  back() {
    this.location.back();
  }
}

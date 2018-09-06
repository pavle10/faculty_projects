import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginService } from './login.service';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { AdminService } from './admin.service';
import { ChangePassComponent } from './change-pass/change-pass.component';
import { RemoveAdminComponent } from './remove-admin/remove-admin.component';
import { AddAirplaneComponent } from './add-airplane/add-airplane.component';
import { RemoveAirplaneComponent } from './remove-airplane/remove-airplane.component';
import { AddDestinationComponent } from './add-destination/add-destination.component';
import { RemoveDestinationComponent } from './remove-destination/remove-destination.component';
import { AddFlightComponent } from './add-flight/add-flight.component';
import { RemoveFlightComponent } from './remove-flight/remove-flight.component';
import { PricelistComponent } from './pricelist/pricelist.component';
import { OperaterMenuComponent } from './operater-menu/operater-menu.component';
import { UserMenuComponent } from './user-menu/user-menu.component';
import { BuyTicketComponent } from './buy-ticket/buy-ticket.component';
import { RemoveTicketComponent } from './remove-ticket/remove-ticket.component';
import { ChooseFlightComponent } from './choose-flight/choose-flight.component';
import { ChooseSeatComponent } from './choose-seat/choose-seat.component';
import { HistoryComponent } from './history/history.component';
import { UserPricelistComponent } from './user-pricelist/user-pricelist.component';
import { StatsComponent } from './stats/stats.component';
import { StatsFlightComponent } from './stats-flight/stats-flight.component';
import { StatsAirplaneComponent } from './stats-airplane/stats-airplane.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'adminMenu', component: AdminMenuComponent },
  { path: 'addAdmin', component: AddAdminComponent },
  { path: 'changePass', component: ChangePassComponent },
  { path: 'removeAdmin', component: RemoveAdminComponent },
  { path: 'addAirplane', component: AddAirplaneComponent },
  { path: 'removeAirplane', component: RemoveAirplaneComponent },
  { path: 'addDestination', component: AddDestinationComponent },
  { path: 'removeDestination', component: RemoveDestinationComponent },
  { path: 'addFlight', component: AddFlightComponent },
  { path: 'removeFlight', component: RemoveFlightComponent },
  { path: 'pricelist', component: PricelistComponent },
  { path: 'operaterMenu', component: OperaterMenuComponent },
  { path: 'userMenu', component: UserMenuComponent },
  { path: 'buyTicket', component: BuyTicketComponent },
  { path: 'removeTicket', component: RemoveTicketComponent },
  { path: 'chooseFlight', component: ChooseFlightComponent },
  { path: 'chooseSeat', component: ChooseSeatComponent },
  { path: 'history', component: HistoryComponent },
  { path: 'userPricelist', component: UserPricelistComponent },
  { path: 'stats', component: StatsComponent },
  { path: 'flightStats', component: StatsFlightComponent },
  { path: 'airplaneStats', component: StatsAirplaneComponent }
];
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegistrationComponent,
    AdminMenuComponent,
    AddAdminComponent,
    ChangePassComponent,
    RemoveAdminComponent,
    AddAirplaneComponent,
    RemoveAirplaneComponent,
    AddDestinationComponent,
    RemoveDestinationComponent,
    AddFlightComponent,
    RemoveFlightComponent,
    PricelistComponent,
    OperaterMenuComponent,
    UserMenuComponent,
    BuyTicketComponent,
    RemoveTicketComponent,
    ChooseFlightComponent,
    ChooseSeatComponent,
    HistoryComponent,
    UserPricelistComponent,
    StatsComponent,
    StatsFlightComponent,
    StatsAirplaneComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule
  ],
  providers: [LoginService, AdminService],
  bootstrap: [AppComponent]
})
export class AppModule { }

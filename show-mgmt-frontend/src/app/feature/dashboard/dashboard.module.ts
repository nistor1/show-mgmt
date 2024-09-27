import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChefComponent } from './chef/chef.component';
import { ChefCardComponent } from './chefs/chef-card/chef-card.component';
import { ChefsComponent } from './chefs/chefs.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import {ClientComponent} from "./client/client.component";
import {EmployeeComponent} from "./employee/employee.component";
import {AdminComponent} from "./admin/admin.component";
import {ShowCardComponent} from "./show-card/show-card.component";
import {ShowComponent} from "./show/show.component";
import {UserComponent} from "./user/user.component";
import {UserCardComponent} from "./user-card/user-card.component";
import {CreateShowComponent} from "./create-show/create-show.component";
import {CreateUserComponent} from "./create-user/create-user.component";
import {CommentCardComponent} from "./comment-card/comment-card.component";
import {OrderCardComponent} from "./order-card/order-card.component";
import {OrdersComponent} from "./orders/orders.component";
import {OrderComponent} from "./order/order.component";


@NgModule({
  declarations: [
    ClientComponent,
    EmployeeComponent,
    AdminComponent,
    ShowComponent,
    ShowCardComponent,
    UserComponent,
    UserCardComponent,
    CreateShowComponent,
    CreateUserComponent,
    CommentCardComponent,
    OrderCardComponent,
    OrderComponent,
    OrdersComponent,
    ChefsComponent,
    ChefComponent,
    ChefCardComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule
  ]
})
export class DashboardModule {
}

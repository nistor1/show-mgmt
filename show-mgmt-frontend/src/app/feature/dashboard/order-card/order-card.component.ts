import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import {ShowModel} from "../../../shared/models/show.model";
import {OrderModel} from "../../../shared/models/order.model";


@Component({
  selector: 'app-order-card',
  templateUrl: './order-card.component.html',
  styleUrl: './order-card.component.scss'
})
export class OrderCardComponent {

  @Input() order!: OrderModel;
  role: string = '';


  constructor(private router: Router) {
    this.role = this.getUserRole();
  }

  viewDetails(orderId: string): void {
    console.log('Order-Card: ' + orderId);
    this.router.navigateByUrl('/dashboard/order/' + orderId);
  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }
  getOrderIdSubstring(): string {
    return this.order?.orderId ? this.order.orderId.substring(0, 5) : '';
  }
}

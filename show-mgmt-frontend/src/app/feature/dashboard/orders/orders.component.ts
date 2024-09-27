import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import {ShowModel} from '../../../shared/models/show.model';
import {ShowService} from '../../../core/service/show/show.service';
import {OrderModel} from '../../../shared/models/order.model';
import {OrderService} from '../../../core/service/order/order.service';
import {UserModel} from "../../../shared/models/user.model";
import {CommentModel} from "../../../shared/models/comment.model";
import {CommentService} from "../../../core/service/comment/comment.service";
import {switchMap} from "rxjs";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent implements OnInit {
  orders: OrderModel[] = [];

  order?: OrderModel;
  orderId?: string;
  role: string = '';

  numberValue: number = 0;
  errorMessage?: string;
  successMessage?: string;


  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
    private showService: ShowService,
    private orderService: OrderService,
    private destroyRef: DestroyRef,
    private router: Router
  ) {
    this.role = this.getUserRole();
  }

  ngOnInit(): void {
    if(this.role === 'CLIENT'){
      this.getAllByUser();
      console.log('Client OrdersComponent initialized');

    } else if(this.role === 'ADMIN' || this.role === 'EMPLOYEE'){
      this.getAll();
      console.log('Employee OrdersComponent initialized');

    }
  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  private getAllByUser(): void {
    if(this.role !== 'CLIENT'){
      this.errorMessage = 'You are not authorized to view this page';
      return;
    }
    console.log('Getting orders for CLIENT');

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');

    this.orderService.getAllByUser(loggedUser.userId || '')
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.orders = response, error: err => console.log(err),
      });
  }

  private getAll(): void {
    this.orderService.getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.orders = response, error: err => console.log(err),
      });
  }
  logOut(): void {
    this.clearCookies();
    localStorage.removeItem('loggedUser');
    this.router.navigateByUrl('/auth/login');
  }

  private clearCookies(): void {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i];
      const equalPos = cookie.indexOf('=');
      const name = equalPos > -1 ? cookie.slice(0, equalPos) : cookie;
      document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;';
    }
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

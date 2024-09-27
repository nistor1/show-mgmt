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
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent implements OnInit {

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

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  ngOnInit(): void {
    console.log('OrderComponent initialized');
    this.getOrderById();
  }

  private getOrderById(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        this.orderId = params.get('orderId')?.toString();
        if (this.orderId) {
          this.orderService.getById(this.orderId)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => this.order = response);
        }
      });
  }
  backToOrders(): void {
    this.router.navigate(['dashboard/orders']);
  }

  viewShowDetails(showId: string): void {
    this.router.navigate(['dashboard/show/' + showId]);
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
  getOrderIdSubstring(): string {
    return this.order?.orderId ? this.order.orderId.substring(0, 5) : '';
  }
}

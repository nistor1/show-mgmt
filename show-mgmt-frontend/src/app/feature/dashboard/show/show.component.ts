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
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrl: './show.component.scss'
})
export class ShowComponent implements OnInit {

  comments: CommentModel[] = [];
  show?: ShowModel;
  showId?: string;
  orderModel: OrderModel = {
    orderId: '',
    user: {} as UserModel,
    show: {} as ShowModel,
    orderDate: '',
    numberOfTicketsToBuy: 0
  };
  role: string = '';

  commentModel: CommentModel = {
    commentId: '',
    user: {} as UserModel,
    show: {} as ShowModel,
    commentDate: '',
    description: ''
  }
  commentDescription: string = '';

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
    this.getShowById();
    this.getComments();
  }

  private getComments(): void {
    console.log('Getting comments for show: ', this.showId);

    this.commentService.getAllByShow(this.showId || '')
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.comments = response, error: err => console.log(err),
      });
  }

  deleteCommentCalled(commentId: string): void {
    console.log('Deleting comment ', commentId);
    this.commentService.deleteById(commentId)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        switchMap(() => this.commentService.getAllByShow(this.showId || ''))
      )
      .subscribe({
        next: updatedComments=> {
          this.comments = updatedComments;
          console.log('Comment deleted successfully');
        },
        error: err => console.log('Error deleting comment:', err)
      });
  }

  addComment(): void {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    if (!loggedUser) {
      console.error('No logged user found');
      this.errorMessage = 'No logged user found';
      this.successMessage = '';
      return;
    }
    if(this.commentDescription === ''){
      this.errorMessage = 'Comment description is required!';
      this.successMessage = '';
      return;
    }

    if (this.show) {
      this.commentModel = {
        ...this.commentModel,
        show: this.show,
        user: loggedUser,
        commentDate: new Date().toISOString(),
        description: this.commentDescription
      };
    }

    this.commentService.save(this.commentModel)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => {
          console.log(response);
          this.successMessage = 'Comment added successfully!';
          this.errorMessage = '';
        },
        error: err => {
          console.error(err);
          this.errorMessage = 'Failed to add comment!';
          this.successMessage = '';
        }
      });
  }

  decreaseValue() {
    if (this.numberValue > 0) {
      this.numberValue--;
    }
  }

  increaseValue() {
    this.numberValue++;
  }

  buyTicket(): void {
    if (this.numberValue <= 0) {
      this.errorMessage = 'Number of tickets must be greater than 0';
      setTimeout(() => this.errorMessage = undefined, 3000);

      return;
    }

    console.log('Buy ticket');
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    if (!loggedUser) {
      console.error('No logged user found');
      return;
    }

    if (this.show) {
      this.orderModel = {
        ...this.orderModel,
        show: this.show,
        user: loggedUser,
        orderDate: new Date().toISOString(),
        numberOfTicketsToBuy: this.numberValue
      };

      this.orderService.save(this.orderModel)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: response => {
            console.log(response);
            if (loggedUser.role === 'CLIENT') {
              this.router.navigate(['/dashboard/client'])
                .then(success => {
                  if (success) {
                    console.log('Navigation is successful!');
                  } else {
                    console.error('Navigation has failed!');
                  }
                });
            } else if (loggedUser.role === 'EMPLOYEE') {
              this.router.navigate(['/dashboard/employee'])
                .then(success => {
                  if (success) {
                    console.log('Navigation is successful!');
                  } else {
                    console.error('Navigation has failed!');
                  }
                });
            }

          },
          error: err => console.error(err)
        });
    } else {
      console.error('Show is not defined');
    }
  }


  private getShowById(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        this.showId = params.get('idShow')?.toString();
        if (this.showId) {
          this.showService.getById(this.showId)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => this.show = response);
        }
      });
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

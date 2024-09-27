import {Component, DestroyRef, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import {ShowModel} from '../../../shared/models/show.model';
import {ShowService} from '../../../core/service/show/show.service';
import {OrderModel} from '../../../shared/models/order.model';
import {OrderService} from '../../../core/service/order/order.service';
import {UserModel} from "../../../shared/models/user.model";

@Component({
  selector: 'app-create-show',
  templateUrl: './create-show.component.html',
  styleUrl: './create-show.component.scss'
})
export class CreateShowComponent implements OnInit {
  showId?: string;
  role: string = '';
  oldShow?: ShowModel;

  newShow: ShowModel = {
    showId: '',
    name: '',
    price: 0,
    location: '',
    eventDate: '',
    description: '',
    numberOfTicketsLeft: 0
  };
  errorMessage: string = '';
  successMessage: string = '';
  constructor(
    private route: ActivatedRoute,
    private showService: ShowService,
    private orderService: OrderService,
    private destroyRef: DestroyRef,
    private router: Router
  ) {
    this.role = this.getUserRole();
  }

  ngOnInit(): void {
    this.getShowById();
  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  createShow(): void {
    if (this.validateShow(this.newShow)) {
      this.showService.createShow(this.newShow).subscribe(
        () => {
          this.successMessage = 'Show created successfully';
          this.errorMessage = '';
        },
        error => {
          this.errorMessage = 'Error creating show: ' + error.message;
          this.successMessage = '';
        }
      );
    } else {
      this.errorMessage = 'Please fill in all fields.';
      this.successMessage = '';
    }
  }

  updateShow(): void {
    if (this.validateShow(this.newShow)) {
      console.log('Update show:' + this.newShow);
      this.showService.updateShow(this.newShow).subscribe(
        () => {
          this.successMessage = 'Show updated successfully';
          this.errorMessage = '';
        },
        error => {
          this.errorMessage = 'Error updating show: ' + error.message;
          this.successMessage = '';
        }
      );
    } else {
      this.errorMessage = 'Please fill in all fields.';
      this.successMessage = '';
    }
  }

  private validateShow(show: ShowModel): boolean {
    return !!show &&
      !!show.name &&
      !!show.price &&
      !!show.location &&
      !!show.eventDate &&
      !!show.description &&
      show.numberOfTicketsLeft !== null &&
      show.numberOfTicketsLeft !== undefined;
  }
  private getShowById(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        this.showId = params.get('idShow')?.toString();
        if (this.showId) {
          this.showService.getById(this.showId)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => this.newShow = response);
        }
      });
    if(this.oldShow !== undefined){

      this.newShow.showId = this.oldShow.showId;
      this.newShow.name = this.oldShow.name;
      this.newShow.price = this.oldShow.price;
      this.newShow.location = this.oldShow.location;
      this.newShow.eventDate = this.oldShow.eventDate;
      this.newShow.description = this.oldShow.description;
      this.newShow.numberOfTicketsLeft = this.oldShow.numberOfTicketsLeft;
    }
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

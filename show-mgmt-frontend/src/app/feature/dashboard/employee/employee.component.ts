import {Component, DestroyRef, OnInit} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import { ChefService } from '../../../core/service/chef/chef.service';
import { ChefModel } from '../../../shared/models/chef.model';
import {UserModel} from "../../../shared/models/user.model";
import {ShowService} from "../../../core/service/show/show.service";
import {ShowModel} from "../../../shared/models/show.model";
import {switchMap} from "rxjs";


@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {
  shows: ShowModel[] = [];
  role: string = '';

  constructor(
    private router: Router,
    private showService: ShowService,
    private destroyRef: DestroyRef,  ) {

    this.role = this.getUserRole();

  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  ngOnInit(): void {
    console.log('EmployeeComponent initialized');
    this.getShows();

  }

  private getShows(): void {
    this.showService.getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.shows = response, error: err => console.log(err),
      });
  }
  deleteShowCalled(showId: string): void {
    console.log('Deleting show ', showId);
    this.showService.deleteById(showId)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        switchMap(() => this.showService.getAll())
      )
      .subscribe({
        next: updatedShows => {
          this.shows = updatedShows;
          console.log('Show deleted successfully');
        },
        error: err => console.log('Error deleting show:', err)
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

  createShow(): void {
    console.log('Create show');
    this.router.navigateByUrl('/dashboard/create-show');
  }
  seeOrders(): void {
    this.router.navigateByUrl('/dashboard/orders');
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

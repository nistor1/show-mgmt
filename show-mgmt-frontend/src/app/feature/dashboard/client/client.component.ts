import {Component, DestroyRef, OnInit} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import { ChefService } from '../../../core/service/chef/chef.service';
import { ChefModel } from '../../../shared/models/chef.model';
import {UserModel} from "../../../shared/models/user.model";
import { ShowModel } from '../../../shared/models/show.model';
import {ShowService} from "../../../core/service/show/show.service";


@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss']
})
export class ClientComponent implements OnInit {
  shows: ShowModel[] = [];
  role: string = '';

  constructor(
    private router: Router,
    private showService: ShowService,
    private destroyRef: DestroyRef,
  ) {
    this.role = this.getUserRole();
  }

  ngOnInit(): void {
    console.log('ClientComponent initialized');
    this.getShows();

  }
  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  private getShows(): void {
    this.showService.getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.shows = response, error: err => console.log(err),
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
  seeOrders(): void {
    console.log('ClientComponent: seeOrders');
    this.router.navigateByUrl('/dashboard/orders')
      .then(() => console.log('Navigation successful'))
      .catch(error => console.error('Navigation failed:', error));

  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

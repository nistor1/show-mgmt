import {Component, DestroyRef, OnInit} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import {switchMap} from "rxjs";
import {ShowService} from "../../../core/service/show/show.service";
import {UserService} from "../../../core/service/user/user.service";
import {UserModel} from "../../../shared/models/user.model";


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  users: UserModel[] = [];
  role: string = '';

  constructor(
    private router: Router,
    private userService: UserService,
    private destroyRef: DestroyRef,
  ) {
    this.role = this.getUserRole();

  }

  ngOnInit(): void {
    console.log('AdminComponent initialized');
    this.getUsers()
  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  private getUsers(): void {
    this.userService.getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.users = response, error: err => console.log(err),
      });
  }
  deleteUserCalled(userId: string): void {
    console.log('Deleting user ', userId);
    this.userService.deleteById(userId)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        switchMap(() => this.userService.getAll())
      )
      .subscribe({
        next: updatedUsers => {
          this.users = updatedUsers;
          console.log('User deleted successfully');
        },
        error: err => console.log('Error deleting user:', err)
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
  createUser(): void {
    console.log('Create user');
    this.router.navigateByUrl('/dashboard/create-user');
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

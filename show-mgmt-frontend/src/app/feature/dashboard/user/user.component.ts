import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import {UserModel} from "../../../shared/models/user.model";
import {UserService} from '../../../core/service/user/user.service';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit {

  user?: UserModel;
  userId?: string;
  role: string = '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private destroyRef: DestroyRef,
    private router: Router
  ) {
    this.role = this.getUserRole();

  }

  ngOnInit(): void {
    this.getUserById();
  }
  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  private getUserById(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        this.userId = params.get('idUser')?.toString();
        if (this.userId) {
          this.userService.getById(this.userId)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => this.user = response);
        }
      });
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
  getUserIdSubstring(): string {
    return this.user?.userId ? this.user.userId.substring(0, 5) : '';
  }
}

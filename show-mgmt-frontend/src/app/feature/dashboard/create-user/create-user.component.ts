import {Component, DestroyRef, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ActivatedRoute, Router} from '@angular/router';
import {ShowModel} from '../../../shared/models/show.model';
import {ShowService} from '../../../core/service/show/show.service';
import {OrderModel} from '../../../shared/models/order.model';
import {OrderService} from '../../../core/service/order/order.service';
import {UserModel} from "../../../shared/models/user.model";
import {UserService} from "../../../core/service/user/user.service";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.scss'
})
export class CreateUserComponent implements OnInit {
  userId?: string;
  role: string = '';
  oldUser?: UserModel;

  newUser: UserModel = {
    userId: '',
    name: '',
    age: 0,
    role: '',
    email: '',
    password: ''
  };
  errorMessage: string = '';
  successMessage: string = '';
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private orderService: OrderService,
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

  createUser(): void {
    if (this.validateUser(this.newUser)) {
      this.userService.createUser(this.newUser).subscribe(
        () => {
          this.successMessage = 'User created successfully';
          this.errorMessage = '';
        },
        error => {
          this.errorMessage = 'Error creating user: ' + error.message;
          this.successMessage = '';
        }
      );
    } else {
      this.errorMessage = 'Please fill in all fields.';
      this.successMessage = '';
    }
  }

  updateUser(): void {
    if (this.validateUser(this.newUser)) {
      console.log('Update user:' + this.newUser);
      this.userService.updateUser(this.newUser).subscribe(
        () => {
          this.successMessage = 'User updated successfully';
          this.errorMessage = '';
        },
        error => {
          this.errorMessage = 'Error updating User: ' + error.message;
          this.successMessage = '';
        }
      );
    } else {
      this.errorMessage = 'Please fill in all fields.';
      this.successMessage = '';
    }
  }

  private validateUser(user: UserModel): boolean {
    return !!user &&
      !!user.name &&
      !!user.age &&
      !!user.role &&
      !!user.email &&
      !!user.password;
  }
  private getUserById(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        this.userId = params.get('idUser')?.toString();
        if (this.userId) {
          this.userService.getById(this.userId)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => {
              if (response !== undefined) {
                this.oldUser = {
                  userId: response.userId,
                  age: response.age,
                  email: response.email,
                  role: response.role,
                  name: response.name,
                  password: ''
                };

                // Assign oldUser data to newUser after getting the response
                this.newUser.userId = this.oldUser.userId;
                this.newUser.name = this.oldUser.name;
                this.newUser.age = this.oldUser.age;
                this.newUser.role = this.oldUser.role;
                this.newUser.email = this.oldUser.email;
                this.newUser.password = '';
              }
            });
        }
      });
  }
  navigateTo(role: string): void {
    this.router.navigateByUrl(`/dashboard/` + role.toLowerCase());
  }
}

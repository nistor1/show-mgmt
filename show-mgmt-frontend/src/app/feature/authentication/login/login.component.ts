import { Component, DestroyRef, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/service/auth/auth.service';
import { UserService } from '../../../core/service/user/user.service';
import { LoginModel } from '../../../shared/models/login.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({});
  errorMessage?: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private destroyRef: DestroyRef
  ) {
  }

  ngOnInit(): void {
    this.buildLoginForm();
  }

  login(): void {
    if (!this.loginForm?.valid) {
      this.errorMessage = 'Invalid form completion!';
      setTimeout(() => this.errorMessage = undefined, 3000);
      return;
    }

    const credentials: LoginModel = {
      email: this.loginForm?.get('email')?.value,
      password: this.loginForm?.get('password2')?.value
    };
    this.authService.login(credentials)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.getUserInfo(),
        error: () => this.errorMessage = 'Invalid credentials'
      });
  }

  private buildLoginForm(): void {
    this.loginForm = this.formBuilder.group({
      email: [ '', [ Validators.required, Validators.email ] ],
      password2: [ '', [ Validators.required ] ]
    });
  }

  private getUserInfo(): void {
    this.userService.getInfo()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(response => {
        localStorage.setItem('loggedUser', JSON.stringify(response));

        const loggedUser = response;

        if(loggedUser.role === 'CLIENT') {
          this.router.navigateByUrl('/dashboard/client');
        } else if(loggedUser.role === 'EMPLOYEE') {
          this.router.navigateByUrl('/dashboard/employee');
        } else {
          this.router.navigateByUrl('/dashboard/admin');
        }
      });
  }
}

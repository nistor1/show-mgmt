import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import {ShowModel} from "../../../shared/models/show.model";
import {UserModel} from "../../../shared/models/user.model";


@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrl: './user-card.component.scss'
})
export class UserCardComponent {

  @Input() user!: UserModel;
  @Output() deleteUser: EventEmitter<string> = new EventEmitter<string>();
  role: string = '';


  constructor(private router: Router) {
    this.role = this.getUserRole();
  }

  viewDetails(userId: string): void {
    console.log('User-Card: ' + userId);
    this.router.navigate([ '/dashboard/user/' + userId ]);
  }
  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }
  deleteUserPressed(userId: string): void {
    this.deleteUser.emit(userId);
  }
  updateUserPressed(userId: string): void {
    this.router.navigateByUrl('/dashboard/create-user/' + userId);
  }
}

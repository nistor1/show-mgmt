import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import {ShowModel} from "../../../shared/models/show.model";


@Component({
  selector: 'app-show-card',
  templateUrl: './show-card.component.html',
  styleUrl: './show-card.component.scss'
})
export class ShowCardComponent {

  @Input() show!: ShowModel;
  @Output() deleteShow: EventEmitter<string> = new EventEmitter<string>();
  role: string = '';


  constructor(private router: Router) {
    this.role = this.getUserRole();
  }

  viewDetails(showId: string): void {
    console.log('Show-Card: ' + showId);
    this.router.navigate([ '/dashboard/show/' + showId ]);
  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  deleteShowPressed(showId: string): void {
    this.deleteShow.emit(showId);
  }
  updateShowPressed(showId: string): void {
    this.router.navigateByUrl('/dashboard/create-show/' + showId);
  }
}

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import {ShowModel} from "../../../shared/models/show.model";
import {CommentModel} from "../../../shared/models/comment.model";
import {ChefModel} from "../../../shared/models/chef.model";


@Component({
  selector: 'app-comment-card',
  templateUrl: './comment-card.component.html',
  styleUrl: './comment-card.component.scss'
})
export class CommentCardComponent {

  @Input() comment!: CommentModel;
  @Output() deleteComment: EventEmitter<string> = new EventEmitter<string>();
  role: string = '';


  constructor(private router: Router) {
    this.role = this.getUserRole();
  }

  viewDetails(commentId: string): void {
    console.log('Show-Card: ' + commentId);
    this.router.navigate([ '/dashboard/comment/' + commentId ]);
  }

  getUserRole(): string {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '{}');
    console.log(loggedUser.role);
    return loggedUser.role || '';
  }

  deleteCommentPressed(commentId: string): void {
    this.deleteComment.emit(commentId);
  }
}

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ChefModel } from '../../../../shared/models/chef.model';


@Component({
  selector: 'app-chef-card',
  templateUrl: './chef-card.component.html',
  styleUrl: './chef-card.component.scss'
})
export class ChefCardComponent {

  @Input() chef!: ChefModel;
  @Output() deleteChef: EventEmitter<string> = new EventEmitter<string>();

  constructor(private router: Router) {
  }

  viewDetails(chefId: string): void {
    this.router.navigate([ '/dashboard/chef/' + chefId ]);
  }

  deleteChefPressed(chefId: string): void {
    this.deleteChef.emit(chefId);
  }
}

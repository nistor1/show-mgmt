import { CommonModule } from '@angular/common';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';

import { CommentCardComponent } from './comment-card.component';


describe('ShowCardComponent', () => {
  let component: CommentCardComponent;
  let fixture: ComponentFixture<CommentCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ CommonModule, RouterModule ],
      declarations: [ CommentCardComponent ]
    })
      .compileComponents();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

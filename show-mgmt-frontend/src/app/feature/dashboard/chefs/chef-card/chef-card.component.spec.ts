import { CommonModule } from '@angular/common';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';

import { ChefCardComponent } from './chef-card.component';


describe('ChefCardComponent', () => {
  let component: ChefCardComponent;
  let fixture: ComponentFixture<ChefCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ CommonModule, RouterModule ],
      declarations: [ ChefCardComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ChefCardComponent);
    component = fixture.componentInstance;
    component.chef = { id: '1', name: 'John Doe', numberOfStars: 5}
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

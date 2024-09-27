import { CommonModule } from '@angular/common';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';

import { ShowCardComponent } from './show-card.component';


describe('ShowCardComponent', () => {
  let component: ShowCardComponent;
  let fixture: ComponentFixture<ShowCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ CommonModule, RouterModule ],
      declarations: [ ShowCardComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ShowCardComponent);
    component = fixture.componentInstance;
    component.show = { showId: '1', name: 'Sala Pala', description: 'Sala Pala', price: 100, numberOfTicketsLeft: 5000, eventDate: 'fdg', location: 'Sala Palatului'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

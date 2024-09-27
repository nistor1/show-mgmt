import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';

import { CreateUserComponent } from './create-user.component';
import { CreateShowComponent } from '../create-show/create-show.component';


describe('ShowComponent', () => {
  let component: CreateShowComponent;
  let fixture: ComponentFixture<CreateShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ RouterModule.forRoot([]), HttpClientModule ],
      declarations: [ CreateShowComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CreateShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

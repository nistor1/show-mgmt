import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { ChefService } from '../../../core/service/chef/chef.service';
import { ChefCardComponent } from './chef-card/chef-card.component';
import { ChefsComponent } from './chefs.component';


describe('ChefsComponent', () => {
  let component: ChefsComponent;
  let fixture: ComponentFixture<ChefsComponent>;
  let chefService: ChefService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientModule, CommonModule, FormsModule, RouterModule ],
      declarations: [ ChefsComponent, ChefCardComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ChefsComponent);
    component = fixture.componentInstance;
    chefService = TestBed.inject(ChefService);
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    // when
    fixture.detectChanges();

    // then
    expect(component).toBeTruthy();
  });

  it('should get all chefs when button is pressed', () => {
    // given
    fixture.detectChanges();
    const button: HTMLButtonElement = fixture.debugElement.nativeElement.querySelector('#search-button');
    const buttonSpy = spyOn(component as any, 'searchAllByRating').and.callThrough();
    const serviceSpy = spyOn(chefService as any, 'getAll');

    // when
    button.click();

    // then
    expect(buttonSpy).toHaveBeenCalled();
    expect(serviceSpy).toHaveBeenCalled();
  });

  it('should logout when button is pressed', () => {
    // given
    fixture.detectChanges();
    const button: HTMLButtonElement = fixture.debugElement.nativeElement.querySelector('#logout-button');
    const buttonSpy = spyOn(component as any, 'logOut').and.callThrough();
    const routerSpy = spyOn(router as any, 'navigateByUrl');

    // when
    button.click();

    // then
    expect(buttonSpy).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith('/auth/login' as any);
  });

  it('should display all chefs', () => {
    // given
    const chef = { id: 'ID_Chef1', name: 'John Doe', numberOfStars: 5 };
    const serviceSpy = spyOn(chefService as any, 'getAll').and.returnValue(of([ chef ]));

    // when
    fixture.detectChanges();

    // then
    expect(serviceSpy).toHaveBeenCalled();
    expect(fixture.debugElement.nativeElement.querySelector('app-chef-card')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#empty-chefs')).toBeFalsy();
  });

  it('should display no chef', () => {
    // given
    const serviceSpy = spyOn(chefService as any, 'getAll').and.returnValue(of([]));

    // when
    fixture.detectChanges();

    // then
    expect(serviceSpy).toHaveBeenCalled();
    expect(fixture.debugElement.nativeElement.querySelector('#empty-chefs')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('app-chef-card')).toBeFalsy();
  });
});

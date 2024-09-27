import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';

import {of} from 'rxjs';
import {Router} from '@angular/router';
import {ShowModel} from "../../../shared/models/show.model";
import {EmployeeComponent} from "./employee.component";
import {ShowService} from "../../../core/service/show/show.service";
import {ShowCardComponent} from "../show-card/show-card.component";


describe('EmployeeComponent', () => {
  let component: EmployeeComponent;
  let fixture: ComponentFixture<EmployeeComponent>;
  let showService: ShowService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, CommonModule, FormsModule, RouterTestingModule],
      declarations: [EmployeeComponent, ShowCardComponent],
      providers: [
        ShowService,
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EmployeeComponent);
    component = fixture.componentInstance;
    showService = TestBed.inject(ShowService);
    router = TestBed.inject(Router);

  });

  it('should create', () => {
    // when
    fixture.detectChanges();

    // then
    expect(component).toBeTruthy();
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
  it('should display all shows', () => {
    // given
    const show: ShowModel = {
      showId: '1',
      name: 'Example Show',
      price: 100,
      location: 'Example Location',
      description: 'Example Description',
      eventDate: '2024-05-25',
      numberOfTicketsLeft: 500
    };
    const serviceSpy = spyOn(showService as any, 'getAll').and.returnValue(of([show]));

    // when
    fixture.detectChanges();

    // then
    expect(serviceSpy).toHaveBeenCalled();
    expect(fixture.debugElement.nativeElement.querySelector('app-show-card')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#empty-shows')).toBeFalsy();
  });
  it('should display no show', () => {
    // given
    const serviceSpy = spyOn(showService as any, 'getAll').and.returnValue(of([]));

    // when
    fixture.detectChanges();

    // then
    expect(serviceSpy).toHaveBeenCalled();
    expect(fixture.debugElement.nativeElement.querySelector('#empty-shows')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('app-show-card')).toBeFalsy();
  });

  it('should call navigateTo("EMPLOYEE") when Employee button is clicked', () => {

    component.role = 'EMPLOYEE';
    fixture.detectChanges();

    const navigateToSpy = spyOn(component, 'navigateTo').and.callThrough();

    const employeeButton = fixture.debugElement.nativeElement.querySelector('.role-button');

    expect(employeeButton).toBeTruthy();

    employeeButton.click();

    expect(navigateToSpy).toHaveBeenCalledWith('EMPLOYEE');
  });


  it('should call createShow() when the create show button is clicked', () => {
    fixture.detectChanges();

    const createShowSpy = spyOn(component, 'createShow').and.callThrough();

    const createShowButton = fixture.debugElement.nativeElement.querySelector('#create-show-button');

    expect(createShowButton).toBeTruthy();

    createShowButton.click();

    fixture.detectChanges();

    expect(createShowSpy).toHaveBeenCalled();
  });

  it('should call seeOrders() when the see orders button is clicked', () => {
    fixture.detectChanges();

    const seeOrdersSpy = spyOn(component, 'seeOrders').and.callThrough();

    const seeOrdersButton = fixture.debugElement.nativeElement.querySelector('#see-orders-button');

    expect(seeOrdersButton).toBeTruthy();

    seeOrdersButton.click();

    fixture.detectChanges();

    expect(seeOrdersSpy).toHaveBeenCalled();
  });
});

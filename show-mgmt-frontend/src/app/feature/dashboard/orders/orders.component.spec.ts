import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';

import {of} from 'rxjs';
import {OrdersComponent} from './orders.component';
import {OrderService} from '../../../core/service/order/order.service';
import {OrderCardComponent} from "../order-card/order-card.component";
import {ActivatedRoute, Router} from '@angular/router';
import {OrderModel} from "../../../shared/models/order.model";
import {UserModel} from "../../../shared/models/user.model";
import {ShowModel} from "../../../shared/models/show.model";

class ActivatedRouteStub {
  // Define a paramMap observable with an empty object as default value
  paramMap = of({});

  // Add other properties or methods as needed for your test
}

describe('OrdersComponent', () => {
  let component: OrdersComponent;
  let fixture: ComponentFixture<OrdersComponent>;
  let orderService: OrderService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, CommonModule, FormsModule, RouterTestingModule],
      declarations: [OrdersComponent, OrderCardComponent],
      providers: [
        OrderService,
        // Provide the stub for ActivatedRoute
        {provide: ActivatedRoute, useClass: ActivatedRouteStub}
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OrdersComponent);
    component = fixture.componentInstance;
    orderService = TestBed.inject(OrderService);
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
});

/*
  it('should display all orders from user', () => {
    // given
    const user: UserModel = {
      userId: '1',
      email: 'example@example.com',
      name: 'John Doe',
      age: 30,
      role: 'user',
      password: 'password'
    };
    const show: ShowModel = {
      showId: '1',
      name: 'Example Show',
      price: 100,
      location: 'Example Location',
      description: 'Example Description',
      eventDate: '2024-05-25',
      numberOfTicketsLeft: 500
    };

    const order: OrderModel = {
      orderId: '1',
      user: user,
      show: show,
      orderDate: '2024-05-25',
      numberOfTicketsToBuy: 2
    };
    const userId = '1';
    const serviceSpy = spyOn(orderService, 'getAllByUser').and.returnValue(of([order]));

    // when
    fixture.detectChanges();

    // then
    expect(serviceSpy).toHaveBeenCalled();
    expect(component.orders.length).toBe(1);
    expect(fixture.nativeElement.querySelector('app-order-card')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('#empty-orders')).toBeFalsy();
  });

  it('should display no orders', () => {
    // given
    const serviceSpy = spyOn(orderService as any, 'getAllByUser').and.returnValue(of([]));

    // when
    fixture.detectChanges();

    // then
    expect(serviceSpy).toHaveBeenCalled();
    expect(fixture.debugElement.nativeElement.querySelector('#empty-chefs')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('app-chef-card')).toBeFalsy();
  });

});
*/

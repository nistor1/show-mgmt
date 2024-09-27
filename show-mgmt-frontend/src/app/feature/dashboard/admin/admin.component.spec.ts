import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {Router, RouterModule} from '@angular/router';

import { AdminComponent } from './admin.component';


describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ RouterModule.forRoot([]), HttpClientModule ],
      declarations: [ AdminComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  it('should create', () => {
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

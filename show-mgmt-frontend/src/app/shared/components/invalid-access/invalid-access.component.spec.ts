import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvalidAccessComponent } from './invalid-access.component';


describe('InvalidAccessComponent', () => {
  let component: InvalidAccessComponent;
  let fixture: ComponentFixture<InvalidAccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ InvalidAccessComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(InvalidAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

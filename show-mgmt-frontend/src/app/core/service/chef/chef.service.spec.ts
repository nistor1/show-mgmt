import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { ChefService } from './chef.service';


describe('ChefService', () => {
  let service: ChefService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ]
    });
    service = TestBed.inject(ChefService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

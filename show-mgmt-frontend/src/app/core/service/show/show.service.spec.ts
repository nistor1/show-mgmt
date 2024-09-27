import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { ShowService } from './show.service';


describe('ChefService', () => {
  let service: ShowService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ]
    });
    service = TestBed.inject(ShowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

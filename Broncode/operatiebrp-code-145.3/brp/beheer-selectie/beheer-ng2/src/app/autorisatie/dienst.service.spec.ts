import {inject, TestBed} from '@angular/core/testing';
import {DienstService} from './dienst.service';
import {HttpModule} from '@angular/http';

describe('DienstService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DienstService],
      imports: [HttpModule]
    });
  });

  it('should be created', inject([DienstService], (service: DienstService) => {
    expect(service).toBeTruthy();
  }));
});

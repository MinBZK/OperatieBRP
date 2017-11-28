import {inject, TestBed} from '@angular/core/testing';
import {HttpModule, Response, ResponseOptions, XHRBackend} from '@angular/http';
import {StamdataService} from './stamdata.service';
import {MockBackend, MockConnection} from '@angular/http/testing';

describe('StamdataService', () => {
  let mockBackend: MockBackend;
  let stamdataService: StamdataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [
        {provide: XHRBackend, useClass: MockBackend},
        StamdataService]
    });
  });

  beforeEach(inject([XHRBackend, StamdataService], (_mockBackend, _service) => {
    mockBackend = _mockBackend;
    stamdataService = _service;
  }));

  it('should be created', () => {
    expect(stamdataService).toBeTruthy();
  });

  describe('Stamdataservice.getStamdata', () => {
    beforeEach(() => {
      const stamData1 = {id: 1, naam: 'stamdata1'};
      const stamData2 = {id: 1, naam: 'stamdata2'};
      const stamData = [stamData1, stamData2];
      mockBackend.connections.subscribe((connection: MockConnection) => {
          const responseOpts = new ResponseOptions({
            body: JSON.stringify(stamData)
          });
          connection.mockRespond(new Response(responseOpts));
        }
      );
    });

    it('should return stamdata', async () => {
      stamdataService.getStamdata().subscribe(data => expect(data.length).toBe(2));
    });
  });
});

import {async, inject, TestBed} from '@angular/core/testing';
import {MockBackend, MockConnection} from '@angular/http/testing';
import {SelectieService} from './selectie.service';
import {HttpModule, Response, ResponseOptions, XHRBackend} from '@angular/http';
import {SelectieTaak} from './selectie-taak';
import moment = require('moment');

describe('SelectieService', () => {
  let mockBackend: MockBackend;
  let selectieService: SelectieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule
      ],
      providers: [
        {provide: XHRBackend, useClass: MockBackend},
        SelectieService
      ]
    });
  });

  beforeEach(inject([XHRBackend, SelectieService], (_mockBackend, _service) => {
    mockBackend = _mockBackend;
    selectieService = _service;
  }));

  it('should be created', () => {
    expect(selectieService).toBeTruthy();
  });

  describe('SelectieService.get requests', () => {
    beforeEach(() => {
        const taken = [new SelectieTaak, new SelectieTaak];
        mockBackend.connections.subscribe((connection: MockConnection) => {
            const responseOpts = new ResponseOptions({
              body: JSON.stringify(taken)
            });
            connection.mockRespond(new Response(responseOpts));
          }
        );
      }
    );

    it('should return tasks,', async(() => {
      selectieService.getTaken().subscribe(taak => {
        expect(taak.length).toBe(2);
      });
    }));

    it('should return tasks from within a specified time period ,', async(() => {
      selectieService.getSelectieTakenBinnenPeriode(moment(), moment()).subscribe(taak => {
        expect(taak.length).toBe(2);
      });
    }));
  });


  describe('SelectieService.post requests', () => {
    let taak1;
    beforeEach(() => {
        taak1 = new SelectieTaak;
        taak1.id = 1;
        mockBackend.connections.subscribe((connection: MockConnection) => {
            const responseOpts = new ResponseOptions({
              body: JSON.stringify(taak1)
            });
            connection.mockRespond(new Response(responseOpts));
          }
        );
      }
    );

    it('should return the task after saving,', async(() => {
      selectieService.slaSelectieTaakOp(taak1).subscribe(taak => {
        expect(taak.id).toBe(1);
      });
    }));
  });


  describe('SelectieService.bepaling default waarden van zoek argumenten', () => {
    it('should give the correct start date', () => {
      const beginMom = moment().subtract(1, 'months');

      expect(selectieService.bepaalBeginEnEindDatums().beginDatum.year()).toBe(beginMom.year());
      expect(selectieService.bepaalBeginEnEindDatums().beginDatum.month()).toBe(beginMom.month());
      expect(selectieService.bepaalBeginEnEindDatums().beginDatum.day()).toBe(beginMom.day());
    });

    it('should give the correct end date', () => {
      const eindMom = moment().add(3, 'months');

      expect(selectieService.bepaalBeginEnEindDatums().eindDatum.year()).toBe(eindMom.year());
      expect(selectieService.bepaalBeginEnEindDatums().eindDatum.month()).toBe(eindMom.month());
      expect(selectieService.bepaalBeginEnEindDatums().eindDatum.day()).toBe(eindMom.day());
    });
  });

});

import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import * as moment from 'moment';
import 'rxjs/add/operator/map';
import {SelectieTaak} from './selectie-taak';
import {ISelectieService} from './iselectie-service';

@Injectable()
export class SelectieService implements ISelectieService {

  constructor(private _http: Http) {
  }

  getTaken(): Observable<SelectieTaak[]> {
    return this._http.get('/api/selectietaak').map(r => r.json());
  }

  getSelectieTakenBinnenPeriode(beginDatum: moment.Moment, eindDatum: moment.Moment): Observable<SelectieTaak[]> {
    const isoDateBeginDatum = moment(beginDatum).format('YYYY-MM-DD');
    const isoDateEindDatum = moment(eindDatum).format('YYYY-MM-DD');
    return this._http.get(`/api/selectietaak?beginDatum=${isoDateBeginDatum}&eindDatum=${isoDateEindDatum}`)
      .map(r => r.json());
  }

  bepaalBeginEnEindDatums(): BeginEindDatum {
    return {
      beginDatum: moment().subtract(1, 'months'),
      eindDatum: moment().add(3, 'months')
    };
  }

  slaSelectieTaakOp(selectieTaak: SelectieTaak): Observable<SelectieTaak> {
    return this._http.post('/api/selectietaak', selectieTaak).map(r => r.json());
  }

}

export class BeginEindDatum {
  beginDatum: moment.Moment;
  eindDatum: moment.Moment;
}

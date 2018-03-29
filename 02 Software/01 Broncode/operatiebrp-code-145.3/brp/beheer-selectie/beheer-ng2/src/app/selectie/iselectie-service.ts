import {SelectieTaak} from './selectie-taak';
import {Observable} from 'rxjs/Observable';
import {BeginEindDatum} from './selectie.service';
import * as moment from 'moment';

export interface ISelectieService {
  getTaken(): Observable<SelectieTaak[]>;
  getSelectieTakenBinnenPeriode(beginDatum: moment.Moment, eindDatum: moment.Moment): Observable<SelectieTaak[]>;
  bepaalBeginEnEindDatums(): BeginEindDatum;
  slaSelectieTaakOp(selectieTaak: SelectieTaak): Observable<SelectieTaak>;
}

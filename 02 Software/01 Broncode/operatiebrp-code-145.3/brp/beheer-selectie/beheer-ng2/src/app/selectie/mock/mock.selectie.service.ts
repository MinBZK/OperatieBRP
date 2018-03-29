import {Injectable} from '@angular/core';
import {BeginEindDatum} from '../selectie.service';
import {Observable} from 'rxjs/Rx';
import * as moment from 'moment';
import {SelectieTaak} from '../selectie-taak';
import {ISelectieService} from '../iselectie-service';

@Injectable()
export class MockSelectieService implements ISelectieService {

  public fail: boolean;

  bepaalBeginEnEindDatums(): BeginEindDatum {
    return {
      beginDatum: moment(),
      eindDatum: moment()
    };
  }

  getTaken(): Observable<SelectieTaak[]> {
    return;
  }

  getSelectieTakenBinnenPeriode(beginDatum: moment.Moment, eindDatum: moment.Moment): Observable<SelectieTaak[]> {
    if (this.fail) {
      return Observable.throw({
        _body: 'error'
      });
    }
    const selectieTaak1 = new SelectieTaak();
    const selectieTaak2 = new SelectieTaak();
    selectieTaak1.id = 1;
    selectieTaak2.id = 2;
    return Observable.of([selectieTaak1, selectieTaak2]);
  }

  slaSelectieTaakOp(selectieTaak: SelectieTaak): Observable<SelectieTaak> {
    return;
  }

}

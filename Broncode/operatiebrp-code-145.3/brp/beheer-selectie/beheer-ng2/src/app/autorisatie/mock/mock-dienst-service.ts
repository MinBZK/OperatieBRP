import {Dienst} from '../dienst';
import {Observable} from 'rxjs/Observable';

export class MockDienstService {

  getDienst(id: number): Observable<Dienst> {
    return Observable.of();
  }
}

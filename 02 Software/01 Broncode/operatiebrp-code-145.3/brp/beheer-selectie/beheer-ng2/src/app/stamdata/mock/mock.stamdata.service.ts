import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {IStamdataService} from '../istamdata-service';
import {Stamdata} from '../stamdata';

@Injectable()
export class MockStamdataService implements IStamdataService {

  getStamdata(): Observable<Stamdata[]> {
    return Observable.of();
  }

}

import {Observable} from 'rxjs/Observable';
import {Stamdata} from './stamdata';

export interface IStamdataService {
  getStamdata(): Observable<Stamdata[]>;
}

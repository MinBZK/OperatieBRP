import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Dienst} from './dienst';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class DienstService {

  constructor(private _http: Http) {
  }

  getDienst(dienstId: number): Observable<Dienst> {
    return this._http.get(`/api/dienst/${dienstId}`).map(r => r.json());
  }

}

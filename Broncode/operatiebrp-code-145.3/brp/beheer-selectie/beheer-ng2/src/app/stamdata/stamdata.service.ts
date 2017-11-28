import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Http} from '@angular/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {IStamdataService} from './istamdata-service';
import {Stamdata} from './stamdata';

@Injectable()
export class StamdataService implements IStamdataService, Resolve<Stamdata[]> {

  private _http: Http;

  constructor(http: Http) {
    this._http = http;
  }

  getStamdata(): Observable<Stamdata[]> {
    return this._http.get('/api/stamdata/statisch?tabel=autaut.seltaakstatus').map(r => r.json());
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Stamdata[] | Observable<Stamdata[]> | Promise<Stamdata[]> {
    return this.getStamdata();
  }

}

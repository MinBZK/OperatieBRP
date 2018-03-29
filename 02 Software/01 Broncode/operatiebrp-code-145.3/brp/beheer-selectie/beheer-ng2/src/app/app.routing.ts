import {Routes, RouterModule} from '@angular/router';
import {TaakComponent} from './selectie/taak/taak.component';
import {HomeComponent} from './home/home.component';
import {StamdataService} from './stamdata/stamdata.service';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'selectie',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'selectie',
    component: TaakComponent,
    resolve: {
      alleSelectieStatussen: StamdataService
    }
  }
];

export const routing = RouterModule.forRoot(routes);

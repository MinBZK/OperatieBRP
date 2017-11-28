import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TaakComponent} from './taak/taak.component';
import {SelectieService} from './selectie.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {TaakDetailComponent} from './taak-detail/taak-detail.component';
import {ToegestaneSelectiestatussenPipe} from './taak-detail/toegestane-selectiestatussen.pipe';
import {SelectieIntervalPipe} from './taak-detail/selectie-interval-pipe';
import {WrappersModule} from '../wrappers/wrappers.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AutorisatieModule} from '../autorisatie/autorisatie.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    WrappersModule,
    NgbModule,
    AutorisatieModule
  ],
  declarations: [TaakComponent, TaakDetailComponent, ToegestaneSelectiestatussenPipe, SelectieIntervalPipe],
  exports: [TaakComponent]
})
export class SelectieModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SelectieModule,
      providers: [SelectieService]
    };
  }
}

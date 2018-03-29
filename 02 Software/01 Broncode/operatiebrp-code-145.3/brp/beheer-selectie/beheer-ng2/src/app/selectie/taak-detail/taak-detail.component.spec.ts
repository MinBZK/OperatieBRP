import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {TaakDetailComponent} from './taak-detail.component';
import {SelectieService} from '../selectie.service';
import * as moment from 'moment';
import {ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ActivatedRoute} from '@angular/router';
import {ToegestaneSelectiestatussenPipe} from './toegestane-selectiestatussen.pipe';
import {SelectieIntervalPipe} from './selectie-interval-pipe';
import {WrappersModule} from '../../wrappers/wrappers.module';
import {MockSelectieService} from '../mock/mock.selectie.service';
import {SelectieTaak} from '../selectie-taak';
import {AutorisatieModule} from '../../autorisatie/autorisatie.module';
import {DienstService} from '../../autorisatie/dienst.service';
import {MockDienstService} from '../../autorisatie/mock/mock-dienst-service';
import {Dienst} from '../../autorisatie/dienst';

describe('TaakDetailComponent', () => {
  let component: TaakDetailComponent;
  let fixture: ComponentFixture<TaakDetailComponent>;
  const activatedRouteMock = {
    snapshot: {
      data: {
        alleStatussen: [{id: 1, naam: 'Test status'}]
      }
    }
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TaakDetailComponent, ToegestaneSelectiestatussenPipe, SelectieIntervalPipe],
      imports: [
        NgbModule.forRoot(),
        ReactiveFormsModule,
        WrappersModule,
        AutorisatieModule
      ]
    }).overrideComponent(TaakDetailComponent, {
      set: {
        providers: [
          {provide: ActivatedRoute, useValue: activatedRouteMock},
          {provide: SelectieService, useClass: MockSelectieService}
        ]
      }
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaakDetailComponent);
    component = fixture.componentInstance;
    component.alleStatussen = activatedRouteMock.snapshot.data.alleStatussen;
    component.taak = new SelectieTaak();
    component.taak.dienstId = 1;
    component.taak.eersteSelectieDatum = moment();
    component.dienst = new Dienst();
    component.dienst.selectiePeilmomentMaterieelResultaat = '';
    component.dienst.selectiePeilmomentFormeelResultaat = '';
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

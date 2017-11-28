import {TestBed, async} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {routing} from './app.routing';
import {LocationStrategy, APP_BASE_HREF, HashLocationStrategy} from '@angular/common';
import {SelectieModule} from './selectie/selectie.module';
import {HttpModule} from '@angular/http';
import {SelectieService} from './selectie/selectie.service';
import {TaakComponent} from './selectie/taak/taak.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MockSelectieService} from './selectie/mock/mock.selectie.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavigatieComponent} from './navigatie/navigatie.component';
import {HomeComponent} from './home/home.component';

describe('AppComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        // TaakComponent
        NavigatieComponent,
        HomeComponent
      ],
      imports: [
        NgbModule.forRoot(),
        SelectieModule,
        HttpModule,
        BrowserAnimationsModule,
        routing
      ],
      providers: [
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: LocationStrategy, useClass: HashLocationStrategy}
      ]
    }).overrideComponent(TaakComponent, {
      set: {
        providers: [
          {provide: SelectieService, useClass: MockSelectieService},
        ]
      }
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});

import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {NavigatieComponent} from './navigatie.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {routing} from '../app.routing';
import {HomeComponent} from '../home/home.component';
import {SelectieModule} from '../selectie/selectie.module';
import {HashLocationStrategy, LocationStrategy, APP_BASE_HREF} from '@angular/common';

describe('NavigatieComponent', () => {
  let component: NavigatieComponent;
  let fixture: ComponentFixture<NavigatieComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        NavigatieComponent,
        HomeComponent
      ],
      imports: [
        BrowserAnimationsModule,
        SelectieModule,
        routing
      ],
      providers: [
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: LocationStrategy, useClass: HashLocationStrategy}
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigatieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

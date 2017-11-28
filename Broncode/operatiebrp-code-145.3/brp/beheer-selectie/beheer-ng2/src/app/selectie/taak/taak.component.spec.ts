import {async, ComponentFixture, ComponentFixtureAutoDetect, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {TaakComponent} from './taak.component';
import {SelectieService} from '../selectie.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MockSelectieService} from '../mock/mock.selectie.service';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {TaakDetailComponent} from '../taak-detail/taak-detail.component';
import {ToegestaneSelectiestatussenPipe} from '../taak-detail/toegestane-selectiestatussen.pipe';
import {SelectieIntervalPipe} from '../taak-detail/selectie-interval-pipe';
import {WrappersModule} from '../../wrappers/wrappers.module';
import {SelectieTaak} from '../selectie-taak';
import {SelectieTaakStatus} from '../selectie-taak-status.enum';
import {DienstService} from '../../autorisatie/dienst.service';
import {MockDienstService} from '../../autorisatie/mock/mock-dienst-service';
import {AutorisatieModule} from '../../autorisatie/autorisatie.module';

describe('TaakComponent', () => {

  let component: TaakComponent;
  let fixture: ComponentFixture<TaakComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TaakComponent,
        TaakDetailComponent,
        ToegestaneSelectiestatussenPipe,
        SelectieIntervalPipe
      ],
      imports: [
        NgbModule.forRoot(),
        FormsModule,
        ReactiveFormsModule,
        NgxDatatableModule,
        WrappersModule,
        AutorisatieModule
      ]
    }).overrideComponent(TaakComponent, {
      set: {
        providers: [
          {provide: SelectieService, useClass: MockSelectieService},
          {provide: ComponentFixtureAutoDetect, useValue: true},
          {provide: DienstService, useClass: MockDienstService}
        ]
      }
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaakComponent);
    component = fixture.componentInstance;
    component.taken = [];
    component.opnieuwTePlannen = false;
    component.dagelijksTerugkerend = false;
    component.reedsGepland = false;
    component.filterValues = {};
    fixture.detectChanges();
  });


  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  describe('TaakComponent.getRowClass', () => {
    const taakGemist = new SelectieTaak();
    const taakNietGemist = new SelectieTaak();
    const taakGeplandGeenId = new SelectieTaak();
    const taakAfgekeurdGeenId = new SelectieTaak();
    const taakGepland = new SelectieTaak();
    const taakAfgekeurd = new SelectieTaak();

    beforeEach(() => {
      taakGemist.opnieuwPlannen = true;
      taakNietGemist.opnieuwPlannen = false;
      taakGeplandGeenId.status = SelectieTaakStatus.INGEPLAND;
      taakGepland.status = SelectieTaakStatus.INGEPLAND;
      taakGepland.id = 1;
      taakAfgekeurdGeenId.status = SelectieTaakStatus.AFGEKEURD;
      taakAfgekeurd.status = SelectieTaakStatus.AFGEKEURD;
      taakAfgekeurd.id = 1;
    });


    it('should return a rowclass with correct status info, if status is "opnieuwPlannen"', () => {
      expect(component.getRowClass(taakGemist)).toBe(' gemiste-selectietaak');
      expect(component.getRowClass(taakNietGemist)).toBeFalsy('');
    });

    it('should return a rowclass with correct status info, if status is "ingepland"', () => {
      expect(component.getRowClass(taakGeplandGeenId)).toBeFalsy('');
      expect(component.getRowClass(taakGepland)).toBe(' opgeslagen-selectietaak');
    });

    it('should return a rowclass with correct status info, if status is "afgekeurd"', () => {
      expect(component.getRowClass(taakAfgekeurdGeenId)).toBeFalsy('');
      expect(component.getRowClass(taakAfgekeurd)).toBe(' afgekeurde-selectietaak');
    });
  });


  describe('TaakComponent.filteren', () => {
    let taakIngepland;
    let taakOpnieuwInTePlannen;
    let taakDagelijksTerugkerend;
    let taakDienstIDBevat1;
    let taakDienstIDBevat2;

    beforeEach(() => {
      taakIngepland = new SelectieTaak();
      taakIngepland.status = SelectieTaakStatus.INGEPLAND;

      taakOpnieuwInTePlannen = new SelectieTaak();
      taakOpnieuwInTePlannen.opnieuwPlannen = true;

      taakDagelijksTerugkerend = new SelectieTaak();
      taakDagelijksTerugkerend.eenheidSelectieInterval = 'Dag';

      taakDienstIDBevat1 = new SelectieTaak();
      taakDienstIDBevat1.dienstId = 1;

      taakDienstIDBevat2 = new SelectieTaak();
      taakDienstIDBevat2.dienstId = 2;
    });

    it('should filter tasks with status "dagelijks terugkerend"', () => {
      component.taken = [taakDagelijksTerugkerend];
      const element = fixture.nativeElement;

      // zet checkbox en trigger event: include dagelijksterugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(1);

      // zet checkbox en trigger event: exclude dagelijksterugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(0);
    });


    it('should filter tasks with status "reeds gepland"', () => {
      component.taken = [taakIngepland];
      const element = fixture.nativeElement;

      // zet checkbox en trigger event: include reeds gepland
      element.querySelector('input[id^="reeds-gepland"]').click();
      expect(element.querySelector('input[id^="reeds-gepland"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(1);

      // zet checkbox en trigger event: exclude reeds gepland
      element.querySelector('input[id^="reeds-gepland"]').click();
      expect(element.querySelector('input[id^="reeds-gepland"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(0);
    });


    it('should filter tasks with status "opnieuw te plannen"', () => {
      component.taken = [taakOpnieuwInTePlannen];
      const element = fixture.nativeElement;

      // zet checkbox en trigger event: include opnieuw te planne
      element.querySelector('input[id^="opnieuw-te-plannen"]').click();
      expect(element.querySelector('input[id^="opnieuw-te-plannen"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(1);

      // zet checkbox en trigger event: exclude dagelijksterugkerend
      element.querySelector('input[id^="opnieuw-te-plannen"]').click();
      expect(element.querySelector('input[id^="opnieuw-te-plannen"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(0);
    });


    it('should filter tasks with status "opnieuw te plannen" and "dagelijks terugkerend"', () => {
      component.taken = [taakDagelijksTerugkerend, taakOpnieuwInTePlannen];
      const element = fixture.nativeElement;

      // include dagelijksterugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(1);

      // include reeds gepland
      element.querySelector('input[id^="opnieuw-te-plannen"]').click();
      expect(element.querySelector('input[id^="opnieuw-te-plannen"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(2);

      // exclude dagelijksterugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(1);

      // exclude reeds gepland
      element.querySelector('input[id^="opnieuw-te-plannen"]').click();
      expect(element.querySelector('input[id^="opnieuw-te-plannen"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(0);
    });


    it('should filter tasks with status "reeds gepland" and "dagelijks terugkerend"', () => {
      component.taken = [taakDagelijksTerugkerend, taakIngepland];
      const element = fixture.nativeElement;

      // include dagelijksterugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(1);

      // include reeds gepland
      element.querySelector('input[id^="reeds-gepland"]').click();
      expect(element.querySelector('input[id^="reeds-gepland"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(2);

      // exclude dagelijksterugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(1);

      // exclude reeds gepland
      element.querySelector('input[id^="reeds-gepland"]').click();
      expect(element.querySelector('input[id^="reeds-gepland"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(0);
    });


    it('should filter tasks with status "reeds gepland" and "opnieuw te plannen"', () => {
      component.taken = [taakOpnieuwInTePlannen, taakIngepland];
      const element = fixture.nativeElement;

      // include dagelijksterugkerend
      element.querySelector('input[id^="opnieuw-te-plannen"]').click();
      expect(element.querySelector('input[id^="opnieuw-te-plannen"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(1);

      // include reeds gepland
      element.querySelector('input[id^="reeds-gepland"]').click();
      expect(element.querySelector('input[id^="reeds-gepland"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(2);

      // exclude dagelijksterugkerend
      element.querySelector('input[id^="opnieuw-te-plannen"]').click();
      expect(element.querySelector('input[id^="opnieuw-te-plannen"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(1);

      // exclude reeds gepland
      element.querySelector('input[id^="reeds-gepland"]').click();
      expect(element.querySelector('input[id^="reeds-gepland"]').checked).toBeFalsy();
      expect(component.rows.length).toBe(0);
    });


    it('should filter tasks correctly when using header and checkbox filters simultaneously', () => {
      // start situatie, 3 taken
      component.taken = [taakDagelijksTerugkerend, taakDienstIDBevat1, taakDienstIDBevat2];
      const element = fixture.nativeElement;

      // include dagelijksterugkerend+trigger filter event
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(3);

      // filter dienst : behoudt enkel dienstId die 2 bevatten,  exclude dagelijks terugkerend + trigger filter event
      component.filterValues = {dienstId: '2'};
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeFalsy();
      // 1 row : enkel taakDienstIDBevat2 blijft behouden
      expect(component.rows.length).toBe(1);

      // geen header-filter, incl dagelijks terugkerend
      component.filterValues = {};
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeTruthy();
      expect(component.rows.length).toBe(3);

      // exclude dagelijks terugkerend
      element.querySelector('input[id^="dagelijks-terugkerend"]').click();
      expect(element.querySelector('input[id^="dagelijks-terugkerend"]').checked).toBeFalsy();
      // alleen rows met dienstID in [1,2] blijven over
      expect(component.rows.length).toBe(2);
    });
  });


  describe('When searching succeeds', () => {
    beforeEach(() => {
      const element = fixture.nativeElement;
      element.querySelector('button[id^="zoeken"]').click();
    });

    it('should add tasks to the component', () => {
      expect(component.taken.length).toBe(2);
    });

    it('should set datatable emptymessage', () => {
      expect(component.dataTableMessages.emptyMessage).toBe('Er zijn geen in te plannen selecties gevonden.');
    });
  });

  describe('When searching fails', () => {
    beforeEach(() => {
      const selectieServiceMock = fixture.debugElement.injector.get(SelectieService);
      selectieServiceMock['fail'] = true;
      const element = fixture.nativeElement;
      element.querySelector('button[id^="zoeken"]').click();
    });

    it('should add no tasks to the component', () => {
      expect(component.taken.length).toBe(0);
    });

    it('should set datatable emptymessage', () => {
      expect(component.dataTableMessages.emptyMessage).toBe('error');
    });
  });

  describe('When searching with begindatum > einddatum', () => {
    beforeEach(() => {
      // Eerst zoeken met een paar resultaten.
      component.zoeken();
      component.zoekForm.controls['beginDatum'].setValue({year: 2017, month: 10, day: 10});
      component.zoekForm.controls['eindDatum'].setValue({year: 2010, month: 10, day: 10});
      component.zoeken();
    });

    it('should unset references', () => {
      expect(component.rows).toEqual([]);
      expect(component.taken).toEqual([]);
    });

    it('should set datatable emptymessage', () => {
      expect(component.dataTableMessages.emptyMessage).toBe('De opgegeven einddatum mag niet voor de begindatum liggen.');
    });
  });
});

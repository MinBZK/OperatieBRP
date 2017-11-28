import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {SelectieService} from '../selectie.service';
import {DatePipe} from '@angular/common';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SelectieTaak} from '../selectie-taak';
import {SelectieTaakStatus} from '../selectie-taak-status.enum';
import {SelectieTaakUpdateEvent} from '../selectie-taak-update-event';
import {DatatableComponent} from '@swimlane/ngx-datatable';
import * as moment from 'moment';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {NgbUtil} from '../../util/ngb-util';
import {DienstService} from '../../autorisatie/dienst.service';
import {Dienst} from '../../autorisatie/dienst';

@Component({
  selector: 'app-selectie-taak',
  templateUrl: './taak.component.html',
  styleUrls: ['./taak.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TaakComponent implements OnInit {
  @ViewChild(DatatableComponent) table: DatatableComponent;
  rows = [];
  filterValues = {};
  selected = [];
  loadingIndicator: boolean;
  taken: SelectieTaak[];
  zoekForm: FormGroup;
  dataTableMessages: any;
  overzichtVerverst: boolean;
  dagelijksTerugkerend: boolean;
  opnieuwTePlannen: boolean;
  reedsGepland: boolean;
  dienst: Dienst;

  private _detailTaken: Map<number, SelectieTaak> = new Map();
  private _datePipe = new DatePipe('nl');

  constructor(private _selectieService: SelectieService, private _dienstService: DienstService, private _fb: FormBuilder) {
    this.dataTableMessages = {
      totalMessage: 'totaal',
      selectedMessage: 'geselecteerd',
      emptyMessage: 'Er is nog niet gezocht op selectietaken.'
    };
  }

  ngOnInit() {
    const beginEindDatum = this._selectieService.bepaalBeginEnEindDatums();
    const beginDatum = {
      year: beginEindDatum.beginDatum.year(),
      month: beginEindDatum.beginDatum.month() + 1,
      day: beginEindDatum.beginDatum.date()
    };
    const eindDatum = {
      year: beginEindDatum.eindDatum.year(),
      month: beginEindDatum.eindDatum.month() + 1,
      day: beginEindDatum.eindDatum.date()
    };
    this.zoekForm = this._fb.group({
      'dagelijksTerugkerend': [false],
      'reedsGepland': [false],
      'opnieuwTePlannen': [false],
      'beginDatum': [beginDatum, Validators.required],
      'eindDatum': [eindDatum, Validators.required],
    });
  }

  zoeken() {
    this.overzichtVerverst = true;
    const beginDatumForm: NgbDateStruct = this.zoekForm.get('beginDatum').value;
    const eindDatumForm: NgbDateStruct = this.zoekForm.get('eindDatum').value;
    const beginDatum = moment(NgbUtil.ngbDateStructToString(beginDatumForm, 'yyyy-MM-dd'));
    const eindDatum = moment(NgbUtil.ngbDateStructToString(eindDatumForm, 'yyyy-MM-dd'));
    if (beginDatum.isAfter(eindDatum)) {
      this.dataTableMessages.emptyMessage = 'De opgegeven einddatum mag niet voor de begindatum liggen.';
      this.taken = this.rows = [];
      return;
    }
    this.selected = [];
    this.loadingIndicator = true;
    this._selectieService.getSelectieTakenBinnenPeriode(beginDatum, eindDatum).subscribe(taken => {
      try {
        this.taken = taken;
        this.applyFilters();
      } finally {
        this.dataTableMessages.emptyMessage = 'Er zijn geen in te plannen selecties gevonden.';
      }
    }, error => {
      this.rows = [];
      let message: string;
      try {
        message = JSON.parse(error._body).error[0];
      } catch (e) {
        message = error._body;
      }
      this.dataTableMessages.emptyMessage = message;
      this.loadingIndicator = false;
    }, () => {
      this.loadingIndicator = false;
      this.filterValues = {};
    });
  }

  onHeaderFilterChange(event, kolomNaam) {
    this.filterValues[kolomNaam] = event.target.value;
    this.applyFilters();
    return true;
  }

  update(event: SelectieTaakUpdateEvent) {
    this.overzichtVerverst = false;
  }

  changeDagelijksTerugkerend(event) {
    this.dagelijksTerugkerend = event.target.checked;
    this.applyFilters();
  }

  changeReedsIngepland(event) {
    this.reedsGepland = event.target.checked;
    this.applyFilters();
  }

  changeOpnieuwInTePlannen(event) {
    this.opnieuwTePlannen = event.target.checked;
    this.applyFilters();
  }

  getRowClass(row): string {
    let rowClass = '';
    if (row.opnieuwPlannen) {
      rowClass += ' gemiste-selectietaak';
    } else if (row.id && row.status === SelectieTaakStatus.INGEPLAND) {
      rowClass += ' opgeslagen-selectietaak';
    }
    if (row.id && row.status === SelectieTaakStatus.AFGEKEURD) {
      rowClass += ' afgekeurde-selectietaak';
    }
    return rowClass;
  }

  onDetailToggle(event) {
    this.dienst = null;
    const taak = this.taken.find(t => t.id === event.value.id);
    this._detailTaken.set(event.value.id, taak);
    this._dienstService.getDienst(taak.dienstId).subscribe(d => this.dienst = d);
  }

  geefDetailTaak(row): SelectieTaak {
    return this._detailTaken.get(row.id);
  }

  toggleExpandRow(row) {
    this.table.rowDetail.toggleExpandRow(row);
  }

  private applyFilters() {
    this.rows = this.applyHeaderFilters(this.applyCheckboxFilters());

    // Whenever the filter changes, always go back to the first page
    this.table.offset = 0;

    if (this.rows.length === 0) {
      this.dataTableMessages.emptyMessage = 'Er zijn geen in te plannen selecties gevonden met de gegeven extra zoekcriteria.';
    }
  }

  private applyHeaderFilters(rowSet): [{}] {
    const tempFilterValues = this.filterValues;
    // filter our data
    const tempRows = rowSet.filter(function (row) {
      let allMatch = true;
      for (const property in tempFilterValues) {
        if (tempFilterValues.hasOwnProperty(property) && row.hasOwnProperty(property)) {
          const filterValue = tempFilterValues[property].toLowerCase();
          allMatch = allMatch && String(row[property]).toLowerCase().indexOf(filterValue) !== -1;
        }
      }
      return allMatch;
    });
    if (tempRows.length === 0) {
      this.dataTableMessages.emptyMessage = 'Er zijn geen in te plannen selecties gevonden met de gegeven extra zoekcriteria.';
    }
    return tempRows;
  }

  private applyCheckboxFilters(): [{}] {
    const dagelijksTerugkerendIsChecked = this.zoekForm.get('dagelijksTerugkerend').value;
    const reedsGeplandIsChecked = this.zoekForm.get('reedsGepland').value;
    const opnieuwInTePlannenIsChecked = this.zoekForm.get('opnieuwTePlannen').value;
    let tempRows;
    if (this.taken != null) {
      tempRows = this.taken
        .filter(taak => !dagelijksTerugkerendIsChecked ? taak.eenheidSelectieInterval !== 'Dag' : taak)
        .filter(taak => !reedsGeplandIsChecked ? taak.status !== SelectieTaakStatus.INGEPLAND : taak)
        .filter(taak => !opnieuwInTePlannenIsChecked ? !taak.opnieuwPlannen : taak)
        .map(taak => this.maakTaakRow(taak));
    }
    return tempRows;
  }

  private maakTaakRow(taak) {
    return {
      id: taak.id,
      dienstId: taak.dienstId,
      toegangLeveringsautorisatieId: taak.toegangLeveringsautorisatieId,
      opnieuwPlannen: taak.opnieuwPlannen,
      status: taak.status,
      afnemercode: taak.afnemerCode,
      partijNaam: taak.afnemerNaam,
      stelsel: taak.stelsel,
      interval: taak.selectieInterval,
      eenheidInterval: taak.eenheidSelectieInterval,
      eersteSelectiedatum: this._datePipe.transform(taak.eersteSelectieDatum, 'dd-MM-yyyy'),
      soort: taak.selectieSoort,
      berekendeSelectiedatum: this._datePipe.transform(taak.berekendeSelectieDatum, 'dd-MM-yyyy'),
      datumPlanning: taak.datumPlanning
    };
  }
}

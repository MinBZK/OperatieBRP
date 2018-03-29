import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SelectieService} from '../selectie.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbUtil} from '../../util/ngb-util';
import {ActivatedRoute} from '@angular/router';
import {CustomValidators} from '../../util/custom-validators';
import {SelectieTaak} from '../selectie-taak';
import {SelectieTaakUpdateEvent} from '../selectie-taak-update-event';
import {Stamdata} from '../../stamdata/stamdata';
import {Dienst} from '../../autorisatie/dienst';
import * as moment from 'moment';

@Component({
  selector: 'app-taak-detail',
  templateUrl: './taak-detail.component.html',
  styleUrls: ['./taak-detail.component.scss']
})
export class TaakDetailComponent implements OnInit {

  @Input() taak: SelectieTaak;
  @Input() dienst: Dienst;
  @Input() index: number;
  @Input() toonNietVerverstMelding: boolean;
  @Output() onUpdate = new EventEmitter<SelectieTaakUpdateEvent>();
  form: FormGroup;
  alleStatussen: Stamdata[];
  alert: IAlert;

  constructor(private _selectieService: SelectieService, private _fb: FormBuilder,
              r: ActivatedRoute) {
    this.alleStatussen = r.snapshot.data.alleSelectieStatussen;
  }

  ngOnInit() {
    const peilmomentFormeelResultaatDatum = this.taak.peilmomentFormeelResultaat != null
      ? this.taak.peilmomentFormeelResultaat.split('T')[0] : null;
    const status = this.alleStatussen.find(s => s.id === this.taak.status);
    const datumPlanning = this.taak.datumPlanning ? NgbUtil.stringToNgbDateStruct(this.taak.datumPlanning) :
      NgbUtil.stringToNgbDateStruct(this.taak.berekendeSelectieDatum);
    this.form = this._fb.group({
      'datumPlanning': [datumPlanning, Validators.required],
      'datumPeilmomentMaterieelRes': [NgbUtil.stringToNgbDateStruct(this.taak.peilmomentMaterieelResultaat),
        CustomValidators.valideerOptioneleDatum],
      'datumPeilmomentFormeelRes': [NgbUtil.stringToNgbDateStruct(peilmomentFormeelResultaatDatum),
        CustomValidators.valideerOptioneleDatum],
      'historievorm': [],
      'selectieStatus': [status && status.id, CustomValidators.valideerSelectieStatus],
      'selectieStatusToelichting': [this.taak.statusToelichting],
      'selectiecriterium': []
    });
    this.form.controls['datumPlanning'].valueChanges.subscribe(v => this.taak.datumPlanning
      = NgbUtil.ngbDateStructToString(v, 'yyyy-MM-dd'));
    this.form.controls['datumPeilmomentMaterieelRes'].valueChanges.subscribe(v => this.taak.peilmomentMaterieelResultaat
      = NgbUtil.ngbDateStructToString(v, 'yyyy-MM-dd'));
    this.form.controls['datumPeilmomentFormeelRes'].valueChanges.subscribe(v => v == null ? v : this.taak.peilmomentFormeelResultaat
      = moment(NgbUtil.ngbDateStructToString(v, 'yyyy-MM-dd')).toISOString());
    this.form.controls['selectieStatus'].valueChanges.subscribe(v => this.taak.status = Number(v));
    this.form.controls['selectieStatusToelichting'].valueChanges.subscribe(v => this.taak.statusToelichting = v);
    if (this.toonNietVerverstMelding) {
      this.alert = {
        type: 'warning',
        message: 'Het overzicht is niet ververst na de laatste wijziging.'
      };
    }
  }

  onSubmit() {
    if (this.form.valid) {
      this.form.updateValueAndValidity();
      this._selectieService.slaSelectieTaakOp(this.taak).subscribe(taak => {
          this.taak = taak;
          this.showAlert({
            type: 'success',
            message: 'De taak is opgeslagen. Ververs het overzicht voor de actuele status.'
          });
          // Event naar parent component voor update.
          this.onUpdate.emit({taak: this.taak});
          this.form.markAsPristine();
        }, error => {
          this.showAlert({
            type: 'danger',
            message: 'Er is iets fout gegaan bij het opslaan van de taak...'
          });
        }
      );
    }
  }

  private showAlert(alert: IAlert) {
    this.alert = alert;
  }

  heeftSelectieInterval(): boolean {
    return this.taak.selectieInterval != null && this.taak.eenheidSelectieInterval != null;
  }
}

interface IAlert {
  type: string;
  message: string;
}

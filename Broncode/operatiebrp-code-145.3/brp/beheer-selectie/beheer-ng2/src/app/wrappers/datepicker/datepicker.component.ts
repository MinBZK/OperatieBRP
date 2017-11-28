import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateNlParserFormatter} from 'app/util/ngb-date-nl-parser-formatter';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.scss'],
  providers: [
    {
      provide: NgbDateParserFormatter,
      useClass: NgbDateNlParserFormatter
    }]
})
export class DatepickerComponent implements OnInit {
  @Input() group: FormGroup;
  @Input() name: string;

  constructor() {
  }

  ngOnInit() {
  }

}

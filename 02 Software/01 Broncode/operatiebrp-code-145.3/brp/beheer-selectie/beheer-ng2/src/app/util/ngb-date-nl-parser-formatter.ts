import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {NgbUtil} from './ngb-util';

export class NgbDateNlParserFormatter extends NgbDateParserFormatter {

  parse(value: string): NgbDateStruct {
    return NgbUtil.stringToNgbDateStruct(value, 'DD-MM-YYYY');
  }

  format(date: NgbDateStruct): string {
    return NgbUtil.ngbDateStructToString(date, 'dd-MM-yyyy');
  }
}

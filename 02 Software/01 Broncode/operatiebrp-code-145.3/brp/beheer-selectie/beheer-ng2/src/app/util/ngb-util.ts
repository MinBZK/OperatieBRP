import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import {DatePipe} from '@angular/common';

export class NgbUtil {

  private static datePipe = new DatePipe('nl');

  public static stringToNgbDateStruct(dateString: string, format?: string): NgbDateStruct {
    if (dateString == null) {
      return null;
    }
    const mom = moment(dateString, format);
    return {
      day: mom.date(),
      month: mom.month() + 1,
      year: mom.year()
    };
  }

  public static ngbDateStructToString(date: NgbDateStruct, format?: string) {
    if (date) {
      return this.datePipe.transform(new Date(date.year, date.month - 1, date.day), format);
    }
    return null;
  }
}

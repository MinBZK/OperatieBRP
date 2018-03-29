import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'selectieIntervalPipe'
})
export class SelectieIntervalPipe implements PipeTransform {

  transform(value: any[], interval: number, eenheid: string): string {
    if (interval && eenheid) {
      let val = null;
      if (interval > 1) {
        val = 'Eens per ' + interval + ' ' + eenheid.toLowerCase() + 'en';
      } else {
        val = 'Elke ' + eenheid.toLowerCase();
      }
      return val;
    }
    return null;
  }

}

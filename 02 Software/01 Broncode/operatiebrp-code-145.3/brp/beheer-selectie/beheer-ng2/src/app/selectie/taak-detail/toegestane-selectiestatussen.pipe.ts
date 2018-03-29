import {Pipe, PipeTransform} from '@angular/core';
import {SelectieTaakStatus} from '../selectie-taak-status.enum';

@Pipe({
  name: 'toegestaneSelectiestatussen'
})
export class ToegestaneSelectiestatussenPipe implements PipeTransform {

  private static STATUS_SET: Set<SelectieTaakStatus> = new Set<SelectieTaakStatus>();

  static initialize() {
    this.STATUS_SET.add(SelectieTaakStatus.IN_TE_PLANNEN);
    this.STATUS_SET.add(SelectieTaakStatus.INGEPLAND);
    this.STATUS_SET.add(SelectieTaakStatus.GEANNULEERD);
    this.STATUS_SET.add(SelectieTaakStatus.NIET_GELEVERD);
    this.STATUS_SET.add(SelectieTaakStatus.AFGEKEURD);
    this.STATUS_SET.add(SelectieTaakStatus.UITVOERING_MISLUKT);
    this.STATUS_SET.add(SelectieTaakStatus.UITVOERING_AFGEBROKEN);
  }

  transform(value: any, args?: any): any {
    if (value) {
      return value.filter(v => ToegestaneSelectiestatussenPipe.STATUS_SET.has(v.id));
    }
    return [];
  }

}

// static initialization
ToegestaneSelectiestatussenPipe.initialize();

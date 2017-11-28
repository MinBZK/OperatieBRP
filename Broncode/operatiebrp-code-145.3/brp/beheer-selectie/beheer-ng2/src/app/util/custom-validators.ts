import {AbstractControl} from '@angular/forms';
import {SelectieTaakStatus} from '../selectie/selectie-taak-status.enum';

export class CustomValidators {

  private static FOUTIEF_STATUSSEN: Set<SelectieTaakStatus> = new Set<SelectieTaakStatus>();

  static initialize() {
    this.FOUTIEF_STATUSSEN.add(SelectieTaakStatus.AFGEKEURD);
    this.FOUTIEF_STATUSSEN.add(SelectieTaakStatus.UITVOERING_MISLUKT);
    this.FOUTIEF_STATUSSEN.add(SelectieTaakStatus.UITVOERING_AFGEBROKEN);
    this.FOUTIEF_STATUSSEN.add(SelectieTaakStatus.UITVOERING_MISLUKT);
    this.FOUTIEF_STATUSSEN.add(SelectieTaakStatus.NIET_GELEVERD);
  }

  static valideerOptioneleDatum(control: AbstractControl) {
    if (!(control.value instanceof Object) && control.value !== null) {
      control.patchValue(null, {
        onlySelf: true,
        emitEvent: false,
        emitModelToViewChange: true,
        emitViewToModelChange: false
      });
    }
  }

  static valideerSelectieStatus(control: AbstractControl) {
    let statusCorrect = true;
    const status = Number(control.value);
    if (CustomValidators.FOUTIEF_STATUSSEN.has(status)) {
      statusCorrect = false;
    }
    return statusCorrect ? null : {statusIncorrect: true};
  }
}

// static initialization
CustomValidators.initialize();

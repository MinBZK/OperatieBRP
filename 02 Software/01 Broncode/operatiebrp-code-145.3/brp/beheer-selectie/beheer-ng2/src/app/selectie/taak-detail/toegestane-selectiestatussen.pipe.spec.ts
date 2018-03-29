import {ToegestaneSelectiestatussenPipe} from './toegestane-selectiestatussen.pipe';
import {SelectieTaakStatus} from '../selectie-taak-status.enum';

describe('ToegestaneSelectiestatussenPipe', () => {
  const pipe = new ToegestaneSelectiestatussenPipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should contain the correct statusses', () => {
    const result = pipe.transform([{id: 1}, {id: 2}, {id: 3}, {id: 4}, {id: 5}, {id: 6}, {id: 7}, {id: 8}, {id: 9}, {id: 10}]);
    expect(result).toEqual([
      {id: SelectieTaakStatus.IN_TE_PLANNEN},
      {id: SelectieTaakStatus.INGEPLAND},
      {id: SelectieTaakStatus.NIET_GELEVERD},
      {id: SelectieTaakStatus.GEANNULEERD},
      {id: SelectieTaakStatus.UITVOERING_MISLUKT},
      {id: SelectieTaakStatus.UITVOERING_AFGEBROKEN},
      {id: SelectieTaakStatus.AFGEKEURD}
    ]);
  });
});

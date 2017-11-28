export class SelectieTaak {
  id: number;
  dienstId: number;
  stelsel: string;
  afnemerCode: string;
  afnemerNaam: string;
  eersteSelectieDatum: any;
  selectieSoort: string;
  berekendeSelectieDatum: any;
  toegangLeveringsautorisatieId: number;
  status: number;

  selectieInterval?: number;
  eenheidSelectieInterval?: string;
  statusToelichting?: string;
  peilmomentMaterieelResultaat?: any;
  peilmomentFormeelResultaat?: any;
  datumPlanning?: any;
  opnieuwPlannen?: boolean;
  selectiecriterium?: string;
}

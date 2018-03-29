Meta:
@status             Klaar
@usecase            SL.1.VA.CAA, SL.1.VA.VVA, SL.0.VA
@regels             R1342, R2591, R2592, R2594
@sleutelwoorden     Selectie Verwijder afnemerindicatie

Narrative:
Testen om steeksproef gewijs vast te stellen dat de volgende use cases worden toegepast op
de dienst Selectie met verwijdering afnemerindicatie:
- SL.1.MST – Maak selectieresultaat totalen - Controleren dat er geen personen resultaat set aanwezig is
- LV.1.SPL – Samenstellen persoonslijst	Steekproef
- LV.1.AFB – Autorisatiefilter bericht	Steekproef
- LV.1.VPB – Verwerk persoon bericht	Steekproef
- LV.1.BO – Bericht opschonen	Steekproef

Scenario: 1.    Selectie met verwijderen afnemerindicatie

Given leveringsautorisatie uit autorisatie/SelectieVerwijderAfnIndPopBepSteekProefProtocolleringGeheim

Given personen uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'SelectieVerwijderenAfnInd' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel                         |
| 1  | vandaag     | Uitvoerbaar | SelectieMetVerwijderenVolledigBericht |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, SELECTIE_UITGEVOERD]     |

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

!-- Geen controle bestand, want nvt voor soort selectie dienst verwijderen
Then zijn er de volgende controlebestanden:
| selectieTaakId | aanwezig | aantalPersonen |
| 1              | nee      | -              |

Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieVerwijderenAfnInd en partij Gemeente Utrecht de afnemerindicatie verwijderd
When het volledigbericht voor leveringsautorisatie SelectieVerwijderenAfnInd is ontvangen en wordt bekeken

!-- Volledig bericht zonder mat / form historie en zonder verantwoording, adres groep ontbreekt want niet geautoriseerd
Then is het synchronisatiebericht gelijk aan Expecteds/Story_3_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon


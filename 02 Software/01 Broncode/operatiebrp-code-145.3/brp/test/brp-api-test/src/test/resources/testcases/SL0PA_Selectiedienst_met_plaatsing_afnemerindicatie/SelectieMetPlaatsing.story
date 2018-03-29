Meta:
@status             Klaar
@sleutelwoorden     Selectie

Narrative:
Selectie met plaatsing afnemerindicatie

Scenario: 1.    Selectie met plaatsing afnemerindicatie
                LT: R2588_LT01, R2667_LT01, R1262_LT29, R1264_LT25, R2056_LT25, R2130_LT25, R2593_LT02, R2673_LT02
                Verwacht resultaat:
                Afnemerindicatie geplaatst en volledig bericht
                Status transitie uitvoerbaar, in uitvoering, selectie uitgevoerd.


Given leveringsautorisatie uit aut/SelectieAutPlaatsAfnemerindicatie
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | SelectieAutPlaatsAfnemerindicatie |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/expected_resultaatset_totalen_1.xml'

And is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht een afnemerindicatie geplaatst
And is er voor persoon met bsn 773201993 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst
And is er voor persoon met bsn 265748185 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst
And is er voor persoon met bsn 632934761 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst
And is er voor persoon met bsn 823826697 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst

When het volledigbericht voor leveringsautorisatie SelectieLeveringautorisatie1 is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/expected_volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, SELECTIE_UITGEVOERD]     |

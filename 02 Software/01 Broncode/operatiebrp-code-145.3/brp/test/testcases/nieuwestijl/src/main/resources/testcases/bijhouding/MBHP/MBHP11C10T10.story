Meta:
@status                 Klaar
@usecase                UCS-BY.1.MP

Narrative: Scenario Wijziging gemeente infrastructureel

Scenario:   Wijziging gemeente infrastructureel bijhoudingsplan
            LT: MBHP11C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP11C10T10.xls

When voer een bijhouding uit MBHP11C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP11C10T10.xml voor expressie //brp:bhg_vbaActualiseerInfrastructureleWijziging_R

Then staat er 0 notificatiebericht voor bijhouders op de queue

Then in kern heeft SELECT gem FROM kern.his_persadres WHERE tsverval is NULL and dateindegel is NULL de volgende gegevens:
| veld | waarde |
| gem  | 7012   |

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen

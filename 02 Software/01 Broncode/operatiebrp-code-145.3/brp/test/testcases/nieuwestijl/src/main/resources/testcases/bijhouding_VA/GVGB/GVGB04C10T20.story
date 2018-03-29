Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Verwerking Verhuizing GBA naar BRP

Scenario:   verhuizing niet-BAG adres met gedeblokkeerde melding
            LT: GVGB04C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-GVGB/GVGB04C10T20-001.xls

When voer een GBA bijhouding uit GVGB04C10T20.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/GVGB/expected/GVGB04C10T20.xml voor expressie /

Then in kern heeft SELECT gem FROM kern.his_persadres WHERE tsverval is NULL and dateindegel is NULL de volgende gegevens:
| veld                      | waarde |
| gem                       | 7012   |

Then staat er 1 notificatiebericht voor bijhouders op de queue
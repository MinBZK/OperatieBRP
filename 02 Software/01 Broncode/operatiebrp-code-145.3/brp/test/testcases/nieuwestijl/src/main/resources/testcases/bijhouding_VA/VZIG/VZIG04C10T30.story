Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Verhuizing intergemeentelijk met hoofdactie Registratie adres

Scenario:   Verhuizing waarbij aangever waarde "G" heeft en de reden waarde "P" heeft
            LT: VZIG04C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG04C10T30-001.xls

When voer een bijhouding uit VZIG04C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG04C10T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT gem FROM kern.his_persadres WHERE tsverval is NULL and dateindegel is NULL de volgende gegevens:
| veld                      | waarde |
| gem                       | 7012   |

Then lees persoon met anummer 6903587105 uit database en vergelijk met expected VZIG04C10T30-persoon1.xml

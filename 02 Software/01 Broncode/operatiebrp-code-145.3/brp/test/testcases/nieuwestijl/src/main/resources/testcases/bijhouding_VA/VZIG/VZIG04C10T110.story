Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Verhuizing intergemeentelijk met hoofdactie Registratie adres

Scenario:   Verhuizing minimaal gevulde velden met adrestype "BAG-adres"
            LT: VZIG04C10T110

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG04C10T110-001.xls

When voer een bijhouding uit VZIG04C10T110.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG04C10T110.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT gem FROM kern.his_persadres WHERE tsverval is NULL and dateindegel is NULL de volgende gegevens:
| veld                      | waarde |
| gem                       | 7012   |

Then lees persoon met anummer 4852546849 uit database en vergelijk met expected VZIG04C10T110-persoon1.xml

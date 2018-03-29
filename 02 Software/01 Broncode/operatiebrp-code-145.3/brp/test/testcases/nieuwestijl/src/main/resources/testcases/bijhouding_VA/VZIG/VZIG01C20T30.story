Meta:
@status                 Klaar
@regels                 R1928
@usecase                UCS-BY.0.VA

Narrative: Persoon wiens adres het betreft moet zelf partner zijn als aangever partner is

Scenario:   R1928 Aangever adreshouding ongelijk aan P en de persoon heeft geen Huwelijk en geen GP
            LT: VZIG01C20T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C20T30-001.xls

When voer een bijhouding uit VZIG01C20T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C20T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |
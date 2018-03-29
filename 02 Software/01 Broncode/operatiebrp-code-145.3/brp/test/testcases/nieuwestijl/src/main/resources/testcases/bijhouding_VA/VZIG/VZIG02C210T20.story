Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1374 Datum aanvang adreshouding moet een volledig bekende datum zijn

Scenario:   Datum aanvang adreshouding is een deels onbekende datum (2017)
            LT: VZIG02C210T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C210T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief


Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C210T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

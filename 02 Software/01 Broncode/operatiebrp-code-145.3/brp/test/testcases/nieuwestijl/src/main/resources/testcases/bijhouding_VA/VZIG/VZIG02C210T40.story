Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1374 Datum aanvang adreshouding moet een volledig bekende datum zijn

Scenario:   Datum aanvang adreshouding waarbij dd onbekend is (2016-01-00)
            LT: VZIG02C210T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C210T40-001.xls

When voer een bijhouding uit VZIG02C210T40.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C210T40.txt

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

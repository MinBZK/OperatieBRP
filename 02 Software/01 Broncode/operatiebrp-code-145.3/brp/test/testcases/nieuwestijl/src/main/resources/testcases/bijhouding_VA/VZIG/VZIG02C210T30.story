Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1374 Datum aanvang adreshouding moet een volledig bekende datum zijn

Scenario:   Datum aanvang adreshouding waarbij mm onbekend is (2005-00-01)
            LT: VZIG02C210T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C210T30.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C210T30.txt

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

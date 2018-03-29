Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2310 Gemeente is verplicht bij een Nederlands adres

Scenario: Gemeentecode zit niet in het request(XML)
          LT: VZIG02C240T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C240T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C240T10.txt

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

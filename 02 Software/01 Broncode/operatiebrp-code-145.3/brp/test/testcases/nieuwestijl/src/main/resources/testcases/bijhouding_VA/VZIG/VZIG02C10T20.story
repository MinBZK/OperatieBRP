Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1356 Postcode moet Nederlands formaat hebben

Scenario: Postcode(4C56DE) is geen geldig Nederlands formaat
          LT: VZIG02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C10T20.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C10T20.txt

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

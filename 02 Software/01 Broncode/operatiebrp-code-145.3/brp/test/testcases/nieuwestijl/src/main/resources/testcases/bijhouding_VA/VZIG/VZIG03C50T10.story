Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1355 Soort adres moet verwijzen naar bestaand stamgegeven

Scenario:  Soort adres verwijst niet naar bestaand stamgegeven X
           LT: VZIG03C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG03C50T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG03C50T10.txt

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |
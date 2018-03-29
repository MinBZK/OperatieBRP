Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1900 Aanduiding verblijfsrecht moet verwijzen naar bestaand stamgegeven

Scenario: R1900 Aanduiding verblijfsrecht verwijst niet naar een bestaand stamgegeven
          LT: WZVB03C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB03C10T10-001.xls

When voer een bijhouding uit WZVB03C10T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB03C10T10.xml

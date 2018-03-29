Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2397 Verwerking Wijziging Verblijfsrecht met code 98

Scenario: Simpele bijhouding Wijziging Verblijfsrecht van niet-Nederlander WZVB02C50T10
          LT: WZVB02C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB-001.xls

When voer een bijhouding uit WZVB02C50T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB02C50T10.xml



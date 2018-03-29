Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2750 Aanduiding verblijfsrecht moet  geldig zijn op datum aanvang verblijfsrecht

Scenario: Aanduiding verblijfsrecht is niet geldig op peildatum
          LT: WZVB02C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB02C60T10-001.xls

When voer een bijhouding uit WZVB02C60T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB02C60T10.xml

Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2349 Datum aanvang verblijfsrecht moet een volledig bekende datum zijn

Scenario: Volledig onbekend voor datum aanvang verblijfsrecht
          LT: WZVB02C20T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB02C20T30-001.xls

When voer een bijhouding uit WZVB02C20T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB02C20T40.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R
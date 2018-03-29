Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2331 Datum voorzien einde verblijfsrecht moet na de datum aanvang verblijfsrecht liggen

Scenario: Datum voorzien einde verblijfsrecht ligt op de datum aanvang verblijfsrecht
          LT: WZVB02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB-001.xls

When voer een bijhouding uit WZVB02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB02C10T20.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R



Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2332 Datum aanvang verblijfsrecht mag niet in de toekomst liggen

Scenario: Datum aanvang verblijfsrecht ligt op de systeemdatum
          LT: WZVB01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB01C40T10.xls

When voer een bijhouding uit WZVB01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB01C40T10.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

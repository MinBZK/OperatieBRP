Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2326 Registratie verblijfsrecht niet toegestaan als de persoon wordt behandeld als Nederlander

Scenario: Registratie verblijfsrecht bij een persoon dat wordt behandeld als Nederlander
          LT: WZVB01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB01C20T10-001.xls

When voer een bijhouding uit WZVB01C20T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB01C20T10.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

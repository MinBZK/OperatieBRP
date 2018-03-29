Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1376 Locatie ten opzichte van adres mag alleen toegestane waarde bevatten

Scenario: locatieTenOpzichteVanAdres(by) is  geldig
          LT: VZIG02C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C20T20-001.xls

When voer een bijhouding uit VZIG02C20T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C20T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
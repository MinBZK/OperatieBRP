Meta:
@status                 Klaar
@regels                 R2352
@usecase                UCS-BY.0.VA

Narrative: R2352 Registratie of een beëindiging van bijzondere verblijsfrechtelijke positie is toegestaan, niet beide in 1 bijhoudingsbericht

Scenario: R2352 Beëindiging van bijzondere verblijsfrechtelijke positie is in een bijhoudingsbericht
          LT: WBVP02C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WBVP/WBVP-001-02C10T30.xls

When voer een bijhouding uit WBVP02C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WBVP/expected/WBVP02C10T30.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

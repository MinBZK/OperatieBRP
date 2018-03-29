Meta:
@status                 Klaar
@regels                 R2502
@usecase                UCS-BY.HG

Narrative: R2502 Betrokkenen in dezelfde relatie mogen niet hetzelfde A- en/of BSN nummer hebben.

Scenario: Twee ingeschrevenen hebben een verschillend anummer maar hetzelfde bsn
          LT: VHNL01C400T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C400T10_anummer1.xls

When voer een bijhouding uit VHNL01C400T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C400T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

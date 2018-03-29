Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2617 OUWKIG moet bekende persoon zijn

Scenario: OUWKIG moet een bekend persoon zijn
          LT: GBNL01C300T10

!-- Vulling van de NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C300T10-001.xls

When voer een bijhouding uit GBNL01C300T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C300T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
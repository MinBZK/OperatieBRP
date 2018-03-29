Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2455 Er moet precies één OUWKIG geregistreerd worden

Scenario: Er worden twee OUWKIGs geregistreerd
          LT: GBNL02C130T20

Given alle personen zijn verwijderd

!-- Vulling van twee OUWKIGs
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C130T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C130T20-002.xls

!-- Geboorte van de sibling waar de OUWKIG twee keer voor komt
When voer een bijhouding uit GBNL02C130T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C130T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
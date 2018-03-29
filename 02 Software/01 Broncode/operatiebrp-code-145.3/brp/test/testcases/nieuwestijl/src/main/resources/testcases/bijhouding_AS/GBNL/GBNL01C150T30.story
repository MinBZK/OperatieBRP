Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1743 OUWKIG moet ingezetene zijn

Scenario: OUWKIG is in het verleden ingezetene en actueel niet-ingezetene, geboorte tijdens verblijf binnenland
          LT: GBNL01C150T30

Given alle personen zijn verwijderd

!-- Vulling van de OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C150T30-001.xls

When voer een bijhouding uit GBNL01C150T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C150T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
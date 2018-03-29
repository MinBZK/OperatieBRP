Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1743 OUWKIG moet ingezetene zijn

Scenario: OUWKIG is geen ingezetene bij registratie geborene
          LT: GBNL01C150T10

Given alle personen zijn verwijderd

!-- Vulling van de OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C150T10-001.xls

When voer een bijhouding uit GBNL01C150T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C150T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
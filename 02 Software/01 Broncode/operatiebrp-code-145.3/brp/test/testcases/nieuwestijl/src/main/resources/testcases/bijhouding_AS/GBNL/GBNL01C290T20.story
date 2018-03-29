Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1732 Ouder mag geen kinderen hebben met identieke personalia

Scenario: De sibling is een pseudo-persoon en de personalia van de kinderen zijn gelijk
          LT: GBNL01C290T20

Given alle personen zijn verwijderd

!-- Vulling van de OUWKIG met pseudo-persoon sibling
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C290T20-001.xls

!-- Geboorte van het kind
When voer een bijhouding uit GBNL01C290T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C290T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
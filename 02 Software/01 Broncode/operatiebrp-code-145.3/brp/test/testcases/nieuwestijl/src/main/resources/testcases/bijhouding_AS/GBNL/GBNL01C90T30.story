Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1721 NOUWKIG verplicht als daarvoor kandidaat is

Scenario: meerdere NOUWKIGs (1 overleden partner van OUWKIG 306 dagen geleden en 1 actueel) aanwezig als kandidaat
          LT: GBNL01C90T30

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C90T30-001.xls

!-- Geboorte
When voer een bijhouding uit GBNL01C90T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C90T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

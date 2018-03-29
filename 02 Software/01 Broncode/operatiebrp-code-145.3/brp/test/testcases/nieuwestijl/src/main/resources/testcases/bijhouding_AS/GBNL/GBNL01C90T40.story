Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1721 NOUWKIG verplicht als daarvoor kandidaat is

Scenario: NOUWKIG (overleden partner van OUWKIG meer dan 306 dagen) aanwezig als kandidaat
          LT: GBNL01C90T40

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C90T40-001.xls

!-- Geboorte
When voer een bijhouding uit GBNL01C90T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C90T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

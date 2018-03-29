Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1731 Kind moet een predicaat of adellijke titel hebben als de vader met identieke geslachtsnaam dat ook heeft

Scenario: Ingezetene (man) heeft predicaat en  Persoon.Samengestelde naam gelijk aan Kind behalve voorvoegsel , Kind geen predicaat
          LT: GBNL01C130T40

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C130T40-001.xls

!-- Geboorte
When voer een bijhouding uit GBNL01C130T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C130T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

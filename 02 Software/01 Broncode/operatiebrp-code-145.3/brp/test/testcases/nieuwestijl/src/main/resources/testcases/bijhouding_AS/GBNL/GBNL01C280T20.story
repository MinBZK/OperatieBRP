Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2543 Kind mag alleen het predicaat of de adellijke titel van de vader voeren, als de geslachtsnaam gelijk is aan die van de vader

Scenario: Stam van vader verschilt van kind dat een predicaat heeft
          LT: GBNL01C280T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C280T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C280T20-002.xls

When voer een bijhouding uit GBNL01C280T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C280T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
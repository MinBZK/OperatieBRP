Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2543 Kind mag alleen het predicaat of de adellijke titel van de vader voeren, als de geslachtsnaam gelijk is aan die van de vader

Scenario: De vader heeft een predicaat en het kind heeft een adellijke titel
          LT: GBNL01C280T70

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C280T70-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C280T70-002.xls

When voer een bijhouding uit GBNL01C280T70.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C280T70.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
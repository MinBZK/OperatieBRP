Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1697 Persoon met de Nederlandse nationaliteit moet een voornaam hebben.

Scenario: Persoon met de NL nationaliteit heeft geen voornaam
          LT: GBNL02C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

When voer een bijhouding uit GBNL02C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C40T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

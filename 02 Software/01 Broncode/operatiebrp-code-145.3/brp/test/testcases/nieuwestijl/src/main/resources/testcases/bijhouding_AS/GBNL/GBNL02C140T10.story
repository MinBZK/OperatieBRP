Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2544 Reden verkrijging mag alleen gevuld zijn bij verkrijging van de Nederlandse nationaliteit

Scenario: Reden is ingevuld bij verkrijging buitenlandse Nationaliteit
          LT: GBNL02C140T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C140T10-001.xls

When voer een bijhouding uit GBNL02C140T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C140T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
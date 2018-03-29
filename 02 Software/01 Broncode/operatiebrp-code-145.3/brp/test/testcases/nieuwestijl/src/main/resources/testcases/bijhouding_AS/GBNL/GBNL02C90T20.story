Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1920 Volgnummers voornamen moeten uniek en aaneensluitend zijn

Scenario: Volgnummer voornaam niet aansluitend xxxxxxxxx
          LT: GBNL02C90T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C90.xls

When voer een bijhouding uit GBNL02C90T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C90T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R


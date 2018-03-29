Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1688 Reden verplicht bij verkrijging Nederlandse nationaliteit

Scenario: Reden verkrijging is niet aanwezig bij een verkrijging Nederlandse nationaliteit
          LT: GBNL02C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C30-002.xls

When voer een bijhouding uit GBNL02C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C30T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
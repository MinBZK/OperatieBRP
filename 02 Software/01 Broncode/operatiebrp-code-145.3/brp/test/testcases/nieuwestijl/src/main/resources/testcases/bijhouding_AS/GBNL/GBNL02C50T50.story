Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1708 Reden verkrijging moet verwijzen naar geldig stamgegeven

Scenario: peildatum is later dan DEG van stamgegeven.
          LT: GBNL02C50T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C50-002.xls

When voer een bijhouding uit GBNL02C50T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C50T50.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

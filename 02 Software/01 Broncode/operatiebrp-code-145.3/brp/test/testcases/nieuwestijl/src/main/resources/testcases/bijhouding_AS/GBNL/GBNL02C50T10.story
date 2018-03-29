Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1708 Reden verkrijging moet verwijzen naar geldig stamgegeven

Scenario: 1. DB init
          preconditie


Given de database is aangepast met: update kern.rdnverknlnation set dateindegel = '20170101', dataanvgel = '20160101' where code = '185'
Given maak bijhouding caches leeg

Scenario: 2. peildatum is gelijk aan DAG van stamgegeven en kleiner dan DEG van stamgegeven.
          LT: GBNL02C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C50T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C50T10-002.xls



When voer een bijhouding uit GBNL02C50T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C50T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

Scenario: 2. DB reset
          postconditie
Given de database is aangepast met: update kern.rdnverknlnation set dateindegel = NULL, dataanvgel = '20101001' where code = '185'
Given maak bijhouding caches leeg
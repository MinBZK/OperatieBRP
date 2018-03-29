Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1685 Nationaliteit moet verwijzen naar een bestaand stamgegeven

Scenario: Nationaliteit komt niet voor als stamgegeven
          LT: GBNL03C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL03C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL03C10T10-002.xls

When voer een bijhouding uit GBNL03C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL03C10T10.xml voor expressie /

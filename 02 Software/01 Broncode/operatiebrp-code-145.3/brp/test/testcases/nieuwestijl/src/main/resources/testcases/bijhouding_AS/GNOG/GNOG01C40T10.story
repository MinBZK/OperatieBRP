Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1736 Erkenner moet minstens 16 jaar zijn

Scenario:   Erkenner is jonger dan 16 jaar xxxxxxxxx
            LT: GNOG01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C40T10-002.xls

When voer een bijhouding uit GNOG01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNOG/expected/GNOG01C40T10.xml voor expressie /
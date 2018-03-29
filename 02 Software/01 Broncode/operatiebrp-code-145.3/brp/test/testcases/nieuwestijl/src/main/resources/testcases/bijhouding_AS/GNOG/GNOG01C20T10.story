Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1695 Door kind te verkrijgen nationaliteit moet bestaan bij ouder

Scenario: Door kind te verkrijgen nationaliteit bestaat bij geen van de ouders
          LT: GNOG01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C20T10-002.xls

When voer een bijhouding uit GNOG01C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNOG/expected/GNOG01C20T10.xml voor expressie /

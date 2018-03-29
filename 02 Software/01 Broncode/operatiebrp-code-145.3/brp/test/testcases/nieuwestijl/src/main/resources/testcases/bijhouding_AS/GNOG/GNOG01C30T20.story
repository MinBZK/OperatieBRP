Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1729 Waarschuwen als erkenner onder curatele staat

Scenario: Op Datum aanvang geldigheid staat een NOUWKIG erkenner onder curatele
          LT: GNOG01C30T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C30T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C30T20-002.xls

When voer een bijhouding uit GNOG01C30T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNOG/expected/GNOG01C30T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
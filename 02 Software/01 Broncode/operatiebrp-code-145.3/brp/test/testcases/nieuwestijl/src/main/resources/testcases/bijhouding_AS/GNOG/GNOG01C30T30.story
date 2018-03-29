Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1729 Waarschuwen als erkenner onder curatele staat

Scenario: Op Datum aanvang geldigheid staat een erkenner niet meer onder curatele
          LT: GNOG01C30T30

Given alle personen zijn verwijderd
!-- OUWKIG die inmiddels niet meer onder curatele staat en OUWKIG niet onder curatele
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C30T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG01C30T30-002.xls

When voer een bijhouding uit GNOG01C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNOG/expected/GNOG01C30T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
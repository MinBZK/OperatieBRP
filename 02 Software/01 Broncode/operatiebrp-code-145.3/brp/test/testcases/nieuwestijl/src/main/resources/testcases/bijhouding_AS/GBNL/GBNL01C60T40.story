Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1695 Door kind te verkrijgen nationaliteit moet bestaan bij ouder

Scenario: Door kind te verkrijgen nationaliteit bestaat bij geen van de ouders maar er is een sterker brondocument voor nationaliteit
          LT: GBNL01C60T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C60T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C60T40-002.xls

When voer een bijhouding uit GBNL01C60T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C60T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

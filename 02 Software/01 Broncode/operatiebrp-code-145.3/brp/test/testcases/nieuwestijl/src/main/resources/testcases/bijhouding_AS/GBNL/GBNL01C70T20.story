Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1696 Persoon met Nederlandse nationaliteit mag geen namenreeks hebben

Scenario: Registratie geborene met nationaliteit anders dan NL en namenreeks = "J"
          LT: GBNL01C70T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C70T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C70T20-002.xls

When voer een bijhouding uit GBNL01C70T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C70T20.xml voor expressie /

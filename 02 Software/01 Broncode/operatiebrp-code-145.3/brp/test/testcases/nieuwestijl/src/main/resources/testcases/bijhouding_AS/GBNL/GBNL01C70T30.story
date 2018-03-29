Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1696 Persoon met Nederlandse nationaliteit mag geen namenreeks hebben

Scenario: Registratie geborene met NL nationaliteit en namenreeks = "N"
          LT: GBNL01C70T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

When voer een bijhouding uit GBNL01C70T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C70T30.xml voor expressie /

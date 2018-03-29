Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1250 Datum aanvang geldigheid moet gelijk zijn aan geboortedatum kind

Scenario: Datum geboorte van het kind ligt na de Datum aanvang geldigheid van de Actie
          LT: GBNL02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

When voer een bijhouding uit GBNL02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C10T20.xml voor expressie /



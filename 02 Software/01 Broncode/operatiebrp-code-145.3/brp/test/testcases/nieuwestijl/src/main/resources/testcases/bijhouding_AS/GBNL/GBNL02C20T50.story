Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1468 Datum geboorte van het kind moet na de datum geboorte van de ouder(s) liggen

Scenario: Datum geboorte van het kind ligt na de datum geboorte beide ouders
          LT: GBNL02C20T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C20T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C20T50-002.xls

When voer een bijhouding uit GBNL02C20T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C20T50.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

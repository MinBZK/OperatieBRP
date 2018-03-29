Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2475 Datum aanvang geldigheid van de actie moet gelijk zijn aan datum erkenning

Scenario:   Datum aanvang geldigheid actie later dan datum erkenning
            LT: GNNG02C60T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C60-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C60-002.xls

When voer een bijhouding uit GNNG02C60T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C60T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
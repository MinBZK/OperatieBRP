Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2488 Datum aanvang geldigheid van de actie moet gelijk zijn aan datum geboorte of datum erkenning

Scenario:   Datum aanvang geldigheid actie niet gelijk aan datum geboorte en gelijk aan datum erkenning
            LT: GNNG02C80T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C80T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C80T30-002.xls

When voer een bijhouding uit GNNG02C80T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C80T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
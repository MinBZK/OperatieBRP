Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2783 Datum erkenning mag maximaal 8 dagen na datum geboorte van het kind liggen

Scenario:   Datum erkenning is 8 dagen na datum geboorte van het kind
            LT: GNNG02C110T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C110T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C110T20-002.xls

When voer een bijhouding uit GNNG02C110T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C110T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
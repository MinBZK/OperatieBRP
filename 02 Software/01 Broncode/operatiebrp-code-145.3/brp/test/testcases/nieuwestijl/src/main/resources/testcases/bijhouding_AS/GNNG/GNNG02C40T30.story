Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2451 Datum einde geldigheid van de actie moet gelijk zijn aan datum erkenning

Scenario:   Datum einde geldigheid Actie later dan Datum aanvang geldigheid ouderschap van erkenner
            LT: GNNG02C40T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C40T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C40T30-002.xls

When voer een bijhouding uit GNNG02C40T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C40T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
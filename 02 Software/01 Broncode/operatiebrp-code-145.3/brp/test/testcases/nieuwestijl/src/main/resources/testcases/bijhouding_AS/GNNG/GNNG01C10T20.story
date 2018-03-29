Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1658 Datum einde geldigheid van de actie moet op of na datum aanvang geldigheid liggen

Scenario:   Datum einde geldigheid van een actie ligt op de datum aanvang geldigheid
            LT: GNNG01C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C10T20-002.xls

When voer een bijhouding uit GNNG01C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG01C10T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2747 Datum aanvang geldigheid van de gewijzigde Voornaam moet recenter zijn dan de Datum aanvang geldigheid van het beëindigde voorkomen van de Voornaam en Samengestelde naam

Scenario:   Datum aanvang geldigheid van de gewijzigde Voornaam is eerder dan de Datum aanvang geldigheid van de te beeindigen voorkomen van de Voornaam en Samengestelde naam
            LT: GNNG01C120T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C110T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C110T10-002.xls

When voer een bijhouding uit GNNG01C120T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG01C120T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
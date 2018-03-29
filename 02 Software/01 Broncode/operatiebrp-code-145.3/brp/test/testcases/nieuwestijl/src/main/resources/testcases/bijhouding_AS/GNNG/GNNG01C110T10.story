Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2746 Datum aanvang geldigheid van het gewijzigde Geslachtsnaamcomponent moet recenter zijn dan de Datum aanvang geldigheid van het beeindigde voorkomen van de Geslachtsnaamcomponent en Samengestelde naam

Scenario:   Datum aanvang geldigheid van de gewijzigde Geslachtsnaamcomponent is eerder dan de Datum aanvang geldigheid van de te beeindigen voorkomen van de Geslachtsnaamcomponent en Samengestelde naam
            LT: GNNG01C110T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C110T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C110T10-002.xls

When voer een bijhouding uit GNNG01C110T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG01C110T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
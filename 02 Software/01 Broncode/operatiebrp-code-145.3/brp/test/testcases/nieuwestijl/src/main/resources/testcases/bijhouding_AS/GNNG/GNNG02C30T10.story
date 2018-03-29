Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2450 Datum erkenning moet na datum geboorte van het kind liggen

Scenario:   Datum aanvang geldigheid van de actie Registratie ouder ligt op de Persoon.Datum geboorte van het Kind
            LT: GNNG02C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C30T10-002.xls

When voer een bijhouding uit GNNG02C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C30T10.xml voor expressie /
Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2522 Datum aanvang geldigheid van de te verliezen nationaliteit moet gelijk zijn aan datum geboorte

Scenario:   Datum aanvang geldigheid van de te verliezen nationaliteit eerder dan datum geboorte
            LT: GNNG02C90T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C90T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C90T10-002.xls

When voer een bijhouding uit GNNG02C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C90T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
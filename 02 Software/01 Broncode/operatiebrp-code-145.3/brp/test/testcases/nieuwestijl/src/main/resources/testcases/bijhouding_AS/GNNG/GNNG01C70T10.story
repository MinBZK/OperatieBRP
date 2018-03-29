Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2011 Voor een persoon met de Nederlandse nationaliteit wordt uitsluitend deze nationaliteit bijgehouden

Scenario:   Persoon heeft de Nederlandse nationaliteit en er wordt een niet-NL nationaliteit geregistreerd
            LT: GNNG01C70T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C70T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C70T10-002.xls

When voer een bijhouding uit GNNG01C70T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG01C70T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
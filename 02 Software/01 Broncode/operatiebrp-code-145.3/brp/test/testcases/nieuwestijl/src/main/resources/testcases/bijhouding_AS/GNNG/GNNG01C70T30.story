Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2011 Voor een persoon met de Nederlandse nationaliteit wordt uitsluitend deze nationaliteit bijgehouden

Scenario:   Persoon heeft een vervallen NL nationaliteit en er wordt een niet-NL nationaliteit geregistreerd
            LT: GNNG01C70T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C70T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C70T30-002.xls

When voer een bijhouding uit GNNG01C70T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

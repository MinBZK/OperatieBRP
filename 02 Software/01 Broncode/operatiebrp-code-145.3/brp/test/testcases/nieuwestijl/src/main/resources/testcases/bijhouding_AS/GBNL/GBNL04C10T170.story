Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Registratie geborene

Scenario:   Registratie geborene in Nederland met OUWKIG(I) en een onbekende NOUWKIG en daarna een intergemeentelijke verhuizing
            LT: GBNL04C10T170

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T20-001.xls

When voer een bijhouding uit GBNL04C10T170a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit GBNL04C10T170b.xml namens partij 'Gemeente BRP 2'

Then heeft het antwoordbericht verwerking Geslaagd


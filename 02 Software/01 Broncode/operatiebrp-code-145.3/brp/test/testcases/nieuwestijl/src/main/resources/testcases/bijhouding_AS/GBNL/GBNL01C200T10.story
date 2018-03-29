Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1816 Staatloos en Nationaliteit sluiten elkaar uit

Scenario:   Indicator staatloos en nationaliteit wordt geregistreerd bij de geboorte
            LT: GBNL01C200T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

When voer een bijhouding uit GBNL01C200T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief


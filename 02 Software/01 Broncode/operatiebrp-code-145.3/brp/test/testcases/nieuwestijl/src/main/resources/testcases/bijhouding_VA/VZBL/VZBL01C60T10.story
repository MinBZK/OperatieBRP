Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2388 Bij een verhuizing naar het buitenland mag de persoon geen bijzondere verblijfsrechtelijke positie hebben

Scenario:   doe een bijhouding waarbij de Persoon de indicatie BVP heeft
            LT: VZBL01C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C60T10.xls

When voer een bijhouding uit VZBL01C60T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C60T10.xml voor expressie /

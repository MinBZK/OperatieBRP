Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1669 Buitenlands adres regel mag alleen zijn opgegeven bij emigratie naar bekend land

Scenario:   voer een foutieve verhuizing naar het buitenland uit waarbij de landcode onbekend en adres regel niet gevuld
            LT: VZBL02C60T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL02C60T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C60T20.xml voor expressie /



Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2366 Aangever migratie moet verwijzen naar bestaand stamgegeven

Scenario:   Persoon.Aangever migratie is geen stamgegeven
            LT: VZBL03C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL03C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL03C10T10.xml voor expressie /



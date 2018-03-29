Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1377 Buitenlands adres regel migratie mag uit niet meer dan 35 karakters bestaan

Scenario:   Persoon.Buitenlands adres regel 1 migratie heeft 35 karakters
            LT: VZBL02C90T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL02C90T10.xls

When voer een bijhouding uit VZBL02C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C90T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
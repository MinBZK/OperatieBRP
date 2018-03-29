Meta:
@status                 Klaar
@regels                 R2427
@usecase                UCS-BY.0.VA

Narrative: R2427 Aangever migratie mag alleen ingevuld zijn bij aangifte door persoon

Scenario:   R2427 Aangever migratie is ingevuld bij een andere reden dan een aangifte door een persoon
            LT: VZBL02C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL02C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C30T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

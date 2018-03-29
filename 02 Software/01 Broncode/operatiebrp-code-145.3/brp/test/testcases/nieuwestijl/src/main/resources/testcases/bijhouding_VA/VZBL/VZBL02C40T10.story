Meta:
@status                 Klaar
@regels                 R2363
@usecase                UCS-BY.0.VA

Narrative: R2363 Aangever migratie moet ingevuld zijn bij aangifte door persoon

Scenario:   R2363 Aangever migratie is niet ingevuld bij reden wijziging migratie met de waarde P
            LT: VZBL02C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL02C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C40T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

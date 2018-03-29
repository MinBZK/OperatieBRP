Meta:
@status                 Klaar
@regels                 R2367
@usecase                UCS-BY.0.VA

Narrative: R2367 Migrant moet bevoegd zijn als aangever ingeschrevene is

Scenario:   R2367 doe een bijhouding waarbij de aangever Ingeschrevene is en de persoon staat onder curatele
            LT: VZBL01C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C10T20.xls

When voer een bijhouding uit VZBL01C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C10T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

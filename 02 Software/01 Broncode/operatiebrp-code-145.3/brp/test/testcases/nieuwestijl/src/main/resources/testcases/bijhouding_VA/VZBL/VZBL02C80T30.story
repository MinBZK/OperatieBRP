Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2362 Opgaaf van reden wijziging migratie met waarde Technische wijzigingen i.v.m. BAG, Infrastructurele wijziging of Onbekend is niet toegestaan

Scenario:   De reden wijziging migratie is infrastructurele wijziging
            LT: VZBL02C80T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL02C80T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C80T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Meta:
@status                 Klaar
@regels                 R2358
@usecase                UCS-BY.0.VA

Narrative: R2358 Datum aanvang geldigheid van nieuw adres moet na de datum aanvang geldigheid van het huidige adres liggen

Scenario:   R2358 Datum aanvang geldigheid van nieuw adres ligt op de datum aanvang geldigheid van het huidige adres
            LT: VZIG02C370T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C370T20-001.xls

When voer een bijhouding uit VZIG02C370T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C370T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
Meta:
@status                 Klaar
@regels                 R2358
@usecase                UCS-BY.0.VA

Narrative: R2358 Datum aanvang geldigheid van nieuw adres moet na de datum aanvang geldigheid van het huidige adres liggen

Scenario:   R2358 Datum aanvang geldigheid van nieuw adres ligt na de datum aanvang geldigheid van het huidige adres
            LT: VZIG02C370T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C370T30-001.xls

When voer een bijhouding uit VZIG02C370T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C370T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld  | waarde |
| count | 2      |
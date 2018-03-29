Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2354 Datum aanvang geldigheid van de actie mag niet in de toekomst liggen

Scenario:   Datum aanvang geldigheid van de Actie ligt in de toekomst(morgen)
            LT: VZIG02C330T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C330T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C330T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |
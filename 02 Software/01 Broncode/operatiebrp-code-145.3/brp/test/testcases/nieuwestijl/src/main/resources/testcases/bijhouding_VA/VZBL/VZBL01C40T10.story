Meta:
@status                 Klaar
@regels                 R2370
@usecase                UCS-BY.0.VA

Narrative: R2370 Emigrant moet zelf ouder zijn als aangever meerderjarig kind is

Scenario:   R2370 Aangever is geen kind
            LT: VZBL01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C40T10.xls

When voer een bijhouding uit VZBL01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C40T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1836 Nevenactie mag alleen betrekking hebben op een hoofdpersoon

Scenario:   Nevenactie heeft geen betrekking op hoofdpersoon
            LT: VZIG01C110T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C110T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C110T10-002.xls

When voer een bijhouding uit VZIG01C110T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C110T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
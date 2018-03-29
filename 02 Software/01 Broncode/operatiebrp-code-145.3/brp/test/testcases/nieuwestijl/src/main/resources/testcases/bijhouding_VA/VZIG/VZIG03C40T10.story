Meta:
@status                 Klaar
@regels                 R1925
@usecase                UCS-BY.0.VA

Narrative: R1925 Verhuizing intergemeentelijk met hoofdactie Registratie adres

Scenario:   R1925 Verhuizing waarbij aangever waarde "X" heeft
            LT: VZIG03C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG03C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG03C40T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
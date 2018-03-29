Meta:
@status                 Klaar
@regels                 R2361
@usecase                UCS-BY.0.VA

Narrative: R2361 Registratie verhuizing buitenland land nederland

Scenario:   voer een foutieve verhuizing naar het buitenland uit waarbij reden ongeldig stamgegeven is
            LT: VZBL03C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL03C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL03C30T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

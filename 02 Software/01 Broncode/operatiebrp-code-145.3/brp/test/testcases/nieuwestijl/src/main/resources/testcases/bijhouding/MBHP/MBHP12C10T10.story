Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Registratie verhuizing buitenland

Scenario:   Verwerking hoofdactie Registratie migratie. Aangever is Ingeschrevene.
            LT: MBHP12C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP12C10T10-001.xls

When voer een bijhouding uit MBHP12C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP12C10T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen

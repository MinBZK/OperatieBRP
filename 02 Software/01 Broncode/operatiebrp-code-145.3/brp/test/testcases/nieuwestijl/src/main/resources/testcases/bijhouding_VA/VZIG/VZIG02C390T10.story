Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2672 Datum aanvang geldigheid van de actie moet na de datum aanvang geldigheid van actuele voorkomen van groep Bijhouding liggen

Scenario:   Actie.Datum aanvang geldigheid ligt voor de Persoon.Bijhouding.Datum aanvang geldigheid van de actuele Persoon.Bijhouding 
            LT: VZIG02C390T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C390T10-001.xls

When voer een bijhouding uit VZIG02C390T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C390T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
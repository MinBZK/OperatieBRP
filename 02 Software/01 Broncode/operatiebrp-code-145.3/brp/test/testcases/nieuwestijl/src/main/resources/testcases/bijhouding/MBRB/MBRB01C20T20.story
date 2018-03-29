Meta:
@auteur                 tjlee
@status                 Klaar
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario:   Melding soortNaam Deblokkeerbaar. In een H-relatie zijn 3 partners betrokken.
            LT: MBRB01C20T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C20T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C20T20-Piet.xls

When voer een bijhouding uit MBRB01C20T20.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C20T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R











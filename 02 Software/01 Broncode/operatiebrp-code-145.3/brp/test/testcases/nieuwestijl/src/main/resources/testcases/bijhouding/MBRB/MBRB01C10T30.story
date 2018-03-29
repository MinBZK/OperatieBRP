Meta:
@auteur                 fuman
@status                 Klaar
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario:   Bericht met Resultaat Verwerking 'Geslaagd' met 'Gedeblokkeerde meldingen'
            LT: MBRB01C10T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_met_his_rel.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit MBRB01C10T30.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R










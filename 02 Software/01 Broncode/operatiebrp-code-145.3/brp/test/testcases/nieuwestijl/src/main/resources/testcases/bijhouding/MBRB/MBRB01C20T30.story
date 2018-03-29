Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1861
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario: Melding soortNaam Fout. R1861 (BRAL2104) H/GP mag alleen door betrokken gemeente worden geregistreerd
          LT: MBRB01C20T30



Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C20T30-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C20T30-danny.xls

When voer een bijhouding uit MBRB01C20T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C20T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


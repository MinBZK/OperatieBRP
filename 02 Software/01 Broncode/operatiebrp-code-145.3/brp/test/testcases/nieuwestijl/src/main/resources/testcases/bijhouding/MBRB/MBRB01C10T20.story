Meta:
@auteur                 fuman
@status                 Klaar
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario: Bericht met Resultaat Verwerking 'Foutief' (niet deblokkeerbaar)
          LT: MBRB01C10T20



Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-danny.xls

When voer een bijhouding uit MBRB01C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R




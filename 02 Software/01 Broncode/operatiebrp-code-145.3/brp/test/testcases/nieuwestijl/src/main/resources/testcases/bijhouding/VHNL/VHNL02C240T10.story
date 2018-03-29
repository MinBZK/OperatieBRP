Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1836 Nevenactie mag alleen betrekking hebben op een hoofdpersoon

Scenario: Nevenactie waarbij 1 partner 'Pseudo-persoon' (P) van de hoofdactie betrokken is
          LT: VHNL02C240T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C240T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C240T10-danny.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C240T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C240T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

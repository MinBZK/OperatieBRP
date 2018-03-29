Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:  R2536 Minimaal één hoofdpersoon moet een ingezetene zijn

Scenario:   Registratie huwelijk waarbij één hoofdpersonen 'Niet-ingezetene' is en de andere 'Onbekend'
            LT: VHNL01C440T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C440-003.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C440-002.xls

When voer een bijhouding uit VHNL01C440T30a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit VHNL01C440T30b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C440T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


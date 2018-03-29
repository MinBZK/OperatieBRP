Meta:
@status                 Klaar
@regels                 R1882
@usecase                UCS-BY.HG

Narrative:
R1882 Datum aanvang geldigheid actie moet gelijk zijn aan datum aanvang relatie

Scenario:   Relatie datum aanvang is later dan datum aanvang geldigheid
            LT: VHNL02C450T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL02C450T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C450T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK
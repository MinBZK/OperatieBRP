Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: R2448 Elke actie heeft minimaal één bron

Scenario:   Uitgevoerde actie heeft meerdere actiebronnen
            LT: ONHW02C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW02C20T20-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 271073561 en persoon 366699945 met relatie id 50000001 en betrokkenheid id 50000001

When voer een bijhouding uit ONHW02C20T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW02C20T20.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 271073561 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 366699945 niet als PARTNER betrokken bij een HUWELIJK

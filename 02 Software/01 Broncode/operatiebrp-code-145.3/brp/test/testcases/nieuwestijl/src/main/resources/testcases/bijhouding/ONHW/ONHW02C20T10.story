Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: R2448 Elke actie heeft minimaal één bron

Scenario:   Uitgevoerde actie heeft geen actiebron
            LT: ONHW02C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 661372169 en persoon 512400969 met relatie id 50000001 en betrokkenheid id 50000001

When voer een bijhouding uit ONHW02C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW02C20T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 661372169 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 512400969 wel als PARTNER betrokken bij een HUWELIJK

Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Ongedaanmaking huwelijk in Nederland

Scenario:   R2465 In BRP gehuwde personen en ongedaanmaking van hun huwelijk
            LT: ONHW01C20T20

Given alle personen zijn verwijderd

!-- Twee ingeschreven ingezeten personen
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW01C20T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW01C20T20-002.xls

!-- Voltrekking huwelijk tussen de twee personen
When voer een bijhouding uit ONHW01C20T20a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 631780841 en persoon 869061513 met relatie id 70000002 en betrokkenheid id 70000002

!-- Ongedaanmaking huwelijk
When voer een bijhouding uit ONHW01C20T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW01C20T20.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
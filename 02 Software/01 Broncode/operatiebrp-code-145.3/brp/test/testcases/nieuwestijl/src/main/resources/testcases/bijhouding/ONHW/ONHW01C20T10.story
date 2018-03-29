Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Ongedaanmaking huwelijk in Nederland

Scenario:   R2465 Ongedaanmaking GBA-huwelijk
            LT: ONHW01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW01C20T10.xls

Given pas laatste relatie van soort 1 aan tussen persoon 335387081 en persoon 879676425 met relatie id 70000002 en betrokkenheid id 70000002

When voer een bijhouding uit ONHW01C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW01C20T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
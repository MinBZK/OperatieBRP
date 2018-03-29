Meta:
@status                 Bug
@usecase                UCS-BY.HG

Narrative: R2653 Datum aanvang geldigheid moet op of na de aanvang van de relatie liggen

Scenario:   Datum aanvang geldigheid van de Actie ligt voor de aanvang relatie
            LT: WPHW01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C10T10-002.xls

!-- Voltrekking van een huwelijk
When voer een bijhouding uit WPHW01C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 899892681 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 953148233 wel als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 899892681 en persoon 953148233 met relatie id 50000001 en betrokkenheid id 50000001
Given pas laatste relatie van soort 1 aan tussen persoon 953148233 en persoon 899892681 met relatie id 50000002 en betrokkenheid id 50000002

!-- Wijziging partnergegevens huwelijk
When voer een bijhouding uit WPHW01C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C10T10.xml voor expressie //brp:bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap_R

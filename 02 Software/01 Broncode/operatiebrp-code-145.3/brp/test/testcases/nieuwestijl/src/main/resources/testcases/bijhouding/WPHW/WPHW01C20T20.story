Meta:
@status                 Bug
@usecase                UCS-BY.HG

Narrative: R2654 De relatie met de gerelateerde moet actueel zijn.

Scenario:   De partnergegevens worden gewijzigd bij een actuele relatie (waarbij er wel een vervallen voorkomen aanwezig is)
            LT: WPHW01C20T20


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C20T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C20T20-002.xls

When voer een bijhouding uit WPHW01C20T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 988371273 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 371729993 wel als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 988371273 en persoon 371729993 met relatie id 50000001 en betrokkenheid id 50000001
Given pas laatste relatie van soort 1 aan tussen persoon 371729993 en persoon 988371273 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit WPHW01C20T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit WPHW01C20T20c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C20T20.xml voor expressie //brp:bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap_R

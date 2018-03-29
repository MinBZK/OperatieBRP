Meta:
@status                 Klaar
@regels                 R2174
@usecase                UCS-BY.HG

Narrative: R2174 Geslachtsnaamwijziging bij beëindigen H/GP in Nederland alleen voor niet-Nederlanders

Scenario:   Geslachtsnaamwijziging waarbij nationaliteit = Nederlander
            LT: NHNL01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL01C10T10-002.xls

When voer een bijhouding uit NHNL01C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 580862537 en persoon 957243017 met relatie id 43100104 en betrokkenheid id 43100104

Then is in de database de persoon met bsn 580862537 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 957243017 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit NHNL01C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NHNL/expected/NHNL01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R













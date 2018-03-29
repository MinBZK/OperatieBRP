Meta:
@status                 Klaar
@regels                 R2174
@usecase                UCS-BY.HG

Narrative: R2174 Geslachtsnaamwijziging bij beëindigen H/GP in Nederland alleen voor niet-Nederlanders

Scenario:   Geslachtsnaamwijziging waarbij nationaliteit = Nederlander
            LT: NGNL01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL01C10T10-002.xls

When voer een bijhouding uit NGNL01C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 393019433 en persoon 789657417 met relatie id 43100114 en betrokkenheid id 43100114


When voer een bijhouding uit NGNL01C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R














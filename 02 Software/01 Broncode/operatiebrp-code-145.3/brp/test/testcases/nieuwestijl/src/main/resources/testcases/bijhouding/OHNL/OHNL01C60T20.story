Meta:
@status                 Klaar
@regels                 R2174
@usecase                UCS-BY.HG

Narrative:
R2174 Geslachtsnaamwijziging bij beëindigen H/GP in Nederland alleen voor niet-Nederlanders

Scenario:   Registratie geslachtsnaam bij 1 partner die de NLse nationaliteit heeft
            LT: OHNL01C60T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL01C60T20.xls

Given pas laatste relatie van soort 1 aan tussen persoon 707778761 en persoon 733639689 met relatie id 2000083 en betrokkenheid id 2000084

When voer een bijhouding uit OHNL01C60T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL01C60T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 707778761 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 733639689 wel als PARTNER betrokken bij een HUWELIJK
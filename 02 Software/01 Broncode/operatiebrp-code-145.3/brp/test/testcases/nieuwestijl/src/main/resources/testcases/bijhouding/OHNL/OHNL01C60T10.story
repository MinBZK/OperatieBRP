Meta:
@status                 Klaar
@regels                 R2174
@usecase                UCS-BY.HG

Narrative:
R2174 Geslachtsnaamwijziging bij beëindigen H/GP in Nederland alleen voor niet-Nederlanders

Scenario:   Registratie geslachtsnaam bij 1 partner die niet-NLse nationaliteit heeft
            LT: OHNL01C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL01C60T10.xls

Given pas laatste relatie van soort 1 aan tussen persoon 856773001 en persoon 140311841 met relatie id 2000081 en betrokkenheid id 2000082

When voer een bijhouding uit OHNL01C60T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL01C60T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 856773001 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 140311841 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1807465490 uit database en vergelijk met expected OHNL01C60T10-persoon1.xml

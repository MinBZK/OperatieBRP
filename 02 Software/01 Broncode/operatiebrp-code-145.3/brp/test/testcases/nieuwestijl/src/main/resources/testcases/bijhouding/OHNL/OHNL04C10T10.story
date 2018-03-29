Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Personen Anne(I) en Jan(P) hebben een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden
            LT: OHNL04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C10T10-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 662714441 en persoon 538201289 met relatie id 70000001 en betrokkenheid id 70000001

When voer een bijhouding uit OHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 662714441 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 538201289 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6356926241 uit database en vergelijk met expected OHNL04C10T10-persoon1.xml














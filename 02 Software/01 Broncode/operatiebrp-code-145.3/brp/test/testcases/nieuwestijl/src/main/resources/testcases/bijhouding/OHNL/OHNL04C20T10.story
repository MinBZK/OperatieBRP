Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden en naamgebruik wordt afgeleid Eigen naam "E"
            LT: OHNL04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C20T10-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 542836361 en persoon 441022601 met relatie id 30000011 en betrokkenheid id 30000011

When voer een bijhouding uit OHNL04C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 542836361 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 441022601 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, geslnaamstamnaamgebruik from kern.pers where bsn = '542836361' de volgende gegevens:
| veld                      | waarde      |
| voornamennaamgebruik      | Anne        |
| geslnaamstamnaamgebruik   | Bakker      |

Then lees persoon met anummer 9297930529 uit database en vergelijk met expected OHNL04C20T10-persoon1.xml














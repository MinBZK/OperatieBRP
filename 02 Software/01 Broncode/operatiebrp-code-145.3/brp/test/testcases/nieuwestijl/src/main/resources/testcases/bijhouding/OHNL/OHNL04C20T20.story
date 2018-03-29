Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden en naamgebruik wordt afgeleid Eigen naam voor naam ex-partner "N"
            LT: OHNL04C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C20T20-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 119573751 en persoon 850104841 met relatie id 30000021 en betrokkenheid id 30000021

When voer een bijhouding uit OHNL04C20T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C20T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 119573751 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 850104841 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, geslnaamstamnaamgebruik from kern.pers where bsn = '119573751' de volgende gegevens:
| veld                      | waarde            |
| voornamennaamgebruik      | Anne              |
| geslnaamstamnaamgebruik   | Bakker-Pietersen  |

Then lees persoon met anummer 9682326305 uit database en vergelijk met expected OHNL04C20T20-persoon1.xml














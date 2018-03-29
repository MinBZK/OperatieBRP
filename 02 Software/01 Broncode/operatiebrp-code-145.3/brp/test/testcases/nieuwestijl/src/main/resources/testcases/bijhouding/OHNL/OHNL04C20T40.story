Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden en naamgebruik wordt afgeleid Eigen naam na naam partner "V"
            LT: OHNL04C20T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C20T40-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 894909897 en persoon 292928889 met relatie id 30000032 en betrokkenheid id 30000032

When voer een bijhouding uit OHNL04C20T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C20T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 894909897 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 292928889 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, geslnaamstamnaamgebruik from kern.pers where bsn = '894909897' de volgende gegevens:
| veld                      | waarde            |
| voornamennaamgebruik      | Anne              |
| geslnaamstamnaamgebruik   | Pietersen-Bakker  |

Then lees persoon met anummer 7083065121 uit database en vergelijk met expected OHNL04C20T40-persoon1.xml














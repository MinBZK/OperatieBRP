Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden en naamgebruik wordt afgeleid Naam ex-partner "P"
            LT: OHNL04C20T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C20T30-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 313402681 en persoon 589761833 met relatie id 30000031 en betrokkenheid id 30000031

When voer een bijhouding uit OHNL04C20T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C20T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 313402681 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 850104841 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, geslnaamstamnaamgebruik from kern.pers where bsn = '313402681' de volgende gegevens:
| veld                      | waarde            |
| voornamennaamgebruik      | Anne              |
| geslnaamstamnaamgebruik   | Pietersen         |

Then lees persoon met anummer 6248246561 uit database en vergelijk met expected OHNL04C20T30-persoon1.xml














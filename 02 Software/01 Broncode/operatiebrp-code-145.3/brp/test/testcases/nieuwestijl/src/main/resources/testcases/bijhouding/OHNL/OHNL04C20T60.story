Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden en naamgebruik wordt afgeleid Laatste ex-partner met de meest recente "Datum einde"
            LT: OHNL04C20T60

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C20T60-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 398031241 en persoon 185285673 met relatie id 30000034 en betrokkenheid id 30000034

When voer een bijhouding uit OHNL04C20T60.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C20T60.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 398031241 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 185285673 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, voorvoegselnaamgebruik, scheidingstekennaamgebruik, geslnaamstamnaamgebruik from kern.pers where bsn = '398031241' de volgende gegevens:
| veld                       | waarde            |
| voornamennaamgebruik       | Libby             |
| voorvoegselnaamgebruik     | l                 |
| scheidingstekennaamgebruik | '                 |
| geslnaamstamnaamgebruik    | Bakker-Thatcher   |

Then lees persoon met anummer 3527273249 uit database en vergelijk met expected OHNL04C20T60-persoon1.xml














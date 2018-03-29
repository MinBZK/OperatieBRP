Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ontbinding huwelijk in Nederland

Scenario:   Een GBA huwelijk, dit huwelijk wordt in de BRP ontbonden en naamgebruik wordt afgeleid Laatste ex-partner met de meest recente "Datum einde"
            LT: OHNL04C20T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C20T50-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 198836673 en persoon 282258681 met relatie id 30000033 en betrokkenheid id 30000033

When voer een bijhouding uit OHNL04C20T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C20T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 198836673 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 282258681 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, geslnaamstamnaamgebruik from kern.pers where bsn = '198836673' de volgende gegevens:
| veld                      | waarde            |
| voornamennaamgebruik      | Libby             |
| geslnaamstamnaamgebruik   | Thatcher-l'Jansen |

Then lees persoon met anummer 3202782497 uit database en vergelijk met expected OHNL04C20T50-persoon1.xml














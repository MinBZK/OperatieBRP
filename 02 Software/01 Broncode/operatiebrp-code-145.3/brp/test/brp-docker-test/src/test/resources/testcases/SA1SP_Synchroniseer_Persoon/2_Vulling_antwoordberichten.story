Meta:
@status             Klaar
@usecase            LV.1.CPI
@sleutelwoorden     Zoek Persoon

Narrative:
Additionele test om te valideren dat bepaalde attributen die in resultaat bericht van zoekPersoon niet voorkomen
wel voorkomen bij de persoon voor andere diensten.

Scenario:   1.2 Synchroniseer persoon vulling antwoord bericht
                LT:


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_vulling antwoordbericht.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut                     | verwachteWaarde |
| samengesteldeNaam     | 1         | adellijkeTitelCode            | H               |
| bijhouding            | 1         | bijhoudingsaardCode           | I               |
| bijhouding            | 1         | nadereBijhoudingsaardCode     | A               |
| adres                 | 1         | huisnummertoevoeging          | kap             |

Then heeft het bericht 6 groepen 'persoon'
Then heeft het bericht 1 groepen 'huwelijk'
Then heeft het bericht 2 groepen 'onderzoek'

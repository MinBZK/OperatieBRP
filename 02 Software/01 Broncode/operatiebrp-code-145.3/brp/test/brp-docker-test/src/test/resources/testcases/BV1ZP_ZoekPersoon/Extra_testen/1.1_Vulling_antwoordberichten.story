Meta:
@status             Klaar
@usecase            LV.1.CPI
@sleutelwoorden     Zoek Persoon

Narrative:
Hoewel Zoek persoon met name bedoeld is om een ingang naar andere diensten te verschaffen,
kan het zijn dat een gebruiker aan de beperkte resultaatset van deze dienst al voldoende informatie
over een persoon heeft (bijvoorbeeld als hij alleen zijn actuele adres nodig heeft).
In dat geval hoeft de gebruiker geen aanvullende diensten meer aan te roepen.
In het resultaat van Zoek persoon worden ook de verplichte gegevens meegeleverd
(o.a. onderzoeken, melding verstrekkingsbeperkingen, en (nadere) bijhoudingsaard)

Deze story is aangemaakt om extra testen uit te kunnen voeren met betrekking tot de xsd-validatie van het
antwoordbericht van zoek persoon.

Ook zijn hier testen toegevoegd met betrekking tot de levering van verplichte gegevens:
- Onderzoeken
- (nadere) bijhoudingsaard
- Verstrekkingbeperking zit in de API testen (Geconverteerde_Data_Test, GBA_Bijhouding_Omzetting_Partnerschap_in_huwelijk.story)

Verder wordt een zo compleet mogelijk antwoordbericht gecontroleerd op de te leveren groepen.
Zo wordt er getest dat adelijke titel niet meekomt in het bericht (met een synchronisatiebericht ter vergelijking (scenario 1.2)

Scenario:   1.1 Persoon heeft adelijke titel = H, Huisnummer staat in onderzoek
                LT:
                Uitwerking:
                - Zoeken op Adelijke titel
                Verwacht resultaat:
                - Adellijke titel niet in antwoordbericht
                Uitwerking:
                -  Zoeken op Postcode
                Verwacht ressultaat:
                - Postcode in bericht, dus groep adres in bericht, dus Huisnummer in bericht, dus onderzoek in bericht
                Uitwerking:
                - Persoon is getrouwd
                Verwacht resultaat:
                - Geen relaties in bericht, dus geen huwelijk in het bericht
                Uitwerking:
                - Extra adres gegevens gevuld (Huisnummertoevoeging)
                Verwacht resultaat:
                - Extra adresgegevens in bericht
                Uitwerking:
                - (nadere) bijhoudingsaard verplicht leveren

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_1.1a.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

!-- Relaties worden niet geleverd, dus huwelijk ook niet
Then heeft het antwoordbericht 0 groepen 'huwelijk'
!-- Adelijke titel mag niet geleverd worden
Then is er voor xpath //brp:adellijkeTitelCode[text()='H'] een node aanwezig in het antwoord bericht
!-- extra attributen onder adres gevuld
Then is er voor xpath //brp:huisnummertoevoeging[text()='kap'] een node aanwezig in het antwoord bericht

!-- (nadere) bijhoudingsaard is verplicht en wordt dus geleverd
Then is er voor xpath //brp:bijhoudingsaardCode[text()='I'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:nadereBijhoudingsaardCode[text()='A'] een node aanwezig in het antwoord bericht
!-- Onderzoek is verplicht (mits attribuut in onderzoek in de lijst blijft)
Then heeft het antwoordbericht 2 groepen 'onderzoek'
Then is er voor xpath //brp:elementNaam[text()='Persoon.Adres.Huisnummer'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/Vulling-antwoordberichten-zoek-persoon.xml voor expressie //brp:lvg_bvgZoekPersoon_R

!-- In 2_Vulling_antwoordberichten.story wordt gevalideerd dat deze attributen in een synchroniseerPersoon wel aanwezig zijn in het antwoord bericht bij de persoon

Scenario: 2.    Zoeken op BOOLEAN onder curatele, Uitwerking: Persoon gevonden, Onder curatele niet in het bercht
                LT:

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Anne'] een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/Persoon_onder_curatele.xml voor expressie //brp:lvg_bvgZoekPersoon_R

Scenario: 3.    Anne is overleden, Persoon gevonden, Overlijden in het bericht
                LT:

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL-Anne_overleden.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
!-- XSD validatie is uitgecomment. Is een Known Issue. Op dit moment gaat het verkeerd. Na de komende BMR wijziging zou het weer goed moeten gaan.
!-- Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Anne'] een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/Persoon_overleden.xml voor expressie //brp:lvg_bvgZoekPersoon_R

Scenario: 4.    Zoeken op gegevens van een betrokkenen, Persoon gevonden
                LT:

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_4.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Anne'] een node aanwezig in het antwoord bericht


Scenario: 5.    Zoeken op gegevens van een relatie huwelijke, persoon gevonden
                LT:

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_5.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Anne'] een node aanwezig in het antwoord bericht


Scenario: 6.    Zoeken op Persoon.Bijhouding.DatumAanvangGeldigheid
                LT:
                Issue: LT_1234XX, ROOD-1761
                Algemene fout ipv 1 persoon gevonden.
                Ligt nog bij team Wit om een regel hiervoor te maken.


Meta:
@status Bug


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_6.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Anne'] een node aanwezig in het antwoord bericht
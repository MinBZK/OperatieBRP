Meta:
@status             Klaar
@usecase            LV.1.CPI
@sleutelwoorden     Zoek Persoon
@regels             R2265aa

Narrative:
Deze story is aangemaakt om extra testen uit te kunnen voeren met betrekking tot de xsd-validatie van het
antwoordbericht van zoek persoon.

Ook zijn hier testen toegevoegd met betrekking tot de levering van verplichte gegevens:
- Onderzoeken

Verder wordt een zo compleet mogelijk antwoordbericht gecontroleerd op de te leveren groepen.

Scenario:   1. Zoekopdracht met een referentie naar een stamgegeven, partij bijhPartij
                Uitwerking:
                - Zoeken op achternaam Bakker en Bijhpartij Voorschoten partijcode 62601
                - 1 persoon met achternaam Bakker en bijhoudingspartij Waddinxveen (62701)
                - 1 persoon met achternaam Bakker en bijhoudingspartij Voorschoten (62601)
                Verwacht resultaat:
                1 persoon met achternaam Klaassen gevonden en bijhpartij 630

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie_bijhPartij_631.xls
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Stamgegevens_1.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '986096969'




Scenario:   2. Zoekopdracht met een referentie naar een stamgegeven, partij bij gemPK
                Uitwerking:
                - Zoeken op achternaam Bakker en gemPK Voorschoten partijcode 62601
                - 1 persoon met achternaam Bakker en GemPk Waddinxveen (62701)
                - 1 persoon met achternaam Bakker en GemPk Voorschoten (62601)
                Verwacht resultaat:
                1 persoon met achternaam Klaassen gevonden en GemPk 630

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie_bijhPartij_631.xls
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Stamgegevens_2.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '986096969'

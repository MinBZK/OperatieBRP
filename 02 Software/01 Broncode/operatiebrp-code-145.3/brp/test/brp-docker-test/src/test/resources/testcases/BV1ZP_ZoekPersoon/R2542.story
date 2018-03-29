Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon


Narrative:
De inhoud van Attribuut in Bericht \ Zoekcriteria.Zoekcriterium dient een Element.Element naam van een Element te zijn waarvoor geldt dat:

Element.Autorisatie gelijk aan 'Optioneel', 'Verplicht', 'Aanbevolen' of 'Bijhoudingsgegevens'

EN

Element.Identificatie database schema is gelijk aan "Kern".

Scenario: 1.    Zoeken op Element autorisatie is Optioneel, database schema is Kern
                LT: R2542_LT01
                Verwacht Resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Optioneel_Kern.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 2.    Zoeken op Element autorisatie is Verplicht, database schema is Kern
                LT: R2542_LT02
                Verwacht Resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Verplicht_Kern.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 3.    Zoeken op Element autorisatie is aanbevolen, database schema is Kern
                LT: R2542_LT03
                Verwacht Resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Aanbevolen_Kern.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 4.    Zoeken op Element autorisatie is bijhoudingsgegevens, database schema is Kern
                LT: R2542_LT04
                Verwacht Resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon3' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Bijhoudingsgegeven_Kern.xml
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2290     | Er bestaat geen autorisatie voor de opgegeven elementen in het zoekcriterium.       |


Scenario: 5.    Zoeken op Element autorisatie is bijhoudingsgegevens, database schema is Kern
                LT: R2542_LT04
                Verwacht Resultaat:
                - Geslaagd, met autorisatie Bijhouder persoon gevonden.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon4' en partij 'Gemeente Bijhouder'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Bijhoudingsgegeven_Kern2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'
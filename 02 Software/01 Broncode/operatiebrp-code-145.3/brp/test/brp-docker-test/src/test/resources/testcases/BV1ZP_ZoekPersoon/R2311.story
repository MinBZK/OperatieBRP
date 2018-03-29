Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2311

Narrative:
Voor elke Zoekcriterium in een zoekvraag geldt dat de opgegeven waarde niet langer mag zijn dat wat bij het betreffende element is toegestaan:
(uitwerking met aanname dat we dit via een stamtabel configureerbaar maken):
Er dient een voorkomen van Zoekelement te zijn voor het opgegeven Element waarbij de opgegeven waarde niet meer tekens mag bevatten dan wat is toegestaan voor het betreffende element.
Bijvoorbeeld:
Een postcode mag maximaal 6 tekens bevatten
SamengesteldeNaam.Geslachtsnaamstam mag maximaal 200 tekens bevatten

Scenario: 1.    Zoek persoon met maximaal toegestaan aantal tekens zoekoptie Exact
                LT: R2311_LT01
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Zoek persoon op postcode met 6 tekens
                + Op achternaam omdat alleen op adresgegevens niet mag

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2311_1.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht


Scenario: 2.    Zoek persoon met maximaal toegestaan aantal tekens zoekoptie Vanaf
                LT: R2311_LT03
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Zoek persoon op woonplaatsnaam met 8 tekens (80 toegestaan)
                + Op achternaam omdat alleen op adresgegevens niet mag

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2311_2.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht


Scenario: 3.    Zoek persoon met maximaal toegestaan aantal tekens zoekoptie Klein
                LT: R2311_LT05
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Zoek persoon op woonplaatsnaam met 8 tekens (80 toegestaan)
                + Op achternaam omdat alleen op adresgegevens niet mag

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2311_3.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht


Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2288

Narrative:
Een zoekvraag voor 'Zoek persoon' dient tenminste één zoekcriterium te bevatten dat geen adresgegeven is
Persoon
Persoon (Jan Jansen):
BSN: 606417801
Geboorte datum: 1960-08-21
Adres:
Baron Schimmelpenninck van der Oyelaan 16
2252EB Voorschoten

Scenario: 1.    Zoek Persoon met tenminste 2 zoekcriteria ongelijk adres.standaard
                LT: R2288_LT01
                Verwacht resultaat:
                - Jan wordt gevonden
                Uitwerking:
                - Persoon.SamengesteldeNaam.Geslachtsnaamstam = Jansen
                - Persoon.SamengesteldeNaam.Voornamen' = Jan

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2288_1.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Vader heeft zelfde achternaam, maar is een pseudopersoon, dus alleen Jan Jansen geleverd
Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 2.    Zoek Persoon met tenminste 1 zoekcriterium ongelijk adres.standaard
                LT: R2288_LT02
                Verwacht resultaat:
                - Jan wordt gevonden
                Uitwerking:
                Uitwerking:
                - Persoon.SamengesteldeNaam.Geslachtsnaamstam = Jansen
                - Persoon.Adres.Woonplaatsnaam' = Voorschoten

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2288_2.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Vader heeft zelfde achternaam, maar is een pseudopersoon, dus alleen Jan Jansen geleverd
Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht


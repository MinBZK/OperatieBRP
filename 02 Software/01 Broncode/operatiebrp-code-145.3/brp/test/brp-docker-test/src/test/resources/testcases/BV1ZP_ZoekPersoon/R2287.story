Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2287

Narrative:
Het resultaat van een zoekvraag mag alleen persoonslijsten bevatten die voldoen aan de Totale populatiebeperking (R2059).

Persoon
Persoon (Jan Jansen):
BSN: 606417801
Geboorte datum: 1960-08-21
Adres:
Baron Schimmelpenninck van der Oyelaan 16
2252EB Voorschoten

Scenario: 1.    Zoek Persoon met autorisatie met verschillende populatiebeperkingen.
                LT: R2287_LT01, R2059_LT01
                Verwacht resultaat:
                - Jan wordt gevonden
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2250AA en 2260ZZ
                - toegang.leveringsautorisatie.nadere populatiebeperking = geboorte.datum > 1959/12/10
                - dienstbundel nadere.populatiebeperking = geboorte.datum < 1965/12/10

                Andere Jansen met geboortedatum > 1965/12/10 komt niet in het resultaat

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_geb_dat_1966.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2287.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Vader heeft zelfde achternaam, maar is een pseudopersoon, dus alleen Jan Jansen geleverd
Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

!-- Controleer dat de andere Jansen, die buiten de pop bep valt niet mee komt in het resultaat
Then is er voor xpath //brp:burgerservicenummer[text()='249361449'] geen node aanwezig in het antwoord bericht

Scenario: 2.    Zoek Persoon met autorisatie met verschillende populatiebeperkingen.
                LT: R2287_LT02, R2059_LT02, R2399_LT02
                Verwacht resultaat:
                - Jan wordt NIET gevonden
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2250AA en 2260ZZ
                - toegang.leveringsautorisatie.nadere populatiebeperking = geboorte.datum > 1959/12/10
                - dienstbundel nadere.populatiebeperking = geboorte.datum < 1960/05/05 VOLDOET NIET

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon dienstbundel nadere popbep voldoet niet' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2287.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Jan voldoet niet aan dienstbundel nadere.populatiebeperking = geboorte.datum < 1960/05/05, dus niet gevonden
Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario: 3.    Zoek Persoon met autorisatie met verschillende populatiebeperkingen.
                LT: R2287_LT03, R2059_LT03
                Verwacht resultaat:
                - Jan wordt NIET gevonden
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2250AA en 2260ZZ
                - toegang.leveringsautorisatie.nadere populatiebeperking = geboorte.datum > 1961/01/01
                - dienstbundel nadere.populatiebeperking = LEEG

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon tla nadere popbep voldoet niet' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2287.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Jan voldoet niet aan toegang.leveringsautorisatie.nadere populatiebeperking , dus niet gevonden
Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario: 4.    Zoek Persoon met autorisatie met verschillende populatiebeperkingen.
                LT: R2287_LT04, R2059_LT04
                Verwacht resultaat:
                - Jan wordt NIET gevonden
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2253AA en 2260ZZ VOLDOET NIET
                - toegang.leveringsautorisatie.nadere populatiebeperking = LEEG
                - dienstbundel nadere.populatiebeperking = LEEG

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon la nadere popbep voldoet niet' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2287.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Jan voldoet niet aan Leveringsautorisatie.populatiebeperking, dus niet gevonden
Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario: 5.    Zoek Persoon met leveringsautorisatie met totale populatiebeperking is LEEG
                LT: R2287_LT05, R2059_LT05
                Verwacht Resultaat:
                - Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep LEEG' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2287.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Vader heeft zelfde achternaam, maar is een pseudopersoon, dus alleen Jan Jansen geleverd
Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht
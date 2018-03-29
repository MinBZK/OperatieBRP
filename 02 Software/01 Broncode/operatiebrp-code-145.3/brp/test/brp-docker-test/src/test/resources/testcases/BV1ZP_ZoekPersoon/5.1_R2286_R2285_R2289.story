Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2286, R2347

Narrative:
Het zoekresultaat bevat alle personen die voldoen aan de in de zoekvraag opgegeven zoekcriteria
(voor zover die voldoen aan de overige verwerkingsregels).

Persoon1:       Wordt geleverd
Naam:           Kim Kardashian
Geboorte datum: 1960-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 16
Postcode:       2252EB
Plaats:         Voorschoten

Persoon2:       (Valt af op achternaam)
Naam:           Kanye west
Geboorte datum: 1960-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 16
Postcode:       2252EB
Plaats:         Voorschoten

Persoon3:       Wordt geleverd
Naam:           Khloe Kardashian
Geboorte datum: 1960-08-21
Adres:          Baron 16
Postcode:       2252EB
Plaats:         Voorschoten

Persoon4:       (Valt af op huisnummer)
Naam:           Kourtney Kardashian
Geboorte datum: 1960-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 15
Postcode:       2252EB
Plaats:         Voorschoten

Persoon5:       Valt af op postcode
Naam:           Kylie Kardashian
Geboorte datum: 1960-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 16
Postcode:       2252EZ
Plaats:         Voorschoten

Persoon6:       Valt af op populatiebeperking geboortedatum
Naam:           Kris Kardashian
Geboorte datum: 1950-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 16
Postcode:       2252EB
Plaats:         Voorschoten

Persoon7:       Valt af door peildatum adres
Naam:           Kendall Kardashian
Geboorte datum: 1950-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 16
Postcode:       2252EB
Plaats:         Voorschoten (Maar pas na 01-01-2016)

Persoon8:       Valt af door huwelijk op 01-01-2016
Naam:           Caitlyn Kardashian
Geboorte datum: 1950-08-21
Adres:          Baron Schimmelpenninck van der Oyelaan 16
Postcode:       2252EB
Plaats:         Voorschoten

Scenario: 1.    Zoek Persoon met meerdere personen op achternaam. Iedereen voldoet het zoekcriterium behalve Kanye
                LT: R2286_LT01, R2286_LT02, R2347_LT01, R2399_LT02
                Verwacht resultaat:
                - 7 personen gevonden (iedereen min Kanye)
                Uitwerking: Zoekcriterium achternaam Kardashian
                - Kanye komt niet in bericht voor door achternaam West

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 7 groepen 'persoon'
Then is er voor xpath //brp:voornamen[text()='Kim'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Khloe'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kourtney'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kendall'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Caitlyn'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kylie'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kris'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kanye'] geen node aanwezig in het antwoord bericht

Then is er voor xpath (//brp:burgerservicenummer)[1][text()='590796674'] een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:burgerservicenummer)[2][text()='606417801'] een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:burgerservicenummer)[3][text()='639969033'] een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:burgerservicenummer)[4][text()='689274889'] een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:burgerservicenummer)[5][text()='692507401'] een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:burgerservicenummer)[6][text()='787018697'] een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:burgerservicenummer)[7][text()='977045833'] een node aanwezig in het antwoord bericht

Scenario: 2.    Zoek Persoon met meerdere personen op achternaam. Iedereen voldoet het zoekcriterium behalve Kanye en Kris
                LT: R2286_LT01, R2286_LT02, R2286_LT03
                Verwacht resultaat:
                - 6 personen gevonden
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2250AA en 2260ZZ
                - toegang.leveringsautorisatie.nadere populatiebeperking = geboorte.datum > 1959/12/10
                - dienstbundel nadere.populatiebeperking = geboorte.datum < 1965/12/10
                - Kris Kardashian met geboortedatum < 1959/12/10 komt niet in het resultaat
                Uitwerking: Zoekcriterium achternaam Kardashian
                - Kanye komt niet in bericht voor door achternaam West

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 6 groepen 'persoon'
Then is er voor xpath //brp:voornamen[text()='Kim'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Khloe'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kourtney'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kendall'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Caitlyn'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kylie'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kris'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kanye'] geen node aanwezig in het antwoord bericht

Scenario: 3.    Zoek Persoon met meerdere personen die gevonden worden
                LT: R2286_LT01, R2286_LT02, R2289_LT01, R2289_LT02, R2289_LT03
                Verwacht resultaat:
                - 6 personen gevonden (iedereen min Kanye)
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2250AA en 2260ZZ
                - toegang.leveringsautorisatie.nadere populatiebeperking = geboorte.datum > 1959/12/10
                - dienstbundel nadere.populatiebeperking = geboorte.datum < 1965/12/10
                - Kris Kardashian met geboortedatum < 1959/12/10 komt niet in het resultaat
                Uitwerking: Zoekcriterium achternaam Kardashian (Exact)
                - Kanye komt niet in bericht voor door achternaam West
                Uitwerking: Zoekcriterium woonplaatsnaam Voorsch (Vanaf)
                - Kendall valt af, want op 01-01-2016 woonde zij nog niet in voorschoten

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls

!-- R2289_LT03
Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_3.1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide


Then heeft het antwoordbericht 5 groepen 'persoon'
Then is er voor xpath //brp:voornamen[text()='Kim'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Khloe'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kourtney'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kendall'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Caitlyn'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kylie'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kris'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Kanye'] geen node aanwezig in het antwoord bericht

!-- R2289_LT01
Given verzoek voor leveringsautorisatie 'Zoek Persoon Max resultaten 8' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_3.2.xml

Then heeft het antwoordbericht verwerking Geslaagd


!-- R2285_LT01, R2289_LT02
Given verzoek voor leveringsautorisatie 'Zoek Persoon Max resultaten max 4' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_3.3.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |

Scenario: 4.    Zoek Persoon met meerdere personen die in het resultaat komen
                LT: R2286_LT01, R2286_LT02
                Verwacht resultaat:
                - 6 personen gevonden (iedereen min Kanye)
                Uitwerking:
                - Leveringsautorisatie.populatiebeperking = postcode tussen 2250AA en 2260ZZ
                - toegang.leveringsautorisatie.nadere populatiebeperking = geboorte.datum > 1959/12/10
                - dienstbundel nadere.populatiebeperking = geboorte.datum < 1965/12/10
                - Kris Kardashian met geboortedatum < 1959/12/10 komt niet in het resultaat
                Uitwerking: Zoekcriterium achternaam Kardashian (Exact)
                - Kanye komt niet in bericht voor door achternaam West
                Uitwerking: Zoekcriterium woonplaatsnaam Voorsch (Exact)
                - Niemand komt in bericht voor door straatnaam

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_4.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 5.    Zoek Persoon met meer tussenresultaten dan toegestaan.
                LT: R2285_LT02
                Uitwerking: Er worden meer dan 12 personen gevonden, het maximum aantal tussenresultaten = 12
                Verwacht resultaat: Foutmelding R2285 De zoekvraag heeft teveel tussenresultaten opgeleverd.

!-- Inladen van een leveringsautorisatie zonder Dienst.maximum aantal zoek resultaten. Default = 10
!-- Inladen van 15 testpersonen, max aantal tussenresultaten is geconfigureerd op 12
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie2.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie3.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie4.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie5.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie6.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie7.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie8.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_5.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                  |
| R2285     | De zoekvraag heeft teveel tussenresultaten opgeleverd.   |

Scenario: 6.    Zoek Persoon met meer tussenresultaten dan toegestaan.
                LT: R2285_LT01, R2289_LT04
                Uitwerking: Er worden 12 personen gevonden, het maximum aantal tussenresultaten = 12,
                door populatiebeperking wordt 1 persoon uit de resultaten gefilterd, waarmee het aantal zoekresultaten op 11 uitkomt.
                Dit is meer dan de default van max 10.
                Verwacht resultaat: Foutmelding R2289 De zoekvraag heeft teveel tussenresultaten opgeleverd.

!-- Inladen van een leveringsautorisatie zonder Dienst.maximum aantal zoek resultaten. Default = 10
!-- Inladen van 12 testpersonen, max aantal tussenresultaten is geconfigureerd op 12
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie2.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie3.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie4.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie5.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie6.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie7.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie8.xls

!-- 1 Persoon valt af door Populatie beperking (Kanye), waarmee het aantal zoekresultaten op 11 komt
!-- R2289_LT04
Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2286_6.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |
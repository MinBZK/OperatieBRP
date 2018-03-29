Meta:
@status             Klaar
@usecase            BV.0.ZP
@regels             R2291
@sleutelwoorden     Zoek Persoon

Narrative:
Als afnemer
Wil ik kunnen zoeken met de zoekoptie exact
zodat ik alleen zoekresultaten krijg die exact voldoen aan de opgegeven waarde.


Scenario:   1. Zoekoptie exact met textattribuut zoeken op achternaam Şahin
            LT: R2291_LT01
            Personen worden ingeladen met afwijkingen in achternaam.
            Personen met diakritische tekens omgezet, lengte vd naam korter en langer worden niet gevonden.
            Persoon met hoofdletter in de naam wordt niet gevonden.
            Verwacht resultaat:
            1. persoon met exact de opgegeven waarde Şahin met bsn 260917321

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahin.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahin_zonder_diakriet.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahi.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahinne.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_SaHin_hoofdletter_zoeken.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2291_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '260917321'
!-- check of diakrieten correct worden geleverd.
Then is er voor xpath //brp:geslachtsnaamstam[text()='Şahin'] een node aanwezig in het antwoord bericht


Scenario:   2. Zoekoptie exact met textattribuut zoeken op achternaam Sahin
            LT: R2291_LT02
            Personen worden ingeladen met afwijkingen in achternaam.
            Personen met diakritische tekens, lengte vd naam korter en langer worden niet gevonden.
            Persoon met hoofdletter in de naam wordt niet gevonden.
            Persoon met Diakriet op begin letter
            Verwacht resultaat:
            1. persoon met exact de opgegeven waarde Şahin met bsn 260917321

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahin.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahin_zonder_diakriet.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahi.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Sahinne.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_SaHin_hoofdletter_zoeken.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2291_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '301659801'

Scenario:   3. Zoekoptie exact met bekende geboortedatum
            LT: R2291_LT03
            Verwacht resultaat:
            - Alleen Jan gevonden
            Uitwerking:
            - Libby geboortedatum: 19660000
            - Jan geboortedatum: 19660821
            - Zoeken op Exact Persoon.Geboorte.Datum = 19660821

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_geb_dat_1966.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby-gebdat-deels-onbekend2.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2291_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Libby'] geen node aanwezig in het antwoord bericht

Scenario:   4.a Zoekoptie exact met gedeeltelijk onbekende geboortedatum
            LT: R2291_LT04
            Verwacht resultaat:
            - Niemand gevonden
            Uitwerking:
            - Libby geboortedatum: 19660100
            - Jan geboortedatum: 19660821
            - Zoeken op Exact Persoon.Geboorte.Datum = 19660000

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_geb_dat_1966.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby-gebdat-deels-onbekend1.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2291_4a.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Libby'] geen node aanwezig in het antwoord bericht

Scenario:   4.b Zoekoptie exact met gedeeltelijk onbekende geboortedatum
            LT: R2291_LT04
            Verwacht resultaat:
            - Allen gevonden
            Uitwerking:
            - Libby geboortedatum: 19660100
            - Jan geboortedatum: 19660821
            - Zoeken op Exact Persoon.Geboorte.Datum = 19660100

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_geb_dat_1966.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby-gebdat-deels-onbekend1.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2291_4b.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht
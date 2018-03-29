Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2293

Narrative:
Indien een zoekcriterium de optie 'Vanaf' bevat, dat dient er een match te zijn met het tekstdeel van de opgegeven waarde na de omzetting van hoofdletters en diakritische tekens naar de bijbehorende kleine letters. Deze optie is alleen mogelijk bij tekstvelden:
De geconverteerde inhoud van de het opgegeven deel dient gelijk te zijn (dus na omzetting van hoofdletters en diakritische tekens). De aanwezige tekst mag dus langer zijn dan de opgegeven zoektekst. Het 'overblijvende deel' wordt niet beschouwd in de vergelijking.

Scenario:   1. Zoekoptie Vanaf met textattribuut zoeken op achternaam ßlußmann
            LT: R2293_LT01, R1270_LT04, R1269_LT09
            1 persoon wordt ingeladen met achternamen diakritische tekens bevat.
            1 persoon wordt ingeladen met achternaam slussman
            Verwacht resultaat:
            Beide personen worden gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_SSlussmann_zonder_diakrieten.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann_zonder_diakrieten.xls

!-- Geslachtsnaam ßlußmann wordt volgens de conversie van diakritische tekens sslussmann
!-- Zoeken met optie 'vanaf' met waarde 'slus' op geslachtsnaam zou dus de persoon op moeten leveren.
!-- Persoon SSlussman zonder diakrieten wordt ook gevonden
!-- Persoon Slusman zonder diakrieten wordt niet gevonden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2293_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1269_LT09 zoekopdracht naar meerdere personen in inkomend bericht, personen niet opgenomen in de ber.pers tabel
Then bestaat er geen voorkomen in berpers tabel voor referentie 0000000A-3000-7000-0000-000000000000 en srt lvg_bvgZoekPersoon

Then heeft het antwoordbericht 2 groepen 'persoon'
!-- check of de diakritische tekens correct worden geleverd.
Then is er voor xpath //brp:geslachtsnaamstam[text()='ßlußmann'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='SSlussmann'] een node aanwezig in het antwoord bericht

!-- R1270_LT04

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 866214665                            |
| richting              | 2                                    |

Scenario:   2. Zoekoptie Vanaf met textattribuut zoeken op achternaam ßlußmannnen
            LT: R2293_LT02
            1 persoon wordt ingeladen met achternamen diakritische tekens bevat.
            1 persoon wordt ingeladen met achternaam sslussman
            Verwacht resultaat:
            Geen personen worden gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_SSlussmann_zonder_diakrieten.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann_zonder_diakrieten.xls

!-- Geslachtsnaam ßlußmann wordt volgens de conversie van diakritische tekens sslussmann
!-- Zoeken met optie 'vanaf' met waarde 'sslussmannen' op geslachtsnaam moet geen persoon opleveren
!-- omdat de geslachtsnaam korter is de opgegeven zoekwaarde.
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2293_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario:   3. Zoekoptie Vanaf met textattribuut zoeken op achternaam ßluß
            LT: R2293_LT03
            1 personen worden ingeladen met achternamen diakritische tekens bevat.
            1 persoon wordt ingeladen met de achternaam SSlussman
            1 persoon wordt ingeladen met achternaam slussman
            Verwacht resultaat:
            Persoon SSlussman en ßlußmann worden gevonden slusmann NIET

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_SSlussmann_zonder_diakrieten.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann_zonder_diakrieten.xls

!-- Geslachtsnaam ßlußmann
!-- Zoekcriterium = 'vanaf' waarde = 'ßluß'
!-- Verwacht is dat persoon met geslachtsnaam 'ßlußmann' wordt gevonden
!-- Verwacht is dat de persoon met geslachtsnaam 'SSlussmann' ook gevonden wordt
!-- Verwacht is dat de persoon met geslachtsnaam 'Slusman' NIET gevonden wordt
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2293_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'
!-- check of de diakritische tekens correct worden geleverd.
Then is er voor xpath //brp:geslachtsnaamstam[text()='ßlußmann'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='SSlussmann'] een node aanwezig in het antwoord bericht

Scenario: 4.1   Zoeken op gedeeltelijk onbekende geboortedatum (1960-00-00), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT03
                Verwacht resultaat;
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Geboortedatum.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 4.2.1 Zoeken op gedeeltelijk onbekende geboortedatum (1960), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT03
                Verwacht resultaat;
                - Jan gevonden


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Geboortedatum2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 4.2.2 Zoeken op Geheel onbekende geboortedatum (0000), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT03
                Verwacht resultaat;
                - Jan gevonden
                - Anne gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Geboortedatum_Geheel_Onbekend.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'

Scenario: 4.2.3 Zoeken op gedeeltelijk onbekende geboortedatum (1960-00), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT03
                Verwacht resultaat;
                - Jan gevonden


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Geboortedatum3.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 4.2.4 Zoeken op gedeeltelijk onbekende geboortedatum (1960-01-00), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT03
                Doel: Vaststellen dat de zoek dienstcorrecte resultaten geeft met
                      zoekcriterium optie Vanaf klein en
                      een element van het type datum

                Verwacht resultaat;
                - Jan gevonden


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Geboortedatum4.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 4.2.5 Zoeken op gedeeltelijk onbekende geboortedatum (1960-1), zoekoptie vanaf, Foutief
                LT: R2293_LT04
                Verwacht resultaat;
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Geboortedatum_Foutief.xml
Then heeft het antwoordbericht verwerking Foutief

Scenario: 4.3   Zoeken op gedeeltelijk onbekende postcode 2252 (matcht met postcode Jan 2252EB), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT01
                Verwacht resultaat;
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Postcode.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

Scenario: 4.4   Zoeken op gedeeltelijk onbekende postcode 52EB (matcht met Niemand), zoekoptie vanaf
                LT: R2293_LT04, R2281_LT01
                Verwacht resultaat;
                - Geen personen gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Postcode2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 4.5   Zoeken op gedeeltelijk onbekende postcode (matcht met Jan), zoekoptie Klein, dus niemand gevonden
                LT: R2293_LT04, R2281_LT08
                Verwacht resultaat;
                - Geen personen gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Gedeeltelijk_Onbekende_Postcode_Klein.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

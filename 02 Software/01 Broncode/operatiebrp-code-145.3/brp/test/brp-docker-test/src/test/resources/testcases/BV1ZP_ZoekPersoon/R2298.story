Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2298

Narrative:
In het resultaatbericht van Zoek persoon, Zoek persoon op adresgegevens en Geef medebewoners van persoon
mag de groep Persoon.Migratie alleen worden opgenomen als Soort migratie.Code gelijk is aan "E" (Emigratie)

Scenario: 1.    Zoek Persoon geeft resultaat met groep Persoon.Migratie.
                LT: R2298_LT01
                Uitwerking:
                Zoekopdracht op Burgerservicenummer met optie exact
                PL: test/brp-api-test/target/blobs/oranje/DELTAVERS08/DELTAVERS08C110T10_xls (bsn: 427389033)
                Verwacht resultaat:
                - Persoon.Migratie groep getoond in zoek resultaat (Migratie.soortCode = E)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/PersoonMigratieCodeE.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2298_1.1.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Persoon.Migratie groep aanwezig, migratie.code = E
Then heeft het antwoordbericht 1 groepen 'migratie'

Then is er voor xpath //brp:voornamen[text()='Jamie'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Sands'] een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/R2298-response.xml voor expressie //brp:lvg_bvgZoekPersoon_R

!-- Controle dat de groepen in andere diensten ook aanwezig zijn
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2298_1.2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/R2298-expected-response-GDP-scenario1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 2.    Zoek Persoon geeft resultaat zonder groep Persoon.Migratie
                LT: R2298_LT02
                Uitwerking:
                Zoekopdracht op Burgerservicenummer met optie exact
                Verwacht resultaat:
                - Persoon.Migratie groep niet getoond in zoek resultaat (Migratie.soortCode = I)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/PersoonMigratieCodeI.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2298_2.1.xml
Then heeft het antwoordbericht verwerking Geslaagd

!-- Persoon.Migratie groep NIET aanwezig, migratie.code = I
Then heeft het antwoordbericht 0 groepen 'migratie'

Then is er voor xpath //brp:voornamen[text()='Bernadette'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Bergma'] een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/R2298-response-scenario-2.xml voor expressie //brp:lvg_bvgZoekPersoon_R

!-- Controle dat de groepen in andere diensten wel aanwezig zijn
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2298_2.2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/BV1ZP_ZoekPersoon/expecteds/R2298-expected-response-GDP-scenario2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R



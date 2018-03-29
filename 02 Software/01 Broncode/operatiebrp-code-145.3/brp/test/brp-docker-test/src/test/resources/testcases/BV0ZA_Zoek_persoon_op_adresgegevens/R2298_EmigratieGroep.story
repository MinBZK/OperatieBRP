Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2298

Narrative:
In het resultaatbericht van Zoek persoon, Zoek persoon op adresgegevens en Geef medebewoners van persoon
mag de groep Persoon.Migratie alleen worden opgenomen als Soort migratie.Code gelijk is aan "E" (Emigratie)

Scenario: 1.    Zoek Persoon op adres geeft resultaat met groep Persoon.Migratie.
                LT: R2298_LT03
                Verwacht resultaat:
                - Persoon.Migratie groep getoond in zoek resultaat (Migratie.soortCode = E)
                Uitwerking:
                PL: test/brp-api-test/target/blobs/oranje/DELTAVERS08/DELTAVERS08C110T10_xls (bsn: 427389033)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/PersoonMigratieCodeE.xls


Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2298_1.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Persoon.Migratie groep aanwezig, migratie.code = E
Then heeft het antwoordbericht 1 groepen 'migratie'

Then is er voor xpath //brp:voornamen[text()='Jamie'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Sands'] een node aanwezig in het antwoord bericht

Scenario: 2.    Zoek Persoon op adres geeft resultaat zonder groep Persoon.Migratie
                LT: R2298_LT04
                Verwacht resultaat:
                - Persoon.Migratie groep niet getoond in zoek resultaat (Migratie.soortCode = I)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/PersoonMigratieCodeI.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2298_2.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Persoon.Migratie groep NIET aanwezig, migratie.code = I
Then heeft het antwoordbericht 0 groepen 'migratie'

Then is er voor xpath //brp:voornamen[text()='Bernadette'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Bergma'] een node aanwezig in het antwoord bericht




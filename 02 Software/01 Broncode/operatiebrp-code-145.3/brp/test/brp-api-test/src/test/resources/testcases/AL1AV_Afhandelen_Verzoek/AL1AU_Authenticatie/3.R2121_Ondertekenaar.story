Meta:
@regels         R2121
@status         Klaar

Narrative:

R2121; Er moet een toegang leveringsautorisatie voor de opgegeven partij, rol en ondertekenaar bestaan


Bij het in behandeling nemen van een leveringsverzoek geldt dat er tenminste één voorkomen van Toegang leveringsautorisatie dient te bestaan
voor de Toegang leveringsautorisatie.Leveringsautorisatie, Toegang leveringsautorisatie.Geautoriseerde en
Toegang leveringsautorisatie.Ondertekenaar die volgt uit het bericht en de ondertekening:
- De waarde van Bericht.Leveringsautorisatie uit de stuurgegevens van het inkomende bericht.
- De waarde van Bericht.Zendende partij uit de stuurgegevens van het inkomende bericht.
        -Deze bevat een Partij.Code die de 'Geautoriseerde Partij' aanduidt.
- Indien aanwezig in het bericht: De waarde van Bericht.Rol uit de parameters van het inkomende bericht. Deze bevat een Rol.Naam die de 'Rol' aanduidt.

-De OIN van het PKI overheidscertifictaat dat is gebruikt voor de digitale ondertekening van het bericht:
    - Indien deze gelijk is aan de OIN van de 'Geautoriseerde partij', dan dient Toegang leveringsautorisatie.Ondertekenaar leeg te zijn.
    - Indien deze NIET gelijk is aan de OIN van de 'Geautoriseerde partij',
        -dan dient het OIN van de Partij in Toegang leveringsautorisatie.Ondertekenaar gelijk te zijn aan de OIN van het PKI overheidscertifictaat
        -dat is gebruikt voor de digitale ondertekening

!-- Test met gevulde OIN voor geautoriseerde partij, Ondertekenaar = Leeg (standaard goed situatie)
Scenario: 1.1   Geautoriseerde Partij OIN is Utrecht, Zendende Partij OIN is Gemeente Utrecht, Toegang.Levereingsautorisatie.Ondertekenaar OIN is Leeg
                LT: R2121_LT01
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- Test met verschillende partijen met zelfde OIN, controle dat vergelijking op basis van OIN plaatsvind
Scenario: 1.2   Geautoriseerde Partij OIN is Utrecht, Zendende Partij OIN is gelijk aan OIN Gemeente Utrecht, Toegang.Levereingsautorisatie.Ondertekenaar OIN is Leeg
                LT: R2121_LT01
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'VerkeerdePartijZelfdeOIN'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- OIN ondertekenaar in leveringsautorisatie  is OIN uit ondertekenaar in bericht
Scenario: 2.1   Geautoriseerde Partij OIN is Utrecht, Ondertekenaar PKI certificaat is Gemeente Haarlem,  Toegang leveringsautorisatie.Ondertekenaar = Gemeente Haarlem
                LT: R2121_LT02
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_Gemeente_Utrecht_Ondertekenaar_Gemeente_Haarlem
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Haarlem'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- Zelde verwachting als bij Geautoriseerde partij OIN = Ondertekenaar OIN uit bericht, Ondertekenaar in leveringsautorisatie is Leeg
Scenario: 2.2   Geautoriseerde Partij OIN is Utrecht, ondertekenaar = PartijZonderOIN, Toegang.Levereingsautorisatie.Ondertekenaar OIN is Leeg
                LT: R2121_LT02
                Verwacht resultaat:
                - Foutmelding: R2343 Er is een autorisatiefout opgetreden
                - Logging: R2121 De ondertekenaar is onjuist.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'PartijZonderOIN'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- De OIN van het PKI overheidscertifictaat dat is gebruikt voor de digitale ondertekening van het bericht:
!-- Indien deze gelijk is aan de OIN van de 'Geautoriseerde partij', dan dient Toegang leveringsautorisatie.Ondertekenaar leeg te zijn

Scenario: 3.    Toegang leveringsautorisatie.Ondertekenaar is NIET leeg
                LT: R2121_LT03
                Verwacht resultaat:
                - Foutief
                - gelogd met regelcode R2121

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/OIN_Certificaat_Ondertekenaar_is_Geautoriseerde_Partij
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief

Then is er een autorisatiefout gelogd met regelcode R2121


Scenario: 4.    Toegang leveringsautorisatie.Ondertekenaar en Toegang leveringsautorisatie.Ondertekenaar beide <> De OIN van het PKI overheidscertifictaat
                LT: R2121_LT04
                Verwacht resultaat:
                - Foutief
                - gelogd met regelcode R2121


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Toegang_leveringsautorisatie_geautoriseerde_en_ondertekenaar_ongelijk
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Olst'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief

Then is er een autorisatiefout gelogd met regelcode R2121


Scenario: 5.    ZendendePartij Ongeldig voor leveringsautorisatie
                LT: R2121_LT05
                Verwacht resultaat:
                - gelogd met regelcode R2120

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Haarlem'
|ondertekenaar|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | Er is een autorisatiefout opgetreden.

Then is er een autorisatiefout gelogd met regelcode R2120

Scenario: 6.    Bericht rol ongeldig
                LT: R2121_LT06
                Verwacht resultaat:
                - Foutmelding: R2343 Er is een autorisatiefout opgetreden
                - gelogd met regelcode R2120

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'LevereningsautorisatieOndertekenaar'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Utrecht'
|rolNaam| 'Bijhoudingsorgaan College'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | Er is een autorisatiefout opgetreden.

Then is er een autorisatiefout gelogd met regelcode R2120


Scenario: 7.    Geautoriseerde Partij OIN is LEEG
                LT: R2121_LT07
                Verwacht resultaat:
                - Foutmelding: R2343 Er is een autorisatiefout opgetreden
                - gelogd met regelcode R2120


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2121/Geautoriseerde_Partij_PartijZonderOIN

Given verzoek geef details persoon met xml GDP_Geen_Ondertekenaar_en_transporteur.xml transporteur NULL ondertekenaar NULL

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | Er is een autorisatiefout opgetreden.

Then is er een autorisatiefout gelogd met regelcode R2120


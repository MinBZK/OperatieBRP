Meta:
@regels         R2122
@status         Klaar

Narrative:

R2122; Er moet een toegang leveringsautorisatie voor de opgegeven partij, rol en transporteur bestaan


Bij het in behandeling nemen van een leveringsverzoek geldt dat er tenminste één voorkomen van Toegang leveringsautorisatie dient te bestaan
voor de Toegang leveringsautorisatie.Leveringsautorisatie, Toegang leveringsautorisatie.Geautoriseerde en
Toegang leveringsautorisatie.Transporteur die volgt uit het bericht en de ondertekening:
- De waarde van Bericht.Leveringsautorisatie uit de stuurgegevens van het inkomende bericht.
- De waarde van Bericht.Zendende partij uit de stuurgegevens van het inkomende bericht.
        -Deze bevat een Partij.Code die de 'Geautoriseerde Partij' aanduidt.
- Indien aanwezig in het bericht: De waarde van Bericht.Rol uit de parameters van het inkomende bericht. Deze bevat een Rol.Naam die de 'Rol' aanduidt.

-De OIN van het PKI overheidscertifictaat dat is gebruikt voor de digitale ondertekening van het bericht:
    - Indien deze gelijk is aan de OIN van de 'Geautoriseerde partij', dan dient Toegang leveringsautorisatie.Transporteur leeg te zijn.
    - Indien deze NIET gelijk is aan de OIN van de 'Geautoriseerde partij',
        -dan dient het OIN van de Partij in Toegang leveringsautorisatie.Transporteur gelijk te zijn aan de OIN van het PKI overheidscertifictaat
        -dat is gebruikt voor de digitale ondertekening

!-- Test met gevulde OIN voor geautoriseerde partij, Ondertekenaar = Leeg (standaard goed situatie)
Scenario: 1.1   Geautoriseerde Partij OIN is Utrecht, Zendende Partij OIN is Gemeente Utrecht, Toegang.Leveringsautorisatie.Transpoteur OIN is Leeg
                LT: R2122_LT01
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- Test met verschillende partijen met zelfde OIN, controle dat vergelijking op basis van OIN plaatsvind
Scenario: 1.2   Geautoriseerde Partij OIN is Utrecht, Zendende Partij OIN is gelijk aan OIN Gemeente Utrecht, Toegang.Leveringsautorisatie.Transpoteur  OIN is Leeg
                LT: R2122_LT01
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'VerkeerdePartijZelfdeOIN'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- OIN ondertekenaar in leveringsautorisatie  is OIN uit ondertekenaar in bericht
Scenario: 2.1   Geautoriseerde Partij OIN is Utrecht, Ondertekenaar PKI certificaat is Gemeente Haarlem,  Toegang leveringsautorisatie.Transporteur = Gemeente Haarlem
                LT: R2122_LT02
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Geautoriseerde_Partij_Gemeente_Utrecht_Transporteur_Gemeente_Haarlem
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Utrecht'
|transporteur|'Gemeente Haarlem'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- Zelde verwachting als bij Geautoriseerde partij OIN = Ondertekenaar OIN uit bericht, Ondertekenaar in leveringsautorisatie is Leeg
Scenario: 2.2   Geautoriseerde Partij OIN is Utrecht, ondertekenaar = PartijZonderOIN, Toegang.leveringsautorisatie.Transporteur OIN is Leeg
                LT: R2122_LT02
                Verwacht resultaat:
                - Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'PartijZonderOIN'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- De OIN van het PKI overheidscertifictaat dat is gebruikt voor de digitale ondertekening van het bericht:
!-- Indien deze gelijk is aan de OIN van de 'Geautoriseerde partij', dan dient Toegang leveringsautorisatie.Ondertekenaar leeg te zijn

Scenario: 3.    Toegang leveringsautorisatie.Transporteur is NIET leeg
                LT: R2122_LT03
                Verwacht resultaat:
                - Foutief
                - gelogd met regelcode R2122

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/OIN_Certificaat_Transporteur_is_Geautoriseerde_Partij
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Utrecht'
|ondertekenaar|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief

Then is er een autorisatiefout gelogd met regelcode R2122


Scenario: 4.    Toegang leveringsautorisatie.Geautoriseerde en Toegang leveringsautorisatie.Transporteur beide <> De OIN van het PKI overheidscertifictaat
                LT: R2122_LT04
                Verwacht resultaat:
                - Foutief
                - gelogd met regelcode R2122


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Toegang_leveringsautorisatie_geautoriseerde_en_transporteur_ongelijk
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Utrecht'
|transporteur|'Gemeente Olst'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief

Then is er een autorisatiefout gelogd met regelcode R2122


Scenario: 5.    ZendendePartij Ongeldig voor leveringsautorisatie
                LT: R2122_LT05
                Verwacht resultaat:
                - R2120

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
|zendendePartijNaam|'Gemeente Haarlem'
|ondertekenaar|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | Er is een autorisatiefout opgetreden.

Then is er een autorisatiefout gelogd met regelcode R2120

Scenario: 6.    Bericht rol ongeldig
                LT: R2122_LT06
                Verwacht resultaat:
                - R2120

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2122/Geautoriseerde_Partij_Gemeente_Utrecht
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'LeveringsautorisatieTransporteur'
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
                LT: R2122_LT07
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

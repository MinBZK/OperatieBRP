Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2383

Narrative:
Indien de verstrekking de Soort dienst Geef medebewoners van persoon betreft en Bericht.Identificatiecriteria is gevuld met
Persoonsidentificatie (Identiteitsnummer (R2191))
OF
Bericht.Identificatiecode nummeraanduiding
OF
een of meer van de volgende adresgegevens:
Bericht.Gemeente code,
Bericht.Afgekorte naam openbare ruimte,
Bericht.Huisnummer,
Bericht.Huisletter,
Bericht.Huisnummertoevoeging,
Bericht.Locatie ten opzichte van adres,
Bericht.Postcode,
Bericht.Woonplaatsnaam
dan
moeten de identificatiecriteria op 'Peilmoment materieel' (R2395) herleidbaar zijn tot één Identificatiecode nummeraanduiding.

Scenario: 1.1   Geef medebewoners van persoon op identiteitsnummer burgerservicenummer en peilmoment leeg
                LT: R2383_LT01, R2377_LT01, R1262_LT25, R1264_LT21, R2054_LT05, R2055_LT07, R2056_LT21
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.2   Geef medebewoners van persoon op identiteitsnummer administratienummer en peilmoment leeg
                LT: R2383_LT01
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|administratienummer|'5398948626'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.3   Geef medebewoners van persoon op identiteitsnummer objectsleutel en peilmoment leeg
                Issue: R2382_LT01 ROOD-1819
                Verwacht resultaat:
                - Geslaagd


Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|objectSleutel|'3986379675759776555_2010553546973428099_2020315814923383585'


Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Geef medebewoners van persoon op Bericht.Identificatiecode nummeraanduiding
                LT:R2383_LT02, R2377_LT02
                Verwacht resultaat;
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|identificatiecodeNummeraanduiding|'0626200010016001'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Geef medebewoners van persoon op overig adresgegeven postcode
                LT:R2383_LT03, R2377_LT03
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|postcode|'2252EB'


Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 07.   Geef medebewoners van persoon op identiteitsnummer administratienummer en burgerservicenummer
                LT: R2383_LT07
                Verwacht resultaat:
                - Foutief
                - R2192: Er moet één identiteitsnummer zijn gevuld.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'606417801'
|administratienummer|'5398948626'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2192     | Er moet één identiteitsnummer zijn gevuld.


Scenario: 08.   Geef medebewoners van persoon op overig adresgegeven postcode, gemeentecode, huisletter, huisnummertoevoeging
                LT: R2383_LT03, R2383_LT08
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|postcode|'2252EB'
|gemeenteCode|'0626'
|huisletter|
|huisnummertoevoeging|
|huisnummer|16

Then heeft het antwoordbericht verwerking Geslaagd

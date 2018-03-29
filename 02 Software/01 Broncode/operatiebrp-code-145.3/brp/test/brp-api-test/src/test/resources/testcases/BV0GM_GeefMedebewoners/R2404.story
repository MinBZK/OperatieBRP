Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2404

Narrative:
Indien identificatiecriterium Persoonsidentificatie (Identiteitsnummer (R2191)) is gebruikt,
dan mag de persoon niet overleden zijn op peildatum; Persoon.Nadere bijhoudingsaard <> "Overleden" (O)
op 'Peilmoment materieel' (R2395)

Scenario: 1.    Geef medebewoners van persoon met nadere bijhoudingsaard <> overleden (actueel)
                LT: R2404_LT01
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

Scenario: 2.    Geef medebewoners van persoon met nadere bijhoudingsaard = Fout
                LT: R2404_LT02, R1403_LT10
                Verwacht resultaat:
                - Foutief
                - R1403: Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_nadere_bijhoudingsaard_fout_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'951688777'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Scenario: 3.    Geef medebewoners van persoon met nadere bijhoudingsaard = Overleden, op BSN
                LT: R2404_LT03
                Verwacht resultaat:
                - Foutief
                - R2404: Persoon is overleden

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_naderebijhoudingsaard_overleden_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'951688777'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                      |
| R2404  | 	Persoon is overleden        |

Scenario: 4.    Geef medebewoners van persoon met nadere bijhoudingsaard = Overleden, op ANR
                LT: R2404_LT04
                Verwacht resultaat:
                - Foutief
                - R2404: Persoon is overleden

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_naderebijhoudingsaard_overleden_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|administratienummer|'1838917138'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                      |
| R2404  | 	Persoon is overleden        |


Scenario: 6.    Geef medebewoners van persoon met nadere bijhoudingsaard = overleden, op BSN, met peildatum dat persoon nog leefde
                LT: R2404_LT06
                Verwacht resultaat:
                - Foutief, R1403 Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_naderebijhoudingsaard_overleden_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|peilmomentMaterieel|'2010-01-01'
|burgerservicenummer|'606417801'

!-- In principe zou je verwachten dat het antwoordbericht melding geslaagd geeft,
!-- Echter door de manier hoe de blob geconverteerd wordt, wordt de voorgaande nadere bijhoudingsaard (voor het overlijden)
!-- gevuld met een ?, dus nadere bijhoudingaard op peilmoment is onbekend
!-- Bij nadere.bijhoudingsaard onbekend hoort de melding bij regel R1403

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |
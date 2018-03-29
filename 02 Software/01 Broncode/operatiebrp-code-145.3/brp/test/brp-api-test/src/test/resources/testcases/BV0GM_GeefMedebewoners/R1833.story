Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R1833


Narrative:
Elke sleutel in een inkomend Bericht om een Persoon te identificeren, voldoet aan 'Gemaskeerde objectsleutel' (R1834).

Scenario: 1.    Geef Medebewoners met ongeldige objectsleutel van persoon
                LT: R1833_LT02
                Verwacht resultaat:
                - Foutief; objectsleutel niet correct

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|objectSleutel|'590984809'

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                        |
| R1833 | De opgegeven objectsleutel voldoet niet aan de gestelde eisen. |

Scenario: 2.    Geef Medebewoners met geldige objectsleutel van persoon
                LT: R1833_LT01
                Verwacht resultaat:
                - Geslaagd; objectsleutel valide identificatie criteria

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|objectSleutel|'3986379675759776555_2010553546973428099_2020315814923383585'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3. Geef details persoon met geldige objectsleutel

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_bevraging
Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners bevraging'
|zendendePartijNaam|'Gemeente Utrecht'
|objectSleutel|'3986379675759776555_2010553546973428099_2020315814923383585'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4. Geef details persoon met niet matchende objectsleutel

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_bevraging
Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners bevraging'
|zendendePartijNaam|'Gemeente Utrecht'
|objectSleutel|'1986379675759776555_2010553546973428099_2020315814923383585'

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                        |
| R1403 | Geen persoon gevonden met opgegeven identificatie criteria |

Scenario: 5. Geef details persoon met ONgeldige objectsleutel

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_bevraging
Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners bevraging'
|zendendePartijNaam|'Gemeente Utrecht'
|objectSleutel|'43521338_312435213385879553A'

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                        |
| R1833 | De opgegeven objectsleutel voldoet niet aan de gestelde eisen. |

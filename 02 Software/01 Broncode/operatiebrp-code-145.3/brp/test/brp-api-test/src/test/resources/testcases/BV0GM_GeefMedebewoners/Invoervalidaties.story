Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2295, R2377

Narrative:
R2295:
Indien gevuld dan mag Bericht.Peilmoment materieel niet na 'Systeemdatum' (R2016) liggen.

Scenario: 1.    Peilmoment in de toekomst
                LT:R2295_LT06
                Verwacht resultaat:
                - R2295: Peilmoment materieel mag niet in de toekomst liggen.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'606417801'
|peilmomentMaterieel|morgen

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R2295 | 	Peilmoment materieel mag niet in de toekomst liggen.          |


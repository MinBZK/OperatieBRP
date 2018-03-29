Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2524, R2585
@sleutelwoorden     Autorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek geldt voor het voorkomen van Leveringsautorisatie zoals
bepaald door berichtparameter leveringsautorisatieIdentificatie in het bericht, dat Leveringsautorisatie.Stelsel
de waarde "BRP" moet bevatten indien
Bericht.Zendende partij verwijst naar een Partij.Datum overgang naar BRP die gevuld is met een
waarde die kleiner of gelijk is aan de 'Systeemdatum' (R2016).

| nr | Levsaut.stelsel | Partij.datumovergangBRP | Ber. def. | Verwerking | Regel                  |
| 1  | BRP             | leeg                    | BRP       | Foutief    | R2585_LT01, R2524_LT01 |
| 2  | BRP             | > systeemdatum          | BRP       | Foutief    | R2585_LT01, R2524_LT02 |
| 3  | BRP             | = systeemdatum          | BRP       | Geslaagd   | R2585_LT01, R2524_LT03 |
| 4  | BRP             | < systeemdatum          | BRP       | Geslaagd   | R2585_LT01, R2524_LT04 |
| 5  | GBA             | leeg                    | BRP       | Foutief    | R2585_LT03, R2524_LT05 |
| 6  | GBA             | > systeemdatum          | BRP       | Foutief    | R2585_LT03, R2524_LT06 |
| 7  | GBA             | = systeemdatum          | BRP       | Foutief    | R2585_LT03, R2524_LT07 |
| 8  | GBA             | < systeemdatum          | BRP       | Foutief    | R2585_LT03, R2524_LT08 |
| 9  | leeg            | leeg                    | BRP       | Foutief    | R2585_LT05, R2524_LT01 |


NB: R2585_LT02 Indien de bericht definitie ongelijk aan BRP is, maar wel over het brp koppelvlak wordt aangeboden, volgt een xsd fout.

Scenario: 1.   Partij.Datum overgang naar BRP is Leeg, BRP stelsel
                LT:  R2585_LT01, R2524_LT01
                Verwacht resultaat:
                - Geen levering, want geen datum overgang BRP gevuld, dus GBA partij
                - GBA partij mag geen BRP autorisatie gebruiken

Given leveringsautorisatie uit autorisatie/Doelbinding_BRP_DatumOvergangBRP_Leeg
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls

Given verzoek geef medebewoners van persoon:
| key                      | value             |
| leveringsautorisatieNaam | 'Doelbinding BRP' |
| zendendePartijNaam       | 'Gemeente GBA'    |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2524

Scenario: 2.   Partij.Datum overgang naar BRP is groter dan systeemdatum, BRP stelsel
                LT:  R2585_LT01, R2524_LT02
                Verwacht resultaat:
                - Foutief
                - GBA partij mag geen BRP autorisatie gebruiken

Given leveringsautorisatie uit autorisatie/Doelbinding_BRP_DatumOvergangBRP_Morgen
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls

Given verzoek geef medebewoners van persoon:
| key                      | value                    |
| leveringsautorisatieNaam | 'Doelbinding BRP'        |
| zendendePartijNaam       | 'DatumOvergangBRPMorgen' |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2524

Scenario: 3.   Partij.Datum overgang naar BRP is systeemdatum, BRP stelsel
                LT:  R2585_LT01, R2524_LT03
                Verwacht resultaat:
                - Geslaagd
                - Volledig bericht geleverd

Given leveringsautorisatie uit autorisatie/Doelbinding_BRP_DatumOvergangBRP_Vandaag
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls

Given verzoek geef medebewoners van persoon:
| key                      | value                     |
| leveringsautorisatieNaam | 'Doelbinding BRP'         |
| zendendePartijNaam       | 'DatumOvergangBRPVandaag' |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.   Partij.Datum overgang naar BRP is kleiner dan systeemdatum, BRP stelsel
                LT:  R2585_LT01, R2524_LT04
                Verwacht resultaat:
                - Geslaagd
                - Mutatiebericht geleverd

Given leveringsautorisatie uit autorisatie/Doelbinding_BRP_DatumOvergangBRP_Gisteren
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
Given verzoek geef medebewoners van persoon:
| key                      | value                      |
| leveringsautorisatieNaam | 'Doelbinding BRP'          |
| zendendePartijNaam       | 'DatumOvergangBRPGisteren' |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5.   Partij.Datum overgang naar BRP is Leeg, stelsel is GBA, ber definitie is BRP
                LT:  R2585_LT03, R2524_LT05
                Verwacht resultaat:
                - Foutief

Given leveringsautorisatie uit autorisatie/Doelbinding_GBA_DatumOvergangBRP_Leeg
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls

Given verzoek geef medebewoners met xml requests/GeefMedeBewoners_DatumOvergangBRPLeeg.xml transporteur 00500001002220647000 ondertekenaar 00500001002220647000

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2585

Scenario: 6.   Partij.Datum overgang naar BRP is groter dan systeemdatum, stelsel is GBA
                LT: R2585_LT03, R2524_LT06
                Verwacht resultaat:
                - Foutief

Given leveringsautorisatie uit autorisatie/Doelbinding_GBA_DatumOvergangBRP_Morgen
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
Given verzoek geef medebewoners met xml requests/GeefMedeBewoners_DatumOvergangBRPMorgen.xml transporteur 00000001002220647330 ondertekenaar 00000001002220647330

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2585

Scenario: 7.    Partij.Datum overgang naar BRP is gelijk aan systeemdatum, stelsel is GBA
                LT:  R2585_LT03, R2524_LT07
                Verwacht resultaat:
                - Geen Levering voor GBA autorisatie
                - Foutmelding: R2524 Stelsel van de leveringsautorisatie moet BRP zijn

Given leveringsautorisatie uit autorisatie/Doelbinding_GBA_DatumOvergangBRP_Vandaag
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
Given verzoek geef medebewoners van persoon:
| key                      | value                     |
| leveringsautorisatieNaam | 'Doelbinding GBA'         |
| zendendePartijNaam       | 'DatumOvergangBRPVandaag' |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2524

Scenario: 8.    Partij.Datum overgang naar BRP is kleiner dan systeemdatum, stelsel is GBA
                LT:  R2524_LT08
                Verwacht resultaat:
                - Geen Levering voor GBA autorisatie
                - Foutmelding: R2524 Stelsel van de leveringsautorisatie moet BRP zijn

Given leveringsautorisatie uit autorisatie/Doelbinding_GBA_DatumOvergangBRP_Gisteren
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
Given verzoek geef medebewoners van persoon:
| key                      | value                      |
| leveringsautorisatieNaam | 'Doelbinding GBA'          |
| zendendePartijNaam       | 'DatumOvergangBRPGisteren' |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2524

Scenario: 9.   Partij.Datum overgang naar BRP is leeg, stelsel is leeg
                LT:  R2585_LT05, R2524_LT01
                Verwacht resultaat:
                - Foutmelding: R2524 Stelsel van de leveringsautorisatie moet BRP zijn

Given leveringsautorisatie uit autorisatie/Doelbinding_BRP_DatumOvergangBRP_Leeg_stelsel_leeg
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
Given verzoek geef medebewoners van persoon:
| key                      | value             |
| leveringsautorisatieNaam | 'Doelbinding BRP' |
| zendendePartijNaam       | 'Gemeente GBA'    |
|burgerservicenummer|'383096777' |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                    |
| R2343 | De autorisatie is onjuist. |

Then is er een autorisatiefout gelogd met regelcode R2524
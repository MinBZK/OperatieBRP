Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoners van Persoon
@regels             R1257, R1258, R1262, R1263, R1264, R2052, R2053, R2054, R2055, R2056, R2120, R2121, R2122, R2130, R2239, R2242, R2243, R2244, R2245

Narrative:
In deze story worden de autorisatieregel voor Geef Medebewoners van persoon getest

Scenario:   1.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT22, R2343_LT01
                Verwacht resultaat:
                - 1. Foutief bericht met de melding "De opgeven leveringsautorisatie bevat niet de opgegeven dienst"
                Uitwerking:
                - twee leveringsautorisatie ingeladen. 1 met 4 diensten, waarvan de 4e geef medebewoners is
                - de andere leveringsautorisatie heeft dezelfde eerste 3 diensten, maar niet de vierde: geef medebewoners
                - Zoeken op dienst id 4 (geef medebewoners)

Given leveringsautorisatie uit autorisatie/doelbinding_met_geef_medebewoners, autorisatie/doelbinding_zonder_geef_medebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'doelbinding zonder geef medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'606417801'
|dienstId|4

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2343    | De opgeven leveringsautorisatie bevat niet de opgegeven dienst |

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   2. De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
            LT: R1262_LT26
            Dienst: Geef medebewoners
            Verwacht resultaat: Response bericht met vulling
            - De gevraagde dienst is niet geldig

Given leveringsautorisatie uit autorisatie/Geef_medebewoners_datumingang_ongeldig
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef medebewoners datumingang ongeldig'
|zendendePartijNaam|'Gemeente Haarlem'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario:   3. De gevraagde dienst is geblokkeerd.
            LT: R1264_LT22
            Dienst: Geef medebewoners
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given leveringsautorisatie uit autorisatie/Geef_medebewoners_dienst_geblokkeerd
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef medebewoners dienst geblokkeerd'
|zendendePartijNaam|'Gemeente Haarlem'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario: 4.    De opgegeven dienst (geef medebewoners) bestaat niet en dient te bestaan
                LT: R2055_LT08
                Verwacht resultaat: De gevraagde dienst bestaat niet

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|burgerservicenummer|'606417801'
|dienstId|9999

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                           |
| R2343    | De opgegeven dienst bestaat niet  |

Then is er een autorisatiefout gelogd met regelcode R2055

Scenario:   5. De gevraagde dienst is geblokkeerd.
            LT: R2056_LT22
            Dienst: Geef medebewoners
                Verwacht resultaat: Response bericht met vulling
                - De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Geef_medebewoners_dienstbundel_geblokkeerd
Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef medebewoners dienstbundel geblokkeerd'
|zendendePartijNaam|'Gemeente Haarlem'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   6. R2239 Ongeldige Dienstbundel
               LT: R2239_LT04
               Verwacht Resultaat:
               Foutieve situatie aangetroffen; verwerking blokkeert
               Meldingstekst:	De dienstbundel is niet geldig.

Given leveringsautorisatie uit autorisatie/GeefMedebewonerDienstbundelOngeldig
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Ongeldige dienstbundel geef medebewoners'
|zendendePartijNaam|'Gemeente Haarlem'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   7.  R2242 Ongeldige Partij
                LT: R2242_LT05
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given leveringsautorisatie uit autorisatie/R2242.txt
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'GemeenteDatumIngangDatumEindeInVerleden'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242

Scenario:   8.  R2243 Ongeldige Ondertekenaar
                LT: R2243_LT04
                Verwacht resultaat:
                - Foutmelding: R2243
                  De ondertekenaar is geen geldige partij.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ
|ondertekenaar|'DatumEindeGisterenPartij'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243


Scenario:   9.  R2244 Ongeldige transporteur
                LT: R2244_LT04
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'606417801'
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeGisterenPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   10. R2245 Ongeldige PartijRol
                LT: R2245_LT04
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De combinatie partij en rol is niet geldig.

Given leveringsautorisatie uit autorisatie/2245.txt
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeVandaag'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245
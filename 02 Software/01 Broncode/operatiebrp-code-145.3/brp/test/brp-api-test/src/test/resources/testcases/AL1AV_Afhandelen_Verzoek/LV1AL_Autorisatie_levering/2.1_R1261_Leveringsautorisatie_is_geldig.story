Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek is het voorkomen van Leveringsautorisatie zoals bepaald door berichtparameter
leveringsautorisatieIdentificatie in het bericht Geldig (R2129) op de 'Systeemdatum' (R2016).

Scenario:   1.  Leveringsautorisatie is niet geldig, datumingang ligt na de systeemdatum
                LT: R1261_LT04
                Verwacht resultaat: Response bericht, met vulling
                Foutmelding

                *Meldings-niveau: *Foutieve situatie aangetroffen; verwerking blokkeert

                *Meldingstekst: *De opgegeven leveringsautorisatie is niet geldig

                *Loggings-niveau: *Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/leveringsautorisatie_morgen_geldig
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|622389609

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De opgegeven leveringsautorisatie is niet geldig.  |

Then is er een autorisatiefout gelogd met regelcode R1261

Scenario:   2.  Leveringsautorisatie is niet geldig, datumeinde is gelijk aan de systeemdatum
                LT: R1261_LT05
                Verwacht resultaat: Response bericht, met vulling
                Foutmelding

                *Meldings-niveau: *Foutieve situatie aangetroffen; verwerking blokkeert

                *Meldingstekst: *De opgegeven leveringsautorisatie is niet geldig

                *Loggings-niveau: *Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/leveringsautorisatie_eindigt_vandaag
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|622389609

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De opgegeven leveringsautorisatie is niet geldig.  |

Then is er een autorisatiefout gelogd met regelcode R1261

Scenario:   3.  Leveringsautorisatie is niet geldig, datumeinde is kleiner dan de systeemdatum
                LT: R1261_LT06
                Verwacht resultaat: Response bericht, met vulling
                Foutmelding

                *Meldings-niveau: *Foutieve situatie aangetroffen; verwerking blokkeert

                *Meldingstekst: *De opgegeven leveringsautorisatie is niet geldig

                *Loggings-niveau: *Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/leveringsautorisatie_eindigt_gisteren
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|622389609

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De opgegeven leveringsautorisatie is niet geldig.  |

Then is er een autorisatiefout gelogd met regelcode R1261
Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek geldt voor het voorkomen van Leveringsautorisatie
zoals bepaald door berichtparameter leveringsautorisatieIdentificatie in het bericht,
dat Leveringsautorisatie.Geblokkeerd? ongelijk is aan "Ja"

Scenario:   1.      De opgegeven leveringsautorisatie is geblokkeerd
                    LT: R1263_LT02
                    Verwacht resultaat: Response bericht, met vulling

                    Foutmelding

                    Meldingsniveau:
                    Foutieve situatie aangetroffen; verwerking blokkeert

                    Meldingstekst:
                    De opgegeven leveringsautorisatie is geblokkeerd door de beheerder

                    Loggingsniveau:
                    Illegale poging


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/leveringsautorisatie_geblokkeerd
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|622389609

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                             |
| R2343    | De opgegeven leveringsautorisatie is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1263
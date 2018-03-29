Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek geldt dat het voorkomen van Leveringsautorisatie
zoals bepaald door parameter leveringsautorisatieIdentificatie in het bericht dient te bestaan.

Scenario:   1.      De opgegeven leveringsautorisatie bestaat niet
                    LT: R2053_LT02
                    Dienst SynchronisatiePersoon
                    Verwacht resultaat: Response bericht met vulling

                    Foutmelding

                    Meldingsniveau:
                    Foutieve situatie aangetroffen; verwerking blokkeert

                    Meldingstekst:
                    De opgegeven leveringsautorisatie bestaat niet

                    Loggingsniveau:
                    Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|leveringsautorisatieId|9999

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                         |
| R2343    | De opgegeven leveringsautorisatie bestaat niet. |

Then is er een autorisatiefout gelogd met regelcode R2053

Scenario:   2.      De opgegeven leveringsautorisatie bestaat niet
                    Dienst: geefDetailsPersoon
                    Verwacht resultaat: Response bericht met foutmelding
!-- R2053 is generiek op Leverigsautorisatie niveau, testen per dienst hebben geen nut.
!-- Waarom staat deze test hier?

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|leveringsautorisatieId|9999
|dienstId|9999

Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                         |
| R2343    | De opgegeven leveringsautorisatie bestaat niet. |

Then is er een autorisatiefout gelogd met regelcode R2053
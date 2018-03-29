Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek anders dan een bevraging
(bericht bevat niet de parameter dienstIdentificatie) geldt het volgende:

Als de Leveringsautorisatie zoals aangegeven door berichtparameter leveringsautorisatieIdentificatie bestaat,
dan dient die Leveringsautorisatie de De dienst die gevraagd wordt (R2085) te bevatten.

Scenario:   1.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT02
                Dienst: Synchronisatie persoon
                Verwacht resultaat: Foutmelding
                Meldingsniveau: Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:De opgeven leveringsautorisatie bevat de gevraagde dienst niet
                Loggingsniveau: Illegale poging


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_dienst
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                 |
| R2343    | De leveringsautorisatie bevat de gevraagde dienst niet. |

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   2.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT04
                Dienst: Plaats afnemerindicatie
                Verwacht resultaat: Foutmelding
                Meldingsniveau: Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:De opgeven leveringsautorisatie bevat de gevraagde dienst niet
                Loggingsniveau: Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_dienst
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                 |
| R2343    | De leveringsautorisatie bevat de gevraagde dienst niet. |

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   3.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT06
                Dienst: Verwijder afnemerindicatie
                Verwacht resultaat: Foutmelding
                Meldingsniveau: Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:De opgeven leveringsautorisatie bevat de gevraagde dienst niet
                Loggingsniveau: Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls

Given leveringsautorisatie uit autorisatie/geen_dienst
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                 |
| R2343    | De leveringsautorisatie bevat de gevraagde dienst niet. |

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   4.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT08
                Dienst: Synchronisatie stamgegeven
                Verwacht resultaat: Foutmelding
                Meldingsniveau: Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:De opgeven leveringsautorisatie bevat de gevraagde dienst niet
                Loggingsniveau: Illegale poging

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_dienst
Given verzoek synchroniseer stamgegeven:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|stamgegeven|ElementTabel
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                 |
| R2343    | De leveringsautorisatie bevat de gevraagde dienst niet. |

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   5.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT10, R2130_LT12, R2130_LT14
                Dienst: Attendering, Mutatielevering op basis van doelbinding, Mutatielevering op basis van Afnemerindicatie
                Verwacht resultaat:
                Geen mutatiebericht

Given leveringsautorisatie uit autorisatie/geen_dienst
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

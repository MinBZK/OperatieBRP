Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek moet de
De dienst die gevraagd wordt (R2085) Geldig (R2129) zijn op 'Systeemdatum' (R2016).

Scenario: 1.    De gevraagde dienst Synchronisatie persoon is niet geldig
                LT: R1262_LT04
                Datum ingang > systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262


Scenario: 2.    De gevraagde dienst Synchronisatie persoon is niet geldig
                LT: R1262_LT05
                Datum einde = systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienst_datumeinde_vandaag
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario: 3.    De gevraagde dienst Synchronisatie persoon is niet geldig
                LT: R1262_LT06
                Datum einde < systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienst_datumeinde_gisteren
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario: 4.    De gevraagde dienst Plaatsen afnemerindicatie is niet geldig
                LT: R1262_LT08
                Datum ingang > systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario: 5.    De gevraagde dienst Verwijder afnemerindicatie is niet geldig
                LT: R1262_LT10
                Datum ingang > systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Autorisatietest'        | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |


Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario: 6.    De gevraagde dienst Synchroniseer Stamgegeven is niet geldig
                LT: R1262_LT12, R2057_LT28
                Datum ingang > systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given verzoek synchroniseer stamgegeven:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|stamgegeven|ElementTabel

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario: 7.    De gevraagde dienst Geef Details Persoon is niet geldig
                LT: R1262_LT14
                Datum ingang > systeemdatum
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario:   8. De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
            LT: R1262_LT16
            Dienst: Attendering
            Verwacht resultaat: Geen mutatiebericht gevonden

Given leveringsautorisatie uit autorisatie/dienst_attendering_morgen_geldig
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   9. De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
            LT: R1262_LT18
            Dienst: Mutatielevering op basis van doelbinding
            Verwacht resultaat: Geen mutatiebericht gevonden

Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   10. De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
                LT: R1262_LT20
                Dienst: Mutatielevering op basis van afnemerindicatie
                Verwacht resultaat: Geen mutatiebericht gevonden

Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_mutlev_afnemerindicatie_morgen_geldig

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Autorisatietest'           | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   11. De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
            LT: R1262_LT22
            Dienst: Zoek Persoon
            Verwacht resultaat: Response bericht met vulling
            - De gevraagde dienst is niet geldig

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2266/Zoek_Persoon_datumingang_ongeldig
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon datumingang ongeldig'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario: 12.    De gevraagde dienst Verwijder afnemerindicatie is niet geldig
                LT: R1262_LT10
                Datum ingang > systeemdatum
                Extra testje, of volgorde van foutafhandeling correct is, afnemerindicatie bestaat niet mag niet teruggegeven worden als de dienst niet geldig is.
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is niet geldig, persoon heeft ook geen afnemerindicatie

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given leveringsautorisatie uit autorisatie/dienst_morgen_geldig
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262
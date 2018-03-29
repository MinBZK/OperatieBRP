Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek geldt het volgende:

In De dienst die gevraagd wordt (R2085) is Dienst.Geblokkeerd? ongelijk aan "Ja".

Scenario: 1.    De gevraagde dienst Synchronisatie persoon is geblokkeerd
                LT: R1264_LT02
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_geblokkeerd
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario: 2.    De gevraagde dienst Plaatsen afnemerindicatie is geblokkeerd
                LT: R1264_LT04
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_geblokkeerd
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario: 3.    De gevraagde dienst Verwijder afnemerindicatie is geblokkeerd
                LT: R1264_LT06
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Autorisatietest'        | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_geblokkeerd
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario: 4.    De gevraagde dienst synchronisatie stamgegeven is geblokkeerd
                LT: R1264_LT08
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_geblokkeerd
Given verzoek synchroniseer stamgegeven:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|stamgegeven|ElementTabel

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario: 5.    De gevraagde dienst Geef Details persoon is geblokkeerd
                LT: R1264_LT10
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_geblokkeerd
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario:   6.  De gevraagde dienst is geblokkeerd
                LT: R1264_LT12
                Dienst: Attendering
                Verwacht resultaat: Geen mutatielevering

Given leveringsautorisatie uit autorisatie/dienst_attendering_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   7.  De gevraagde dienst is geblokkeerd
                LT: R1264_LT14
                Dienst: Mutatielevering op basis van doelbinding
                Verwacht resultaat: Geen mutatielevering

Given leveringsautorisatie uit /levering_autorisaties_nieuw/dienst_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   8.  De gevraagde dienst is geblokkeerd
                LT: R1264_LT16
                Dienst: Mutatielevering op basis van afnemerindicatie
                Verwacht resultaat: Geen mutatielevering

Given leveringsautorisatie uit autorisatie/dienst_mutlev_afnemerindicatie_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   9. De gevraagde dienst is geblokkeerd.
            LT: R1264_LT18
            Dienst: Zoek Persoon
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2266/Zoek_Persoon_dienst_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon dienst geblokkeerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264
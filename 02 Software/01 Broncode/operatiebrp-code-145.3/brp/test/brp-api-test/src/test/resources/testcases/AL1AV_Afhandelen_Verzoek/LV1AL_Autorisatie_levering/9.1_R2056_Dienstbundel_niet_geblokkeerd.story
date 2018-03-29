Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een leveringsverzoek geldt het volgende:

Bij de Dienstbundel van de (via berichtparameter dienstIdentificatie) opgegeven Dienst is Dienstbundel.Geblokkeerd? ongelijk aan "Ja".

Scenario:   1.  Synchronisatie persoon, De dienstenbundel is geblokkeerd
                LT: R2056_LT02
                Verwacht resultaat: De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienstbundel_geblokkeerd
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   2.  Plaatsen afnemerindicatie, De dienstenbundel is geblokkeerd
                LT: R2056_LT04
                Verwacht resultaat: De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienstbundel_geblokkeerd
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   3.  Verwijderen afnemerindicatie, De dienstenbundel is geblokkeerd
                LT: R2056_LT06
                Verwacht resultaat: De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Autorisatietest'        | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given leveringsautorisatie uit autorisatie/dienstbundel_geblokkeerd
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   4.  synchroniseer stamgegeven, De dienstenbundel is geblokkeerd
                LT: R2056_LT08
                Verwacht resultaat: De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienstbundel_geblokkeerd
Given verzoek synchroniseer stamgegeven:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|stamgegeven|ElementTabel
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   5.  Geef details persoon, De dienstenbundel is geblokkeerd
                LT: R2056_LT10
                Verwacht resultaat: De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/dienstbundel_geblokkeerd
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   6.  De dienstenbundel is geblokkeerd
                LT: R2056_LT12
                Dienst: Attendering
                Verwacht resultaat:
                Geen Mutatielevering

Given leveringsautorisatie uit autorisatie/dienstbundel_attendering_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   7.  De dienstenbundel is geblokkeerd
                LT: R2056_LT14
                Dienst: Mutatielevering op basis van doelbinding
                Verwacht resultaat:
                Geen Mutatielevering

Given leveringsautorisatie uit autorisatie/dienstbundel_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:  8.   De dienstenbundel is geblokkeerd
                LT: R2056_LT16
                Dienst: Mutatielevering op basis van afnemerindicatie
                Verwacht resultaat:
                Geen Mutatielevering

Given leveringsautorisatie uit autorisatie/dienstbundel_mutlev_afnemerindicatie_geblokkeerd

Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Autorisatietest'        | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |


When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   9. De gevraagde dienst is geblokkeerd.
            LT: R2056_LT18
            Dienst: Zoek Persoon
                Verwacht resultaat: Response bericht met vulling
                - De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2266/Zoek_Persoon_dienstbundel_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon dienstbundel geblokkeerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056
Meta:

@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2457, R2459
@versie             1

Narrative:
R2457

De groep Vrij Bericht die aanwezig is voor de verzendende partij moet nu geldig zijn.

Bij het in behandeling nemen van het vrije bericht geldt dat 'Geautoriseerde Verzend Partij'
zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452) Geldig (R2129) is op de 'Systeemdatum' (R2016).

R2459


De groep Vrij Bericht die aanwezig is voor de ontvangende partij moet nu geldig zijn.

Bij het in behandeling nemen van het vrije bericht geldt dat 'Geautoriseerde Ontvangst Partij'
zoals gedefinieerd door De autorisatie voor het ontvangen van een vrij bericht (R2453) Geldig (R2129) is op de 'Systeemdatum' (R2016).

Scenario: 1.    Zowel 'Geautoriseerde Verzend Partij' (Datum.Ingang gisteren, Datum.Einde Leeg) als 'Geautoriseerde Ontvangst Partij' (Datum.Ingang vandaag, Datum Einde morgen) is geldig
                LT: R2457_LT01, R2459_LT02
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Zowel 'Geautoriseerde Verzend Partij' (Datum.Ingang vandaag, Datum Einde morgen) als 'Geautoriseerde Ontvangst Partij' (Datum.Ingang gisteren, Datum.Einde Leeg) Partij is geldig
                LT: R2457_LT02, R2459_LT01
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|transporteur|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Vrij bericht Geautoriseerde Verzend Partij Datum Ingang = morgen Datum Einde is Leeg
                LT: R2457_LT03, R2343_LT01
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2457

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangMorgenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangMorgenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijVrijBerichtDatumIngangMorgenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangMorgenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2457

Scenario: 4.    Vrij bericht 'Geautoriseerde Verzend Partij' Datum Ingang = gisteren Datum Einde is Vandaag
                LT: R2457_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2457

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeVandaag'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeVandaag'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeVandaag'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeVandaag'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2457

Scenario: 5.    Vrij bericht 'Geautoriseerde Verzend Partij' Datum Ingang = gisteren Datum Einde is Gisteren
                LT: R2457_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2457

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeGisteren'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeGisteren'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeGisteren'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeGisteren'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2457

Scenario: 6.    Vrij bericht  'Geautoriseerde Ontvangst Partij' Datum Ingang = morgen Datum Einde is Leeg
                LT: R2459_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2459

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangMorgenDatumEindeLeeg'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2459

Scenario: 7.    Vrij bericht  'Geautoriseerde Ontvangst Partij' Datum Ingang = Gisteren Datum Einde is Vandaag
                LT: R2459_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2459

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeVandaag'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2459

Scenario: 8.    Vrij bericht  'Geautoriseerde Ontvangst Partij' Datum Ingang = Gisteren Datum Einde is Gisteren
                LT: R2459_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2459

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeGisteren'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2459
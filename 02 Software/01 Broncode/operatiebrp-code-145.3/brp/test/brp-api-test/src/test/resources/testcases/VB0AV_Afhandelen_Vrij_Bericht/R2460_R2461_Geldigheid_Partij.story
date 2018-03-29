Meta:

@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2460, R2462
@versie             1

Narrative:
R2460

Bij het in behandeling nemen van het vrije bericht geldt dat 'Geautoriseerde Verzend Partij' zoals gedefinieerd door
De autorisatie voor het verzenden van een vrij bericht (R2452)
moet verwijzen naar een Geldig voorkomen stamgegeven op peildatum (R1284) op 'Systeemdatum' (R2016) in Partij.

R2461

Bij het in behandeling nemen van het vrije bericht geldt dat 'Geautoriseerde Ontvangst Partij' zoals gedefinieerd door
De autorisatie voor het ontvangen van een vrij bericht (R2453) moet verwijzen naar een Geldig voorkomen stamgegeven
op peildatum (R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario: 1.    Zowel zendende (Datum.Ingang gisteren, Datum.Einde Leeg) als ontvangende (Datum.Ingang vandaag, Datum Einde morgen) Partij is geldig
                LT: R2460_LT01, R2461_LT02
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Zowel zendende (Datum.Ingang vandaag, Datum Einde morgen) als ontvangende (Datum.Ingang gisteren, Datum.Einde Leeg) Partij is geldig
                LT: R2460_LT02, R2461_LT01
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangVandaagDatumEindeMorgen'
|zenderVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ontvangerVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijDatumIngangVandaagDatumEindeMorgen'
|transporteur|'PartijDatumIngangVandaagDatumEindeMorgen'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Vrij bericht zendende Partij Datum Ingang = morgen Datum Einde is Leeg
                LT: R2460_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2460

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangMorgenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangMorgenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijDatumIngangMorgenDatumEindeLeeg'
|transporteur|'PartijDatumIngangMorgenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2460

Scenario: 4.    Vrij bericht zendende Partij Datum Ingang = gisteren Datum Einde is Vandaag
                LT: R2460_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2460

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeVandaag'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeVandaag'
|ontvangerVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeVandaag'
|transporteur|'PartijDatumIngangGisterenDatumEindeVandaag'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2460

Scenario: 5.    Vrij bericht zendende Partij Datum Ingang = gisteren Datum Einde is Gisteren
                LT: R2460_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2460

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeGisteren'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeGisteren'
|ontvangerVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeGisteren'
|transporteur|'PartijDatumIngangGisterenDatumEindeGisteren'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2460

Scenario: 6.    Vrij bericht ontvangende Partij Datum Ingang = morgen Datum Einde is Leeg
                LT: R2461_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2461

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangMorgenDatumEindeLeeg'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2461

Scenario: 7.    Vrij bericht ontvangende Partij Datum Ingang = Gisteren Datum Einde is Vandaag
                LT: R2461_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2461

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangGisterenDatumEindeVandaag'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2461

Scenario: 8.    Vrij bericht ontvangende Partij Datum Ingang = Gisteren Datum Einde is Gisteren
                LT: R2461_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2461

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangGisterenDatumEindeGisteren'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2461
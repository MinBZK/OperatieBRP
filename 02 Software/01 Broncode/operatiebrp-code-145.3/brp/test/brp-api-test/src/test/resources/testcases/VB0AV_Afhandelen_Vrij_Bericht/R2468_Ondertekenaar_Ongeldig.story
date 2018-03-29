Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2468
@versie             1

Narrative:

Het OIN van het PKI-overheidscertificaat waarmee het vrije bericht is ondertekend
(De 'Ondertekenaar', zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452)) moet verwijzen naar een Geldig voorkomen stamgegeven op peildatum (R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario: 1.    Ondertekenaar (Datum.Ingang gisteren, Datum.Einde Leeg) is geldig
                LT: R2468_LT01
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaarDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijOndertekenaarDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijOndertekenaarDatumIngangGisterenDatumEindeLeeg'
|ondertekenaar|'Partij4DatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijOndertekenaarDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Ondertekenaar (Datum.Ingang gisteren, Datum.Einde morgen) is geldig
                LT: R2468_LT02
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|zenderVrijBericht|'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|ontvangerVrijBericht|'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|ondertekenaar|'PartijOndDatumIngangGisterenDatumEindeMorgen'
|transporteur|'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Ondertekenaar (Datum.Ingang morgen, Datum.Einde Leeg) is ONgeldig
                LT: R2468_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2468

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaarDatumIngangMorgenDatumEindeLeeg'
|zenderVrijBericht|'PartijOndertekenaarDatumIngangMorgenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|ondertekenaar|'Partij2DatumIngangMorgenDatumEindeLeeg'
|transporteur|'PartijOndertekenaarDatumIngangMorgenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2468

Scenario: 4.    Ondertekenaar (Datum.Ingang gisteren, Datum.Einde vandaag) is ONgeldig
                LT: R2468_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2468

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|    'PartijOndertekenaarDatumIngangGisterenDatumEindeVandaag'
|zenderVrijBericht|     'PartijOndertekenaarDatumIngangGisterenDatumEindeVandaag'
|ontvangerVrijBericht|  'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|ondertekenaar|         'Partij5DatumIngangGisterenDatumEindeVandaag'
|transporteur|          'PartijOndertekenaarDatumIngangGisterenDatumEindeVandaag'
|soortNaam|             'Beheermelding'
|inhoud|                'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2468

Scenario: 5.    Ondertekenaar (Datum.Ingang gisteren, Datum.Einde gisteren) is ONgeldig
                LT: R2468_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2468


Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|    'PartijOndertekenaarDatumIngangGisterenDatumEindeGisteren'
|zenderVrijBericht|     'PartijOndertekenaarDatumIngangGisterenDatumEindeGisteren'
|ontvangerVrijBericht|  'PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen'
|ondertekenaar|         'Partij7DatumIngangGisterenDatumEindeGisteren'
|transporteur|          'PartijOndertekenaarDatumIngangGisterenDatumEindeGisteren'
|soortNaam|             'Beheermelding'
|inhoud|                'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2468


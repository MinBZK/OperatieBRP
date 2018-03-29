Meta:

@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2462

Narrative:
Bij het in behandeling nemen van het vrije bericht geldt dat voor 'Geautoriseerde Verzend Partij'
zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452) waarde Partij.Geblokkeerd vrij bericht? ongelijk aan "Ja" is.

Bij het in behandeling nemen van het vrije bericht geldt dat voor 'Geautoriseerde Ontvangst Partij'
zoals gedefinieerd door De autorisatie voor het ontvangen van een vrij bericht (R2453) waarde
Partij.Geblokkeerd vrij bericht? ongelijk aan "Ja" is.

Scenario: 1.    De Geautoriseerde Zendende Partij' is geblokkeerd
                LT: R2462_LT02
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2462

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijGeblokkeerd'
|zenderVrijBericht|'PartijGeblokkeerd'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijGeblokkeerd'
|transporteur|'PartijGeblokkeerd'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2462

Scenario: 2.    De Ondertekenaar is geblokkeerd voor vrij bericht
                LT: R2462_LT03

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaarGeblokkeerd'
|zenderVrijBericht|'PartijOndertekenaarGeblokkeerd'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'OndertekenaarGeblokkeerd'
|transporteur|'PartijOndertekenaarGeblokkeerd'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    De transporteur is geblokkeerd voor vrij bericht
                LT: R2462_LT04

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijTransporteurGeblokkeerd'
|zenderVrijBericht|'PartijTransporteurGeblokkeerd'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijTransporteurGeblokkeerd'
|transporteur|'TransporteurGeblokkeerd'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    De Geautoriseerde Zendende Partij' is geblokkeerd, Maar ISC partij
                LT: R2462_LT05
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendeSysteem|'ISC'
|zendendePartijNaam|'PartijGeblokkeerdOvergangBRPLeeg'
|zenderVrijBericht|'PartijGeblokkeerdOvergangBRPLeeg'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijGeblokkeerdOvergangBRPLeeg'
|transporteur|'PartijGeblokkeerdOvergangBRPLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5.    De 'Geautoriseerde ontvangst Partij' is geblokkeerd
                LT: R2463_LT02
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2463

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijGeblokkeerd'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2463

Scenario: 6.    De 'Geautoriseerde ontvangst Partij' is geblokkeerd, Maar GBA partij
                LT: R2463_LT03
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijGeblokkeerdOvergangBRPLeeg'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7.    De Geautoriseerde Zendende Partij' is geblokkeerd, Maar ISC als zendende systeem gevuld
                LT: ??
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2462

Given verzoek verstuur vrij bericht:
|key|value
|zendendeSysteem|'ISC'
|zendendePartijNaam|'PartijGeblokkeerd'
|zenderVrijBericht|'PartijGeblokkeerd'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijGeblokkeerd'
|transporteur|'PartijGeblokkeerd'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2462
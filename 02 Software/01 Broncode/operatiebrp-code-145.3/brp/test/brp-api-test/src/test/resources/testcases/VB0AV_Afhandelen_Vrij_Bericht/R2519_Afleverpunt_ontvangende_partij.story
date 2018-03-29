Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2519

Narrative:
Als de ontvangende partij voor het vrij bericht aangesloten is op het BRP-stelsel, dan dient de partij een afleverpunt te hebben.

Bij het in behandeling nemen van het vrije bericht moet gelden dat 'Het afleverpunt klopt' zoals gedefinieerd door
De autorisatie voor het ontvangen van een vrij bericht (R2453).


Scenario: 1.    Partij.Datum overgang BRP kleiner dan systeemdatum, Groep vrij bericht is geldig, Groep vrij bericht is niet geblokkeerd, afleverpunt aanwezig
                LT: R2519_LT01
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

Scenario: 2.    Partij.Datum overgang BRP kleiner dan systeemdatum, Groep vrij bericht is NIET geldig, Groep vrij bericht is niet geblokkeerd, afleverpunt aanwezig
                LT: R2519_LT02
                Verwacht resultaat:
                - Foutmelding R2459

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

Scenario: 3.    Partij.Datum overgang BRP kleiner dan systeemdatum, Groep vrij bericht is geldig, Groep vrij bericht is WEL geblokkeerd, afleverpunt aanwezig
                LT: R2519_LT03
                Verwacht resultaat:
                - Foutmelding R2463

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

Scenario: 4.    Partij.Datum overgang BRP kleiner dan systeemdatum, Groep vrij bericht is geldig, Groep vrij bericht is niet geblokkeerd, GEEN afleverpunt aanwezig
                LT: R2519_LT04
                Verwacht resultaat:
                - Foutmelding R2519

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgenGeenAfleverpunt'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2519

Scenario: 5.    Partij.Datum overgang BRP is LEEG, Groep vrij bericht is NIET geldig, Groep vrij bericht is niet geblokkeerd, afleverpunt aanwezig
                LT: R2519_LT05
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangMorgenDatumEindeLeegOvergangBRPLeeg'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 6.    Partij.Datum overgang BRP is LEEG, Groep vrij bericht is geldig, Groep vrij bericht is WEL geblokkeerd, afleverpunt aanwezig
                LT: R2519_LT06
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

Scenario: 7.    Partij.Datum overgang BRP groter dan systeemdatum, Groep vrij bericht is geldig, Groep vrij bericht is niet geblokkeerd, GEEN afleverpunt aanwezig
                LT: R2519_LT07
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgenGeenAfleverpuntOvergangBRPGroter'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd
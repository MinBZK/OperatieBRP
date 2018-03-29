Meta:

@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2454, R2456, R2343
@versie             1

Narrative:

Er moet een 'Geautoriseerde Verzend Partij' zijn voor het vrije bericht. Hiervan is sprake als de groep Vrij Bericht aanwezig is voor de verzendende partij.

Bij het in behandeling nemen van het vrije bericht geldt dat 'Geautoriseerde Verzend Partij'
zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452) dient te bestaan.

Scenario: 1.    Verzendende partij vrij bericht bestaat en ontvangende partij vrij bericht bestaat, Partij niet geblokkeerd
                LT: R2454_LT01, R2456_LT01, R2462_LT01, R2463_LT01, R1410_LT01
                Verwacht resulaat:
                - Verzending + Ontvangst vrij bericht geslaagd

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

Scenario: 2.    Verzendende partij vrij bericht bestaat NIET en ontvangende partij vrij bericht bestaat
                LT: R2454_LT02, R2343_LT02
                Verwacht resulaat:
                - Foutief
                - R2343: Er is een autorisatiefout opgetreden.
                - Gelogd met R2454

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtAlleenAfleverpuntGeenAutorisatie'
|zenderVrijBericht|'PartijVrijBerichtAlleenAfleverpuntGeenAutorisatie'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtAlleenAfleverpuntGeenAutorisatie'
|transporteur|'PartijVrijBerichtAlleenAfleverpuntGeenAutorisatie'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2454

Scenario: 3.    Verzendende partij vrij bericht bestaat en ontvangende partij vrij bericht bestaat NIET
                LT: R2456_LT02, R2343_LT02
                Verwacht resulaat:
                - Foutief
                - R2343: Er is een autorisatiefout opgetreden.
                - Gelogd met R2456

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtAlleenAfleverpuntGeenAutorisatie'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2456
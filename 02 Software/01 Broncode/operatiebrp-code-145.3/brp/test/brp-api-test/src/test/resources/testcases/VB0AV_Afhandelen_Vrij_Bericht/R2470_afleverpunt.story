Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2470
@versie             1

Narrative:
Versie 1:
Als het vrij bericht afkomstig is van een partij die aangesloten is op het BRP-stelsel, dan dient de verzendende partij een afleverpunt te hebben.

Indien Bericht.Zendende systeem <> 'ISC' geldt dat bij het in behandeling nemen van het vrije bericht
'Het afleverpunt klopt' zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452)

Versie 2:

Bij het in behandeling nemen van het vrije bericht moet gelden dat 'Het afleverpunt klopt'
zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452)

Scenario: 1.    Verzendende partij vrij bericht bestaat en verzendende partij heeft Partij.Afleverpunt vrij bericht.
                LT: R2470_LT01
                Verwacht resulaat:
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

Scenario: 2.    Verzendende partij vrij bericht bestaat en verzendende partij heeft GEEN Partij.Afleverpunt vrij bericht.
                LT: R2470_LT02, R2343_LT02
                Verwacht resulaat:
                - Foutief
                - R2343: Er is een autorisatiefout opgetreden.
                - Gelogd met R2470

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtGeenAfleverpunt'
|zenderVrijBericht|'PartijVrijBerichtGeenAfleverpunt'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtGeenAfleverpunt'
|transporteur|'PartijVrijBerichtGeenAfleverpunt'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2470


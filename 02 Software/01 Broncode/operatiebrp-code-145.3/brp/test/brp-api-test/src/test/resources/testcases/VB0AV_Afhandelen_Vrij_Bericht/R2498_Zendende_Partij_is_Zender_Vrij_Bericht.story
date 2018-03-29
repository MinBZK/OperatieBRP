Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2498

Narrative:
Bij het in behandeling nemen van het vrije bericht geldt dat Bericht.Zendende partij uit stuurgegevens gelijk dient te zijn aan Bericht.Zender vrij bericht uit de parameters.

Scenario: 1.    Verzendende partij is gelijk aan zender vrij bericht
                LT: R2498_LT01
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

Scenario: 2.    Verzendende partij is NIET gelijk aan zender vrij bericht
                LT: R2498_LT02
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2498
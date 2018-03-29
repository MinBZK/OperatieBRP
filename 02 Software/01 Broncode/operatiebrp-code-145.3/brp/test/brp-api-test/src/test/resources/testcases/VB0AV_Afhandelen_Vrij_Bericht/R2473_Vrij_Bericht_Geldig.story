Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2473
@versie             1

Narrative:

Bij het in behandeling nemen van het vrije bericht geldt dat Bericht.Soort vrij bericht
moet verwijzen naar een Geldig voorkomen stamgegeven op peildatum (R1284) op 'Systeemdatum' (R2016) in Soort vrij bericht.

Scenario: 1.    Soort vrij bericht datum ingang < systeemdatum, datum einde leeg
                LT: R2473_LT01
                Verwacht resultaat:
                - Geslaagd

Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dataanvgel is aangepast met 20100101

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

Scenario: 2.    Soort vrij bericht datum ingang = systeemdatum, datum einde > systeemdatum
                LT: R2473_LT02
                Verwacht resultaat:
                - Geslaagd

Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dataanvgel is aangepast met vandaag
Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dateindegel is aangepast met morgen

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

Scenario: 3.    Soort vrij bericht datum ingang > systeemdatum , datum einde LEEG
                LT: R2473_LT03
                Verwacht resultaat:
                - Foutief
                - R2473: 	Soort vrij bericht is niet geldig

Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dataanvgel is aangepast met morgen

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2473    | Soort vrij bericht is niet geldig     |


Scenario: 4.    Soort vrij bericht datum ingang < systeemdatum , datum einde LEEG
                LT: R2473_LT04
                Verwacht resultaat:
                - Foutief
                - R2473: 	Soort vrij bericht is niet geldig

Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dataanvgel is aangepast met gisteren
Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dateindegel is aangepast met vandaag

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2473    | Soort vrij bericht is niet geldig     |

Scenario: 5.    Soort vrij bericht datum ingang < systeemdatum , datum einde LEEG
                LT: R2473_LT05
                Verwacht resultaat:
                - Foutief
                - R2473: 	Soort vrij bericht is niet geldig

Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dataanvgel is aangepast met gisteren
Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dateindegel is aangepast met gisteren

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2473    | Soort vrij bericht is niet geldig     |

Scenario: 6.    Postconditie scenario
                LT:
                Terugzetten uitgangssituatie

Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dataanvgel is aangepast met gisteren
Given stamtabel SoortVrijBericht gegeven Beheermelding met attribuut dateindegel is aangepast met morgen
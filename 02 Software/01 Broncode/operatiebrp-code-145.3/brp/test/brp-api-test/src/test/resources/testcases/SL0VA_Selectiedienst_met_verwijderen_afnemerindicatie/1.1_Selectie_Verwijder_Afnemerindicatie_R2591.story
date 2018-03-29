Meta:

@status             Klaar
@usecase            SL.1.VA.CAA, SL.1.VA.VVA, SL.0.VA
@regels             R1342, R1409, R2591, R2592, R2594
@sleutelwoorden     Selectie Verwijder afnemerindicatie

Narrative:
Selectie met verwijderen afnemerindicatie

Scenario: 1.    Selectie met verwijderen afnemerindicatie, zonder volledig bericht
                LT: R2591_LT02, R2667_LT04
                Verwacht resultaat:
                Autorisisatie bij selectietaak is zonder volledig bericht
                Bij persoon wordt succesvol de afnemerindicatie verwijderd, geen volledig bericht
                Status transitie is Uitvoerbaar, in uitvoering, selectie uitgevoerd.

Given leveringsautorisatie uit autorisatie/SelectieMetVerwijderenAfnemerindicatieGeenBericht2

Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|606417801|SelectieMetVerwijderenAfnIndicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | SelectieMetVerwijderenAfnemerindicatieGeenBericht2 |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieMetVerwijderenAfnIndicatie en partij Gemeente Utrecht de afnemerindicatie verwijderd

Then is er geen synchronisatiebericht voor leveringsautorisatie SelectieMetVerwijderenAfnIndicatie

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, SELECTIE_UITGEVOERD]     |

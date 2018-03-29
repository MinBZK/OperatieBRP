Meta:

@status             Klaar
@usecase            SL.0.PA
@sleutelwoorden     Selectie met plaatsing afnemerindicatie

Narrative:
Meerdere taken in 1 selectie run

Scenario:   1.  Meerdere taken in een run en meerdere runs in een story
                LT: R2588_LT05
                Verwacht resultaat:
                - 4 berichten geleverd
                - 2 berichten voor gemeente Utrecht
                - 2 berichten voor gemeente Olst
                - 0 berichten voor gemeente Haarlem met Nadere Populatie Beperking	: Persoon.Geboorte.Datum > 2016/12/10
                - Na tweede run dus 8 aantal berichten in totaal

Given leveringsautorisatie uit aut/MeerdereToegangLeveringsAutorisiatie
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel    |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 2  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 3  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 4  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 5  | vandaag     | Uitvoerbaar | ToegangC/DienstC |


Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er 4 bericht geleverd
And is er voor persoon met bsn 595891305 en leveringautorisatie MeerdereToegangen en partij Gemeente Utrecht een afnemerindicatie geplaatst
And is er voor persoon met bsn 595891305 en leveringautorisatie MeerdereToegangen en partij Gemeente Olst een afnemerindicatie geplaatst
And is er voor persoon met bsn 595891305 en leveringautorisatie MeerdereToegangen en partij Gemeente Haarlem geen afnemerindicatie geplaatst

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel    |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 2  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 3  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 4  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 5  | vandaag     | Uitvoerbaar | ToegangC/DienstC |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er 8 bericht geleverd

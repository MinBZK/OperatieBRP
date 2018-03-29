Meta:

@status             Klaar
@usecase            SL.0.PA
@sleutelwoorden     Selectie

Narrative:
Meerdere taken in 1 selectie run en run twee keer gedraaid

Scenario:   1.  Meerdere taken in een run en meerdere runs in een story
                LT: R2588_LT05, R2558_LT01
                Verwacht resultaat:
                - Per run voor de eerste 4 taken wordt Anne Bakker gevonden, voor de vijfde taak niet vanwege populatiebeperking

Given leveringsautorisatie uit aut/MeerdereToegangLeveringsAutorisiatie
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel    |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 2  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 3  | vandaag     | Uitvoerbaar | ToegangC/DienstC |


Given persoonsbeelden uit specials:specials/Jan_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag' met '1' personen

!-- Voor toegangleveringautorisatie geen persoon geselecteerd door populatiebeperking, dus geen resultaat bestand, wel totalenbestand aanwezig
And is het totalenbestand voor selectietaak '3' en datumplanning 'vandaag' gelijk aan 'expecteds/MeerdereTakenInRun_Resultaatset_totalen.xml'


!-- Nog een tweede identieke run erachteraan
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel    |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 2  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 3  | vandaag     | Uitvoerbaar | ToegangA/DienstC |
| 4  | vandaag     | Uitvoerbaar | ToegangB/DienstC |
| 5  | vandaag     | Uitvoerbaar | ToegangC/DienstC |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '3' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '4' en datumplanning 'vandaag' met '1' personen

!-- Voor toegangleveringautorisatie geen persoon geselecteerd door populatiebeperking, dus geen resultaat bestand, wel totalenbestand aanwezig
And is het totalenbestand voor selectietaak '5' en datumplanning 'vandaag' gelijk aan 'expecteds/MeerdereTakenInRun_Resultaatset_totalen2.xml'



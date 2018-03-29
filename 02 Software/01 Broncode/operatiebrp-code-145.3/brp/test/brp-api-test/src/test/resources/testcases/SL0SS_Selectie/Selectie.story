Meta:
@status             Klaar
@sleutelwoorden     Selectie

Narrative:
Een eerste selectie test

Scenario: 1. Selectie test waar en controle op status tranistie
            LT: R2666_LT02
            Uitwerking:
            Geen controle bestand aanmaken voor selectie taak, status gaat van uitvoerbaar naar in uitvoering, vervolgens naar te leveren


Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, TE_LEVEREN]     |


Scenario: 2. Selectie test onwaar
Given leveringsautorisatie uit aut/SelectieAutOnwaar
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutOnwaar |
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen in '1' resultaatbestanden
And is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/Selectie_Scenario2_Resultaatset_totalen.xml'

Scenario: 3. Selectie test NPB voor 1 persoon waar
Given leveringsautorisatie uit aut/SelectieAutNPB
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutNPB |
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

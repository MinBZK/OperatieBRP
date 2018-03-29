Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R2578

Narrative:
Wanneer er geprotocolleerd dient te worden, wordt er een bestand aangemaakt met de te protocolleren gegevens.
Dit bestand wordt geplaatst op dezelfde locatie als de selectieresultaatset personen en de selectieresultaatset totalen, mogelijk wel in een aparte map.
Het bestand met de te protocolleren gegevens krijgt een herkenbare naam waaruit op te maken valt bij welke uitgevoerde selectietaak dit bestand hoort.


Scenario:   1.      Selectie, Partij/Rol.Rol is ongelijk aan Bijhouder en prot.niveau is ongelijk aan 'Geheim'
                    LT: R2578_LT01, R2569_LT01, R2664_LT01, R2665_LT01
                    Verwacht resultaat:
                    -protocollering bestand aanwezig
                    -Transitie van Te protocolleren naar protocollering in uitvoering, naar protocollering uitgevoerd

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is protocollering bestand aanwezig 'true' voor selectietaak '1' en datumplanning 'vandaag'

When protocollering voor selecties wordt gestart
Then is de selectie geprotocolleerd met 0 leveringsaantekeningen en 0 personen

Given de beheerder aangeeft dat selectietaak 1 geprotocolleerd dient te worden
When protocollering voor selecties wordt gestart
Then is de selectie geprotocolleerd met 1 leveringsaantekeningen en 1 personen

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, TE_LEVEREN, TE_PROTOCOLLEREN, PROTOCOLLERING_IN_UITVOERING, PROTOCOLLERING_UITGEVOERD]     |


Scenario:   2.      Selectie, Partij/Rol.Rol is Bijhouder en prot.niveau is ongelijk aan 'Geheim'
                    LT: R2578_LT02, R2664_LT02
                    Verwacht resultaat:
                    - geen protocollering bestand aanwezig
                    - geen transitie naar protocollering in uitvoering

Given leveringsautorisatie uit aut/SelectieAutWaarBijhouder
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaarBijhouder |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is protocollering bestand aanwezig 'false' voor selectietaak '1' en datumplanning 'vandaag'

When protocollering voor selecties wordt gestart
Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, TE_LEVEREN] |



Scenario:   3.      Selectie, Partij/Rol.Rol is geen Bijhouder en prot.niveau is gelijk aan 'Geheim'
                    LT: R2578_LT03, R2665_LT02
                    Verwacht resultaat:
                    - geen protocollering bestand aanwezig

Given leveringsautorisatie uit aut/SelectieAutWaarGeheim
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaarGeheim |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is protocollering bestand aanwezig 'false' voor selectietaak '1' en datumplanning 'vandaag'



Scenario:   4.      Selectie, Partij/Rol.Rol is Bijhouder en prot.niveau is gelijk aan 'Geheim'
                    LT: R2578_LT04
                    Verwacht resultaat:
                    - geen protocollering bestand aanwezig

Given leveringsautorisatie uit aut/SelectieAutWaarBijhouderGeheim
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaarBijhouderGeheim |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is protocollering bestand aanwezig 'false' voor selectietaak '1' en datumplanning 'vandaag'

Scenario:  5.       Selectie, Partij/Rol.Rol is ongelijk aan Bijhouder en prot.niveau is ongelijk aan 'Geheim'
                    LT: R2663_LT02
                    Verwacht resultaat:
                    -protocollering bestand niet aanwezig wordt verwijderd dmv een stap.
                    -Transitie van Te protocolleren naar protocollering in uitvoering, naar protocolleringbestand niet gevonden

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is protocollering bestand aanwezig 'true' voor selectietaak '1' en datumplanning 'vandaag'

!-- geen bestand gevonden voor de selectietaak
Given het protocolleringsbestand voor selectietaak 1 en datum planning 'vandaag' is verwijderd
Given de beheerder aangeeft dat selectietaak 1 geprotocolleerd dient te worden
When protocollering voor selecties wordt gestart

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, TE_LEVEREN, TE_PROTOCOLLEREN, TE_PROTOCOLLEREN_BESTAND_NIET_GEVONDEN]     |


Scenario:  6.       Selectie, Partij/Rol.Rol is ongelijk aan Bijhouder en prot.niveau is ongelijk aan 'Geheim'
                    LT: E2665_LT02
                    Verwacht resultaat:
                    -Protocollering mislukt (dmv stap) check op protocollering transitie mislukt.
                    -Transitie van Te protocolleren naar protocollering in uitvoering, naar protocollering mislukt

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z


When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is protocollering bestand aanwezig 'true' voor selectietaak '1' en datumplanning 'vandaag'

!-- Corrupt bestand zodat protocollering mislukt
Given een corrupt protocollering bestand voor selectietaak '1' en datumplanning 'vandaag'

Given de beheerder aangeeft dat selectietaak 1 geprotocolleerd dient te worden
When protocollering voor selecties wordt gestart

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, TE_LEVEREN, TE_PROTOCOLLEREN, PROTOCOLLERING_MISLUKT]     |



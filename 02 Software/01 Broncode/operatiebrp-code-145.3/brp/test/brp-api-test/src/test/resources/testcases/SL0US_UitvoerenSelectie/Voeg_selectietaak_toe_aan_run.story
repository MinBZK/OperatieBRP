Meta:
@status             Klaar
@sleutelwoorden     Selectie

Narrative:
Selectie taken toevoegen aan selectie run
usecases
SL.1.US.CU – Controleer uitvoerbare selectietaken
SL.1.US.CA – Controleer autorisatie selectietaak
Voldoet de taak aan bovenstaande dan wordt de selectierun id toegevoegd aan de taak



Scenario: 1 Succesvolle selectierun
            LT: R2572_LT01, R2570_LT01, R2574_LT02


Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel   |
| 1  | vandaag     | Uitvoerbaar | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then hebben de volgende taken een selectie run id:
| id | datplanning | status      | dienstSleutel   |
| 1  | morgen      | Uitvoerbaar | SelectieAutWaar |


Scenario: 2 Selectierun datplanning morgen
            LT: R2572_LT02, R2570_LT02

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel   |
| 1  | morgen      | Uitvoerbaar | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Scenario: 3 Selectierun status ongelijk aan selectie uitvoerbaar
            LT: R2572_LT03

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status    | dienstSleutel   |
| 1  | vandaag     | Afgekeurd | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Scenario: 4 Selectierun status ongelijk aan selectie uitvoerbaar
            LT: R2572_LT03

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status        | dienstSleutel   |
| 1  | vandaag     | In uitvoering | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Scenario: 5 Datum planning is NULL
            LT:

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status        | dienstSleutel   |
| 1  |             | In uitvoering | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Meta:
@status             Klaar
@sleutelwoorden     Selectie



Scenario:   1. Selectie met historie vorm 'Geen', peil momenten leeg
            Historievorm: Geen
            peilmomentMaterieelResultaat: Leeg
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - conform R2678 : de peilmomenten op selectietaak krijgen waarde=systeemdatum

!-- Selectie met historievorm 'Geen', peilmomenten zijn leeg
Given leveringsautorisatie uit aut/SelectieHistorievormGeen
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel            |
| 1  | vandaag     | Uitvoerbaar | SelectieHistorievormGeen |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then zijn peilmomenten op selectietaak:
| selectieTaakId | peilmomentMaterieelResultaat | peilmomentFormeelResultaat |
| 1              | vandaag                      | vandaag                    |


Scenario:   2. Selectie zonder historie vorm, peil momenten leeg
            LT: R2678_LT01
            Historievorm: Geen
            peilmomentMaterieelResultaat: Leeg
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - conform R2678 : er is geen historiefilter gebruikt, dus de peilmomenten op selectietaak zijn niet gemuteerd

!-- Selectie met historievorm 'Geen', peilmomenten zijn leeg
Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel   |
| 1  | vandaag     | Uitvoerbaar | SelectieAutWaar |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then zijn peilmomenten op selectietaak:
| selectieTaakId | peilmomentMaterieelResultaat | peilmomentFormeelResultaat |
| 1              | NULL                         | NULL                       |

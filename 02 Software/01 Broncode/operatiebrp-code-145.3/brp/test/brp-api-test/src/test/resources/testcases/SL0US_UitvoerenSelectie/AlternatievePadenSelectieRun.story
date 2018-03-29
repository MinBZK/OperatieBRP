Meta:
@status             Klaar
@sleutelwoorden     Selectie

Narrative:
Selectie taken toevoegen aan selectie run
usecases
SL.1.US.CU – Controleer uitvoerbare selectietaken
SL.1.US.CA – Controleer autorisatie selectietaak
Voldoet de taak aan bovenstaande dan wordt de selectierun id toegevoegd aan de taak

Scenario: 1 Selectie taak bevat meerdere dienstbundels met selectie diensten voor dezelfde toegang
            LT:
            Uitwerking:
            Verwacht Resultaat:

Given leveringsautorisatie uit aut/SelectieMeerdereToegang
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel           |
| 1  | vandaag     | Uitvoerbaar | SelectieMeerdereToegang |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen
Then hebben de volgende taken een selectie run id:
| id | datplanning | status      | dienstSleutel           |
| 1  | morgen      | Uitvoerbaar | SelectieMeerdereToegang |


Scenario: 2 Autorisatie bevat dubbele selectie dienst
            LT:
            Uitwerking: De autorisatie bevat 2 bundels met beide de selectie dienst,
            waarvan beide met selectiesoort plaatsing
            Verwacht Resultaat: 1 afnemerindicatie geplaatst bij persoon
            NB: testen met een dubbele dienst waarin zowel plaatsing als verwijdering zijn opgenomen geven wisselende resultaten
            omdat niet te garanderen is dat het plaatsen als eerste gebeurt

Given leveringsautorisatie uit aut/SelectieDubbeleDienst
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel         |
| 1  | vandaag     | Uitvoerbaar | SelectieDubbeleDienst |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen
Then hebben de volgende taken een selectie run id:
| id | datplanning | status              | dienstSleutel         |
| 1  | vandaag     | Selectie uitgevoerd | SelectieDubbeleDienst |

!-- Ophalen van het volledigbericht nav het plaatsen van de afnemerindicatie
!-- het verwijderen van de afnemerindicatie is niet succesvol, omdat er op het moment van verwijdering nog geen afnemerindicatie is geplaatst
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie SelectieLeveringautorisatieDubbeleDienst is ontvangen en wordt bekeken
Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatieDubbeleDienst en partij Gemeente Utrecht een afnemerindicatie geplaatst

Scenario: 3 Autorisatie bevat geen dienst selectie
            LT:
            Uitwerking: De autorisatie bevat 2 bundels met beide de selectie dienst,
            waarvan 1 van het soort Plaatsing en de ander van het soort Verwijdering
            Verwacht Resultaat:

Given leveringsautorisatie uit aut/SelectieDienstOntbreekt
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel           |
| 1  | vandaag     | Uitvoerbaar | SelectieDienstOntbreekt |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Scenario: 4 Selectie taak komt meerdere keren voor in zelfde run

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel   |
| 1  | vandaag     | Uitvoerbaar | SelectieAutWaar |
| 2  | vandaag     | Uitvoerbaar | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then hebben de volgende taken een selectie run id:
| id | datplanning | status      | dienstSleutel   |
| 1  | morgen      | Uitvoerbaar | SelectieAutWaar |

Scenario: 5 Selectie soort ontbreekt in autorisatie
            Uitwerking: In de autorisatie is er geen waarde ingegeven voor his_dienstsel.srtsel
            Verwacht resultaat: Als de soort selectie niet is opgegeven in de autorisatie kan de selectie taak niet succesvol worden afgerond

Given leveringsautorisatie uit aut/SelectieZonderSoort
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel       |
| 1  | vandaag     | Uitvoerbaar | SelectieZonderSoort |


Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar met fouten

Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Scenario: 6 DienstID in selectie taak verwijst niet naar dienst van type selectie
            Uitwerking: De dienstsleutel verwijst naar dienst van soort plaatsing afnemerindicatie
            NB: De taak krijgt eind status AUTORISATIE_GEWIJZIGD,
            deze wordt toegekend omdat er geen aparte status is voor selectie taken die wegens autorisatie fouten niet worden uitgevoerd


Given leveringsautorisatie uit aut/SelectiemetOverigeDiensten
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel              |
| 1  | vandaag     | Uitvoerbaar | SelectiemetOverigeDiensten |


Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is status transitie:
| selectieTaakId | statusTransitie                      |
| 1              | [UITVOERBAAR, AUTORISATIE_GEWIJZIGD] |

Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

Scenario: 7 Nadere selectie criterium bevat foutieve expressie
            NB: De taak krijgt eind status AUTORISATIE_GEWIJZIGD,
            deze wordt toegekend omdat er geen aparte status is voor selectie taken die wegens autorisatie fouten niet worden uitgevoerd

Given leveringsautorisatie uit aut/SelectieFoutieveExpressie
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel             |
| 1  | vandaag     | Uitvoerbaar | SelectieFoutieveExpressie |


Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is status transitie:
| selectieTaakId | statusTransitie                      |
| 1              | [UITVOERBAAR, AUTORISATIE_GEWIJZIGD] |

Then geen resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag'

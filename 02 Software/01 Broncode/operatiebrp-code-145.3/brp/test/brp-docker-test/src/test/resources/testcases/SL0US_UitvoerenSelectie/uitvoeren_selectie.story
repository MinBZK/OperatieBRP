Meta:
@status             Klaar
@sleutelwoorden     Selectie

Scenario:   1. Uitvoeren selectie
               Uitwerking: Selectie run met meerdere taken, autorisaties en personen


Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | indsellijstgebruiken | dienstSleutel                   |
| vandaag     | Uitvoerbaar | false                | SelectieMetPlaatsing            |
| vandaag     | Uitvoerbaar | true                 | SelectieMetPlaatsingUitBestand  |
| vandaag     | Uitvoerbaar | false                | StandaardSelectie               |
| vandaag     | Uitvoerbaar | false                | StandaardSelectieMetControleren |
| vandaag     | Uitvoerbaar | true                 | StandaardSelectieUitBestand     |

!-- Selecteer 10 personen obv selectie bestand
Given selectielijsten per dienst:
| soortIdentificatie  | identificatienummers  | dienstSleutel                  |
| Burgerservicenummer | 970825353 , 904004041 , 410587849 , 311237113 , 710450825 , 220016793 , 481772297 , 215738561 , 135668669 , 594834089 | SelectieMetPlaatsingUitBestand |
| Burgerservicenummer | 970825353 , 904004041 , 410587849 , 311237113 , 710450825 , 220016793 , 481772297 , 215738561 , 135668669 , 594834089 | StandaardSelectieUitBestand    |

!-- Er worden 35 personen ingeladen
And alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
|filenaam
|/LO3PL/Selectie/Jan1.xls
|/LO3PL/Selectie/Jan2.xls
|/LO3PL/Selectie/Jan3.xls
|/LO3PL/Selectie/Jan4.xls
|/LO3PL/Selectie/Jan5.xls
|/LO3PL/Selectie/Jan6.xls
|/LO3PL/Selectie/Jan7.xls
|/LO3PL/Selectie/Jan8.xls
|/LO3PL/Selectie/Jan9.xls
|/LO3PL/Selectie/Jan10.xls
|/LO3PL/Selectie/Jan11.xls
|/LO3PL/Selectie/Jan12.xls
|/LO3PL/Selectie/Jan13.xls
|/LO3PL/Selectie/Jan14.xls
|/LO3PL/Selectie/Jan15.xls
|/LO3PL/Selectie/Jan16.xls
|/LO3PL/Selectie/Jan17.xls
|/LO3PL/Selectie/Jan18.xls
|/LO3PL/Selectie/Jan19.xls
|/LO3PL/Selectie/Jan20.xls
|/LO3PL/Selectie/Jan21.xls
|/LO3PL/Selectie/Jan22.xls
|/LO3PL/Selectie/Jan23.xls
|/LO3PL/Selectie/Jan24.xls
|/LO3PL/Selectie/Jan25.xls
|/LO3PL/Selectie/Jan26.xls
|/LO3PL/Selectie/Jan27.xls
|/LO3PL/Selectie/Jan28.xls
|/LO3PL/Selectie/Jan29.xls
|/LO3PL/Selectie/Jan30.xls
|/LO3PL/Selectie/Jan31.xls
|/LO3PL/Selectie/Jan32.xls
|/LO3PL/Selectie/Jan33.xls
|/LO3PL/Selectie/Jan34.xls
|/LO3PL/Selectie/Jan35.xls


When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 10 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'StandaardSelectie' en datumuitvoer 'vandaag':
 |type                         |aantal
 |Resultaatset personen        |>=1
 |Resultaatset totalen         |==1
 |Controlebestand              |==0

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'StandaardSelectieMetControleren' en datumuitvoer 'vandaag':
  |type                         |aantal
  |Resultaatset personen        |>=1
  |Resultaatset totalen         |==1
  |Controlebestand              |==1

!-- Controle op status wijziging R2692_LT01, controle op vullen TsREG voor nieuwe status en TsVerval voor oude status handmatig uitgevoerd
Then heeft de actuele status rij de volgende waarden:
| dienstSleutel                   | status              | gewijzigddoor |
| StandaardSelectieMetControleren | CONTROLEREN         | Systeem       |
| SelectieMetPlaatsing            | SELECTIE_UITGEVOERD | Systeem       |
| SelectieMetPlaatsingUitBestand  | SELECTIE_UITGEVOERD | Systeem       |
| StandaardSelectie               | TE_LEVEREN          | Systeem       |
| StandaardSelectieUitBestand     | TE_LEVEREN          | Systeem       |

Then is er voor persoon met bsn 970825353 en leveringautorisatie SelectieMetPlaatsingUitBestand en partij Gemeente Standaard en soortDienst SELECTIE een afnemerindicatie geplaatst

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetPlaatsingUitBestand

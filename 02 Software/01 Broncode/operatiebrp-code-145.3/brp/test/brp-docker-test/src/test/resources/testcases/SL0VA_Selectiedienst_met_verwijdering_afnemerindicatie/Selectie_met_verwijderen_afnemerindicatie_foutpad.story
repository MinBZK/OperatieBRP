Meta:
@status             Klaar
@sleutelwoorden     Selectie met verwijdering afnemerindicatie

Narrative:

Indien bij de Dienst waarvoor geldt dat Dienst.Soort selectie is gelijk aan 'Selectiedienst met verwijdering afnemerindicatie'
met versturen van een volledig bericht
dan wordt de afnemerindicatie van personen binnen de selectie verwijderd en vervolgens een volledig bericht verstuurd.


Scenario:   1. Selectie met verwijdering afnemerindicatie foutpad
            LT: R2591_LT04
            Uitwerking:
            Persoon heeft geen afnemerindicatie
            Persoon wordt wel opgenomen in resultaatset totalen
            Afnemerindicatie wordt niet verwijderd
            Volledig bericht wordt niet verstuurd


Given alle selectie personen zijn verwijderd
And selectiepersonen uit bestand /LO3PL/R2286/Kim.xls

Given een selectierun met de volgende selectie taken:
| datplanning | status      | dienstSleutel                           |
| vandaag     | Uitvoerbaar | selectiemetverwijderingafnemerindicatie |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 5 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiemetverwijderingafnemerindicatie' en datumuitvoer 'vandaag':
| type                 | aantal |
| Resultaatset totalen | ==1    |

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

Then is er een logregel gelogd met regel R1401 in container SELECTIE_AFNEMERINDICATIE

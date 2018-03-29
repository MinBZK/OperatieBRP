Meta:
@status Klaar
@driverSoort phantomjs
@binaryPath phantomjs.binary
@closeDriver true

Narrative:
Als beheerder wil ik de in te plannen selectietaken kunnen raadplegen


Scenario:   0. Taken met verschillende statussen toevoegen aan de db als preconditie voor overige scenarios
            Uitwerking:
            Autorisatie BeheerSelectieGeenInterval met meerdere toegangen
            Taak met status gepland voor scenario om de filter Reeds gepland te checken
            Taak met status uitvoerbaar voor scenario om de filter Reeds gepland te checken is nog buiten scope dit moet nog gebouwd worden!


Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status                | indsellijstgebruiken | dienstSleutel                 |
| 20180630    | Geannuleerd           | false                | SelectieStandaardGeenInterval |
| 20180701    | Afgekeurd             | false                | SelectieStandaardGeenInterval2 |


Scenario:   1. Filtering opnieuw in te plannen tonen in het overzicht
            LT: BS.1.RI_001_LT01, BS.1.RI_001_LT02
            Doel:
            Check op filtering Opnieuw in te plannen
            Uitwerking:
            Op 30-06-2018 is een taak met de status Geannuleerd
            Op 01-07-2018 is een taak met de status Afgekeurd
            Op 02-06-2018 is een taak met de status Uitvoering afgebroken
            Taken met status Geannuleerd, Afgekeurd, Uitvoering afgebroken worden in het overzicht niet getoond
            Taken met status Geannuleerd, Afgekeurd, Uitvoering afgebroken worden in het overzicht getoond als het vinkje opnieuw in te plannen is aangevinkt.



Given er is een verbinding met de applicatie

When begindatum is aangepast naar 30-06-2018
And einddatum is aangepast naar 02-07-2018
When er op zoeken wordt geklikt
Then is er 1 selectietaak aanwezig

When er op zoeken wordt geklikt
And de volgende checkboxen worden aangevinkt:
|id
|opnieuw-te-plannen
Then is er 3 selectietaak aanwezig

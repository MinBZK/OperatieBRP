Meta:
@status Klaar
@sleutelwoorden beheer

Narrative:
Creëer selectietaken bij eenmalige selectiedienst (Interval = NULL)

Scenario: 0 Eerste selectie datum is leeg

Given leveringsautorisatie uit autorisatie/SelectieEenmaligEersteSelDatumLeeg

When de berekende taken opgevraagd worden voor de begindatum 2017-05-01 en einddatum 2017-12-31


Scenario: 1 Selectie dienst zonder interval en meerdere toegangen
            LT: R2699_LT01
            DOEL:
            Vaststellen dat er een enkele selectie taak wordt aangemaakt per toegang igv selectie interval
            niet gevuld is, de eerste selectie datum binnen de periode valt en er nog geen selectie taak voor de dienst aanwezig is

Given leveringsautorisatie uit autorisatie/SelectieEenmalig

When de berekende taken opgevraagd worden voor de begindatum 2017-05-01 en einddatum 2017-12-31

Then zijn er 3 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| null | 028101      | Gemeente Tiel    | 2017-06-01          | Standaard selectie | 2017-06-01             | 1          | null                         | null                       | 1      | null          | true           |
| null | 034401      | Gemeente Utrecht | 2017-06-01          | Standaard selectie | 2017-06-01             | 1          | null                         | null                       | 1      | null          | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-01          | Standaard selectie | 2017-06-01             | 1          | null                         | null                       | 1      | null          | true           |

Scenario: 2 Selectie dienst zonder interval en reeds taak aanwezig met eindstatus
            LT: R2699_LT02
            DOEL:
            Vaststellen dat er een enkele selectie taak wordt aangemaakt igv selectie interval niet gevuld is,
            de eerste selectie datum binnen de periode valt en er een
            selectie taak voor de dienst aanwezig is met eindstatus (Geannuleerd)

Meta:
@status Onderhanden
!-- Dit scenario is technisch niet mogelijk vanwegen een DB constraint

Given leveringsautorisatie uit autorisatie/SelectieEenmaligEenToegang
And de volgende selectie taken:
| id | datplanning | status      | dienstSleutel    | volgnummer |
| 1  | 20170501    | Geannuleerd | SelectieEenmalig | 1          |

When de berekende taken opgevraagd worden voor de begindatum 2017-05-01 en einddatum 2017-12-31

Then zijn er 2 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| 1    | 034401      | Gemeente Utrecht | 2017-06-01          | Standaard selectie | 2017-06-01             | 1          | null                         | null                       | 5      | 2017-06-01    | null           |
| null | 034401      | Gemeente Utrecht | 2017-06-01          | Standaard selectie | 2017-06-01             | 2          | null                         | null                       | 1      | null          | null           |


Scenario: 3 Selectie dienst zonder interval en reeds taak aanwezig met status Gepland
            LT: R2699_LT03
            DOEL:
            Vaststellen dat er geen selectie taak
            wordt aangemaakt igv selectie interval
            niet gevuld is, de eerste selectie datum binnen de periode valt
            en er reeds een taak aanwezig is <> eindstatus (Gepland)

Given leveringsautorisatie uit autorisatie/SelectieEenmaligEenToegang
And de volgende selectie taken:
| id | datplanning | status  | dienstSleutel    | volgnummer |
| 1  | 20170601    | Gepland | SelectieEenmalig | 1          |

When de berekende taken opgevraagd worden voor de begindatum 2017-05-01 en einddatum 2017-12-31

Then zijn er 1 selectietaken berekend met de volgende inhoud:
| id | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| 1  | 034401      | Gemeente Utrecht | 2017-06-01          | Standaard selectie | 2017-06-01             | 1          | null                         | null                       | 2      | 2017-06-01    | true           |

Scenario: 4 Eerste selectie datum is groter dan de periode eind datum
            LT: R2699_LT04
            DOEL:
            Vaststellen dat er geen selectie taak
            wordt aangemaakt igv de eerste selectie
            datum niet binnen het periode overzicht valt

Given leveringsautorisatie uit autorisatie/SelectieEenmaligEenToegang

When de berekende taken opgevraagd worden voor de begindatum 2017-05-01 en einddatum 2017-05-31

Then zijn er 0 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam | eersteSelectieDatum | selectieSoort | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |

Scenario: 5 Datum eerste selectie van de dienst is gelijk aan periode begindatum
            LT: R2699_LT05
            DOEL:
            Vaststellen dat er een selectie taak
            wordt aangemaakt igv de eerste selectie datum gelijk is aan de periode begindatum
            niet gevuld is, de eerste selectie datum binnen de periode valt
            en er reeds een taak aanwezig is <> eindstatus (Gepland)

Given leveringsautorisatie uit autorisatie/SelectieEenmaligEenToegang

When de berekende taken opgevraagd worden voor de begindatum 2017-06-01 en einddatum 2017-06-30

Then zijn er 1 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| null | 034401      | Gemeente Utrecht | 2017-06-01          | Standaard selectie | 2017-06-01             | 1          | null                         | null                       | 1      | null          | true           |

Scenario: 6 Eerste selectie datum is kleiner dan de periode begin datum
            LT: R2699_LT04
            DOEL:
            Vaststellen dat er geen selectie taak
            wordt aangemaakt igv de eerste selectie
            datum niet binnen het periode overzicht valt

Given leveringsautorisatie uit autorisatie/SelectieEenmaligEenToegang

When de berekende taken opgevraagd worden voor de begindatum 2017-07-01 en einddatum 2017-07-31

Then zijn er 0 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam | eersteSelectieDatum | selectieSoort | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |

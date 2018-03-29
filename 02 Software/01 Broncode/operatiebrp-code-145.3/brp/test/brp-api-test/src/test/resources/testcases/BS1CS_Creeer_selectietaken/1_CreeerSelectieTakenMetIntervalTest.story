Meta:
@status       Klaar
@usecase      BV.0.ZA

Narrative:
Valideren dat op basis van de autorisatie de juiste selectie taken voorgesteld worden.

Scenario: 1 De selectie taken worden met een dagelijkse interval aangemaakt waarbij de peilmomenten zijn gevuld bij de selectie dienst
            LT: R2706_LT01
            DOEL: Vaststellen dat er per (geldige) toegang een selectie taak gecreëerd wordt conform de interval

Given leveringsautorisatie uit autorisatie/SelectieDagelijkseIntervalMeerdereToegangen

When de berekende taken opgevraagd worden voor de begindatum 2017-06-30 en einddatum 2017-07-02

!-- Taken worden gesorteerd op op berekendeselectiedatum/datumplanning en afnemercode
Then zijn er 4 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| null | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-06-30             | 1          | 2017-06-30                   | 2017-06-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-06-30             | 1          | 2017-06-30                   | 2017-06-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-07-01             | 2          | 2017-07-01                   | 2017-07-01T00:00Z[UTC]     | 1      | null          | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-07-01             | 2          | 2017-07-01                   | 2017-07-01T00:00Z[UTC]     | 1      | null          | true           |

Scenario: 2 De selectie taken worden met een maandelijkse interval aangemaakt waarbij de peilmomenten zijn gevuld bij de dienst
            LT: R2706_LT02
            DOEL: Vaststellen dat er per toegang een selectie taak gecreëerd wordt conform de interval

Given leveringsautorisatie uit autorisatie/SelectieMaandelijkseIntervalMeerdereToegangen

When de berekende taken opgevraagd worden voor de begindatum 2017-06-30 en einddatum 2017-08-01

!-- Taken worden gesorteerd op op berekendeselectiedatum/datumplanning en afnemercode
!-- Voor de toegang van gemeente Tiel worden een taken aangemaakt omdat deze pas de 2de interval geldig is
Then zijn er 5 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| null | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-06-30             | 1          | 2017-06-30                   | 2017-06-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-06-30             | 1          | 2017-06-30                   | 2017-06-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 028101      | Gemeente Tiel    | 2017-06-30          | Standaard selectie | 2017-07-30             | 2          | 2017-07-30                   | 2017-07-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-07-30             | 2          | 2017-07-30                   | 2017-07-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-07-30             | 2          | 2017-07-30                   | 2017-07-30T00:00Z[UTC]     | 1      | null          | true           |

Scenario: 3 De berekende selectie taken worden niet na de eind datum van de dienst aangemaakt
            LT: R2706_LT03
            DOEL: Vaststellen dat er geen selectie taken gecreëerd worden als de berekende selectie datum groter is dan datum einde van de dienst

Given leveringsautorisatie uit autorisatie/SelectieMaandelijkseIntervalEenToegang

When de berekende taken opgevraagd worden voor de begindatum 2017-07-01 en einddatum 2017-12-31

!-- De toegang in de autorisatie eindigd op 2017-09-01, er worden geen taken berekend na die datum
!-- Voor de toegang van gemeente Tiel worden geen taken aangemaakt omdat deze pas de 2de interval geldig is
Then zijn er 2 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| null | 034401      | Gemeente Utrecht | 2017-07-01          | Standaard selectie | 2017-07-01             | 1          | null                         | null                       | 1      | null          | true           |
| null | 034401      | Gemeente Utrecht | 2017-07-01          | Standaard selectie | 2017-08-01             | 2          | null                         | null                       | 1      | null          | true           |

Scenario: 4 De selectie taken worden alleen aangemaakt voor 'geldige autorisaties'
            LT: R2721_LT01, R2721_LT02, R2721_LT03, R2721_LT04, R2721_LT05, R2721_LT06, R2721_LT07, R2721_LT08, R2721_LT09, R2721_LT10
            Omschrijving:
            Er worden meerdere ongeldige leveringsautorisaties ingeladen waarvoor geen selectie taken gecreeerd moeten worden.

Given leveringsautorisatie uit autorisatie/SelectieGeldig,
                               autorisatie/SelectieDienstbundelGeblokkeerd,
                               autorisatie/SelectieDienstGeblokkeerd,
                               autorisatie/SelectieLeveringsautorisatieGeblokkeerd,
                               autorisatie/SelectieOngeldigeDienst,
                               autorisatie/SelectieOngeldigeDienstBundel,
                               autorisatie/SelectieOngeldigeLeveringsautorisatie,
                               autorisatie/SelectieOngeldigePartij,
                               autorisatie/SelectieOngeldigeToegang,
                               autorisatie/SelectieToegangGeblokkeerd

!-- autorisatie/SelectieOngeldigePartijRol

When de berekende taken opgevraagd worden voor de begindatum 2017-07-01 en einddatum 2017-08-01

!-- Enkel selectie taken voor geldige autorisatie
Then zijn er 1 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| null | 034401      | Gemeente Utrecht | 2017-01-01          | Standaard selectie | 2017-07-01             | 7          | null                         | null                       | 1      | null          | true           |

Scenario: 5 Eerste selectie datum is leeg, interval wel gevuld
            LT: R2706_LT05
            DOEL: Vaststellen dat er geen selectietaken berekend worden indien de eerste selectie datum niet is gevuld

Given leveringsautorisatie uit autorisatie/SelectieMaandelijkseIntervalEersteSelDatLeeg

When de berekende taken opgevraagd worden voor de begindatum 2017-06-01 en einddatum 2017-12-01

Then zijn er 0 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |

Scenario: 6 Er worden geen dubbele selectie taken aangemaakt
            LT: R2706_LT06, R2707
            DOEL: Vaststellen dat er geen dubbele selectie taken berekend worden igv er reeds selectie taken bestaan voor de dienst / toegang

Given leveringsautorisatie uit autorisatie/SelectieDagelijkseIntervalMeerdereToegangen
And de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                     | volgnummer |
| 1  | 20170630    | Gepland | SelectieIntervalMeerdereToegangen | 1          |
| 2  | 20170702    | Gepland | SelectieIntervalMeerdereToegangen | 3          |

When de berekende taken opgevraagd worden voor de begindatum 2017-06-30 en einddatum 2017-07-03

!-- Taken worden gesorteerd op op berekendeselectiedatum/datumplanning en afnemercode
Then zijn er 6 selectietaken berekend met de volgende inhoud:
| id   | afnemerCode | afnemerNaam      | eersteSelectieDatum | selectieSoort      | berekendeSelectieDatum | volgnummer | peilmomentMaterieelResultaat | peilmomentFormeelResultaat | status | datumPlanning | opnieuwPlannen |
| 1    | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-06-30             | 1          | null                         | null                       | 2      | 2017-06-30    | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-06-30             | 1          | 2017-06-30                   | 2017-06-30T00:00Z[UTC]     | 1      | null          | true           |
| null | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-07-01             | 2          | 2017-07-01                   | 2017-07-01T00:00Z[UTC]     | 1      | null          | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-07-01             | 2          | 2017-07-01                   | 2017-07-01T00:00Z[UTC]     | 1      | null          | true           |
| 2    | 034401      | Gemeente Utrecht | 2017-06-30          | Standaard selectie | 2017-07-02             | 3          | null                         | null                       | 2      | 2017-07-02    | true           |
| null | 036101      | Gemeente Alkmaar | 2017-06-30          | Standaard selectie | 2017-07-02             | 3          | 2017-07-02                   | 2017-07-02T00:00Z[UTC]     | 1      | null          | true           |
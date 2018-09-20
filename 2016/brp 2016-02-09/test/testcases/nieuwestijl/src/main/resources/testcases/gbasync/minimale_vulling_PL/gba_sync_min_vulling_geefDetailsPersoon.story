Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 aapos
@jiraIssue              TEAMBRP-3219
@status                 Onderhanden
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren geconverteerde PL

Scenario: Geconverteerde datatest met minimale PL vulling
Given een initiele vulling uit bestand 1100-uit-gbavPersoon-pl-minVulling.xls
Given een sync uit bestand 3100-uit1100-gbavPersoon- VerhuizingPlminVulling.xls

When voor persoon 954345289 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'adres'
And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep         | nummer | verwerkingssoort  |
| adres         | 1      | Toevoeging        |
| adres         | 2      | Wijziging         |
| adres         | 3      | Verval            |

Scenario: synchroniseerPersoon met minimale PL vulling na verhuizing
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And extra waardes:
 | SLEUTEL                                 | WAARDE    |
 | stuurgegevens.zendendePartij            | 034401    |
 | stuurgegevens.zendendeSysteem           | SYNC          |
 | parameters.abonnementNaam               | Geen pop.bep. levering op basis van doelbinding          |
 | zoekcriteriaPersoon.burgerservicenummer | 954345289          |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 954345289            |


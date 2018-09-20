Meta:
@sprintnummer           65
@epic                   Mutatielevering basis
@auteur                 dihoe
@jiraIssue              TEAMBRP-2181
@status                 Klaar
@sleutelwoorden         synchronisatie

Narrative:
    Als stelselbeheerder
    wil ik dat het aanmaken van een (volledig- of mutatiebericht) bij ontbrekende ouders niet crasht
    zodat het systeem wanneer dit optreedt in de lucht blijft

Scenario: 1. volledigbericht wordt aangemaakt na uitvoeren geefSynchronisatiePersoon met een persoon met 1 ontbrekende ouder en 1 onbekende ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_ontbrekende_ouders_01.yml
When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes |
| identificatienummers        | burgerservicenummer  | 390827319        |


Scenario: 2. mutatiebericht wordt aangemaakt na uitvoeren verhuizing met een persoon met 1 ontbrekende ouder en 1 onbekende ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand persoon_met_ontbrekende_ouders_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes |
| identificatienummers        | burgerservicenummer  | 390827319        |
| parameters                  | soortSynchronisatie  | Mutatiebericht   |


Scenario: 3. volledigbericht wordt aangemaakt na uitvoeren geefSynchronisatiePersoon met een persoon met 2 ontbrekende ouders

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_ontbrekende_ouders_03.yml
When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes |
| identificatienummers        | burgerservicenummer  | 391093769        |


Scenario: 4. mutatiebericht wordt aangemaakt na uitvoeren verhuizing met een persoon met 2 ontbrekende ouders

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_zonder_abonnementgroepen
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand persoon_met_ontbrekende_ouders_04.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het mutatiebericht voor leveringsautorisatie Abo zonder abonnementgroepen is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes |
| identificatienummers        | burgerservicenummer  | 391093769        |
| parameters                  | soortSynchronisatie  | Mutatiebericht   |


Scenario: 5. volledigbericht en mutatiebericht berichten worden aangemaakt voor een persoon met 1 ontbrekende en 1 bekende ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_ontbrekende_ouders_05.yml
When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes |
| identificatienummers        | burgerservicenummer  | 392823470        |

When voor persoon 392823470 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes |
| identificatienummers        | burgerservicenummer  | 392823470        |
| parameters                  | soortSynchronisatie  | Mutatiebericht   |

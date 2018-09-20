Meta:
@sprintnummer           65
@epic                   Mutatielevering basis
@auteur                 anjaw
@jiraIssue              TEAMBRP-2180
@status                 Klaar
@sleutelwoorden         synchronisatie

Narrative:
    Als stelselbeheerder
    wil ik dat het aanmaken van een (volledig- of mutatiebericht) bij onbekende ouders niet crasht
    zodat het systeem wanneer dit optreedt in de lucht blijft

Scenario: 1. volledigbericht wordt aangemaakt na uitvoeren geefSynchronisatiePersoon met een persoon met onbekende ouders

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht' met ondertekenaar 00000001002220647000 en transporteur 00000001002220647000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_onbekende_ouders_01.yml
When het bericht wordt verstuurd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2. Mutatiebericht wordt aangemaakt na uitvoeren verhuizingBinnengemeentelijk met een persoon met onbekende ouders

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand persoon_met_onbekende_ouders_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3. volledigbericht wordt aangemaakt na uitvoeren geefSynchronisatiePersoon met een persoon met 1 onbekende ouder en 1 bekende ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht' met ondertekenaar 00000001002220647000 en transporteur 00000001002220647000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_onbekende_ouders_03.yml
When het bericht wordt verstuurd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
samengesteldeNaam           | voornamen            | Gerrie,Truus


Scenario: 4. mutatiebericht wordt aangemaakt na uitvoeren verhuizingBinnengemeentelijk met een persoon met 1 onbekende ouder en 1 bekende ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
When voor persoon 380911747 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
parameters                  | soortSynchronisatie  | Mutatiebericht

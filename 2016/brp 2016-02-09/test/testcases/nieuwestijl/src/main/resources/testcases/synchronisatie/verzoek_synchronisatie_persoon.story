Narrative:
In order to de complete gevens van een persoon te krijgen
As a Afnemer met de autorisatie om een persoon op te vragen
I want to perform een verzoek SynchronisatiePersoon

Scenario: Succesvol uitvoeren geefSynchronisatiePersoon

Meta:
@status Klaar
@auteur sasme
@regels VR00092
@sleutelwoorden         synchronisatie

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand verzoek_synchronisatie_persoon_testcase_01.yml
When het bericht wordt verstuurd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep 		              | attribuut            | verwachteWaardes     |
| identificatienummers        | burgerservicenummer  | 340014155            |
| nationaliteit               | nationaliteitCode    | 0001,0001,0001,0001,0001,0027  |

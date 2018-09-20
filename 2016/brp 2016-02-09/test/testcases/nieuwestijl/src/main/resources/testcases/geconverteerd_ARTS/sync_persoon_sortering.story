Synchronisatie Persoon - Sortering van verificatie voorkomens

Meta:
@status        Klaar
@sprintnummer  70
@jiraIssue     TEAMBRP-2422

Narrative:
Volledigberichten bevatten correct gesorteerde voorkomens van de groep verificatie

Scenario: Sortering van verificatie voorkomens in het volledigbericht
Meta:
@regels VR00092

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon

And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 290131789
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes
| identificatienummers | burgerservicenummer | 290131789
| verificatie          | actieInhoud         | 29007, 29006, 29005


Scenario: Sortering van verificatie voorkomens in het volledigbericht
Meta:
@regels VR00092

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 290143925
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes
| identificatienummers | burgerservicenummer | 290143925
| verificatie          | actieInhoud         | 29014, 29012, 29013


Scenario: Sortering van verificatie voorkomens in het volledigbericht
.....Let op.... Bij de verificatie van partijcode wordt gebruik gemaakt van id's. Dat betekent dat er na een
BMR change de code van de partij waarop getest wordt niet meer overeenkomt met het id.
Tzt deze testscenario refactoren...

Meta:
@regels VR00092

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 290136581
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes
| identificatienummers | burgerservicenummer | 290136581
| verificatie          | actieInhoud         | 29019, 29021
| verificatie          | partijCode          | 199900, 199900


Scenario: Synchronisatie persoon - Sortering verantwoording
Meta:
 @regels VR00090b

Given de database wordt gereset voor de personen 310027603
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | stuurgegevens.zendendePartij            | 036101
 | zoekcriteriaPersoon.burgerservicenummer | 310027603
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht voor xpath //brp:synchronisatie/brp:partijCode de waarde 036101
Then hebben de attributen in de groepen de volgende waardes:
| groep                    | attribuut  | verwachteWaardes
| administratieveHandeling | code       | 01011, 05005, 05005, 99999
| administratieveHandeling | partijCode | 098901, 098901, 098901, 034301, 029801, 098901, 098901, 098901

And is het synchronisatiebericht gelijk aan /testcases/geconverteerd_ARTS/synchronisatie_persoon/VR00090-SyncStructuur-TC04-1-dataresponse.xml voor expressie //brp:synchronisatie

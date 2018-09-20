Meta:
@sprintnummer           65
@epic                   Mutatielevering basis
@auteur                 dihoe
@jiraIssue              TEAMBRP-2184
@status                 Klaar
@sleutelwoorden         synchronisatie

Narrative:
    Als stelselbeheerder
    wil ik dat het aanmaken van een (volledig- of mutatiebericht) bij (deels of volledig) onbekende datums niet crasht
    zodat het systeem wanneer dit optreedt in de lucht blijft

Scenario: volledigbericht wordt aangemaakt na uitvoeren geefSynchronisatiePersoon met een persoon met onbekende datums
!-- persoon in database heeft de waardes:
!-- 19840700 in veld his_persgeboorte.datgeboorte
!-- 0 in veld his_persvoornaam.dataanvgel
!-- 20120000 in velden his_persadres.dataanvgel en his_persadres.dataanvadresh
!-- 19910013 in veld pers.datinschr
!-- nog 1 extra test gedaan met de waardes 0 in datgeboorte, resultaat is goed

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_onbekende_datums_01.yml
When het bericht wordt verstuurd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut             | verwachteWaardes  |
| identificatienummers      | burgerservicenummer   | 360381169         |


Scenario: mutatiebericht wordt aangemaakt na uitvoeren verhuizingBinnengemeentelijk met een persoon die onbekende datums heeft

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_zonder_abonnementgroepen
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand persoon_met_onbekende_datums_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Abo zonder abonnementgroepen is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                    | attribuut              | verwachteWaardes  |
| parameters               | soortSynchronisatie    | Mutatiebericht    |
| identificatienummers     | burgerservicenummer    | 360381169         |


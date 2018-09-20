Meta:
@status Klaar
@auteur anjaw
@regels VR00092, R1805

Narrative:
In order to de nationaliteiten in berichten in correcte volgorde te ontvangen
As a Afnemer
I want to een levering ontvangen na een handeling omtrend nationaliteiten

R1805	VR00092	Volgorde van meervoudig voorkomende groepen in een bericht

Scenario: Verkrijging vreemde nationaliteit mutatiebericht

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verkrijgingVreemdeNationaliteit , met de acties registratieNationaliteitNaam
And testdata uit bestand verkrijging_vreemde_nationaliteit_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft 'nationaliteitCode' in 'nationaliteit' de waarde '0028'
And hebben de attributen in de groepen de volgende waardes:
| groep 		            | attribuut             | verwachteWaardes  |
| identificatienummers      | burgerservicenummer   | 340014155         |
| nationaliteit             | nationaliteitCode     | 0028              |

Scenario: Verkrijging vreemde nationaliteit vulbericht

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_geselecteerde_personen_voor_afnemer=505002
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verkrijgingVreemdeNationaliteit , met de acties registratieNationaliteitNaam
And testdata uit bestand verkrijging_vreemde_nationaliteit_testcase_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op geselecteerde personen voor afnemer = 505002 is ontvangen en wordt bekeken
Then heeft 'nationaliteitCode' in 'nationaliteit' de waardes '0001,0027'
And hebben de attributen in de groepen de volgende waardes:
| groep 		            | attribuut             | verwachteWaardes    |
| identificatienummers      | burgerservicenummer   | 210010587,210010356 |
| nationaliteit             | nationaliteitCode     | 0001,0027           |

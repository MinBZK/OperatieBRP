Meta:
@sprintnummer           66
@epic                   Mutatielevering basis
@auteur                 rarij
@jiraIssue              TEAMBRP-1988, TEAMBRP-2418
@status                 Klaar
@regels                 VR00057,R1333

Narrative:
    Als stelselbeheerder
    wil ik dat indien de evaluatie van een populatiebeperking als resultaat een NULL oplevert, deze als ONWAAR behandeld wordt
    zodat het systeem de strenge variant hanteert voor het vergelijken van deels of volledig onbekende datums

!-- Voor een overzicht van de uitwerkingen die geleid hebben tot onderstaande scenario's, zie https://www.modernodam.nl/jira/browse/TEAMBRP-2418

Scenario: 1. Als het resultaat van de expressie VOOR de bijhouding ONWAAR is, dan wordt GEEN mutatiebericht aangemaakt

Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon
Given de database is gereset voor de personen 393908586

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand mutatiebericht_als_persoon_met_onbekende_datum_in_doelbinding_blijft_01.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 393908586       |
| geboorte             | 1      | datum               | 2002            |


Scenario: 2. Als het resultaat van de expressie VOOR de bijhouding WAAR is, dan wordt een mutatiebericht aangemaakt
Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon_2
Given de database is gereset voor de personen 393908586
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand mutatiebericht_als_persoon_met_onbekende_datum_in_doelbinding_blijft_02.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon 2 is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 393908586       |
| geboorte             | 1      | datum               | 2002            |


Scenario: 3. Als het resultaat van de expressie VOOR de bijhouding NULL is, dan wordt GEEN mutatiebericht aangemaakt

Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon_3
Given de database is gereset voor de personen 393908586
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand mutatiebericht_als_persoon_met_onbekende_datum_in_doelbinding_blijft_03.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon 3 is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 393908586       |
| geboorte             | 1      | datum               | 2002            |

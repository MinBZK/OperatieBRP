Meta:
@sprintnummer           66
@epic                   Mutatielevering basis
@auteur                 dihoe
@jiraIssue              TEAMBRP-1987,TEAMBRP-2419
@status                 Klaar
@regels                 VR00056,R1348

Narrative:
    Als stelselbeheerder
    wil ik dat indien de evaluatie van een populatiebeperking als resultaat een NULL oplevert, deze als ONWAAR behandeld wordt
    zodat het systeem de strenge variant hanteert voor het vergelijken van deels of volledig onbekende datums

!-- Voor een overzicht van de uitwerkingen die geleid hebben tot onderstaande scenario's, zie https://www.modernodam.nl/jira/browse/TEAMBRP-2419

Scenario: 1. Als het resultaat van de expressie door de bijhouding van ONWAAR naar WAAR gaat, dan wordt een vulbericht aangemaakt

Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon
Given de database is gereset voor de personen 393908586
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand vulbericht_als_persoon_met_onbekende_datum_nieuw_in_doelbinding_komt_01.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| identificatienummers | burgerservicenummer | 393908586        |

Scenario: 2. Als het resultaat van ONWAAR naar NULL gaat, dan wordt GEEN vulbericht aangemaakt
Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon_3
Given de database is gereset voor de personen 393908586
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand vulbericht_als_persoon_met_onbekende_datum_nieuw_in_doelbinding_komt_02.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon 3 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3. Als het resultaat van ONWAAR naar ONWAAR gaat, dan wordt GEEN vulbericht aangemaakt
Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon
Given de database is gereset voor de personen 393908586
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand vulbericht_als_persoon_met_onbekende_datum_nieuw_in_doelbinding_komt_02.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 4. Als het resultaat van NULL naar WAAR gaat, wordt een vulbericht aangemaakt
Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon_3
Given de database is gereset voor de personen 393908586
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand vulbericht_als_persoon_met_onbekende_datum_nieuw_in_doelbinding_komt_03.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon 3 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| identificatienummers | burgerservicenummer | 393908586        |

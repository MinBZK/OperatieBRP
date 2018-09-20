Meta:
@auteur                 dihoe
@sprintnummer           63
@epic                   Mutatielevering op basis van doelbinding
@jiraIssue              TEAMBRP-1901
@regels                 VR00056,VR00097,R1348,R1556
@status                 Klaar

Narrative:
    In order to correct kunnen leveren aan de hand van een administratieve handeling
    As a afnemer
    I want to een vulbericht ontvangen als een persoon bij Mutatielevering op doebinding "nieuw in doelbinding" komt

Scenario: 1. Een persoon is verhuisd naar een nieuwe gemeente, afnemer krijgt een vulbericht omdat hij hiervoor een abonnement heeft
Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_Haarlem_2000-2099
Given de database is gereset voor de personen 677080426
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand vulbericht_als_persoon_nieuw_in_doelbinding_komt_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
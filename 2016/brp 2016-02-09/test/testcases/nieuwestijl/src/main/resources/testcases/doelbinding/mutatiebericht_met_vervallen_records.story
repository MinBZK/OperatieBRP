Meta:
@sprintnummer           65
@epic                   Mutatielevering basis
@auteur                 dihoe
@jiraIssue              TEAMBRP-2251
@status                 Klaar
@regels                 VR00078,VR00059,R1546,R1349

Narrative:
    Bug: vervallen records staan niet altijd in mutatieberichten
    Het groepenfilter zou hetzelfde moeten reageren in de volgende 2 situaties:
        1. Geen record voor groep X
        2. Wel een record voor groep X, maar alle vinkjes op FALSE
    In deze situatie zou voor een mutatiebericht het vinkje voor MaterieleHistorie genegeerd moeten worden.
    Dit is een uitzondering die in het groepfilter zit, maar niet werkte voor situatie 1.
    De verwerkingssoort (toevoeging en verval) kan nu niet gecheckt worden, vandaar dat er gecheckt wordt of het huisnummer 2x voorkomt

Scenario: mutatiebericht van een persoon die door verhuizing in doelbinding blijft, met een abo zonder groep

Given leveringsautorisatie uit /levering_autorisaties/Abo_zonder_abonnementgroepen
Given de database is gereset voor de personen 846514953
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand mutatiebericht_met_vervallen_records_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Abo zonder abonnementgroepen is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep | attribuut  | verwachteWaardes |
| adres | huisnummer | 55,95            |

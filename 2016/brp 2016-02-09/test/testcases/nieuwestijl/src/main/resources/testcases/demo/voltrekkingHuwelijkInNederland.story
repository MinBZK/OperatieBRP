Meta:
@epic                   Voltrekking huwelijk in Nederland
@auteur                 dihoe
@status                 Uitgeschakeld
@regels                 registratieNaamgebruik

Narrative:

Scenario: 1. voltrekking Huwelijk In Nederland, daarna registratie naamgebruik

Given de personen 727750537,212208937,814591139,875271467,646257353,564779209 zijn verwijderd
Given de standaardpersoon Matilda met bsn 646257353 en anr 4765349650 zonder extra gebeurtenissen
Given de standaardpersoon Gregory met bsn 564779209 en anr 7960380178 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand voltrekkingHuwelijkInNederland_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep    | attribuut  | verwachteWaardes |
| document | partijCode | 017401,017401    |

Meta:
@sprintnummer           63
@epic                   Mutatielevering op basis van doelbinding
@auteur                 koapp
@jiraIssue              TEAMBRP-2031
@status                 Uitgeschakeld
@regels                 VR00057,diana67

Narrative:
    As a afnemer
    I want to een mutatiebericht ontvangen als er een mutatie optreed bij een persoon binnen de doelbinding
    So that I mijn gegevens kan bijwerken

Scenario: voltrekking Huwelijk In Nederland

Given de personen 727750537,212208937,814591139,875271467,646257353,564779209 zijn verwijderd
Given de standaardpersoon Matilda met bsn 646257353 en anr 4765349650 zonder extra gebeurtenissen
Given de standaardpersoon Gregory met bsn 564779209 en anr 7960380178 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand voltrekkingHuwelijkInNederland_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                          | attribuut           | verwachteWaardes    |
| stuurgegevens                  | zendendePartij      | 199903              |
| stuurgegevens                  | zendendeSysteem     | BRP                 |
| voltrekkingHuwelijkInNederland | partijCode          | 017401,017401       |
| identificatienummers           | burgerservicenummer | 564779209,646257353 |
| document                       | soortNaam           | Huwelijksakte       |
| document                       | identificatie       | 123                 |
| document                       | partijCode          | 017401              |


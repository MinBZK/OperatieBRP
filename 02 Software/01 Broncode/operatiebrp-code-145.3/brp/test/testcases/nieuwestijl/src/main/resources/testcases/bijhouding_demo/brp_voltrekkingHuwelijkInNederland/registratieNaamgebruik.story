Meta:
@auteur                 dihoe
@status                 Onderhanden
@sleutelwoorden         bijhouding,huwelijk,naamgebruik,dianatest2

Narrative: voltrekking huwelijk in Nederland, acties registratieAanvangHuwelijkGeregistreerdPartnerschap en registratieNaamgebruik

Scenario: 1. R1677 - naamgebruik attributen moeten ontbreken indien deze worden afgeleid
Meta:
@regels                 BRAL0512,R1677

Given de personen 826933129,526521673,141901317,631512457,407238153,316980201 zijn verwijderd
Given de standaardpersoon Sandy met bsn 407238153 en anr 5720862482 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 316980201 en anr 7945376530 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand registratieNaamgebruik_01.yml
And testdata uit bestand registratieNaamgebruik_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes |
| melding | regelCode | BRAL0512         |

Scenario: 2. R1683 - Afgeleide registratie Naamgebruik. Test is nog niet af, moet nog een step komen om de database te controleren
Meta:
@regels                 VR00009a,R1683c

Given de personen 826933129,526521673,141901317,631512457,407238153,316980201 zijn verwijderd
Given de standaardpersoon Sandy met bsn 407238153 en anr 5720862482 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 316980201 en anr 7945376530 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand registratieNaamgebruik_01.yml
And testdata uit bestand registratieNaamgebruik_03.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd



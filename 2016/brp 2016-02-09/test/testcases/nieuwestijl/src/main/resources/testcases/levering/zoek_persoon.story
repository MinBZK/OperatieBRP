Narrative:
Dit is een basis testscenario voor zoekPersoon !! let op !! software nog niet gebouwd!

Scenario: Succesvol uitvoeren zoekPersoon

Meta:
@status Onderhanden
@auteur dihoe

Given de database is gereset voor de personen 340014155
And verzoek van type zoekPersoon

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
parameters                  | soortSynchronisatie  | Vulbericht

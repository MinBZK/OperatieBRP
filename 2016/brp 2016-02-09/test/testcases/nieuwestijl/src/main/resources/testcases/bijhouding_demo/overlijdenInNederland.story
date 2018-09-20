Meta:
@auteur dihoe
@sprintnummer 66
@epic Bijhouding
@status Klaar
@sleutelwoorden

Narrative:
As a tester
I want to have a basis testscenario voor overlijdenInNederland
So that het gebruikt kan worden voor het testen van een bijhouding op overlijden

Scenario: Als een persoon in Nederland overlijdt worden een vulbericht aangemaakt

Given de database is gereset voor de personen 393908586
And administratieve handeling van type overlijdenInNederland , met de acties registratieOverlijden
And testdata uit bestand overlijdenInNederland_01.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van datum overlijden is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut             | verwachteWaardes  |
| identificatienummers      | burgerservicenummer   | 393908586         |



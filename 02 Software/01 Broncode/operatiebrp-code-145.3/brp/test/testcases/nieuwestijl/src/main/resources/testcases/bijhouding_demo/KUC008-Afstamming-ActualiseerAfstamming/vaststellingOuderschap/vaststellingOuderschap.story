Meta:
@status Klaar
@auteur koapp
@sleutelwoorden letop-geen-database-controle-overgenomen

Narrative:
As a user
I want to perform a registratieOuder en registratieNationaliteit on vaststellingOuderschap
So that I can do a bassale vaststellingOuderschap


Scenario: bassale vaststellingOuderschap

Given de database is gereset voor de personen 110011296, 110015927
And administratieve handeling van type vaststellingOuderschap , met de acties registratieOuder, registratieNationaliteit
And testdata uit bestand vaststellingOuderschap_Vaderschap-TC01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

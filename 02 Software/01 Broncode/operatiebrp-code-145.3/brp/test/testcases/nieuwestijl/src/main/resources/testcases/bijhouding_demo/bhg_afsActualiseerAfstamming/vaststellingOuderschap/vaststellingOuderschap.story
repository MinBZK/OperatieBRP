Meta:
@status Onderhanden
@auteur koapp

Narrative:
As a user
I want to perform an happyflow on vaststellingOuderschap
So that I can use it when needed

Scenario: happyflow vaststellingOuderschap

Given de database is gereset voor de personen 110011296, 110015927
And administratieve handeling van type vaststellingOuderschap , met de acties registratieOuder, registratieNationaliteit
And testdata uit bestand vaststellingOuderschap_happyflow.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

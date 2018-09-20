Meta:
@status Klaar
@auteur koapp

Narrative:
As a user
I want to perform an happyflow on toevoegingGeboorteakte
So that I can use it when needed

Scenario: happyflow toevoegingGeboorteakte

Given de database is gereset voor de personen 110012434,110012628,110012823
And administratieve handeling van type toevoegingGeboorteakte , met de acties registratieGeboorte, registratieNationaliteit
And testdata uit bestand toevoegingGeboorteakte_happyflow.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

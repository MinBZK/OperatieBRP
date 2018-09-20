Meta:
@status Klaar
@auteur koapp

Narrative:
As a user
I want to perform an happyflow on verbeteringGeboorteakte
So that I can use it when needed

Scenario: happyflow verbeteringGeboorteakte

Given de database is gereset voor de personen 110012434,110012628,110012823
And administratieve handeling van type verbeteringGeboorteakte , met de acties registratieGeboorte
And testdata uit bestand verbeteringGeboorteakte_happyflow.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Meta:
@status                 Klaar
@sleutelwoorden         verbeteringGeboorteakte
@auteur                 dihoe

Narrative:
Afstamming, verbetering geboorteakte met de actie registratie geboorte

Scenario: 1. 2 testpersonen uit de database

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 110012434,110012628,110012823
And administratieve handeling van type verbeteringGeboorteakte , met de acties registratieGeboorte
And testdata uit bestand verbetering_geboorteakte_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

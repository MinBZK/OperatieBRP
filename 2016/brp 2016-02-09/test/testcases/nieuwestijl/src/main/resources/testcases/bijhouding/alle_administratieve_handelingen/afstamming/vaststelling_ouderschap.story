Meta:
@status                 Klaar
@sleutelwoorden         vaststellingOuderschap
@auteur                 dihoe

Narrative:
Afstamming, vaststelling ouderschap met de acties registratie ouder en registratie nationaliteit

Scenario: 1. 2 testpersonen uit de database

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 110011296, 110015927
And administratieve handeling van type vaststellingOuderschap , met de acties registratieOuder, registratieNationaliteit
And testdata uit bestand vaststelling_ouderschap_01.yml
And testdata uit bestand vaststelling_ouderschap_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Narrative:
Verwerk een overlijden

Scenario: Overlijden persoon

Meta:
@status Klaar
@auteur rohar

Given verzoek van bericht bhg_ovlRegistreerOverlijden
And testdata uit bestand overlijden_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

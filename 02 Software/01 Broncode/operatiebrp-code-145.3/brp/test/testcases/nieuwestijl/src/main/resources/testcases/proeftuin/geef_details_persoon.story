Narrative:
Geef details persoon op verschillende personen

Scenario: Geef details persoon gehuwd persoon

Meta:
@status Klaar
@auteur anjaw

Given verzoek van bericht bhg_bvgGeefDetailsPersoon
And testdata uit bestand geef_details_persoon_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Scenario: Geef details persoon gehuwd persoon met kinderen

Meta:
@status Klaar
@auteur anjaw

Given verzoek van bericht bhg_bvgGeefDetailsPersoon
And testdata uit bestand geef_details_persoon_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

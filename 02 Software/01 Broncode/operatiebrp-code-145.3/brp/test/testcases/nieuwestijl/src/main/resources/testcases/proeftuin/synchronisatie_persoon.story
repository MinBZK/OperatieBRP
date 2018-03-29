Narrative:
Synchronisatie persoon

Scenario: Synchronisatie Persoon

Meta:
@status Klaar
@auteur anjaw

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Automatisch synchroniseren populatie NIET 036101 is ontvangen en wordt bekeken

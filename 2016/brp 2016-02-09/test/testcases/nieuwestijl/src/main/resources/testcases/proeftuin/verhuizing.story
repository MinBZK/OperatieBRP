Narrative:
Verwerk een verhuizing en ontvang een mutatielevering obv doelbinding

Scenario: Verhuis een persoon

Meta:
@status Klaar
@auteur rohar

Given verzoek van bericht bhg_vbaRegistreerVerhuizing
And testdata uit bestand verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Automatisch synchroniseren populatie NIET 036101 is ontvangen en wordt bekeken

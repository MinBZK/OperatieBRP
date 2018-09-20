Narrative:
Afnemerindicatie plaatsen,
persoon verhuizen zodat mutatielevering obv afnemerindicatie wordt getriggered,
vervolgens afnemerindicatie verwijderen

Scenario: Afnemerindicatie plaatsen
Meta:
@status Klaar
@auteur anjaw

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then wacht tot alle berichten zijn ontvangen
When het volledigbericht voor leveringsautorisatie Synchroniseren via indicatie voor partij 036101 is ontvangen en wordt bekeken

Scenario: Verhuis persoon
Meta:
@status Klaar
@auteur anjaw

Given verzoek van bericht bhg_vbaRegistreerVerhuizing
And testdata uit bestand afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het mutatiebericht voor leveringsautorisatie Synchroniseren via indicatie voor partij 036101 is ontvangen en wordt bekeken

Scenario: Afnemerindicatie verwijderen
Meta:
@status Klaar
@auteur anjaw

Given verzoek van type verwijderingAfnemerindicatie
And testdata uit bestand afnemerindicatie_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Synchroniseren via indicatie voor partij 036101 is ontvangen en wordt bekeken


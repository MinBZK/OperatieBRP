Meta:
@status                 Klaar
@sleutelwoorden         OMGP

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Omzetten geregistreerd partnerschap in huwelijk
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:OMGP02C10T10/Gemeente_einde_ongelijk_Gemeente_aanvang/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|368360441

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Omzetten geregistreerd partnerschap in Huwelijk
          LT: OMGP02C10T10
          Gemeente einde ongelijk Gemeente aanvang; Relatie.reden.einde "Omzetting"

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:OMGP02C10T10/Gemeente_einde_ongelijk_Gemeente_aanvang/dbstate002

When voor persoon 368360441 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het registreren huwelijk in buitenland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

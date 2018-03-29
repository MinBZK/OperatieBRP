Meta:
@status                 Klaar
@sleutelwoorden         AGBL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Aangaan geregistreerd partnerschap in buitenland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:AGBL02C10T40/Landcode_ongelijk_aan_6030_en_0000_en_99/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|644277129

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Aangaan geregistreerd partnerschap in Buitenland
          LT:
          Aangaan geregistreerd partnerschap in buitenland.
          Landcode ongelijk aan 6030 en 0000 en 9999 velden Relatie.Buitenlandse plaats aanvang en Relatie.Buitenlandse regio aanvang gevuld

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:AGBL02C10T40/Landcode_ongelijk_aan_6030_en_0000_en_99/dbstate003


When voor persoon 644277129 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het registreren huwelijk in buitenland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

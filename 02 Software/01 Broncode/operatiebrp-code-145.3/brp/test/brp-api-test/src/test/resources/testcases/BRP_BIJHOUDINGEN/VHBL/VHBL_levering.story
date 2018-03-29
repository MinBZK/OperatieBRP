Meta:
@status                 Klaar
@sleutelwoorden         VHBL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Registreren Huwelijk in buitenland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:VHBL04C10T10/Registratie_Voltrekking_huwelijk_in_buit/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|870956425

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 1. Administratieve handeling van type Registratie Huwelijk in Buitenland
          LT:
          2 Ingeschreven personen Marjan Visser & Marjan Visser.
          Registratie van huwelijk in buitenland.

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:VHBL04C10T10/Registratie_Voltrekking_huwelijk_in_buit/dbstate003
When voor persoon 870956425 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het registreren huwelijk in buitenland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

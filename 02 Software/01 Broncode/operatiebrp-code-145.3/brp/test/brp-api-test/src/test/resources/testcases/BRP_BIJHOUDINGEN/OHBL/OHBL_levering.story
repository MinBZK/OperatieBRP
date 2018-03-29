Meta:
@status                 Klaar
@sleutelwoorden         OHBL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Ontbinden Huwelijk in buitenland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:OHBL02C10T10/Ontbinding_huwelijk_in_het_buitenland._V/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|975422601

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 1. Administratieve handeling van type Ontbinden Huwelijk in Buitenland
          LT: OHBL02C10T10
          Ontbinding huwelijk in het buitenland.  buitenlandsePlaatsEinde, buitenlandseRegioEinde en landGebiedEindeCode gevuld.

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:OHBL02C10T10/Ontbinding_huwelijk_in_het_buitenland._V/dbstate002
When voor persoon 975422601 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav ontbinding huwelijk in buitenland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

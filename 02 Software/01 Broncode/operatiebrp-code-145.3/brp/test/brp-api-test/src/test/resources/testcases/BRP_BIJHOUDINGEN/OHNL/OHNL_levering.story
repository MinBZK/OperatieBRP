Meta:
@status                 Klaar
@sleutelwoorden         OHNL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Ontbinden Huwelijk in Nederland
correct geleverd wordt aan afnemers van het BRP.


Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:OHNL04C20T10/Een_GBA_huwelijk,_dit_huwelijk_wordt_in_/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|542836361

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 1. Administratieve handeling van type Ontbinden Huwelijk in Nederland
          LT: OHNL04C20T10
          Ontbinding huwelijk in het Nederland.

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:OHNL04C20T10/Een_GBA_huwelijk,_dit_huwelijk_wordt_in_/dbstate002
When voor persoon 542836361 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav ontbinding huwelijk in Nederland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Meta:
@status                 Klaar
@sleutelwoorden         WGIS04C10T10

!-- test op onderhanden omdat testgeval van Oranje ook op onderhanden staat

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Registratie wijziging gemeente infrastructureel
met een 'Actueel' persoon correct geleverd wordt aan afnemers van het BRP.
WGIS04C10T10 - Registratie wijziging gemeente infrastructureel met een 'Actueel' persoon



Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:WGIS04C10T50/Registratie_wijziging_gemeente_infrastru/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|336792025

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Registratie wijziging adres infrastructureel met een BAG-adres
          LT: WGIS04C10T10
          Registratie type Registratie wijziging gemeente infrastructureel met een 'Actueel' persoon


Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:WGIS04C10T50/Registratie_wijziging_gemeente_infrastru/dbstate002

When voor persoon 336792025 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


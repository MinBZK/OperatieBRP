Meta:
@status                 Klaar
@sleutelwoorden         AGNL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Aangaan geregistreerd partnerschap in Nederland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:AGNL01C800T10/R1572_Ouder_en_wijzigen_Geslachtsnaamcom/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|188518113

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Registratie Partnerschap met actie registratie geslachtsnaam
          LT: AGNL01C800T10
          Registratie geregistreerd partnerschap met neven actie registratie geslachtsnaam.


Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:AGNL01C800T10/R1572_Ouder_en_wijzigen_Geslachtsnaamcom/dbstate003
When voor persoon 188518113 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het geregistreerd partnerschap
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


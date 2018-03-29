Meta:
@status                 Klaar
@sleutelwoorden         EGBL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Beeindiging geregistreerd partnerschap in Buitenland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:EGBL02C10T10/Registratie_einde_geregistreerd_partners/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|515232361

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type beeindigingGeregistreerdPartnerschapInBuitenland met actie registratieNaamgebruik
          LT: EGBL02C10T10
          beeindigingGeregistreerdPartnerschapInBuitenland met actie registratieNaamgebruik

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:EGBL02C10T10/Registratie_einde_geregistreerd_partners/dbstate002
When voor persoon 515232361 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het beeindigen van het geregistreerd partnerschap
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2. Administratieve handeling van type beeindigingGeregistreerdPartnerschapInBuitenland met actie registratieGeslachtsnaam
          LT: EGBL02C10T20
          beeindigingGeregistreerdPartnerschapInBuitenland met actie registratieGeslachtsnaam

Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:EGBL02C10T20/Verwerken_einde_geregistreerd_partnersch/dbstate002
When voor persoon 917674121 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het beeindigen van het geregistreerd partnerschap
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon
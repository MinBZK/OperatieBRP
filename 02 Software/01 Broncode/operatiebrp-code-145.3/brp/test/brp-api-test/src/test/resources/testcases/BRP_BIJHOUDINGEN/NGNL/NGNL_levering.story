Meta:
@status                 Klaar
@sleutelwoorden         NGNL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Nietigverklaring geregistreerd partnerschap in Nederland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:NGNL04C10T50/Nevenactie_Registratie_geslachtsnaam_Eig/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|403428233

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Nietigverklaring geregistreerd partnerschap in Nederland
          LT: NGNL04C10T50
          Nietigverklaring geregistreerd partnerschap in Nederland met neven actie Registratie geslachtsnaam Eigen naam voor naam partner "E"
          NB: De nevenactie wordt uitgevoerd op Piet Jansen.

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:NGNL04C10T50/Nevenactie_Registratie_geslachtsnaam_Eig/dbstate003
When voor persoon 403428233 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het geregistreerd partnerschap
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


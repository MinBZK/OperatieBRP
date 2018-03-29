Meta:
@status                 Klaar
@sleutelwoorden         EGNL

Narrative:
Beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:EGNL01C70T30/R2040_Beeindiging_van_een_geregistreerd_/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|594645001

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Beeindiging van een geregistreerd partnerschap. Reden einde GP is A.
          LT: EGNL01C70T30

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Partnerschap_Einde

Given persoonsbeelden uit BIJHOUDING:EGNL01C70T30/R2040_Beeindiging_van_een_geregistreerd_/dbstate002
When voor persoon 594645001 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie_bericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering partnerschap is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig_bericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

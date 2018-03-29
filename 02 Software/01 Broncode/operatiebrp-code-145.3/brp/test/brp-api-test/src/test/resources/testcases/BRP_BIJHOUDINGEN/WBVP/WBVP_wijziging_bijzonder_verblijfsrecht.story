Meta:
@status                 Klaar
@sleutelwoorden         WBVP

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Registratie van bijzondere verblijsfrechtelijke positie
correct geleverd wordt aan afnemers van het BRP.
WBVP02C10T20 - Registratie van bijzondere verblijsfrechtelijke positie is in een bijhoudingsbericht


Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:WBVP02C10T20/R2352_Registratie_van_bijzondere_verblij/dbstate001


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|968599497

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Registratie van bijzondere verblijsfrechtelijke positie
          LT: WBVP02C10T20
          Registratie type Registratie van bijzondere verblijsfrechtelijke positie


Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:WBVP02C10T20/R2352_Registratie_van_bijzondere_verblij/dbstate002
When voor persoon 968599497 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


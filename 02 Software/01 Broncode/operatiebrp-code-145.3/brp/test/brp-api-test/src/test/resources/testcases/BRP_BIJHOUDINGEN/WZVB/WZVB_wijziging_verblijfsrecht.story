Meta:
@status                 Klaar
@sleutelwoorden         WZVB04C10T10

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Registratie verblijfsrecht
van ingezetene waarbij er nog geen verblijfsrecht is geregistreerd
correct geleverd wordt aan afnemers van het BRP.
WZVB04C10T10 - Registratie verblijfsrecht van ingezetene waarbij er nog geen verblijfsrecht is geregistreerd



Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:WZVB04C10T10/Registratie_verblijfsrecht_van_ingezeten/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|176407881

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Registratie wijziging adres infrastructureel met een BAG-adres
          LT: WZVB04C10T10
          Registratie type egistratie verblijfsrecht van ingezetene waarbij er nog geen verblijfsrecht is geregistreerd


Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:WZVB04C10T10/Registratie_verblijfsrecht_van_ingezeten/dbstate002

When voor persoon 176407881 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


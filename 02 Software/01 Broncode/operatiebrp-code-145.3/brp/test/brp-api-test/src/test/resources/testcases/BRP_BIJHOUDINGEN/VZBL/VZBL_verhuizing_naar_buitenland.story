Meta:
@status                 Klaar
@sleutelwoorden         VZBL04C10T30

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Verhuizing buitenland
correct geleverd wordt aan afnemers van het BRP.
VZBL04C10T30	Aangever I - Ingeschrevene



Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZBL04C10T30/Verwerking_hoofdactie_Registratie_migrat/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|221765001

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Registratie wijziging adres infrastructureel met een BAG-adres
          LT: VZBL04C10T30
          Registratie type Verhuizing buitenland

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:VZBL04C10T30/Verwerking_hoofdactie_Registratie_migrat/dbstate002

When voor persoon 221765001 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Meta:
@status                 Klaar
@sleutelwoorden         VZBG04C20T10

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Verhuizing binnengemeentelijk met hoofdactie Registratie adres
correct geleverd wordt aan afnemers van het BRP.
VZBG04C10T10 - Succesvolle verwerking binnengemeentelijke verhuizing



Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZBG04C20T10/Verwerking_Volledige_verstrekkingsbeperk/dbstate001


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|122004851

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Verhuizing binnengemeentelijk met hoofdactie Registratie adres
          LT: VZBG04C10T10
          Registratie type Verhuizing binnengemeentelijk met hoofdactie Registratie adres


Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:VZBG04C20T10/Verwerking_Volledige_verstrekkingsbeperk/dbstate002
When voor persoon 122004851 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Mutatiebericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


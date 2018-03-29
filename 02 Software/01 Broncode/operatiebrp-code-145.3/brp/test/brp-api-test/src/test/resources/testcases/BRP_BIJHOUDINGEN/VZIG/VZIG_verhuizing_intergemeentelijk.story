Meta:
@status                 Klaar
@sleutelwoorden         VZIG

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type verhuizing intergemeentelijk
correct geleverd wordt aan afnemers van het BRP.
VZIG01C30T80 - De adreshouder is een ouder van een minderjarig en meerderjarig kind (qua leeftijd 18 en geen H/GP)


Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:VZIG01C30T80/R1929_De_adreshouder_is_een_ouder_van_ee/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|922735177

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type verhuizing intergemeentelijk
          LT: VZIG01C30T80
          Registratie type verhuizing intergemeentelijk

Given persoonsbeelden uit BIJHOUDING:VZIG01C30T80/R1929_De_adreshouder_is_een_ouder_van_ee/dbstate004
When voor persoon 922735177 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/bericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/Volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


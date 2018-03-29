Meta:
@status                 Klaar
@sleutelwoorden         GBNL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type geboorte in Nederland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:GBNL04C40T10/Registratie_volledige_verstrekkingsbeper/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|760877257

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Geboorte in nederland, Nevenactie Registratie verstrekkingsbeperking
          LT: GBNL04C10T10

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:GBNL04C40T10/Registratie_volledige_verstrekkingsbeper/dbstate003
When voor persoon 760877257 wordt de laatste handeling geleverd

!-- Volledigbericht voor ouder dat geboorte van een kind krijgt
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/GBNL04C40_volledigbericht_ouder.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Ophalen van de mutatie leveringen nav de geboorte voor de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/GBNL04C40_mutatiebericht_ouder.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Ophalen van de volledigbericht leveringen nav de geboorte voor het kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/GBNL04C40_volledigbericht_kind.xml voor expressie //brp:lvg_synVerwerkPersoon

[306706921, 954614537, 676907337]
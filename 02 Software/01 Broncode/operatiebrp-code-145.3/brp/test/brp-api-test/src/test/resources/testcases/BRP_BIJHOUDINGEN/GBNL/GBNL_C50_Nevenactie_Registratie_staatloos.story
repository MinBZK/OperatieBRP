Meta:
@status                 Klaar
@sleutelwoorden         GBNL

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type geboorte in Nederland
correct geleverd wordt aan afnemers van het BRP.

Scenario: 1. Persoonsbeeld Initiele vulling als uitgangssituatie

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:GBNL04C50T10/Registratie_van_een_geboorte_in_Nederlan/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|933296009

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Administratieve handeling van type Geboorte in nederland, Nevenactie Registratie staatloos
                LT: GBNL04C50T10
                Verwacht resultaat:
                - Onderstaande berichten

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie


Given persoonsbeelden uit BIJHOUDING:GBNL04C50T10/Registratie_van_een_geboorte_in_Nederlan/dbstate003
When voor persoon 933296009 wordt de laatste handeling geleverd

!-- Volledigbericht voor ouder dat geboorte van een kind krijgt
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/GBNL04C50_volledigbericht_ouder.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Ophalen van de mutatie leveringen nav de geboorte voor de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/GBNL04C50_mutatiebericht_ouder.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Ophalen van de volledigbericht leveringen nav de geboorte voor het kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/GBNL04C50_volledigbericht_kind.xml voor expressie //brp:lvg_synVerwerkPersoon

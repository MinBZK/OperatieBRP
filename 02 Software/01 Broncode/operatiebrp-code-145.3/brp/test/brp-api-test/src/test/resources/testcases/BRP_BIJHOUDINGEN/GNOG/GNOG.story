Meta:
@status                 Klaar
@sleutelwoorden         GNOG

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Geboorte in Nederland met erkenning OP geboortedatum
correct geleverd wordt aan afnemers van het BRP.


Scenario: 1. Administratieve handeling van type Registratie geborene via Geboorte in Nederland op geboortedatum
          LT: GNOG04C10T10
          Uitwerking:
          GNOG04C10T10 - Registratie geborene in Nederland met OUWKIG(I) en NOUWKIG(I)
          Met Bijz.VerblijfsR. Positie waarbij geboortegemeente en gemeente van bijhoudingspartij van OUWKIG NOUWKIG verschillend zijn

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNOG04C10T10/Registratie_geborene_in_Nederland_met_OU/dbstate003
When voor persoon 888959369 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2. Administratieve handeling van type Registratie identificatienummers Kind
          LT: GNOG04C20T10
          Uitwerking:
          GNOG04C20T10 - Registratie identificatienummers Kind

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNOG04C20T10/Registratie_identificatienummers_(Kind)_/dbstate003
When voor persoon 590151113 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3. Administratieve handeling van type Registratie nationaliteit met waarde gelijk aan NL waarbij Bijzondere verblijfsrechtelijke positie aanwezig is
          LT: GNOG04C30T10
          Uitwerking:
          GNOG04C30T10 - Registratie nationaliteit met waarde gelijk aan NL waarbij Bijzondere verblijfsrechtelijke positie aanwezig is

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNOG04C30T10/Registratie_nationaliteit_met_waarde_gel/dbstate003
When voor persoon 588142633 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 4. Administratieve handeling van type Registratie volledige verstrekkingsbeperking
          LT: GNOG04C40T10
          Uitwerking:
          GNOG04C40T10 - Registratie volledige verstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNOG04C40T10/Registratie_volledige_verstrekkingsbeper/dbstate003
When voor persoon 536477929 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 5. Administratieve handeling van type Registratie van een geboorte in Nederland met erkenning op geboortedatum die staatloos is met meerdere bronnen
          LT: GNOG04C50T10
          Uitwerking:
          GNOG04C50T10 - Registratie van een geboorte in Nederland met erkenning op geboortedatum die staatloos is met meerdere bronnen

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNOG04C50T10/Registratie_van_een_geboorte_in_Nederlan/dbstate003
When voor persoon 871032521 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

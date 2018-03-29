Meta:
@status                 Klaar
@sleutelwoorden         GNNG

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Geboorte in Nederland met erkenning na geboortedatum
correct geleverd wordt aan afnemers van het BRP.




Scenario: 1. Administratieve handeling van type Registratie geborene via Geboorte in Nederland na geboortedatum
          LT: GNNG04C10T10
          GNNG04C10T10 - Registratie geborene via Geboorte in Nederland na geboortedatum

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C10T10/Registratie_geborene_via__Geboorte_in_Ne/dbstate003
When voor persoon 376715881 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2. Administratieve handeling van type Geboorte in Nederland met erkenning na geboortedatum: nevenactie Registratie identificatienummers
          LT: GNNG04C20T10
          GNNG04C20T10 - Geboorte in Nederland met erkenning na geboortedatum: nevenactie Registratie identificatienummers

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C20T10/Registratie_identificatienummers_via__Ge/dbstate003
When voor persoon 519952777 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 3. Administratieve handeling van type Geboorte in Nederland met erkenning na geboortedatum: nevenactie Registratie identificatienummers
          LT: GNNG04C30T10
          GNNG04C30T10 - Registratie nationaliteit via Geboorte in Nederland met erkenning na geboortedatum met DAG geboortedatum

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C30T10/Registratie_nationaliteit_via__Geboorte_/dbstate003
When voor persoon 609335145 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 4. Administratieve handeling van type Registratie nationaliteit via met Bijzondere Verblijfsrechtelijke Postie PROBAS
          LT: GNNG04C30T20
          GNNG04C30T20 - Registratie nationaliteit via met Bijzondere Verblijfsrechtelijke Postie PROBAS

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C30T20/Registratie_nationaliteit_via_met_Bijzon/dbstate003
When voor persoon 376715881 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 5. Administratieve handeling van type Registratie staatloos via Geboorte in Nederland na geboortedatum
          LT: GNNG04C40T10
          GNNG04C40T10 - Registratie staatloos via Geboorte in Nederland na geboortedatum

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C40T10/Registratie_staatloos_via__Geboorte_in_N/dbstate003
When voor persoon 594571625 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6. Administratieve handeling van type Registratie verstrekkingsbeperking via Geboorte in Nederland na geboortedatum
          LT: GNNG04C50T10
          GNNG04C50T10 - Registratie verstrekkingsbeperking via Geboorte in Nederland na geboortedatum

Given leveringsautorisatie uit autorisatie/autorisatiealles,
                               autorisatie/Attendering_Mutatielevering_afnemerindicatie,
                               autorisatie/Attendering_Mutatielevering_afnemerindicatie_verstrekkingsbeperking,
                               autorisatie/Attendering_Mutatielevering_afnemerindicatie_verstrekkingsbeperking_maar_niet_voor_partij

Given persoonsbeelden uit BIJHOUDING:GNNG04C50T10/Registratie_verstrekkingsbeperking_via__/dbstate003
When voor persoon 609335145 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Omdat er een verstrekkingsbeperking is bij de persoon mag er geen afnemerindicatie geplaatst worden bij de persoon voor deze partij
!-- Daarom mag er ook geen volledig bericht gezonden worden aan de afnemer
Then is er geen synchronisatiebericht voor leveringsautorisatie Attendering met plaatsing en verstrekkingsbeperking

!-- verstrekkingsbeperking is partij specifiek, dus voor andere partijen wordt wel geleverd
When het volledigbericht voor partij KUC033-PartijVerstrekkingsbeperking en leveringsautorisatie Attendering met plaatsing en verstrekkingsbeperking maar niet voor partij is ontvangen en wordt bekeken

Scenario: 7. Administratieve handeling van type Registratie ouder - Ingeschrevene
          LT: GNNG04C60T10
          GNNG04C60T10 - Registratie ouder - Ingeschrevene

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C60T10/Nevenactie_Registratie_ouder_met_ingesch/dbstate003
When voor persoon 329424361 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 8. Administratieve handeling van type Registratie ouder - Pseudo-persoon met adellijke titel
          LT: GNNG04C60T20
          GNNG04C60T20 - Registratie ouder - Pseudo-persoon met adellijke titel

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C60T20/Nevenactie_Registratie_ouder_met_ingesch/dbstate002
When voor persoon 450004041 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 9. Administratieve handeling van type Registratie ouder - Pseudo-persoon met predicaat
          LT: GNNG04C60T30
          GNNG04C60T30 - Registratie ouder - Pseudo-persoon met predicaat

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C60T30/Nevenactie_Registratie_ouder_met_ingesch/dbstate002
When voor persoon 961869641 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10. Administratieve handeling van type Registratie ouder - Pseudo-persoon geboren in het buitenland
          LT: GNNG04C60T40
          GNNG04C60T40 - Registratie ouder - Pseudo-persoon geboren in het buitenland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C60T40/Nevenactie_Registratie_ouder_met_ingesch/dbstate002
When voor persoon 454733161 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. Administratieve handeling van type Registratie geslachtsnaam voornaam met adellijke titel en 2 voornamen
          LT: GNNG04C70T10
          GNNG04C70T10 - Registratie geslachtsnaam voornaam met adellijke titel en 2 voornamen

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C70T10/Nevenactie_Registratie_geslachtsnaam_voo/dbstate003
When voor persoon 693560137 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12. Administratieve handeling van type Registratie geslachtsnaam voornaam met predicaat
          LT: GNNG04C70T20
          GNNG04C70T20 - Registratie geslachtsnaam voornaam met predicaat

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C70T20/Nevenactie_Registratie_geslachtsnaam_voo/dbstate003
When voor persoon 553387145 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13. Administratieve handeling van type Registratie geslachtsnaam voornaam waarbij voornamen vervallen dmv Indicatie Namenreeks J
          LT: GNNG04C70T30
          GNNG04C70T30 - Registratie geslachtsnaam voornaam waarbij voornamen vervallen dmv Indicatie Namenreeks J

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C70T30/Nevenactie_Registratie_geslachtsnaam_voo/dbstate003
When voor persoon 553387145 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14. Administratieve handeling van type Registratie NL nationaliteit waardoor niet beeindigd overige nationaliteit vervalt
          LT: GNNG04C80T10
          GNNG04C80T10 - Registratie NL nationaliteit waardoor niet beeindigd overige nationaliteit vervalt

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T10/Registratie_NL_nationaliteit_waardoor_ni/dbstate003
When voor persoon 354860665 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. Administratieve handeling van type Registratie NL nationaliteit waardoor overige nationaliteit in hetzelfde bericht wordt beeindigd vervalt
          LT: GNNG04C80T20
          GNNG04C80T20 - Registratie NL nationaliteit waardoor overige nationaliteit in hetzelfde bericht wordt beeindigd vervalt

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T20/Registratie_NL_nationaliteit_waardoor_ov/dbstate003
When voor persoon 134746417 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16. Administratieve handeling van type Registratie NL nationaliteit waardoor niet beeindigde overige nationaliteiten meervoud vervallen
          LT: GNNG04C80T30
          GNNG04C80T30 - Registratie NL nationaliteit waardoor niet beeindigde overige nationaliteiten meervoud vervallen

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T30/Registratie_NL_nationaliteit_waardoor_ni/dbstate003
When voor persoon 333939657 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17. Administratieve handeling van type Registratie NL nationaliteit waardoor bijzondere verblijfsrechtelijke positie vervalt
          LT: GNNG04C80T40
          GNNG04C80T40 - Registratie NL nationaliteit waardoor bijzondere verblijfsrechtelijke positie vervalt

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T40/Registratie_NL_nationaliteit_waardoor_bi/dbstate003
When voor persoon 424633929 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18. Administratieve handeling van type Registratie overige nationaliteit waardoor niet beeindigd overige nationaliteit niet vervalt
          LT: GNNG04C80T50
          GNNG04C80T50 - Registratie overige nationaliteit waardoor niet beeindigd overige nationaliteit niet vervalt

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T50/Registratie_overige_nationaliteit_waardo/dbstate003
When voor persoon 849339017 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19. Administratieve handeling van type Registratie overige nationaliteit waardoor overige nationaliteit in hetzelfde bericht wordt beeindigd niet vervalt
          LT: GNNG04C80T60
          GNNG04C80T60 - Registratie overige nationaliteit waardoor overige nationaliteit in hetzelfde bericht wordt beeindigd niet vervalt

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T60/Registratie_overige_nationaliteit_waardo/dbstate003
When voor persoon 512589513 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20. Administratieve handeling van type Registratie overige nationaliteit waardoor niet beeindigde overige nationaliteiten meervoud niet vervallen
          LT: GNNG04C80T70
          GNNG04C80T70 - Registratie overige nationaliteit waardoor niet beeindigde overige nationaliteiten meervoud niet vervallen

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T70/Registratie_overige_nationaliteit_waardo/dbstate003
When voor persoon 219987609 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21. Administratieve handeling van type Registratie overige nationaliteit waardoor bijzondere verblijfsrechtelijke positie niet vervalt
          LT: GNNG04C80T80
          GNNG04C80T80 - Registratie overige nationaliteit waardoor bijzondere verblijfsrechtelijke positie niet vervalt

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C80T80/Registratie_overige_nationaliteit_waardo/dbstate003
When voor persoon 366120633 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22. Administratieve handeling van type Beëindiging nationaliteit zonder redenverliescode
          LT: GNNG04C90T10
          GNNG04C90T10 - Beëindiging nationaliteit zonder redenverliescode

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C90T10/Verwerking_van_de_nevenactie_Beeindiging/dbstate003
When voor persoon 875425161 wordt de laatste handeling geleverd

!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario22.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario22.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario22.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 23. Administratieve handeling van type Beëindiging nationaliteit met redenverliescode
          LT: GNNG04C90T20
          GNNG04C90T20 - Beëindiging nationaliteit met redenverliescode

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:GNNG04C90T20/Verwerking_van_de_nevenactie_Beeindiging/dbstate003
When voor persoon 258221689 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/geboorte-bericht-scenario23.xml voor expressie //brp:lvg_synVerwerkPersoon
!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario23.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Volledigbericht van de ouder nav het attenderingscriterum
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24. Erkenning na geboorte waarbij de ouders verschillende achternamen hebben
              LT: GNNG04C70T40

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:GNNG04C70T40/Nevenactie_Registratie_geslachtsnaam_van/dbstate003
When voor persoon 693560137 wordt de laatste handeling geleverd
!-- Volledigbericht van het geboren kind
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario24.xml voor expressie //brp:lvg_synVerwerkPersoon
!-- Mutatiebericht van de registratie van het kind bij de ouder
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario24.xml voor expressie //brp:lvg_synVerwerkPersoon

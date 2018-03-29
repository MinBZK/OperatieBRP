Meta:
@status                 Klaar
@sleutelwoorden         CHUW

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Correctie Huwelijk
correct geleverd wordt aan afnemers van het BRP.

Scenario: 0. Persoonsbeeld Init vulling voor Correctie Huwelijk met Nadere aanduiding verval O
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T10/Correctie_Huwelijk_met_Nadere_aanduiding/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|953977481

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1. Administratieve handeling van type Correctie Huwelijk met Nadere aanduiding verval O
          LT: CHUW04C10T10
          CHUW04C10T10	Correctie Huwelijk met Nadere aanduiding verval: O

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T10/Correctie_Huwelijk_met_Nadere_aanduiding/dbstate004
When voor persoon 953977481 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het correctie huwelijk met nadere aanduiding o
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2a. Persoonsbeeld Init vulling voor Correctie Huwelijk met Nadere aanduiding verval S
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T20/2._Correctie_Huwelijk_met_Nadere_aanduid/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|435048521

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2b. Administratieve handeling van type Correctie Huwelijk met Nadere aanduiding verval S
          LT: CHUW04C10T20
          CHUW04C10T20 - Correctie Huwelijk met Nadere aanduiding verval: S is strijdig

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T20/3._DB_reset_______________postconditie/dbstate003
When voor persoon 435048521 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het correctie huwelijk met nadere aanduiding S
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 3a. Persoonsbeeld Init vulling voor Correctie Huwelijk zonder Nadere aanduiding verval
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T30/Correctie_Huwelijk_zonder_Nadere_aanduid/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|457091721

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3b. Administratieve handeling van type Correctie Huwelijk zonder Nadere aanduiding verval
          LT: CHUW04C10T30
          CHUW04C10T30 - Correctie Huwelijk zonder Nadere aanduiding verval

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T30/Correctie_Huwelijk_zonder_Nadere_aanduid/dbstate004
When voor persoon 457091721 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het correctie Correctie Huwelijk zonder Nadere aanduiding verval
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 4a. Persoonsbeeld Init vulling voor Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T40/Nederlandse_gegevens_corrigeren_met_maxi/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|525597001

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4b. Administratieve handeling van type Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland
          LT: CHUW04C10T40
          CHUW04C10T40 - Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T40/Nederlandse_gegevens_corrigeren_met_maxi/dbstate004
When voor persoon 525597001 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het correctie Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 5a. Persoonsbeeld Init vulling voor Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T50/Buitenlandse_gegevens_corrigeren_met_max/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|698456713

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5b. Administratieve handeling van type Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland
          LT: CHUW04C10T50
          CHUW04C10T50 - Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T50/Buitenlandse_gegevens_corrigeren_met_max/dbstate007
When voor persoon 698456713 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav correctie Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6a. Persoonsbeeld Init vulling voor correctie Gegevens einde relatie corrigeren Nederland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T60/Gegevens_einde_relatie_corrigeren_Nederl/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|328744153

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 6b. Administratieve handeling van type correctie Gegevens einde relatie corrigeren Nederland
          LT: CHUW04C10T60
          CHUW04C10T60 - Gegevens einde relatie corrigeren Nederland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T60/Gegevens_einde_relatie_corrigeren_Nederl/dbstate005
When voor persoon 328744153 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav correctie Gegevens einde relatie corrigeren Nederland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 7a. Persoonsbeeld Init vulling voor correctie Gegevens einde relatie corrigeren buitenland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T70/Gegevens_einde_relatie_corrigeren_einde_/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|250020361

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7b. Administratieve handeling van type correctie Gegevens einde relatie corrigeren buitenland
          LT: CHUW04C10T70
          CHUW04C10T70 - Gegevens einde relatie corrigeren buitenland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T70/Gegevens_einde_relatie_corrigeren_einde_/dbstate005
When voor persoon 250020361 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav correctie Gegevens einde relatie corrigeren buitenland
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 8a. Persoonsbeeld Init vulling voor correctie GBA-persoon leidt tot notificatie met toelichting teruggestuurd
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T80/GBA-persoon_leidt_tot_notificatie_met_to/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|124957481

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 8b. Administratieve handeling van type correctie GBA-persoon leidt tot notificatie met toelichting teruggestuurd
          LT: CHUW04C10T80
          CHUW04C10T80 - GBA-persoon leidt tot notificatie met toelichting teruggestuurd

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T80/GBA-persoon_leidt_tot_notificatie_met_to/dbstate003
When voor persoon 124957481 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav correctie GBA-persoon leidt tot notificatie met toelichting teruggestuurd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 9a. Persoonsbeeld Init vulling voor correctie soort brondocument Geboorteakte
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T90/soort_brondocument_Geboorteakte_________/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|355911401

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 9b. Administratieve handeling van type correctie soort brondocument Geboorteakte
          LT: CHUW04C10T90
          CHUW04C10T90 - soort brondocument Geboorteakte

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T90/soort_brondocument_Geboorteakte_________/dbstate004
When voor persoon 355911401 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav correctie soort brondocument Geboorteakte
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 10a. Persoonsbeeld Init vulling voor correctie Het laten herleven van een ontbonden huwelijk
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T100/Het_laten_herleven_van_een_ontbonden_huw/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|300039001

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 10b. Administratieve handeling van type correctie Het laten herleven van een ontbonden huwelijk
          LT: CHUW04C10T100
          CHUW04C10T100 - Het laten herleven van een ontbonden huwelijk

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T100/Het_laten_herleven_van_een_ontbonden_huw/dbstate005
When voor persoon 300039001 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav correctie Het laten herleven van een ontbonden huwelijk
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 11a. Persoonsbeeld Init vulling voor Correctie na ontrelateren
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T110/2._Ontrelateren_bij_Ontbinding_huwelijk_/dbstate007
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|791172041

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 11b. Administratieve handeling van type Correctie na ontrelateren
          LT: CHUW04C10T110
          CHUW04C10T110	- Correctie na ontrelateren

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T110/4._Corrigeer_Huwelijk_____________postco\dbstate002
When voor persoon 791172041 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav Correctie na ontrelateren
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 12a. Persoonsbeeld Init vulling voor Correctie triggert ontrelateren
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T120/2._Ontrelateren_bij_Ontbinding_huwelijk_/dbstate007
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|866171721

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 12b. Administratieve handeling van type Correctie triggert ontrelateren
          LT: CHUW04C10T120
          CHUW04C10T120	- Correctie triggert ontrelateren

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T120/4._Corrigeer_Huwelijk_____________postco/dbstate002
When voor persoon 866171721 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav Correctie triggert ontrelateren
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13A. Persoonsbeeld Init vulling voor Correctie Huwelijk
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T130/Correctie_aanvang_huwelijk_naar_het_verl/dbstate002
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|953977481

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 13B. Correctie aanvang huwelijk naar het verleden met een pseudo persoon.
               LT: CHUW04C10T130

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T130/Correctie_aanvang_huwelijk_naar_het_verl/dbstate003
When voor persoon 953977481 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het correctie huwelijk met nadere aanduiding o
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13C. Persoonsbeeld NA Correctie Huwelijk met historievorm GEEN en peilmaterieel tussen de oude en nieuwe huwelijksdatum
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CHUW04C10T130/Correctie_aanvang_huwelijk_naar_het_verl/dbstate003
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|953977481
|historievorm|Geen
|peilmomentMaterieelResultaat|2016-05-05

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expecteds/GeefDetails_met_peilmoment_scenario_13.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


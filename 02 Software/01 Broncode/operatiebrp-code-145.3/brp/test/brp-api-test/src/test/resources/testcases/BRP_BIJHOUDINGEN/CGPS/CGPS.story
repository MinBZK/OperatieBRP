Meta:
@status                 Klaar
@sleutelwoorden         CGPS

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Correctie Geregistreerd Partnerschap
correct geleverd wordt aan afnemers van het BRP.


Scenario: 0. Persoonsbeeld Init vulling voor Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T10/Nederlandse_gegevens_corrigeren_met_maxi/dbstate003
When voor persoon 326018281 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Scenario: 1. Administratieve handeling van type Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland
          LT: CGPS04C10T10
          CGPS04C10T10 - Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T10/Nederlandse_gegevens_corrigeren_met_maxi/dbstate004
When voor persoon 326018281 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2a. Persoonsbeeld Init vulling voor Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T20/Buitenlandse_gegevens_corrigeren_met_max/dbstate006

When voor persoon 631636201 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Scenario: 2b. Administratieve handeling van type Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland
          LT: CGPS04C10T20
          CGPS04C10T20 - Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T20/Buitenlandse_gegevens_corrigeren_met_max/dbstate007
When voor persoon 631636201 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 3a. Persoonsbeeld Init vulling voor Buitenlandse gegevens corrigeren met maximaal aantal velden voor aanvang buitenland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T30/Gegevens_einde_relatie_corrigeren_Nederl/dbstate004

When voor persoon 992387401 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Scenario: 3b. Administratieve handeling van type Gegevens einde relatie corrigeren Nederland
          LT: CGPS04C10T30
          CGPS04C10T30 - Gegevens einde relatie corrigeren Nederland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T30/Gegevens_einde_relatie_corrigeren_Nederl/dbstate005
When voor persoon 992387401 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 4a. Persoonsbeeld Init vulling voor Gegevens einde relatie corrigeren buitenland
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T40/Gegevens_einde_relatie_corrigeren_einde_/dbstate004

When voor persoon 236312601 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Scenario: 4b. Administratieve handeling van type Gegevens einde relatie corrigeren buitenland
          LT: CGPS04C10T40
          CGPS04C10T40 - Gegevens einde relatie corrigeren buitenland

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T40/Gegevens_einde_relatie_corrigeren_einde_/dbstate005
When voor persoon 236312601 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 5a. Persoonsbeeld Init vulling voor Het laten herleven van een beëindigd geregistreerd partnerschap
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T50/Het_laten_herleven_van_een_Einde_Geregis/dbstate004

When voor persoon 902502281 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken


Scenario: 5b. Administratieve handeling van type Het laten herleven van een beëindigd geregistreerd partnerschap
          LT: CGPS04C10T50
          CGPS04C10T50 - Het laten herleven van een beëindigd geregistreerd partnerschap

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T50/Het_laten_herleven_van_een_Einde_Geregis/dbstate005
When voor persoon 902502281 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6a. Persoonsbeeld Init vulling voor De registergemeente van het GP is de bijhoudingspartij
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T60/De_bijhoudingsgemeente_van_1_betrokken_p/dbstate003

When voor persoon 300190761 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken


Scenario: 6b. Administratieve handeling van type De registergemeente van het GP is de bijhoudingspartij
          LT: CGPS04C10T60
          CGPS04C10T60 - De registergemeente van het GP is de bijhoudingspartij

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T60/De_bijhoudingsgemeente_van_1_betrokken_p/dbstate004
When voor persoon 300190761 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 7a. Persoonsbeeld Init vulling voor IST-gegevens worden verwijderd bij Correctie GP
Given leveringsautorisatie uit  autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T70/IST-gegevens_worden_verwijderd_na_een_Co/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|342729081

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7b. Administratieve handeling van type IST-gegevens worden verwijderd bij Correctie GP
          LT: CGPS04C10T70
          CGPS04C10T70 - IST-gegevens worden verwijderd bij Correctie GP

Given leveringsautorisatie uit autorisatie/autorisatiealles, autorisatie/Attendering_Mutatielevering_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:CGPS04C10T70/IST-gegevens_worden_verwijderd_na_een_Co/dbstate003
When voor persoon 342729081 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav de correctie
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

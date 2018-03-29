Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 7 (Inschrijving) mutatieberichten

Scenario: 01 DELTAVERS07C10T10	Geen verschil (behalve CAT07 versie en timestamp opgehoogd)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T10_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/01.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS07C10T30	Verificatie (element 71.10 en 71.20)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T30_xls
When voor persoon 556172425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS07C10T40	Van verstrekkingsbeperking naar geen verstrekkingsbeperking
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T40_xls
When voor persoon 944918281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS07C10T50	Wijziging in PK meegeconverteerd
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T50_xls
When voor persoon 944918281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS07C10T60	Wijziging in gemeente PK
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T60_xls
When voor persoon 944918281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/05.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- In de onderstande scenario hoort de bijhouding eigenlijk actueel te zijn (mutatiebericht). Dit is a known issue bij migratie)

Scenario: 06 DELTAVERS07C10T70	Opschorten met reden M
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T70_xls
When voor persoon 944918281 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAVERS07C10T80	Geen verstrekkingsbeperking naar verstrekkingsbeperking
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T80_xls
When voor persoon 944918281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie07/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAVERS07C10T20	Geen verstrekkingsbeperking naar verstrekkingsbeperking
             LT:

!-- Geen mutatie bericht omdat enkel het voorkomen van AfgeleidAdministratief wijzigd

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS07/DELTAVERS07C10T20_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

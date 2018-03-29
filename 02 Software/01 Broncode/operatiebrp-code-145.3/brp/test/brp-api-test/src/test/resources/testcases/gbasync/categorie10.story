Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative:
Geconverteerde datatest DELTAVERS10


Scenario: 01 DELTAVERS10/DELTAVERS10C10T10_xls  
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS10/DELTAVERS10C10T10_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie10/scenario01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS10/DELTAVERS10C10T20_xls  
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS10/DELTAVERS10C10T20_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie10/scenario02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS10/DELTAVERS10C10T40_xls  
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS10/DELTAVERS10C10T40_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie10/scenario03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS10/DELTAVERS10C10T60_xls  
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS10/DELTAVERS10C10T60_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie10/scenario04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS10/DELTAVERS10C10T70_xls  
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS10/DELTAVERS10C10T70_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie10/scenario05.xml voor expressie //brp:lvg_synVerwerkPersoon

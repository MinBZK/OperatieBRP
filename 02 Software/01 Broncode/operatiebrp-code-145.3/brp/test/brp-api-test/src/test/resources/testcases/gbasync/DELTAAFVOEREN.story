Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative:
Geconverteerde datatest DELTAAFVOEREN


Scenario: 01 DELTAAFVOEREN/DELTAAFVOERENC10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T100_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAAFVOEREN/DELTAAFVOERENC10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T10_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAAFVOEREN/DELTAAFVOERENC10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T20_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAAFVOEREN/DELTAAFVOERENC10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T30_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAAFVOEREN/DELTAAFVOERENC10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T40_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAAFVOEREN/DELTAAFVOERENC10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T50_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAAFVOEREN/DELTAAFVOERENC10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T60_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAAFVOEREN/DELTAAFVOERENC10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T70_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAAFVOEREN/DELTAAFVOERENC10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T80_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAAFVOEREN/DELTAAFVOERENC10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAAFVOEREN/DELTAAFVOERENC10T90_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAAFVOEREN/scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

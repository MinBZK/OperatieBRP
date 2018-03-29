Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative:
Geconverteerde datatest DELTACOMBO


Scenario: 01 DELTACOMBO/DELTACOMBOC10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T170_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTACOMBO/DELTACOMBOC10T180_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T180_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTACOMBO/DELTACOMBOC10T190_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T190_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTACOMBO/DELTACOMBOC10T200_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T200_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTACOMBO/DELTACOMBOC10T210_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T210_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTACOMBO/DELTACOMBOC10T220_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T220_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTACOMBO/DELTACOMBOC10T230_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T230_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTACOMBO/DELTACOMBOC10T240_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T240_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTACOMBO/DELTACOMBOC10T250_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T250_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTACOMBO/DELTACOMBOC10T260_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T260_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTACOMBO/DELTACOMBOC10T270_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T270_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTACOMBO/DELTACOMBOC10T280_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T280_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTACOMBO/DELTACOMBOC10T290_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T290_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTACOMBO/DELTACOMBOC10T300_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T300_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTACOMBO/DELTACOMBOC10T310_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T310_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTACOMBO/DELTACOMBOC10T320_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTACOMBO/DELTACOMBOC10T320_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTACOMBO/scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative:
Geconverteerde datatest Vb-Casussen

Scenario: 01 Vb-CASUSSEN/POCHIS02C10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T100_xls
When voor persoon 334118025 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 Vb-CASUSSEN/POCHIS02C10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T10_xls
When voor persoon 725819273 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 Vb-CASUSSEN/POCHIS02C10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T110_xls
When voor persoon 924754953 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 Vb-CASUSSEN/POCHIS02C10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T120_xls
When voor persoon 296049529 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 Vb-CASUSSEN/POCHIS02C10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T130_xls
When voor persoon 903214921 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 Vb-CASUSSEN/POCHIS02C10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T140_xls
When voor persoon 782230921 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 Vb-CASUSSEN/POCHIS02C10T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T150_xls
When voor persoon 663209481 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 Vb-CASUSSEN/POCHIS02C10T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T160_xls
When voor persoon 355587865 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 Vb-CASUSSEN/POCHIS02C10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T170_xls
When voor persoon 312961881 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 Vb-CASUSSEN/POCHIS02C10T180_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T180_xls
When voor persoon 712563337 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 Vb-CASUSSEN/POCHIS02C10T190_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T190_xls
When voor persoon 494442281 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 Vb-CASUSSEN/POCHIS02C10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T20_xls
When voor persoon 286813129 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 Vb-CASUSSEN/POCHIS02C10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T50_xls
When voor persoon 623801577 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon
Then is er voor xpath //brp:onderzoek[@brp:verwerkingssoort="Referentie"] een node aanwezig in het levering bericht
Then is er voor xpath //brp:onderzoek[@brp:verwerkingssoort="Toevoeging"] een node aanwezig in het levering bericht


Scenario: 14 Vb-CASUSSEN/POCHIS02C10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T60_xls
When voor persoon 143337105 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 Vb-CASUSSEN/POCHIS02C10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T70_xls
When voor persoon 103805849 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 Vb-CASUSSEN/POCHIS02C10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T80_xls
When voor persoon 308831433 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 Vb-CASUSSEN/POCHIS02C10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:Vb-CASUSSEN/POCHIS02C10T90_xls
When voor persoon 562963017 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/Vb-CASUSSEN/scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

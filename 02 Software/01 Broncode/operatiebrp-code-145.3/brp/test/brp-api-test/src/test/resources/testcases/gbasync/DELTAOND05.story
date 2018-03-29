Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Scenario: 01 DELTAOND05C10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T100_xls
When voor persoon 269914201 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAOND05C10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T10_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAOND05C10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T110_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAOND05C10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T120_xls
When voor persoon 269914201 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAOND05C10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T130_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAOND05C10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T140_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAOND05C10T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T150_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAOND05C10T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T160_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAOND05C10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T170_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAOND05C10T180_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T180_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAOND05C10T190_xls

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T190_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon
Then is er voor xpath //brp:onderzoek[@brp:verwerkingssoort="Verval"] een node aanwezig in het levering bericht
Then is er voor xpath //brp:onderzoek[@brp:verwerkingssoort="Toevoeging"] een node aanwezig in het levering bericht

Scenario: 12 DELTAOND05C10T200_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T200_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND05/Scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAOND05C10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T20_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAOND05C10T210_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T210_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAOND05C10T220_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T220_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAOND05C10T230_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T230_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND05/Scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAOND05C10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T30_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAOND05C10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T40_xls
When voor persoon 269914201 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAOND05C10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T50_xls
When voor persoon 269914201 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAOND05C10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T60_xls
When voor persoon 269914201 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21 DELTAOND05C10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T70_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22 DELTAOND05C10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T80_xls
When voor persoon 269914201 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23 DELTAOND05C10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND05/DELTAOND05C10T90_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND05/Scenario23.xml voor expressie //brp:lvg_synVerwerkPersoon

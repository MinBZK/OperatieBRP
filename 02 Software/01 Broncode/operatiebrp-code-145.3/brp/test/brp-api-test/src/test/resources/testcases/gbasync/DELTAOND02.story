Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Scenario: 01 DELTAOND02C10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T100_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAOND02C10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T10_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAOND02C10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T110_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAOND02C10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T120_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAOND02C10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T130_xls
When voor persoon 747010377 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAOND02C10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T140_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAOND02C10T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T150_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAOND02C10T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T160_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAOND02C10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T170_xls
When voor persoon 175566793 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND02/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAOND02C10T180_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T180_xls
When voor persoon 175566793 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAOND02C10T190_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T190_xls
When voor persoon 175566793 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon
Then is er voor xpath //brp:onderzoek[@brp:verwerkingssoort="Verval"] een node aanwezig in het levering bericht
Then is er voor xpath //brp:onderzoek[@brp:verwerkingssoort="Toevoeging"] een node aanwezig in het levering bericht

Scenario: 12 DELTAOND02C10T200_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T200_xls
When voor persoon 747010377 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon
Then heeft het bericht 4 groepen 'onderzoek'

Scenario: 13 DELTAOND02C10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T20_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAOND02C10T210_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T210_xls
When voor persoon 111721155 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAOND02C10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T30_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAOND02C10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T40_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAOND02C10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T50_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAOND02C10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T60_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAOND02C10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T70_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAOND02C10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T80_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21 DELTAOND02C10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C10T90_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22 DELTAOND02C20T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T100_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23 DELTAOND02C20T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T10_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24 DELTAOND02C20T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T110_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario24.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 25 DELTAOND02C20T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T120_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario25.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 26 DELTAOND02C20T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T130_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario26.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 27 DELTAOND02C20T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T140_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario27.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 28 DELTAOND02C20T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T150_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario28.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 29 DELTAOND02C20T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T160_xls
When voor persoon 747723977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario29.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 30 DELTAOND02C20T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T20_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario30.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 31 DELTAOND02C20T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T30_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario31.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 32 DELTAOND02C20T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T40_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario32.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 33 DELTAOND02C20T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T50_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario33.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 34 DELTAOND02C20T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T60_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario34.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 35 DELTAOND02C20T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T70_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario35.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 36 DELTAOND02C20T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T80_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario36.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 37 DELTAOND02C20T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND02/DELTAOND02C20T90_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND02/Scenario37.xml voor expressie //brp:lvg_synVerwerkPersoon

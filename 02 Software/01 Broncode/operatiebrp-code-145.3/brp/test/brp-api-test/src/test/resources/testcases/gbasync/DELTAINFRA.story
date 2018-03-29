Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative:
Geconverteerde datatest DELTAINFRA

Scenario: 01 DELTAINFRA/DELTAINFRAC10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T100_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAINFRA/DELTAINFRAC10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T10_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAINFRA/DELTAINFRAC10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T110_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAINFRA/DELTAINFRAC10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T120_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAINFRA/DELTAINFRAC10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T130_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAINFRA/DELTAINFRAC10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T140_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAINFRA/DELTAINFRAC10T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T150_xls
When voor persoon 699092553 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAINFRA/DELTAINFRAC10T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T160_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAINFRA/DELTAINFRAC10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T170_xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAINFRA/DELTAINFRAC10T180_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T180_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAINFRA/DELTAINFRAC10T190_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T190_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAINFRA/DELTAINFRAC10T200_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T200_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAINFRA/DELTAINFRAC10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T20_xls
When voor persoon 316925913 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAINFRA/DELTAINFRAC10T210_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T210_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAINFRA/DELTAINFRAC10T220_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T220_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAINFRA/DELTAINFRAC10T230_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T230_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAINFRA/DELTAINFRAC10T240_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T240_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAINFRA/DELTAINFRAC10T250_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T250_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAINFRA/DELTAINFRAC10T260_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T260_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAINFRA/DELTAINFRAC10T270_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T270_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21 DELTAINFRA/DELTAINFRAC10T280_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T280_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22 DELTAINFRA/DELTAINFRAC10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T30_xls
When voor persoon 792006537 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23 DELTAINFRA/DELTAINFRAC10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T40_xls
When voor persoon 893570953 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24 DELTAINFRA/DELTAINFRAC10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T50_xls
When voor persoon 208181921 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario24.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 25 DELTAINFRA/DELTAINFRAC10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T60_xls
When voor persoon 789206729 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario25.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 26 DELTAINFRA/DELTAINFRAC10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T70_xls
When voor persoon 328279705 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario26.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 27 DELTAINFRA/DELTAINFRAC10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T80_xls
When voor persoon 328279705 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario27.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 28 DELTAINFRA/DELTAINFRAC10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC10T90_xls
When voor persoon 971683657 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario28.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 29 DELTAINFRA/DELTAINFRAC20T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T10_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario29.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 30 DELTAINFRA/DELTAINFRAC20T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T20_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario30.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 31 DELTAINFRA/DELTAINFRAC20T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T30_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario31.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 32 DELTAINFRA/DELTAINFRAC20T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T40_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario32.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 33 DELTAINFRA/DELTAINFRAC20T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T50_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario33.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 34 DELTAINFRA/DELTAINFRAC20T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T60_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario34.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 35 DELTAINFRA/DELTAINFRAC20T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T70_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario35.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 36 DELTAINFRA/DELTAINFRAC20T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T80_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario36.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 37 DELTAINFRA/DELTAINFRAC20T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC20T90_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario37.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 38 DELTAINFRA/DELTAINFRAC30T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T100_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario38.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 39 DELTAINFRA/DELTAINFRAC30T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T10_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario39.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 40 DELTAINFRA/DELTAINFRAC30T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T20_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario40.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 41 DELTAINFRA/DELTAINFRAC30T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T30_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario41.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 42 DELTAINFRA/DELTAINFRAC30T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T40_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario42.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 43 DELTAINFRA/DELTAINFRAC30T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T50_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario43.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 44 DELTAINFRA/DELTAINFRAC30T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T60_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario44.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 45 DELTAINFRA/DELTAINFRAC30T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T70_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario45.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 46 DELTAINFRA/DELTAINFRAC30T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T80_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario46.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 47 DELTAINFRA/DELTAINFRAC30T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAINFRA/DELTAINFRAC30T90_xls
When voor persoon 903802569 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAINFRA/scenario47.xml voor expressie //brp:lvg_synVerwerkPersoon

Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Scenario: 01 DELTAOND11C10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T100_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAOND11C10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T10_xls
When voor persoon 949260617 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAOND11C10T110_xls
             LT:
Meta:
@status Bug
!-- ROOD-373
!-- test faalt vanwege sortering van onderzoeken.

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T110_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAOND11C10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T120_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAOND11C10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T130_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAOND11C10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T140_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAOND11C10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T20_xls
When voor persoon 803868297 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAOND11C10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T30_xls
When voor persoon 113233814 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAOND11C10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T40_xls
When voor persoon 576560169 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAOND11C10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T50_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAOND11C10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T60_xls
When voor persoon 446029865 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAOND11C10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T70_xls
When voor persoon 116047719 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAOND11C10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T80_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAOND11C10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND11/DELTAOND11C10T90_xls
When voor persoon 232632881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND11/14.xml voor expressie //brp:lvg_synVerwerkPersoon
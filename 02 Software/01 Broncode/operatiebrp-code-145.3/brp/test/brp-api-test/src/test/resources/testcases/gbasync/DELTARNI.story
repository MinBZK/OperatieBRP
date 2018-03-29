Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Scenario: 01 DELTARNIC10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T100_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTARNIC10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T10_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTARNIC10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T110_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTARNIC10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T120_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTARNIC10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTARNIC10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T140_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTARNIC10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T20_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTARNIC10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T30_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTARNIC10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T40_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTARNIC10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T50_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTARNIC10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T60_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTARNIC10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T70_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTARNIC10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T80_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTARNIC10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T90_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTARNI/14.xml voor expressie //brp:lvg_synVerwerkPersoon
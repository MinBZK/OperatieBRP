Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 12 (Reisdocument) mutatieberichten

Scenario: 01 DELTAVERS12C10T10	Delta in CAT12 Reisdocument inhoudelijke wijziging
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C10T10_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS12C10T20	Delta in CAT12 Reisdocument verwijderen van PL
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C10T20_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS12C10T30	Delta in CAT12 Reisdocument toevoegen en bestaande wijzigen
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C10T30_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS12C10T40	Delta in CAT12 Reisdocument verwijderen en nieuwe toevoegen
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C10T40_xls
When voor persoon 126685575 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Nieuw

Scenario: 05. DELTAVERS12C20T10
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T10_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06. DELTAVERS12C20T20
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T20_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07. DELTAVERS12C20T30
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T30_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08. DELTAVERS12C20T40
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T40_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09. DELTAVERS12C20T50
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T50_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10. DELTAVERS12C20T60
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T60_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. DELTAVERS12C20T70
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T70_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12. DELTAVERS12C20T80
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T80_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13. DELTAVERS12C20T90
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T90_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. DELTAVERS12C20T120
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T120_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16. DELTAVERS12C20T130
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T130_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17. DELTAVERS12C20T140
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T140_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18. DELTAVERS12C20T150
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T150_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19. DELTAVERS12C20T160
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T160_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20. DELTAVERS12C20T170
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS12/DELTAVERS12C20T170_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie12/20.xml voor expressie //brp:lvg_synVerwerkPersoon

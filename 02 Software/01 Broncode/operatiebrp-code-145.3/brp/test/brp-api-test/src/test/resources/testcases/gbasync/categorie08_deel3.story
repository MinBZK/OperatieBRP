Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat8

Narrative: Leveren categorie 8 (verblijfplaats) mutatieberichten

Scenario: 10 DELTAVERS08C80T10e
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10e_xls

!-- When voor persoon 270433417 wordt de 4 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 270433417 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAVERS08C80T10f
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10f_xls

!-- When voor persoon 270433417 wordt de 5 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 270433417 wordt de 4 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 270433417 wordt de laatste handeling geleverd
When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAVERS08C80T10g
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10g_xls

!-- When voor persoon 270433417 wordt de 6 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 270433417 wordt de 5 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 4 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 270433417 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13. DELTAVERS08C90T10 Correctie adres met bestaande immigratie groep
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C90T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Nieuw

Scenario: 14. DELTAVERS08C100T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C100T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. DELTAVERS08C110T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C110T10_xls
When voor persoon 427389033 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16. DELTAVERS08C110T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C110T20_xls
When voor persoon 427389033 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17. DELTAVERS08C110T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C110T30_xls
When voor persoon 427389033 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18. DELTAVERS08C110T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C110T40_xls
When voor persoon 427389033 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19. DELTAVERS08C120T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C120T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20. DELTAVERS08C120T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C120T20_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21. DELTAVERS08C120T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C120T30_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22. DELTAVERS08C120T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C120T40_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel3/22.xml voor expressie //brp:lvg_synVerwerkPersoon

Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat8

Narrative: Leveren categorie 8 (verblijfplaats) mutatieberichten

Scenario: 01 DELTAVERS08C70T10 Bestaande immigratie daarna verhuizen binnen NL C110T20
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C70T10_xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS08C70T20 Functie Adres van W naar B C110T30
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C70T20_xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 03 DELTAVERS08C70T30 Toevoegen extra CAT58 (extra adres) C110T40
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C70T30_xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 04 DELTAVERS08C70T40 1b. punt adres naar adres C110T80
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C70T40_xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 05 DELTAVERS08C70T50 adres naar punt adres C110T90
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C70T50_xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 06 DELTAVERS08C80T10a
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10a_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 07 DELTAVERS08C80T10b
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10b_xls

!-- When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

When voor persoon 270433417 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 08 DELTAVERS08C80T10c
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10c_xls

!-- When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

!-- When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 270433417 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 09 DELTAVERS08C80T10d
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10d_xls

!-- When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

!-- When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken

!-- When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
!-- When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 270433417 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel2/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


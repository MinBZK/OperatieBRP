Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 2 (ouder1) mutatieberichten

Scenario: 01.DELTAVERS02C10T10 CAT02 Wijzigen Puntouder naar Ouder1
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02. DELTAVERS02C10T100 CAT03 Juridisch geen ouder wijzigen naar Ouder2
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T100_xls
When voor persoon 930663433 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03. DELTAVERS02C10T110 CAT02 Ouder1 wijzigen naar Juridisch geen ouder
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T110_xls
When voor persoon 747723977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04. DELTAVERS02C10T120 CAT03 Ouder2 wijzigen naar Juridisch geen ouder
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T120_xls
When voor persoon 918521609 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05. DELTAVERS02C10T130 CAT02 Juridisch geen ouder wijzigen naar Puntouder1
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T130_xls
When voor persoon 754692553 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06. DELTAVERS02C10T140 CAT03 Juridisch geen ouder wijzigen naar Puntouder2
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T140_xls
When voor persoon 549991049 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07. DELTAVERS02C10T150 Precies dezelfde PL
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T150_xls
When voor persoon 239723417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08. DELTAVERS02C10T160 CAT02 Ouder1 wijzigen naar RNI
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T160_xls
When voor persoon 556392681 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09. /DELTAVERS02/DELTAVERS02C10T160a.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T160a_xls
When voor persoon 780262281 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10. DELTAVERS02C10T170 CAT03 Beide Ouders wijzigen naar RNI
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T170_xls
When voor persoon 238278025 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. DELTAVERS02C10T170 CAT03 Beide Ouders wijzigen naar RNI (a)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T170_xls
When voor persoon 238278025 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/11.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- bestand DELTAVERS02C10T180.xls bestaat niet. Scenario verwijderd

Scenario: 13. DELTAVERS02C10T190 CAT02 wijzigen gewone Ouder naar Adoptieouder naar Juridisch geen ouder
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T190_xls
When voor persoon 238029657 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14. DELTAVERS02C10T20	CAT02 Wijzigen Ouder1
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T20_xls
When voor persoon 234988137 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. DELTAVERS02C10T200 CAT02 wijzigen gewone Ouder naar Adoptieouder + voornaamwijziging naar Juridisch geen ouder
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T200_xls
When voor persoon 203767433 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken


Scenario: 16. DELTAVERS02C10T210 CAT02 wijzigen gewone Ouder naar Adoptieouder + voornaamwijziging
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T210_xls
When voor persoon 840901641 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17. DELTAVERS02C10T220 CAT02 wijzigen gewone Ouder naar Adoptieouder naar nieuwe Adoptieouder + voornaamwijziging
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T220_xls
When voor persoon 752889801 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18. DELTAVERS02C10T230 CAT02 wijzigen gewone Ouder naar Adoptieouder naar nieuwe Adoptieouder
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T230_xls
When voor persoon 747010377 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19. DELTAVERS02C10T30	CAT03 Wijzigen Ouder2
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T30_xls
When voor persoon 865243657 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20. DELTAVERS02C10T40	CAT03 Correctie Ouder2
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T40_xls
When voor persoon 279574873 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21. DELTAVERS02C10T50	CAT02 Wijzigen Ouder1 (WALG) CAT02/52 wijzigen naar CAT02
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T50_xls
When voor persoon 621713065 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22. DELTAVERS02C10T60	CAT02 wijzigen gewone Ouder naar Adoptieouder
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T60_xls
When voor persoon 175566793 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23. DELTAVERS02C10T70	CAT02 RNI wijzigen naar Ouder1
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T70_xls
When voor persoon 801309001 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/23.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- bestand DELTAVERS02C10T80.xls bestaat niet. Scenario verwijderd

Scenario: 25. DELTAVERS02C10T90	CAT02 Juridisch geen ouder wijzigen naar Ouder1
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T90_xls
When voor persoon 175991017 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/25.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 26. DELTAVERS02C20T20
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C20T20_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/26.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Nieuw

Scenario: 27. DELTAVERS02C20T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C20T10_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/27.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 28. DELTAVERS02C20T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C20T30_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/28.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 29. DELTAVERS02C10T240_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T240_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie02/29.xml voor expressie //brp:lvg_synVerwerkPersoon


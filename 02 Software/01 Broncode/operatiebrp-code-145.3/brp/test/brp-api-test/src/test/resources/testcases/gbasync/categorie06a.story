Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 6 (Overlijden) mutatieberichten

Scenario: 01 DELTAVERS06C10T10	Registratie Overlijden zonder opschortreden O
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC10T10_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS06C10T20	Registratie Overlijden met opschortreden O
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC10T20_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS06C10T30	Registratie Overlijden met opschortreden F
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC10T30_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS06C10T40	Registratie Overlijden na eerdere onterechte registratie Overlijden
             LT:
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC10T40_xls
When voor persoon 249342777 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS06C20T10	Wijziging registratie Overlijden (zonder Onjuist = registratie niet conform regels)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC20T10_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAVERS06C20T20	Wijziging registratie Overlijden met opschortreden O (zonder Onjuist = registratie niet conform regels)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC20T20_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAVERS06C30T10	Onterechte registratie Overlijden
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC30T10_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAVERS06C30T20 Correcte registratie Overlijden (zonder Onjuist)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC30T20_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAVERS06C30T30	Correctie van 2 registraties Overlijden (oudste rij was niet onjuist gemaakt)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC30T30_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAVERS06C30T40	Correctie van 2 registraties Overlijden (oudste rijen waren wel onjuist gemaakt)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC30T40_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAVERS06C30T50 Correctie registratie Overlijden met opschortreden O
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC30T50_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAVERS06C40T10	DW-013 - Correctie in de historie waardoor rij vervalt
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC40T10_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAVERS06C50T10	Correctie ingangsdatum registratie overlijden
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC50T10_xls
When voor persoon 160207125 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAVERS06C50T20	2 rijen toevoegen (Registratie overlijden en correctie op dezelfde dag)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC50T20_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAVERS06C60T10	Registratie Overlijden verwijderd (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC60T10_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAVERS06C60T20	Registratie en correctie Overlijden verwijderd (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC60T20_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAVERS06C60T30	Registratie Overlijden na eerdere onterechte registratie Overlijden
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC60T30_xls
When voor persoon 249342777 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAVERS06C60T40	Registratie Overlijden gecorrigeerd (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC60T40_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie06/18.xml voor expressie //brp:lvg_synVerwerkPersoon
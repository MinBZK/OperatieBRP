Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat1

Narrative: Leveren categorie 1 (Persoon) mutatieberichten

Scenario: 01. DELTAVERS01aC20T140 - DW-022 Correctie voornaam
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T140_xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02. DELTAVERS01aC20T150 - DW-012 Correctie naamgebruik
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T150_xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03. DELTAVERS01aC20T160 - DW-023 Correctie voornaam na actualisering (3-PL'en en zelfde DEG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T160_xls
!-- When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04. DELTAVERS01aC20T160b
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T160b_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05. DELTAVERS01aC20T170 - DW-023 Correctie voornaam na actualisering (3-PL'en en verschillende DEG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T170_xls
!-- When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06. DELTAVERS01aC20T180 - DW-021 Verwijderen voornaam (actualisering)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T180_xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07. DELTAVERS01aC20T190 - DW-025 (n.v.t.) Verwijderen voornaam correctie (uiteindelijk DW-022)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T190_xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08. DELTAVERS01aC20T200 - DW-025 Verwijderen nummerverwijzing correctie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T200_xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09. DELTAVERS01aC20T210 - Correctie in de historie (DW-013) maar wodt getriggerd door DW-003
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T210_xls
When voor persoon 286813129 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10. DELTAVERS01aC20T220 - DW-021 Wijziging naamgegevens (voornamen) persvoornaam
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T220_xls
!-- When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. DELTAVERS01aC20T230 - DW-023 Correctie voornaam in historie na actualisering
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T230_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12. DELTAVERS01aC30T10 - Actualisering naam op dezefde dag
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC30T10_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: DELTAVERS01aC20T260
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T260_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/DELTAVERS01aC20T260m.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel2/DELTAVERS01aC20T260v.xml voor expressie //brp:lvg_synVerwerkPersoon


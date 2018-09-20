Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 aapos
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat1
@componenten            database,routeringcentrale,mutatielevering

Narrative: Leveren categorie 1 (Persoon) mutatieberichten
Given het abonnement /testdata/abonnement.sql

Scenario: 01. DELTAVERS01aC20T140 - DW-022 Correctie voornaam
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T140.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 02. DELTAVERS01aC20T150 - DW-012 Correctie naamgebruik
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T150.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 03. DELTAVERS01aC20T160 - DW-023 Correctie voornaam na actualisering (3-PL'en en zelfde DEG)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T160.xls
When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 04. DELTAVERS01aC20T160b
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T160b.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 05. DELTAVERS01aC20T170 - DW-023 Correctie voornaam na actualisering (3-PL'en en verschillende DEG)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T170.xls
When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 06. DELTAVERS01aC20T180 - DW-021 Verwijderen voornaam (actualisering)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T180.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 07. DELTAVERS01aC20T190 - DW-025 (n.v.t.) Verwijderen voornaam correctie (uiteindelijk DW-022)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T190.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 08. DELTAVERS01aC20T200 - DW-025 Verwijderen nummerverwijzing correctie
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T200.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 09. DELTAVERS01aC20T210 - Correctie in de historie (DW-013) maar wodt getriggerd door DW-003
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T210.xls
When voor persoon 286813129 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 10. DELTAVERS01aC20T220 - DW-021 Wijziging naamgegevens (voornamen) persvoornaam
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T220.xls
When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 11. DELTAVERS01aC20T230 - DW-023 Correctie voornaam in historie na actualisering
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T230.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 12. DELTAVERS01aC30T10 - Actualisering naam op dezefde dag
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC30T10.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T260
Meta:
@regels     ter_beoordeling

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T260.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given verzoek van type geefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 854820425
| stuurgegevens.zendendePartij              | 034401
| parameters.abonnementNaam                 | testabo0

When het bericht wordt verstuurd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

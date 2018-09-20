Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 aapos
@jiraIssue              TEAMBRP-3335
@status                 Onderhanden
@sleutelwoorden         GeconverteerdeDataTest
@componenten            database,routeringcentrale,mutatielevering

Narrative: Leveren categorie 1 mutatieberichten

Given het abonnement /testdata/abonnement.sql

Scenario: DELTAVERS01aC10T10 - DW-021 Wijziging naamgegevens (voornamen) persvoornaam
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T10.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T20 - DW-021 Wijziging naamgegevens (Adellijke titel/predikaat) perssamengesteldenaam
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T20.xls
When voor persoon 822062793 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T30 - DW-021 Wijziging naamgegevens (voorvoegsel geslachtsnaam) perssamengesteldenaam
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T30.xls
When voor persoon 211986689 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T40 - DW-021 Wijziging naamgegevens (geslachtsnaam) perssamengesteldenaam
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T40.xls
When voor persoon 860709577 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T50 - DW-021 Wijziging geslacht
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T50.xls
When voor persoon 495922985 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T60 - DW-011 Wijziging naamgebruik persnaamgebruik
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T60.xls
When voor persoon 401321897 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T70 - Wijziging naamgegevens, geslacht, naamgebruik en document
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T70.xls
When voor persoon 617045641 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T90 - Wijziging scheidingsteken
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T90.xls
When voor persoon 319343017 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC10T100 - DW-011 Wijziging geboorte
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T100.xls
When voor persoon 319343017 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T50
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50.xls
When voor persoon 833478217 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T50a - 1a. Wijziging naamgegevens , geldigheid, verantwoording (WALG)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50a.xls
When voor persoon 213581097 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T50b
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50b.xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T50c
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50c.xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T50d
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50d.xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T140 - DW-022 Correctie voornaam
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T140.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T150 - DW-012 Correctie naamgebruik
Meta:
@regels     xsd_fout
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T150.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T160 - DW-023 Correctie voornaam na actualisering (3-PL'en en zelfde DEG)
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T160.xls
When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T160b
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T160b.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T170 - DW-023 Correctie voornaam na actualisering (3-PL'en en verschillende DEG)
Meta:
@regels     geenlev_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T170.xls
When voor persoon 854820425 wordt de 1 na laatste handeling geleverd
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T180 - DW-021 Verwijderen voornaam (actualisering)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T180.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T190 - DW-025 (n.v.t.) Verwijderen voornaam correctie (uiteindelijk DW-022)
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T190.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T200 - DW-025 Verwijderen nummerverwijzing correctie
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T200.xls
When voor persoon 254534521 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T210 - Correctie in de historie (DW-013) maar wodt getriggerd door DW-003
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T210.xls
When voor persoon 286813129 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T220 - DW-021 Wijziging naamgegevens (voornamen) persvoornaam
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T220.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T230 - DW-023 Correctie voornaam in historie na actualisering
Meta:
@regels     xsd_fout

Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T230.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC30T10 - Actualisering naam op dezefde dag
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC30T10.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
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

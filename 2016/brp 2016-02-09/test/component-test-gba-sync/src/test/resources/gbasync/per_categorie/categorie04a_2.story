Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 luwid
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat4
@componenten            database,routeringcentrale,mutatielevering


Narrative: Leveren categorie 4 (nationaliteit) mutatieberichten
Given het abonnement /testdata/abonnement.sql

Scenario: DELTAVERS04aC20T10 Correctie verkrijging NL nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC20T10.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC20T20 Correctie verkrijging vreemde nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC20T20.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC20T30 Correctie verkrijging NL nationaliteit (nationaliteit eerder correct verkregen en beeindigd)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC20T30.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC30T10 Correctie beeindiging NL nationaliteit (Verlies gelijk, ingangsdatum later)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC30T10.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC30T20 Correctie beeindiging vreemde nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC30T20.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC30T30 Correctie beeindiging vreemde nationaliteit (continuering vreemde nationaliteit)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC30T30.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC30T40 Correctie beeindiging NL nationaliteit (Verlies gelijk, ingangsdatum eerder)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC30T40.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC50T10 Onterecht geregistreerde nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC50T10.xls
When voor persoon 954911945 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC50T20 Onterecht geregistreerde nationaliteit na eerdere onterechte registratie
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC50T20.xls
When voor persoon 954911945 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC50T30 Vervallen nationaliteit met in historie 2x vervallen nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC50T30.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

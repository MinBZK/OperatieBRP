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
Scenario: DELTAVERS04aC80T70 PROBAS verwijderd (zonder aanmaak historie, zonder groep 82)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T70.xls
When voor persoon 434328297 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10a Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10a.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10b Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10b.xls
When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 963363529 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10c Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10c.xls
When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10d Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10d.xls
When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10e Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10e.xls
When voor persoon 963363529 wordt de 4 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10f Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10f.xls
When voor persoon 963363529 wordt de 5 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 963363529 wordt de 4 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC90T10g Verschillende mutaties mbt nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC90T10g.xls
When voor persoon 963363529 wordt de 5 na laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 963363529 wordt de 4 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

When voor persoon 963363529 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor abonnement testabo0 wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC120T10 Actualisering reden verkrijging op dezelfde dag
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC120T10.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

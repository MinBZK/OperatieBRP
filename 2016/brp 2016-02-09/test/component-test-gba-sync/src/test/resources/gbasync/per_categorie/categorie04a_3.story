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
Scenario: DELTAVERS04aC60T10 Correctie beeindiging NL nationaliteit (Verlies aangepast, ingangsdatum ongelijk)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T10.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC60T20 Correctie beeindiging NL nationaliteit (Verlies aangepast, ingangsdatum gelijk)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T20.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC60T30 Verkrijging NL nationaliteit + Verkrijging vreemde nationaliteit (na eerdere verkrijging en beeindiging)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T30.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC60T40 Correctie (in historie) verkrijging NL na beeindiging (latere verkrijging)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T40.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC60T50 Correctie (in historie) verkrijging NL na beeindiging (eerdere verkrijging)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T50.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC60T60 Nationaliteit wijziging in adm gegevens zonder historie (WALG)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T60.xls
When voor persoon 409753257 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC60T70 Correctie verlies met verkrijging en (latere) verlies
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC60T70.xls
When voor persoon 528618313 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC70T10 Correctie van verkrijging NL na eerdere verkrijging NL (zonder verlies) DW-
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T10.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC70T20 Correctie van verkrijging NL na eerdere verkrijging NL (zonder verlies, nieuwe DAG)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T20.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC70T30 Correctie van verkrijging en verlies NL nationaliteit (2 lege rijen toevoegen) - DW-
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T30.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

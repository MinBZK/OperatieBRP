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

Scenario: DELTAVERS04aC10T10 Beeindiging NL nationaliteit + Verkrijging vreemde nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T10.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T20 Beeindiging vreemde nationaliteit + Verkrijging NL nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T20.xls
When voor persoon 800570121 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T30 Beeindiging onbekende nationaliteit + Verkrijging NL nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T30.xls
When voor persoon 519395529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T40 Beeindiging NL nationaliteit + Beeindiging vreemde nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T40.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T50 Beeindiging NL nationaliteit + Beeindiging vreemde nationaliteit (na eerdere verkrijging en beeindiging)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T50.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T60 Beeindiging staatloos + Verkrijging NL nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T60.xls
When voor persoon 164278989 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T70 Beeindiging bijzonder NL + Verkrijging NL nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T70.xls
When voor persoon 850275209 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T80 Beeindiging vastgesteld niet NL
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T80.xls
When voor persoon 639113801 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T90 Beeindiging NL met PROBAS indicatie (wordt in praktijk wellicht niet zo geregistreerd)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T90.xls
When voor persoon 434328297 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC10T100 Beeindiging nationaliteit met in historie 2x vervallen nationaliteit
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC10T100.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide




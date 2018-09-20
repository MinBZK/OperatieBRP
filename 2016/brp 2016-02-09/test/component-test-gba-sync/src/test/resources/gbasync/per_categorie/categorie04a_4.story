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
Scenario: DELTAVERS04aC70T40 Correctie van verkrijging en verlies NL nationaliteit (1 lege rij toevoegen) - DW-
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T40.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC70T50 Correctie in actueel en historie (2x lege rij)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T50.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC70T60 Correctie in actueel en historie (lege rij) (variatie op 04aC70T70)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T60.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC70T70 Correctie in actueel en historie (gevulde rij) (variatie op 04aC70T60)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC70T70.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC80T10 Voornaamwijziging & beeindiging nationaliteit (geen invloed tussen verschillende groepen)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T10.xls
When voor persoon 475727241 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC80T20 Geen verschil uitsluitend datumtijdstempel en versienummer OK (niet uitgewerkt)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T20.xls
When voor persoon 475727241 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC80T30 Correctie PROBAS
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T30.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC80T40 Correctie PROBAS (historie bevat geen aanduiding PROBAS)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T40.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC80T50 WALG ingangsdatum cat 04 incl PROBAS
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T50.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS04aC80T60 PROBAS verwijderd (zonder aanmaak historie)
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS04a/DELTAVERS04aC80T60.xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor abonnement testabo0 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

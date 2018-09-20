Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 luwid
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat8

Narrative: Leveren categorie 8 (verblijfplaats) mutatieberichten

Scenario: 10 DELTAVERS08C80T10e
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10e.xls
When voor persoon 270433417 wordt de 4 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

Scenario: 11. DELTAVERS08C80T10f
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10f.xls
When voor persoon 270433417 wordt de 5 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 270433417 wordt de 4 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

Scenario: 12. DELTAVERS08C80T10g
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10g.xls
When voor persoon 270433417 wordt de 6 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 270433417 wordt de 5 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 4 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

Scenario: 13. DELTAVERS08C90T10 Correctie adres met bestaande immigratie groep
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C90T10.xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

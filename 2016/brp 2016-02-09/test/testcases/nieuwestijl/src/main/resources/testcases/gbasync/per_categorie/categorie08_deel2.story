Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 dihoe
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat8

Narrative: Leveren categorie 8 (verblijfplaats) mutatieberichten

Scenario: 01. DELTAVERS08C70T10 Bestaande immigratie daarna verhuizen binnen NL C110T20
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C70T10.xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 02. DELTAVERS08C70T20 Functie Adres van W naar B C110T30
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C70T20.xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 03. DELTAVERS08C70T30 Toevoegen extra CAT58 (extra adres) C110T40
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C70T30.xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 04. DELTAVERS08C70T40 1b. punt adres naar adres C110T80
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C70T40.xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 05. DELTAVERS08C70T50 adres naar punt adres C110T90
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C70T50.xls
When voor persoon 525082281 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 06. DELTAVERS08C80T10a
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10a.xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 07. DELTAVERS08C80T10b
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10b.xls
When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 270433417 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

Scenario: 08. DELTAVERS08C80T10c
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10c.xls
When voor persoon 270433417 wordt de 2 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 270433417 wordt de 1 na laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

When voor persoon 270433417 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide

Scenario: 09. DELTAVERS08C80T10d
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C80T10d.xls
When voor persoon 270433417 wordt de 3 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

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

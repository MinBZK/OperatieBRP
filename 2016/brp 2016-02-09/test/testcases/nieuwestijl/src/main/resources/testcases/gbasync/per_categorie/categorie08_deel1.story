Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 dihoe
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat8

Narrative: Leveren categorie 8 (verblijfplaats) mutatieberichten

Scenario: 01. DELTAVERS08C10T10 DW-021, DW-032 Intergemeentelijke adreswijziging C20T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C10T10.xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 270433417
| stuurgegevens.zendendePartij              | 034401

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 02. DELTAVERS08C20T10 DW-021, DW-032 Hervestiging door immigratie C30T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C20T10.xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 626949737
| stuurgegevens.zendendePartij              | 034401

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 03. DELTAVERS08C20T20 DW-025 Correctie immigratie
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C20T20.xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 04. DELTAVERS08C30T10 Wijziging adres binnengemeentelijk C40T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T10.xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 941099593
| stuurgegevens.zendendePartij              | 034401
| parameters.abonnementNaam                 | Geen pop.bep. levering op basis van doelbinding

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 05. DELTAVERS08C30T20 Wijziging adres buitenland C40T20
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T20.xls
When voor persoon 616902505 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 06. DELTAVERS08C30T30 Correctie op adres huisnummer C40T30
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T30.xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 07. DELTAVERS08C30T40 Correctie op adres in historie huisnummer in historie
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T40.xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 08. DELTAVERS08C30T50 Correctie op adres in historie geldigheid in historie
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T50.xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 09. DELTAVERS08C30T60
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T60.xls
When voor persoon 221217241 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 10. DELTAVERS08C30T70
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C30T70.xls
When voor persoon 383096777 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

!-- onderstaande scenario bevat a known issue bij migratie, moet eigenlijk mutatiebericht leveren.

Scenario: 11. DELTAVERS08C40T10 Emigratie C50T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C40T10.xls
When voor persoon 616902505 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 12. DELTAVERS08C50T10 Correctie in de omschrijving van de aangifte adreshouding C70T10
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C50T10.xls
When voor persoon 599136777 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 13. DELTAVERS08C60T10 Verwijderen van historische verblijfplaatsgegevens C90T10
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C60T10.xls
When voor persoon 971826377 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 14. DELTAVERS08C60T20 Invoegen (in midden) van historische verblijfplaatsgegevens
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS08/DELTAVERS08C60T20.xls
When voor persoon 971826377 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


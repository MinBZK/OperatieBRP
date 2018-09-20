Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 aapos
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat1

Narrative: Leveren categorie 1 (Persoon) mutatieberichten

Scenario: 01. DELTAVERS01aC10T10 - DW-021 Wijziging naamgegevens (voornamen) persvoornaam
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T10.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 02. DELTAVERS01aC10T20 - DW-021 Wijziging naamgegevens (Adellijke titel/predikaat) perssamengesteldenaam
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T20.xls
When voor persoon 822062793 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 03. DELTAVERS01aC10T30 - DW-021 Wijziging naamgegevens (voorvoegsel geslachtsnaam) perssamengesteldenaam
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T30.xls
When voor persoon 211986689 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 04. DELTAVERS01aC10T40 - DW-021 Wijziging naamgegevens (geslachtsnaam) perssamengesteldenaam
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T40.xls
When voor persoon 860709577 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 05. DELTAVERS01aC10T50 - DW-021 Wijziging geslacht
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T50.xls
When voor persoon 495922985 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 06. DELTAVERS01aC10T60 - DW-011 Wijziging naamgebruik persnaamgebruik
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T60.xls
When voor persoon 401321897 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 07. DELTAVERS01aC10T70 - Wijziging naamgegevens, geslacht, naamgebruik en document
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T70.xls
When voor persoon 617045641 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 08. DELTAVERS01aC10T90 - Wijziging scheidingsteken
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T90.xls
When voor persoon 319343017 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 09. DELTAVERS01aC10T100 - DW-011 Wijziging geboorte
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC10T100.xls
When voor persoon 319343017 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 10. DELTAVERS01aC20T50
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50.xls
When voor persoon 833478217 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 11. DELTAVERS01aC20T50a - 1a. Wijziging naamgegevens , geldigheid, verantwoording (WALG)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50a.xls
When voor persoon 213581097 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 12. DELTAVERS01aC20T50b
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50b.xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 13. DELTAVERS01aC20T50c
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50c.xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 14. DELTAVERS01aC20T50d
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T50d.xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T260
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T260.xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 854820425
| stuurgegevens.zendendePartij              | 034401

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: DELTAVERS01aC20T280
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS01a/DELTAVERS01aC20T280.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 854820425
| stuurgegevens.zendendePartij              | 034401

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
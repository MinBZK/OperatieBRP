Meta:
@sprintnummer           82
@epic                   Levering Onderzoek
@auteur                 aapos
@jiraIssue              TEAMBRP-3809
@status                 Onderhanden
@sleutelwoorden         GeconverteerdeDataTest

Narrative:
            Als
            wil ik
            zodat

== Controleregels ==

== Verwerkinglogica ==

== Acceptatie Criteria ==


Scenario: 01 DELTAONDC10T10.xls vanuit de gba synchronisatie wordt een onderzoek gestart op alle persoonsgegevens

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T10.xls

When voor persoon 948226249 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 948226249                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Given verzoek van bericht bhg_bvgGeefDetailsPersoon
And extra waardes:
| SLEUTEL                                 | WAARDE                 |
| stuurgegevens.zendendePartij            | 036101                 |
| parameters.abonnementNaam               | Abo GeefDetailsPersoon |
| parameters.peilmomentMaterieelResultaat | []                     |
| parameters.peilmomentFormeelResultaat   | []                     |
| parameters.historievorm                 | []                     |
| zoekcriteriaPersoon.burgerservicenummer | 948226249              |
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 02 DELTAONDC10T20

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T20.xls
When voor persoon 635783113 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 03 DELTAONDC10T30.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T30.xls
When voor persoon 465988969 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 04 DELTAONDC10T40.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T40.xls
When voor persoon 144892625 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 05 DELTAONDC10T50.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T50.xls
When voor persoon 445790441 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 06 DELTAONDC10T60.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T60.xls
When voor persoon 771217225 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 07 DELTAONDC10T70.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T70.xls
When voor persoon 179753137 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 08 DELTAONDC10T80.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T80.xls
When voor persoon 659797641 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 09 DELTAONDC10T90.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T90.xls
When voor persoon 144343897 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 10 DELTAONDC10T100.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC10T100.xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 11 DELTAONDC20T10.xls

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC20T10.xls
When voor persoon 501849257 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 12 DELTAONDC30T10.xls
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC30T10.xls
When voor persoon 271650825 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 13 DELTAONDC40T10.xls
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC40T10.xls
When voor persoon 460452265 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 14 DELTAONDC50T10.xls
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC50T10.xls
When voor persoon 225680865 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 15 DELTAONDC60T10.xls
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC60T10.xls
When voor persoon 949260617 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 16 DELTAONDC70T10.xls
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC70T10.xls
When voor persoon 575351913 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'

Scenario: 17 DELTAONDC80T10.xls
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAOND/DELTAONDC80T10.xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 2 groepen 'onderzoek'
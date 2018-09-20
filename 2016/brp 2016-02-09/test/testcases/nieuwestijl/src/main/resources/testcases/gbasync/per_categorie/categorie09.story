Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 aapos
@jiraIssue              TEAMBRP-3335
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 9 mutatieberichten

Scenario: 01. DELTAVERS09C10T10 Toevoeging eerste kind
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T10.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 02. DELTAVERS09C10T20 Toevoegen 2 Kinderen (eerst geen Kind)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T20.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 03. DELTAVERS09C10T30 Kind toevoegen (Als tweede kind)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T30.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 04. DELTAVERS09C10T30a Kind toevoegen (Als tweede kind)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T30a.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 05. DELTAVERS09C10T40 Kind rij toevoegd waarbij de bestaande actuele stapel een 85.10 heeft met standaardwaarde
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T40.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 06. DELTAVERS09C10T50 KIND historie toevoegen op een WALG manier
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T50.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 07. DELTAVERS09C10T60 2 Kinderen toevoegen (Als tweede en derde kind, met in totaal 3 PL'en)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T60.xls
When voor persoon 205409593 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 205409593 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 08. DELTAVERS09C10T70 Hist. Kind rij toevoegd waarbij de bestaande actuele stapel een 85.10 heeft met standaardwaarde
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T70.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 09. DELTAVERS09C10T80 Onterecht opgevoerde Kind toch weer opgevoerd
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C10T80.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 10. DELTAVERS09C20T10 Kindgegevens wijzigen (voornaam wijzigt) met historie
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T10.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 11. DELTAVERS09C20T20 Kindgegevens corrigeren (voornaam wijzigt) historie wordt onjuist
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T20.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 12. DELTAVERS09C20T30 Kindgegevens wijzigen (voornaam wijzigt) op een WALG manier
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T30.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 13. DELTAVERS09C20T40 Kindgegevens wijzigen (voornaam wijzigt 2x) met historie
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T40.xls
When voor persoon 205409593 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

Then is het bericht xsd-valide
When voor persoon 205409593 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 14. DELTAVERS09C20T50 Kindgegevens wijzigen van tweede Kind (actualisering)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T50.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 15. DELTAVERS09C20T60 Kindgegevens wijzigen van tweede Kind (WALG)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T60.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 16. DELTAVERS09C20T70 Kindgegevens wijzigen van tweede Kind (WALG)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C20T70.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 17. DELTAVERS09C30T10 Actualiseren onterecht Kind en er blijven geen kinderen over (correctie na ten onrechte opgevoerde kind)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T10.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 18. DELTAVERS09C30T20 Acutaliseren onterecht Kind en er blijft 1 kind over
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T20.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 19. DELTAVERS09C30T30 KIND verwijderd op een WALG manier
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T30.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 20. DELTAVERS09C30T40 KIND verwijderd op een WALG manier (met meerdere kinderen) Kind 1 verwijderen
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T40.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 21. DELTAVERS09C30T50 KIND verwijderd op een WALG manier (met meerdere kinderen) Kind 2 verwijderen
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T50.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 22. DELTAVERS09C30T60 Stapel volgorde gewijzgd
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T60.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 23. DELTAVERS09C30T70 Stapel volgorde gewijzgd en 1 Kind wordt verwijderd (WALG)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T70.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 24. DELTAVERS09C30T80 Stapel volgorde gewijzgd en 1 Kind wordt verwijderd (WALG)
zie ook TEAMBRP-4047
Meta:
@regels     ter_beoordeling,R2015

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T80.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| bronnen | 1      | bron      | ja       |
| bron    | 1      | document  | nee      |

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                   | WAARDE
| zoekcriteriaPersoon.burgerservicenummer   | 205409593
| stuurgegevens.zendendePartij              | 034401

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 25. DELTAVERS09C30T90 Stapel volgorde gewijzgd en 1 Kind wordt verwijderd (WALG)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C30T90.xls
When voor persoon 205409593 wordt de 1 na laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then wacht tot alle berichten zijn ontvangen

When voor persoon 205409593 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 26. DELTAVERS09C40T10 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T10.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 27. DELTAVERS09C40T20 Element 82.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T20.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 28. DELTAVERS09C40T30 Element 83.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T30.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 29. DELTAVERS09C40T40 Element 84.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T40.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 30. DELTAVERS09C40T50 Element 86.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T50.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 31. DELTAVERS09C40T60 Element 85.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T60.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 32. DELTAVERS09C40T70 Element 02.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T70.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 33. DELTAVERS09C40T80 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T80.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 34. DELTAVERS09C40T90 Element 85.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T90.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 35. DELTAVERS09C40T100 Element 02.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T100.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 36. DELTAVERS09C40T110 Element 82.10 en 86.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T110.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 37. DELTAVERS09C40T120 Element 83.30 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel in 2 stapels
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T120.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 38. DELTAVERS09C40T130 Precies dezelfde PL
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T130.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 39. DELTAVERS09C40T160 Leeg met Leeg
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T160.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 40. DELTAVERS09C40T170 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel in 2 stapels (HIS)
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T170.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 41. DELTAVERS09C40T180 Element 81.10 wijkt af tussen IST-OUD.HIS en IST-Nieuw.actueel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T180.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 42. DELTAVERS09C40T190 2 dezelfde stapel in IST.OUD.act verg 1 stapel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T190.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 43. DELTAVERS09C40T200 IST.OUD.actueel in andere stapel
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C40T200.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 44. DELTAVERS09C50T10 Toevoeging CAT11
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C50T10.xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 45. DELTAVERS09C60T10 Toevoeging/correctie historie kind???
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand /deltabepaling/DELTAVERS09/DELTAVERS09C60T10.xls
When voor persoon 836242889 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

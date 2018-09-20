Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus31
@status                 Klaar
@regels                 VR00114,R1563,R1561

Narrative: Casus 31: Start een onderzoek op een reeds beëindigd gegeven. Geen autorisatie voor formele historie

Scenario: 1. niet leveren start een onderzoek op een bestaand gegeven, dat reeds is beëindigd. De afnemer heeft geen autorisatie op materiële historie op deze groep

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given alle personen zijn verwijderd
Given een sync uit bestand DELTAONDC40T50.xls
When voor persoon 651919113 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | ja      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | ja      |

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | nee      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee      |


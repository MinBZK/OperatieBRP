Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus32
@status                 Klaar
@regels                 VR00114,R1563,R1561

Narrative: Casus 32: Lopend onderzoek aanwezig op een vervallen gegeven. Niet geautoriseerd voor formele historie

Scenario: 1. leveren (zonder onderzoek) er wordt een VolledigBericht opgevraagd, waarin zich een onderzoek bevindt dat betrekking heeft op een vervallen gegeven. De afnemer is niet geautoriseerd voor formele historie op de groep waarvan het vervallen gegeven in onderzoek staat

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given alle personen zijn verwijderd
Given een sync uit bestand DELTAONDC40T50.xls
When voor persoon 651919113 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'Abo met alleen verantwoordingsinfo True' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 651919113                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | nee      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee      |


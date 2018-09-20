Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus7
@status                 Klaar
@regels                 R2063,R2065,R1962,R1973,R2051


Narrative: Casus 7. Een administratieve handeling die de gegevens in onderzoek wijzigt en gelijktijdig de onderzoeksgegevens wijzigt.

Scenario: 1. leveren, wijziging op onderzoek gegevens zelf gelijktijding met wijziging  op gegevens in onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand wijziging_onderzoek_met_wijziging_gegeven.xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                           |
| onderzoek          | 2      | datumAanvang           | 2011-05-01                                |
| onderzoek          | 2      | verwachteAfhandeldatum | 0000                                      |
| onderzoek          | 2      | datumEinde             | 2012-03-16                                |
| onderzoek          | 2      | omschrijving           | Conversie GBA: 081110                     |
| onderzoek          | 2      | statusNaam             | Afgesloten                                |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.AfgekorteNaamOpenbareRuimte |
| onderzoek          | 4      | datumAanvang           | 2011-05-01                                |
| onderzoek          | 4      | verwachteAfhandeldatum | 0000                                      |
| onderzoek          | 4      | omschrijving           | Conversie GBA: 081120                     |
| onderzoek          | 4      | statusNaam             | In uitvoering                             |
| gegevenInOnderzoek | 2      | elementNaam            | Persoon.Adres.Huisnummer                  |


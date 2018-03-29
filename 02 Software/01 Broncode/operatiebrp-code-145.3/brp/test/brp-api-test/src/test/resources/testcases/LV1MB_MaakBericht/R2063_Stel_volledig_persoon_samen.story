Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R2063

Narrative:  Als afnemer,
            wil ik dat bij een volledig bericht een volledig persoon samengesteld kan worden,
            zodat de afnemer alle groepen van de persoon geleverd krijgt

Scenario:   1. Volledig bericht met alle groepen volledig persoon
               LT: R2063_LT01
               Verwacht Resultaat: Volledig bericht met alle groepen van volledig persoon
               - Inhoudelijke groepen (met identificerende groepen)
               - Verantwoordingsgroepen
               - Onderzoeksgroepen

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Controle op inhoudelijke groepen
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut                   | aanwezig  |
| persoon            | 1      | adressen                    | ja        |
| persoon            | 1      | geslachtsnaamcomponenten    | ja        |
| persoon            | 1      | geboorte                    | ja        |
| persoon            | 1      | geslachtsaanduiding         | ja        |
| persoon            | 1      | voornamen                   | ja        |
| persoon            | 1      | betrokkenheden              | ja        |

!-- Controle op Identificerende groepen wordt getest in R1542

!-- Controle op verantwoordingsgroepen (Administratieve handeling, Actie, Actie \ Bron, Document, Gedeblokkeerde melding )
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                      | nummer | attribuut                   | aanwezig  |
| persoon                    | 1      | administratieveHandelingen  | ja        |
| administratieveHandeling   | 1      | bronnen                     | ja        |
| bron                       | 1      | document                    | ja        |
| administratieveHandeling   | 1      | bijgehoudenActies           | ja        |
| bijgehoudenActies          | 1      | actie                       | ja        |
| actie                      | 3      | bronnen                     | ja        |

!-- Controle op onderzoeksgroepen
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                      | nummer | attribuut                   | aanwezig  |
| persoon                    | 1      | onderzoeken                 | ja        |
| onderzoek                  | 2      | datumAanvang                | ja        |
| onderzoek                  | 2      | datumEinde                  | ja        |
!-- | onderzoek                  | 2      | Status                  | ja        | komt er niet in bij een afnemer, wel voor bijhouder
| onderzoek                  | 1      | gegevensInOnderzoek         | ja        |
| gegevenInOnderzoek         | 1      | elementNaam                 | ja        |
| gegevenInOnderzoek         | 1      | voorkomenSleutelGegeven     | ja        |

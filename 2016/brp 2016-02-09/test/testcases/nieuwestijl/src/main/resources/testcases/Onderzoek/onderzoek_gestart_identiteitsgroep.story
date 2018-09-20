Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@jiraIssue
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 VR00086, R1551, R2063,R2051

Narrative: er wordt een onderzoek gestart op een attribuut dat binnen de identiteitsgroep valt

Scenario: 1. Het in onderzoek zetten van een identiteitsgroep attribuut (Huwelijk.Soortcode)

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand onderzoek_gestart_identiteitsgroep_01.xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut            | aanwezig |
| onderzoeken        | 1      | onderzoek            | ja       |
| gegevenInOnderzoek | 1      | objectSleutelGegeven | ja       |

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde    |
| gegevenInOnderzoek | 1      | elementNaam | Huwelijk.SoortCode |

Scenario: 2. Het in onderzoek zetten van het object Persoon

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 921674569 zijn verwijderd
Given de standaardpersoon Olivia met bsn 921674569 en anr 5235604242 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(921674569)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek naar attribuut in identiteitsgroep', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon')
    }

}
slaOp(persoon)

When voor persoon 921674569 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                              |
| synchronisatie     | 1      | naam                   | Aanvang onderzoek                            |
| synchronisatie     | 1      | partijCode             | 059401                                       |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                   |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-08-01                                   |
| onderzoek          | 2      | omschrijving           | Onderzoek naar attribuut in identiteitsgroep |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon                                      |
| actie              | 1      | soortNaam              | Registratie onderzoek                        |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut            | aanwezig |
| gegevenInOnderzoek | 1      | objectSleutelGegeven | ja       |

Then is er voor xpath //brp:persoon[@brp:objectSleutel=//brp:objectSleutelGegeven/text()] een node aanwezig in het levering bericht

Scenario: 2a. Volledig bericht met onderzoek op object persoon

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 921674569                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek
@jiraIssue              TEAMBRP-2575
@status                 Klaar
@regels                 VR00083,R1549

Narrative: Onderzoeksdeel: Leveren van ActieInhoud en ActieVerval mag alleen bij autorisatie voor verantwoordingsinformatie

Scenario: 1. Start onderzoek met een bijhouder die geen ABO-partij is (rol = niet 4)

Given de personen 627129705, 304953337, 234379753 zijn verwijderd
Given de standaardpersoon Olivia met bsn 234379753 en anr 8258546962 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(234379753)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150301) {
        gestartOp(aanvangsDatum:'2015-03-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-09-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Scenario: 2. Wijzig onderzoek

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(234379753)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150701) {
        wijzigOnderzoek(wijzigingsDatum:'2015-07-01', omschrijving:'Omschrijving en verwachte afhandeldatum gewijzigd', aanvangsDatum: '2015-03-01', verwachteAfhandelDatum: '2015-10-01')
    }
}
slaOp(persoon)

Scenario: 3. Beeindig onderzoek

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(234379753)
nieuweGebeurtenissenVoor(persoon) {
     onderzoek(partij: 18901, registratieDatum: 20151001) {
            afgeslotenOp(eindDatum:'2015-10-01')
        }
}
slaOp(persoon)

Scenario: 4. Mutatiebericht - Attributen actieInhoud en actieVerval worden getoond bij Abonnement met groep Nadere verantwoording = Ja
             en worden niet getoond bij Abonnement met groep Nadere verantwoording = Nee
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_geen_verantwoording
When voor persoon 234379753 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut           | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud         | ja       |
| afgeleidAdministratief | 2      | actieVerval         | ja       |
| onderzoek              | 2      | actieInhoud         | ja       |
| onderzoek              | 3      | tijdstipRegistratie | ja       |
| onderzoek              | 3      | actieVerval         | ja       |

When het mutatiebericht voor leveringsautorisatie Abo geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut           | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud         | nee      |
| afgeleidAdministratief | 2      | actieVerval         | nee      |
| onderzoek              | 2      | actieInhoud         | nee      |
| onderzoek              | 3      | actieVerval         | nee      |

Scenario: 5. Volledigbericht - Attributen actieInhoud en actieVerval worden getoond bij Abonnement met groep Nadere verantwoording = Ja

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 234379753                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut           | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud         | ja       |
| afgeleidAdministratief | 2      | actieVerval         | ja       |
| onderzoek              | 2      | actieInhoud         | ja       |
| onderzoek              | 3      | tijdstipRegistratie | ja       |
| onderzoek              | 3      | actieVerval         | ja       |

Scenario: 6. Volledigbericht - Attributen actieInhoud en actieVerval worden niet getoond bij Abonnement met groep Nadere verantwoording = Nee

Given leveringsautorisatie uit /levering_autorisaties/Abo_geen_verantwoording
Given verzoek voor leveringsautorisatie 'Abo geen verantwoording' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                  |
| zoekcriteriaPersoon.burgerservicenummer | 234379753               |
| stuurgegevens.zendendePartij            | 034401                  |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut           | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud         | nee      |
| afgeleidAdministratief | 2      | actieVerval         | nee      |
| onderzoek              | 2      | actieInhoud         | nee      |
| onderzoek              | 3      | actieVerval         | nee      |

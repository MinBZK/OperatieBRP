Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus46
@status                 Klaar
@regels                 R1319,R1543

Narrative: Casus 46: VolledigBericht met een onderzoek op een niet geautoriseerd gegeven en een onderzoek op een groep
           waarvan de afnemer voor geen enkel attribuut is geautoriseerd.

Scenario: 1. Volledig bericht met een onderzoek op een in de persoonslijst aanwezig gegeven, waarvoor de afnemer niet is geautoriseerd. Tevens een onderzoek op een groep, waarbinnen geen attribuut autorisatie bestaat.

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep

Given de personen 627129705, 304953337, 799911689 zijn verwijderd
Given de standaardpersoon Olivia met bsn 799911689 en anr 1265464802 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(799911689)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151010) {
        gestartOp(aanvangsDatum:'2015-10-10', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 799911689 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2. Plaatsing afnemerindicatie

Given verzoek voor leveringsautorisatie 'Abo onderzoek met autorisatie op att binnen groep' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand plaatsing_afnemer_indicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoeken    | 1      | onderzoek    | nee      |

Scenario: 3. Synchronisatie persoon

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                             |
| zoekcriteriaPersoon.burgerservicenummer | 799911689                                          |


When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoeken    | 1      | onderzoek    | ja       |

Scenario: 4. Onderzoek op groep nationaliteit


Given de persoon beschrijvingen:
persoon = Persoon.metBsn(799911689)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151015) {
        gestartOp(aanvangsDatum:'2015-10-14', omschrijving:'Onderzoek is gestart op groep nationaliteit', verwachteAfhandelDatum:'2016-08-01')
        gegevensInOnderzoek('Persoon.Nationaliteit.Standaard')
    }

}
slaOp(persoon)

When voor persoon 799911689 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoeken    | 1      | onderzoek    | ja       |

Scenario: 5. Controleer volledigbericht voor afnemer zonder authorisatie
Given verzoek voor leveringsautorisatie 'Abo onderzoek met autorisatie op att binnen groep' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                             |
| zoekcriteriaPersoon.burgerservicenummer | 799911689                                          |
| stuurgegevens.zendendePartij            | 034401                                             |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoek      | 1      | onderzoek    | ja       |
| onderzoek      | 2      | onderzoek    | nee      |

Scenario: 6. Controleer volledigbericht bij synchronisatie persoon
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                             |
| zoekcriteriaPersoon.burgerservicenummer | 799911689                                          |


When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoek      | 1      | onderzoek    | ja       |
| onderzoek      | 3      | onderzoek    | ja       |

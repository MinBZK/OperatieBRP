Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-2574
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 R1548

Narrative:
Tijdstip registratie en Tijdstip verval niet mee leveren in het onderzoeksdeel wanneer afnemer geen autorisatie op formele historie heeft

Scenario: Casus 13. Beeindigen onderzoek gelijktijdig met het wijzigen van het gegeven in onderzoek
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given alle personen zijn verwijderd
Given een sync uit bestand beeindigen_onderzoek_met_wijziging.xls
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
| groep       | nummer | attribuut           | aanwezig |
| onderzoeken | 1      | onderzoek           | ja       |
| adressen    | 1      | adres               | ja       |
| onderzoek   | 2      | tijdstipRegistratie | nee      |

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 651919113                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut           | aanwezig |
| onderzoeken | 1      | onderzoek           | ja       |
| adressen    | 1      | adres               | ja       |
| onderzoek   | 2      | tijdstipRegistratie | ja       |
| onderzoek   | 4      | tijdstipRegistratie | ja       |

Scenario: R1548_A Leveren van Datum/tijd registratie en Datum/tijd verval mag alleen bij autorisatie voor formele historie

Given de personen 627129705, 304953337, 550733929 zijn verwijderd
Given de standaardpersoon Olivia met bsn 550733929 en anr 9487849746 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(550733929)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150102) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

When voor persoon 550733929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut           | aanwezig |
| onderzoeken | 1      | onderzoek           | ja       |
| adressen    | 1      | adres               | ja       |
| onderzoek   | 2      | tijdstipRegistratie | nee      |


When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut           | aanwezig |
| onderzoek | 2      | tijdstipRegistratie | ja       |
| adressen  | 1      | adres               | ja       |



Scenario: R1548_B. Onderzoek wijzigen,  controleer dat  Datum/tijd registratie en Datum/tijd verval niet in het mutatie bericht voorkomen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(550733929)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150801) {
            wijzigOnderzoek(wijzigingsDatum:'2015-08-01', omschrijving:'Wijziging onderzoek verwachte afhandel datum', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
        }
    }
    slaOp(persoon)

When voor persoon 550733929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut           | aanwezig |
| onderzoek | 2      | tijdstipRegistratie | nee      |
| onderzoek | 3      | tijdstipRegistratie | nee      |
| onderzoek | 3      | tijdstipVerval      | nee      |

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut           | aanwezig |
| onderzoek | 2      | tijdstipRegistratie | ja       |
| onderzoek | 3      | tijdstipRegistratie | ja       |
| onderzoek | 3      | tijdstipVerval      | ja       |

Scenario: R1548_C. Onderzoek beeindigen, controleer dat  Datum/tijd registratie en Datum/tijd verval niet in het mutatie bericht voorkomen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(550733929)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151010) {
        afgeslotenOp(eindDatum:'2015-10-09')
    }
}
slaOp(persoon)

When voor persoon 550733929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut           | aanwezig |
| onderzoeken | 1      | onderzoek           | ja       |
| adressen    | 1      | adres               | ja       |
| onderzoek   | 2      | tijdstipRegistratie | nee      |
| onderzoek   | 3      | tijdstipRegistratie | nee      |
| onderzoek   | 3      | tijdstipVerval      | nee      |


When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut           | aanwezig |
| onderzoeken | 1      | onderzoek           | ja       |
| adressen    | 1      | adres               | ja       |
| onderzoek   | 2      | tijdstipRegistratie | ja       |
| onderzoek   | 3      | tijdstipRegistratie | ja       |
| onderzoek   | 3      | tijdstipVerval      | ja       |

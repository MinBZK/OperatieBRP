Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus12
@status                 Klaar
@regels                 R1319,R1543

Narrative: Casus 12. Een administratieve handeling die de Omschrijving van het onderzoek wijzigt, waarvoor de afnemer slechts voor een aantal
           specifieke attributen binnen die groep is geautoriseerd.

Scenario: 1. Start onderzoek op een groep

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 826569353 zijn verwijderd
Given de standaardpersoon Olivia met bsn 826569353 en anr 2084945042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(826569353)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op groep geboorte', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Geboorte')
    }
}
slaOp(persoon)

When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                        |
| synchronisatie     | 1      | naam                   | Aanvang onderzoek                      |
| synchronisatie     | 1      | partijCode             | 059401                                 |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                             |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-08-01                             |
| onderzoek          | 2      | omschrijving           | Onderzoek is gestart op groep geboorte |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Geboorte                       |
| actie              | 1      | soortNaam              | Registratie onderzoek                  |

Scenario: 2. wijziging op onderzoek gegevens voor een lopend onderzoek op een groep, waarvoor slechts 1 attribuut is geautoriseerd

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 782112201 zijn verwijderd
Given de standaardpersoon Olivia met bsn 782112201 en anr 2360390546 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(782112201)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150702) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op groep adres', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}
slaOp(persoon)

When voor persoon 782112201 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde         |
| synchronisatie     | 1      | naam         | Aanvang onderzoek       |
| synchronisatie     | 1      | partijCode   | 034401                  |
| onderzoek          | 2      | datumAanvang | 2015-01-01              |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Adres.Standaard |

Scenario: 3. Wijziging op onderzoek naar groep

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(782112201)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150802) {
            wijzigOnderzoek(wijzigingsDatum:'2015-08-01', omschrijving:'Wijziging verwachte afhandeldatum onderzoek op groep adres', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
        }
    }
    slaOp(persoon)

When voor persoon 782112201 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                                            |
| synchronisatie     | 1      | naam                   | Wijziging onderzoek                                        |
| synchronisatie     | 1      | partijCode             | 034401                                                     |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                                 |
| onderzoek          | 2      | omschrijving           | Wijziging verwachte afhandeldatum onderzoek op groep adres |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-10-10                                                 |
| onderzoek          | 3      | datumAanvang           | 2015-01-01                                                 |
| onderzoek          | 3      | verwachteAfhandeldatum | 2015-08-01                                                 |
| onderzoek          | 3      | omschrijving           | Onderzoek is gestart op groep adres                        |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Standaard                                    |

Scenario: 4. Beeindiging onderzoek naar groep

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(782112201)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20151010) {
        afgeslotenOp(eindDatum:'2015-10-09')
    }
}
slaOp(persoon)

When voor persoon 782112201 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:

| groep          | nummer | attribuut  | verwachteWaarde       |
| synchronisatie | 1      | naam       | Beeindiging onderzoek |
| synchronisatie | 1      | partijCode | 034401                |
| onderzoek      | 2      | datumEinde | 2015-10-09            |
| onderzoek      | 2      | statusNaam | Afgesloten            |


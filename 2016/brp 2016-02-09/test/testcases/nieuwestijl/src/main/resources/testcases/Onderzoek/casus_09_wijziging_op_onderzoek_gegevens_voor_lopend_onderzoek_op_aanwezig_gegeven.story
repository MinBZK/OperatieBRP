Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus9
@status                 Klaar

Narrative: Casus 9. Een administratieve handeling dat de onderzoeksgegevens wijzigt bij lopend onderzoek

Scenario: 1. het huisnummer van de persoon wordt in onderzoek gezet, vervolgens wordt de verwachte afhandeldatum van het onderzoek gewijzigd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 630151593 zijn verwijderd
Given de standaardpersoon Olivia met bsn 630151593 en anr 4313036562 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 9. Start onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2. mutatie bericht na wijzigen onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150202) {
        wijzigOnderzoek(wijzigingsDatum:'2015-02-02', omschrijving:'Casus 9. Onderzoek gegevens gewijzigd', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                             |
| synchronisatie     | 1      | naam                   | Wijziging onderzoek                         |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                  |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-10-10                                  |
| onderzoek          | 2      | omschrijving           | Casus 9. Onderzoek gegevens gewijzigd       |
| onderzoek          | 2      | statusNaam             | In uitvoering                               |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Huisnummer                    |

Scenario: 3. mutatiebericht na beeindigen onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151012) {
        afgeslotenOp(eindDatum:'2015-10-12')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                             |
| synchronisatie     | 1      | naam                   | Beeindiging onderzoek                       |
| onderzoek          | 2      | datumEinde             | 2015-10-12                                  |
| onderzoek          | 2      | omschrijving           | Casus 9. Onderzoek gegevens gewijzigd       |
| onderzoek          | 2      | statusNaam             | Afgesloten                                  |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Huisnummer                    |


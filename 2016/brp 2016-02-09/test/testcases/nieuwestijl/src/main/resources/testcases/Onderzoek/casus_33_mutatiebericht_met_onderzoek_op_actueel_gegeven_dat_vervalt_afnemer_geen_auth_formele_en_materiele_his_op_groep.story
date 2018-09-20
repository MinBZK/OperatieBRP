Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus33
@status                 Klaar
@regels                 VR00114,R1563,R1561

Narrative: Casus 33: Er is sprake van een gestart onderzoek op Adres, het adres krijgt een datum einde in een AH. Daarna wordt het onderzoek op het Adres beëindigd (in een separate AH).
           Er is geen autorisatie voor formele of materiële historie.

Scenario: 1. niet leveren, er is sprake van een bestaand onderzoek op een actueel gegeven.
Dit actuele gegeven wordt later beeindigd of komt te vervallen.
In de meest recente AH wordt het onderzoek beeindigd, dat resulteert in een mutatiebericht.
De afnemer is niet geautoriseerd voor formele en materiele historie

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de personen 627129705, 304953337, 891833225 zijn verwijderd
Given de standaardpersoon Olivia met bsn 891833225 en anr 6715235090 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(891833225)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20130401) {
        gestartOp(aanvangsDatum:'2013-04-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

When voor persoon 891833225 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2. Verhuizing, zodat het onderzoek naar een vervallen adres wijst

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(891833225)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20131017, registratieDatum: 20131017) {
        naarGemeente 'Hillegom',
            straat: 'Dorpsstraat', nummer: 30, postcode: '2180AA', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

When voor persoon 891833225 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3. Onderzoek naar vervallen gegeven beeindigen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(891833225)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20141212) {
        afgeslotenOp(eindDatum:'2014-12-12')
    }
}
slaOp(persoon)

When voor persoon 891833225 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4. Volledig bericht opvragen voor een persoon met een beeindigd onderzoek naar een vervallen gegeven

Given verzoek voor leveringsautorisatie 'Abo met alleen verantwoordingsinfo True' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 891833225                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 891833225                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


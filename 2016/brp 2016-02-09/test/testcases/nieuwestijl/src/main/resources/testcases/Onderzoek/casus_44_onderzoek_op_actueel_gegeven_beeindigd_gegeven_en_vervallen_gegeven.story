Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus44
@status                 Klaar
@regels                 VR00116,R2063,R2065,R1563,R1962,R1973

Narrative: Casus 44: VolledigBericht met alle mogelijke varianten van onderzoek: wordt aan een afnemer geleverd.

Scenario: 1. Volledigbericht met een onderzoek op een actueel gegeven, een beëindigd gegeven en een vervallen gegeven in de persoonslijst.

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20090101 ) {
        gestartOp(aanvangsDatum:'2009-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
        naarGemeente 'Pijnacker',
            straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BX', woonplaats: "Pijnacker"
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20120501) {
        gestartOp(aanvangsDatum:'2012-05-01', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20120510, registratieDatum: 20120510) {
        naarGemeente 'Delft',
            straat: 'Aalscholverring', nummer: 30, postcode: '2623PD', woonplaats: "Delft"
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op NaamOpenbareRuimte', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.NaamOpenbareRuimte')
    }
}
slaOp(persoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| stuurgegevens.zendendePartij            | 034401                                          |
| zoekcriteriaPersoon.burgerservicenummer | 407124585                                       |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 407124585 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde                  |
| gegevenInOnderzoek | 1      | elementNaam | Persoon.Adres.Postcode           |
| gegevenInOnderzoek | 2      | elementNaam | Persoon.Adres.Huisnummer         |
| gegevenInOnderzoek | 3      | elementNaam | Persoon.Adres.NaamOpenbareRuimte |

Scenario: 2. Wanneer alle attributen van een groepen in onderzoek gezet worden, dan zijn in de mutatielevering ook alle gegevens in onderzoek geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand Alle_att_binnen_groep_onderzoek.xls
When voor persoon 651919113 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then heeft het bericht 7 groepen 'gegevenInOnderzoek'

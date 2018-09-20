Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek
@status                 Uitgeschakeld
@regels                 R1549,R1561

Narrative: Volledig bericht met een onderzoek op een beëindigd en een vervallen gegeven, alsmede een onderzoek op een ontbrekend gegeven.
           De afnemer heeft geen historie autorisatie en geen autorisatie op het ontbrekend gegeven.
           Casus 47

Scenario: 1. Olivia wordt geboren, dan wordt een onderzoek gestart op postcode

Given de personen 627129705, 304953337, 837053961 zijn verwijderd
Given de standaardpersoon Olivia met bsn 837053961 en anr 2686452641 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(837053961)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20090101 ) {
        gestartOp(aanvangsDatum:'2009-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Scenario: 2. Olivia verhuist, nieuw onderzoek wordt gestart op NaamOpenbareRuimte

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(837053961)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20120510, registratieDatum: 20120510) {
        naarGemeente 'Delft',
            straat: 'Aalscholverring', nummer: 30, postcode: '2623PD', woonplaats: "Delft"
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(837053961)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op NaamOpenbareRuimte', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.NaamOpenbareRuimte')
    }
}
slaOp(persoon)

Scenario: 3. Onderzoek gestart op een ontbrekende gegeven in persoonsdeel (Olivia is niet getrouwd)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(837053961)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
    }
}
slaOp(persoon)

Scenario: 4. Afnemer heeft geen historie autorisatie en geen autorisatie op het ontbrekend gegeven

Given leveringsautorisatie uit Abo geen materiele - formele historie en geen verantwoording
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And extra waardes:
| SLEUTEL                                 | WAARDE                                                       |
| stuurgegevens.zendendePartij            | 034401                                                       |
| parameters.abonnementNaam               | Abo geen materiele - formele historie en geen verantwoording |
| zoekcriteriaPersoon.burgerservicenummer | 837053961                                                    |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 837053961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Abo geen materiele - formele historie en geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut   | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam | ja       |
| gegevenInOnderzoek | 2      | elementNaam | nee      |

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde                  |
| gegevenInOnderzoek | 1      | elementNaam | Persoon.Adres.NaamOpenbareRuimte |



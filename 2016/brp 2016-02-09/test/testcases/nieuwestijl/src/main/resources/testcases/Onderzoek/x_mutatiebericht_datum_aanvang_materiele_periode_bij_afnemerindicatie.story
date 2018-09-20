Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-2572
@sleutelwoorden         onderzoek
@status                 Uitgeschakeld
@regels                 VR00058,R1544

Narrative: Begrens het bericht op datum aanvang materiële periode

Scenario: 1. Geen mutatiebericht voor een beëindiging onderzoek op een gegeven dat een einddatum heeft vóór de Datum aanvang materiele periode bij de afnemerindicatie
Casus 52

Given leveringsautorisatie uit Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
    naarGemeente 'Pijnacker',
        straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BV', woonplaats: "Pijnacker"
}
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20130401) {
        gestartOp(aanvangsDatum:'2013-04-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20131017, registratieDatum: 20131017) {
        naarGemeente 'Hillegom',
            straat: 'Dorpsstraat', nummer: 30, postcode: '2180AA', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20131212) {
        afgeslotenOp(eindDatum:'2013-12-12')
    }
}
slaOp(persoon)

When voor persoon 407124585 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 2. Wel een Mutatiebericht voor een beëindiging van een gegeven met een DEG groter dan de Datum Aanvang Materiele Periode.
             In deze administratieve handeling wordt ook het lopende onderzoek op het gegeven beëindigd
Casus 53

Given leveringsautorisatie uit Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
    naarGemeente 'Pijnacker',
        straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BV', woonplaats: "Pijnacker"
}
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20130401) {
        gestartOp(aanvangsDatum:'2013-04-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20140607, registratieDatum: 20140607) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 30, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(persoon)

nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401) {
        afgeslotenOp(eindDatum:'2015-03-24')
    }
}
slaOp(persoon)

When voor persoon 407124585 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                  |
| onderzoek          | 2      | datumAanvang | 2013-04-01                       |
| onderzoek          | 2      | datumEinde   | 2015-03-24                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op postcode |
| onderzoek          | 3      | omschrijving | Onderzoek is gestart op postcode |
| onderzoek          | 2      | statusNaam   | Afgesloten                       |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Adres.Postcode           |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut | aanwezig |
| adres | 1      | postcode  | ja       |
| adres | 2      | postcode  | nee      |

Scenario: 3. Geen mutatiebericht met een beëindiging onderzoek vóór de Datum Aanvang Materiele Periode
Casus 54

Given leveringsautorisatie uit Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
    naarGemeente 'Pijnacker',
        straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BV', woonplaats: "Pijnacker"
}
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20130401) {
        gestartOp(aanvangsDatum:'2013-04-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20131224) {
        afgeslotenOp(eindDatum:'2013-12-24')
    }
}
slaOp(persoon)

When voor persoon 407124585 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 4. Mutatiebericht met een beëindiging onderzoek ná de Datum Aanvang Materiele Periode
Casus 55

Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
    naarGemeente 'Pijnacker',
        straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BV', woonplaats: "Pijnacker"
}
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20130401) {
        gestartOp(aanvangsDatum:'2013-04-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401) {
        afgeslotenOp(eindDatum:'2015-01-08')
    }
}
slaOp(persoon)

When voor persoon 407124585 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                  |
| onderzoek          | 2      | datumAanvang | 2013-04-01                       |
| onderzoek          | 2      | datumEinde   | 2015-01-08                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op postcode |
| onderzoek          | 3      | omschrijving | Onderzoek is gestart op postcode |
| onderzoek          | 2      | statusNaam   | Afgesloten                       |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Adres.Postcode           |


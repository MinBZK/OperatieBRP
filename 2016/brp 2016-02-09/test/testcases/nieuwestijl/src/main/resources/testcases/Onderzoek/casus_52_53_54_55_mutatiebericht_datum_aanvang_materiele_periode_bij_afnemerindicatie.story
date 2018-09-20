Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus52,casus53,casus54,casus55
@status                 Klaar
@regels                 VR00058,R1544

Narrative: Casus 52, casus 53, casus 54 en casus 55. Mutatiebericht van een beeindiging onderzoek

Scenario: 1. Casus 52 Mutatiebericht van een beeindiging onderzoek op een Adres
             met een DEG kleiner dan de Datum Aanvang Materiele Periode.
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
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

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand casus_52_53_54_55_mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_01.yml
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

Scenario: 2. Casus 53 Mutatiebericht voor een beeindiging van een gegeven
             met een DEG groter dan de Datum Aanvang Materiele Periode.
             In deze administratieve handeling wordt ook het lopende onderzoek op het gegeven beëindigd

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

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand casus_52_53_54_55_mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_01.yml
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

Scenario: 3. Casus 54 Mutatiebericht met een beeindiging onderzoek
             vóór de Datum Aanvang Materiele Periode

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

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand casus_52_53_54_55_mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_02.yml
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

Scenario: 4. Casus 55 Mutatiebericht met een beeindiging onderzoek
             na de Datum Aanvang Materiele Periode

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

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand casus_52_53_54_55_mutatiebericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_02.yml
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


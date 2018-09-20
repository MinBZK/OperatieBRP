import nl.bzk.brp.datataal.model.Persoon

def adam = Persoon.uitDatabase(persoon: 1001)
def eva = Persoon.uitDatabase(persoon: 1002)

def willem = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam(
                [stam: 'Vorstenhove', voorvoegsel: 'van', scheidingsteken: ' ', adellijkeTitel: 'P', predicaat: 'J']
            )
            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        ouders moeder: eva
        erkendDoor adam
        identificatienummers bsn: 345345354, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
        behandeldAlsNederlander(true)
        staatloos(true)
        verstrekkingsbeperking {
            volledig ja
        }
    }

    verhuizing(aanvang: 19900101) {
        binnenGemeente straat: 'Lindestraat', nummer: 123, letter: 'A', postcode: '1234AB', woonplaats: "'s-Hertogenbosch"
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarGemeente 'Rotterdam',
            straat: 'Pier', nummer: 13, postcode: '3020XX', woonplaats: "Hoek van Holland"
        verstrekkingsbeperking {
            volledig nee
        }
    }

    wijzigingGezag() {
        derdeHeeftGezag ja
        ouderlijkGezag(adam, nee)
    }
}
willem = Persoon.slaOp(willem)

def truus = Persoon.metId(2000003)
Persoon.nieuweGebeurtenissenVoor(willem) {
    huwelijk() {
        op '2010/06/08' te 'Amsterdam' gemeente 'Amsterdam'
        met truus
        naamgebruik('eigen')
    }

    naamswijziging(aanvang:20090909) {
        geslachtsnaam(stam: 'Vorstenhove', voorvoegsel:'van').wordt(stam: 'Cock', voorvoegsel: 'de')
    }

    geslachtswijziging(aanvang:20090909) {
        geslacht 'VROUW'
    }

    verstrekkingsbeperking(aanvang: 20110824) {
        volledig nee
        registratieBeperkingen(
            [partij: 54601],
            [omschrijving: 'Bibliotheek om de hoek', gemeenteVerordening: 36101]
        )
    }

    curatele(aanvang: 20150121) { ja }

    scheiding() {
        van truus
        op('2011/06/09').te('Den Bosch').gemeente("'s-Hertogenbosch")
    }

    overlijden() {
        op '2015/03/03' te 'Rome' land 'Italië'
    }
}
willem = Persoon.slaOp(willem)
//FIXME gebruik nieuwe AUTAUT DSL
//Persoon.nieuweGebeurtenissenVoor(willem) {
//    afnemerindicaties {
//        plaatsVoor(afnemer: 36101, abonnement: 'Abonnement 2') {
//            datumAanvangMaterielePeriode '2000/02/02'
//            eindeVolgen '2010/12/31'
//        }
//        verwijderVan afnemer: 'Gemeente Boxmeer', abonnement: 'Abonnement 1'
//    }
//}
//slaOp(willem)
//
//Persoon.nieuweGebeurtenissenVoor(willem) {
//    onderzoek(partij: 34401) {
//        gestartOp(aanvangsDatum: '2015-01-01',
//            omschrijving: 'Onderzoek is gestart',
//            verwachteAfhandelDatum: '2015-04-01')
//
//        gegevensInOnderzoek('Persoon.Adres.Standaard')
//    }
//}
//slaOp(willem)

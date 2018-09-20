import static nl.bzk.brp.datataal.model.Persoon.*

def adam = uitDatabase persoon: 1001
def eva = uitDatabase persoon: 1002

tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        ouders moeder: eva
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}

nieuweGebeurtenissenVoor(tester) {
    erkend() {
        door(adam)
        nationaliteiten('Franse')
    }

    verhuizing(aanvang: 19900101) {
        binnenGemeente straat: 'Lindestraat', nummer: 123, letter: 'A', postcode: '1234AB', woonplaats: "'s-Hertogenbosch"
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarGemeente 'Rotterdam',
                straat: 'Pier', nummer: 13, postcode: '3020XX', woonplaats: "Hoek van Holland"
    }

//    huwelijk(aanvang: 19980521) {
//        op '2014/04/28' te "'s-Gravenhage" gemeente 317
//        met uitDatabase(persoon:12)
//    }
}

tester = slaOp(tester)

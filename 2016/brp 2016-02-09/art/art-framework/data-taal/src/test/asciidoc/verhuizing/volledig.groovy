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
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 96756766, anummer: 8934753799
    }
}
Persoon.slaOp(willem)

Persoon.nieuweGebeurtenissenVoor(willem) {
    verhuizing(aanvang: 19900101) {
        binnenGemeente straat: 'Lindestraat', nummer: 123, letter: 'A', postcode: '1234AB', woonplaats: "'s-Hertogenbosch"
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarGemeente 'Rotterdam',
            straat: 'Pier', nummer: 13, postcode: '3020XX', woonplaats: "Hoek van Holland"
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarBuitenland 'Mali', adres: 'regel1, regel2, regel3'
        verstrekkingsbeperking {
            volledig ja
            registratieBeperkingen(
                [partij: 36101]
            )
        }
    }

    GBABijhoudingOverig(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarGemeente 'Rotterdam',
            straat: 'Pier', nummer: 13, postcode: '3020XX', woonplaats: "Hoek van Holland"
    }

    GBABijhoudingOverig(aanvang: 19900101) {
        binnenGemeente straat: 'Lindestraat', nummer: 123, letter: 'A', postcode: '1234AB', woonplaats: "'s-Hertogenbosch"
    }

    GBABijhoudingOverig(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarBuitenland 'Mali', adres: 'regel1, regel2, regel3'
        verstrekkingsbeperking {
            volledig ja
            registratieBeperkingen(
                [partij: 34401]
            )
        }
    }

    GBABijhoudingOverig(partij: 'Gemeente Rotterdam', aanvang: 19900101) {
        conversieGBA()
    }
}

Persoon.slaOp(willem)

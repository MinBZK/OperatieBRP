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
                    [stam: 'Vlag']
            )
            samengesteldeNaam voornamen: 'Manuèl', stam: 'Vlag'
        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 96756777, anummer: 8934753700
    }
}
Persoon.slaOp(willem)

Persoon.nieuweGebeurtenissenVoor(willem) {
    naamswijziging(partij: 54101, aanvang: 20101010, toelichting:'rijmelarij') {
        geslachtsnaam([stam:'Vlag']).wordt([stam:'Zonnig', voorvoegsel:'het'])
    }

    naamswijziging() {
        geslachtsnaam(1).wordt([stam:'Zonnig', voorvoegsel:'het'])
    }
}

Persoon.slaOp(willem)

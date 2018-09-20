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
        identificatienummers bsn: 96756754, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
        behandeldAlsNederlander(true)
        staatloos(true)
        verstrekkingsbeperking { volledig ja }
    }
}
Persoon.slaOp(willem)

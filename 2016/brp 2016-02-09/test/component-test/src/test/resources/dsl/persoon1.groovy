package dsl

import static nl.bzk.brp.datataal.model.Persoon.slaOp
import static nl.bzk.brp.datataal.model.Persoon.uitGebeurtenissen

tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

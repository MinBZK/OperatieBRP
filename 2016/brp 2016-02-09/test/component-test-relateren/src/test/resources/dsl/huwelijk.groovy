package dsl

import static nl.bzk.brp.datataal.model.Persoon.nieuweGebeurtenissenVoor
import static nl.bzk.brp.datataal.model.Persoon.onbekende
import static nl.bzk.brp.datataal.model.Persoon.slaOp
import static nl.bzk.brp.datataal.model.Persoon.uitGebeurtenissen


def man = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Utrecht', aanvang: 19800101) {
        op 19800101 te 'Utrecht' gemeente 'Utrecht'
        geslacht 'MAN'
        namen {
            voornamen 'Piet'
            geslachtsnaam stam: 'Klaassen'
        }
        identificatienummers bsn: 283215161, anummer: 8019523858
    }
}
man = slaOp(man)

def vrouwOnbekend = onbekende(aanvang: 19770101, registratieDatum: 20060101) {
    geboorte {
        op 19770101 te 'Amsterdam' gemeente 'Amsterdam'
        samengesteldeNaam(voornamen: 'Annemarie', stam: 'Brouwer', voorvoegsel: 'van der')
        geslacht('VROUW')
        identificatienummers bsn: 555627433, anummer: 6068609810
    }
}

vrouwOnbekend = slaOp(vrouwOnbekend)

def vrouw = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Amsterdam', aanvang: 19770101) {
        op 19770101 te 'Amsterdam' gemeente 'Amsterdam'
        geslacht 'VROUW'
        namen {
            voornamen 'Annemarie'
            geslachtsnaam stam: 'Brouwer', voorvoegsel: 'van der'
        }
        identificatienummers bsn: 555627433, anummer: 6068609810

    }
}

vrouw = slaOp(vrouw)

def manOnbekend = onbekende(aanvang: 19770101, registratieDatum: 20060101) {
    geboorte {
        op 19770101 te 'Utrecht' gemeente 'Utrecht'
        samengesteldeNaam(voornamen: 'Piet', stam: 'Klaassen')
        geslacht('MAN')
        identificatienummers bsn: 283215161, anummer: 8019523858
    }
}

manOnbekend = slaOp(manOnbekend)

nieuweGebeurtenissenVoor(man) {
    huwelijk {
        op 20060101 te 'Vaals' gemeente 981
        met vrouwOnbekend
    }
}

slaOp(man)

nieuweGebeurtenissenVoor(vrouw) {
    huwelijk {
        op 20060101 te 'Vaals' gemeente 981
        met manOnbekend
    }
}

slaOp(vrouw)

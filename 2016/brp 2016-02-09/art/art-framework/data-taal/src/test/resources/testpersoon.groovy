import nl.bzk.brp.datataal.model.Persoon

def vader = Persoon.uitDatabase(persoon:1001)
def moeder = Persoon.uitDatabase(persoon:1002)

tester = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: 'ooievaar') {
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Peter'
            geslachtsnaam 'Balkenende'
        }
        op '1978/10/22' te 'Monster' gemeente 1783
        ouders moeder:moeder, vader:vader
        identificatienummers bsn: 123434538, anummer: 8934753756
        verstrekkingsbeperking {
            volledig nee
        }
    }
}
Persoon.slaOp(tester)

def partner = Persoon.uitDatabase(persoon:2000003)
Persoon.nieuweGebeurtenissenVoor(tester) {
    verhuizing(aanvang: 20060131, toelichting: 'nieuwe woning') {
        binnenGemeente straat: 'Lindestraat', nummer: 123, letter:'A'
        verstrekkingsbeperking {
            volledig ja
        }
    }

    verhuizing() {
        naarBuitenland 'Zwitserland',
            adres: ['regel1', 'regel2']
    }

    huwelijk(aanvang: 20080330, toelichting: 'huisje-boompje-beestje') {
        op '2008/03/30' te 'Rotterdam' gemeente 'Rotterdam'
        met partner
        naamgebruik('PARTNER')
    }

    naamswijziging(aanvang: 20101010) {
        geslachtsnaam 1 wordt stam:'Superman'
    }

    geslachtswijziging(aanvang:20101010) {
        geslacht 'VROUW'
    }
}
Persoon.slaOp(tester)

def ni = Persoon.nietIngeschrevene(aanvang: 19670401, registratieDatum: 19670402) {
    geboorte {
        op '1967/04/01' te 'Barcelona' land 'Spanje'
    }
    samengesteldeNaam(
        stam: 'Montoya',
        voornamen: 'Inigo'
    )
    geslacht('MAN')
}
Persoon.slaOp(ni)

nederlander = Persoon.ingeschreveneVan(ni) {
    vestigingInNederland(aanvang: 20150420, partij: 'Gemeente Rotterdam', toelichting: 'import') {

        identificatienummers bsn: 123456789, anummer: 98765431
        namen {
            voornamen('Inigo')
            geslachtsnaam('Montoya')
        }
        ouderVan(moeder)
        partnerVan(partner)
    }
}
Persoon.slaOp(nederlander)

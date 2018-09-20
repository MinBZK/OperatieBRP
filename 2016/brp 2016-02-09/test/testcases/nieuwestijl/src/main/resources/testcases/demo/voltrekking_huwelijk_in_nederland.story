Meta:
@auteur             dihoe
@status             Onderhanden
@regels dianadet
@sleutelwoorden     voltrekkingHuwelijkInNederland

Narrative:  Als
            wil ik
            zodat

Scenario: 1. Johnny en Anita gaan een voltrekking huwelijk in Nederland aan

Given de database is gereset voor de personen 126477735, 64258099
Given de personen 556645959 zijn verwijderd
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19901225, toelichting: '1e kind') {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederJohnny, vader: vaderJohnny
        identificatienummers bsn: 556645959, anummer: 5607075602
    }
}
slaOp(Johnny)
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 754209878 zijn verwijderd
Given de persoon beschrijvingen:
def moederAnita = uitDatabase bsn: 306741817
def vaderAnita  = uitDatabase bsn: 306867837
def Johnny      = uitDatabase bsn: 556645959

Anita = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19920603, toelichting: '1e kind') {
        op '1996/01/08' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Anita', 'Karen'
            geslachtsnaam 'Corner'

        }
        ouders moeder: moederAnita, vader: vaderAnita
        identificatienummers bsn: 754209878, anummer: 6248016146
    }
        huwelijk() {
              op 20150501 te 'Delft' gemeente 'Delft'
              met Johnny
    }
}
slaOp(Anita)

Given de personen 564568041 zijn verwijderd
Given de persoon beschrijvingen:
def moederKindje = uitDatabase bsn: 754209878
def vaderKindje  = uitDatabase bsn: 556645959

Kindje = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 20150601, toelichting: '1e kind') {
        op '2015/06/01' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Jennifer', 'Anita'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederKindje, vader: vaderKindje
        identificatienummers bsn: 564568041, anummer: 3549026834
    }
}
slaOp(Kindje)
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand voltrekking_huwelijk_in_nederland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd



Meta:
@auteur             dihoe
@status             Klaar
@regels dianademo
@sleutelwoorden     aangaanGeregistreerdPartnerschapInNederland

Narrative:  Als
            wil ik
            zodat

Scenario: 1. Johnny en Anita gaan een geregistreerd partnerschap in Nederland aan

Given de database is gereset voor de personen 126477735, 64258099
Given de personen 556645959 zijn verwijderd
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19901225, toelichting: '1e kind') {
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
    geboorte(partij: 34401, aanvang: 19920603, toelichting: '1e kind') {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederAnita, vader: vaderAnita
        identificatienummers bsn: 754209878, anummer: 6248016146
    }
        partnerschap() {
              op 20150527 te 'Delft' gemeente 'Delft'
              met Johnny
    }
}
slaOp(Anita)

When voor persoon 754209878 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                 | aanwezig |
| partner | 1      | geregistreerdPartnerschap | ja       |


Meta:
@sprintnummer       72
@epic               Mutatielevering basis
@auteur             rarij
@jiraIssue          TEAMBRP-2685
@status             Klaar

Narrative:  Als afnemer,
            wil ik dat een mutatiebericht met een vervallende ouder correct wordt verwerkt,
            zodat de ouder betrokkenheid komt te vervallen en geen nieuwe betrokkenheid wordt opgevoerd.

Scenario:   Afnemer krijgt een mutatiebericht a.g.v. een persoon waarvan een ouder is komen te vervallen
            Verwacht resultaat:
                - 1 voorkomen van groep ouder
                - 1 voorkomen van groep persoon onder groep ouder met daarin persoonsgegevens van de vader
                - 1 voorkomen van groep ouderschap onder groep ouder met daarin historie en verantwoording
                  informatie (tsReg, tsVerv, aV en dAG)

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
def vader_George     =    Persoon.uitDatabase(bsn: 150018502)
def moeder_George    =    Persoon.uitDatabase(bsn: 306741817)

George = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'George'
                geslachtsnaam 'Murphy'
            }
        ouders moeder: moeder_George, vader: vader_George
        identificatienummers bsn: 290844423, anummer: 1199553289
    }
    vaderVastgesteld() {
      nooitOuderGeweest(vader_George)
    }
}
slaOp(George)

When voor persoon 290844423 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290844423, 150018502 |

And hebben attributen in voorkomens de volgende waardes:
| groep      | nummer | attribuut              | verwachteWaarde |
| ouderschap | 1      | datumAanvangGeldigheid | 1980-02-03      |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut              | aanwezig |
| ouderschap | 1      | tijdstipVerval         | ja       |
| ouderschap | 1      | actieVerval            | ja       |

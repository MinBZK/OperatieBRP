Meta:
@sprintnummer       71
@epic               Change 2015003: Gegevensmodel relaties en betrokkenheden (fund.issue)
@auteur             rarij
@jiraIssue          TEAMBRP-2600
@status             Klaar

Narrative:  Als afnemer,
            wil ik dat het beeindigen van het ouderschap bij een onbekende ouder goed verwerkt wordt,
            zodat er correct geleverd wordt.

            Deze situatie kan optreden bij de adoptie van een vondeling: er wordt een een nieuwe ouder
            (meestal zelfs twee) toegevoegd terwijl het ouderschap van de oorspronkelijke onbekende ouder wordt beëindigd.

Scenario:   Adopteren van een kind van een onbekende ouder, resulteert 3 voorkomen van de groep ouderschap nl:
            - Ouderschap Wijziging met DAG en DEG
            - Ouderschap Verval met DAG
            - Ouderschap Toevoeging met DAG

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
def eva		=		Persoon.uitDatabase(bsn: 306741817)
vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20100203, toelichting: 'vondeling', registratieDatum: 20100203) {
            op '2010/02/03' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Rémi'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 420968337, anummer: 1759085586
    }
}
slaOp(vondeling)

Persoon.nieuweGebeurtenissenVoor(vondeling) {
    geadopteerd(aanvang: 20150110, registratieDatum: 20150110) {
        ouders(moeder: eva)
    }
}
slaOp(vondeling)

When voor persoon 420968337 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep      | nummer    | verwerkingssoort |
| ouderschap | 1         | Wijziging        |
| ouderschap | 2         | Verval           |
| ouderschap | 3         | Toevoeging       |

And hebben attributen in voorkomens de volgende waardes:
| groep      | nummer | attribuut              | verwachteWaarde |
| ouderschap | 1      | datumAanvangGeldigheid | 2010-02-03      |
| ouderschap | 1      | datumEindeGeldigheid   | 2015-01-10      |
| ouderschap | 2      | datumAanvangGeldigheid | 2010-02-03      |
| ouderschap | 3      | datumAanvangGeldigheid | 2015-01-10      |

Meta:
@auteur             rarij
@status             Uitgeschakeld

Narrative:  Deze story betreft een demo story van een vervallende ouder. Daarnaast wordt in deze story ook een voorbeeld
            van controles op verwerkingssoorten en aanwezigheid van attrobuten uitgewerkt.

Scenario:   Persoon wordt geboren en heeft twee ouders. Vervolgens komt de relatie met een van de ouders te vervallen.
            Verwacht resultaat:
            - 1 groep ouderschap die is komen te vervallen

Given de database is gereset voor de personen 150018502, 306741817
Given de personen 290271782 zijn verwijderd
Given de persoon beschrijvingen:
def vader_George     =    Persoon.uitDatabase(bsn: 150018502)
def moeder_George    =    Persoon.uitDatabase(bsn: 306741817)

George = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'George'
                geslachtsnaam 'Murphy'
            }
        ouders moeder: moeder_George, vader: vader_George
        identificatienummers bsn: 290271782, anummer: 639479881
    }
    vaderVastgesteld() {
      nooitOuderGeweest(vader_George)
    }
}
slaOp(George)

When voor persoon 290271782 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
!-- Oplossing 1: Check middels een normale stap
Then is de verwerkingssoort van groep ouderschap in voorkomen 1, Verval

!-- Oplossing 2: Check middels een tabel waardoor met 1 stap, meerdere groepen getest kan worden
And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep         | nummer    | verwerkingssoort  |
| ouderschap    | 1         | Verval            |

!-- Oplossing 3: Dit is eigenlijk een workaround daar de verwerkingssoort zelf hier niet mee getest wordt maar wel op de
!-- aanwezigheid op bepaalde attributen die bij een bepaalde verwerkingssoort getoond worden
And hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut       | aanwezig |
| ouderschap | 1      | tijdstipVerval  | ja       |
| ouderschap | 1      | actieVerval     | ja       |

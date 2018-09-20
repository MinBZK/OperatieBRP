Meta:
@sprintnummer       79
@auteur             luwid
@jiraIssue          TEAMBRP-3129
@status             Klaar




Narrative:
Als beheerder
wil ik ook de actuele Datum Einde Geldigheid, Tijdstip Registratie en eventueel Tijdstip Verval van een groep kunnen gebruiken in expressies
zodat ik bestaande GBA voorwaarderegels kan converteren. In deze ART wordt alleen attribuut tijdstip registratie getest, testen voor een attendering met
 een bepaalde DEG of tsVerval wordt lastig daar dit geen actuele records zijn

Als afnemer
wil ik dat de actuele tijdstipRegistratie van een groep gebruikt kan worden in expressietaal
zodat een afnemer attendering en populatiebeperking gebasseerd op de actuele tsRegistratie kan maken.

Scenario: 1. Een Populatiebeperking is gemaakt voor levering wanneer de actuele waarde van een tijdstipRegistratie in expressie wordt gebruikt. Als
tijdstipRegistratie van de geboorte na 2014/06/01 is dan komt de persoon  in de doelbinding.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_geboorte_tijdstip_registratie, /levering_autorisaties/mutatielevering_tijdstip_registratie_voor_het_jaar_1900, /levering_autorisaties/attendering_obv_geboorte_tijdstip_registratie
Given de persoon beschrijvingen:
def vader_John     =   Persoon.uitDatabase(bsn: 150018502)
def moeder_John    =   Persoon.uitDatabase(bsn: 306741817)

John = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 20141010) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'John'
                geslachtsnaam 'Hendricks'
            }
        ouders moeder: moeder_John
        erkendDoor vader_John
        identificatienummers bsn: 488209481, anummer: 1323695090
    }
}
slaOp(John)
When voor persoon 488209481 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie mutatielevering obv geboorte tijdstip registratie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 488209481, 306741817, 150018502 |

Scenario: 2. Als tijdstipRegistratie van de geboorte voor 1900/06/01 is dan komt de persoon NIET in de doelbinding. Geen bericht wordt geleverd.

When voor persoon 488209481 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie mutatielevering tijdstip registratie voor het jaar 1900 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3. Een Attendering is gemaakt die een bericht verstuurt wanneer een tijdstipRegistratie als attenderingscriteria is gebruikt

When voor persoon 488209481 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie attendering obv geboorte tijdstip registratie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 488209481, 306741817, 150018502 |


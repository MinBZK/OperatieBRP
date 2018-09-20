Meta:
@sprintnummer       72
@epic               Mutatielevering basis
@auteur             rarij
@jiraIssue          TEAMBRP-2686
@status             Klaar

Narrative:
            Als afnemer
            wil ik dat het inschrijven van een NI-ouder goed wordt verwerkt
            zodat een afnemer met volledige autorisatie, een mutatiebericht ontvangt waarin het ouderdeel is bijgewerkt.

Scenario:   Afnemer met volledige autorisatie ontvangt een mutatiebericht n.a.v. het registreren van een NI-ouder
            Verwacht resultaat:
            - Soort persoon was Niet Ingeschreven en wordt Ingeschreven (soortCode was N en wordt I)
            - Twee groepen ouderschap nl.: Een vervallen en een toegevoegde groep ouderschap
            - Een vervallen groep ouder met daarbij behorende sub-groepen (samengesteldeNaam, geboorte, geslachtsaanduiding)
            - Een toegevoegde groep ouder met daarbij behorende sub-groepen (identificatienummers,samengesteldeNaam,
              geboorte, geslachtsaanduiding)

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
def moeder_Jimmy   =    Persoon.uitDatabase(bsn: 306741817)

def vader_Jimmy_NI = Persoon.nietIngeschrevene(aanvang: 19720101, registratieDatum: 19720101) {
  geboorte {
    op '1972/01/01' te 'Kyoto' land 'Japan'
  }
  samengesteldeNaam(
    stam: 'Kenshin',
    voornamen: 'Rourouni'
  )
  geslacht('MAN')
}
vader_Jimmy_NI = slaOp(vader_Jimmy_NI)

def Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
        op '1980/02/01' te 'Delft' gemeente 'Delft'
        geslacht 'MAN'
        namen {
            voornamen 'Jimmy'
            geslachtsnaam 'Scott'
        }
        ouders moeder: moeder_Jimmy, vader: vader_Jimmy_NI
        identificatienummers bsn: 290972954, anummer: 2000603257
    }
 }
Jimmy = slaOp(Jimmy)

def vader_Jimmy_I = Persoon.ingeschreveneVan(vader_Jimmy_NI)     {
  vestigingInNederland(partij: 34401, aanvang: 19800403, toelichting: 'Verkrijging NL Nationaliteit', registratieDatum: 19800403) {
            identificatienummers(bsn: 290650136, anummer: 2000603925)
              nationaliteiten 'Japanse'
              ouderVan(Jimmy)
              partnerVan(moeder_Jimmy)
  }
}
slaOp(vader_Jimmy_I)

When voor persoon 290972954 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut              | verwachteWaardes          |
| identificatienummers | burgerservicenummer    | 290972954, 290650136      |

And hebben attributen in voorkomens de volgende waardes:
| groep   | nummer | attribuut | verwachteWaarde |
| persoon | 2      | soortCode | N               |
| persoon | 3      | soortCode | I               |

And heeft het bericht 2 groepen 'ouderschap'
And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep         | nummer    | verwerkingssoort  |
| ouderschap    | 1         | Verval            |
| ouderschap    | 2         | Toevoeging        |

!-- Op dit moment is het helaas niet mogelijk om te controleren of de verwerkingssoort behorende bij een groep wel/niet
!-- klopt, daar dit stuk functionaliteit nog niet gebouwd is. Derhalve zal dit middels een workaround getest worden nl.:
!-- door te kijken naar de verwerkingssoorten van de groepen binnen object ouder. Deze moeten ook zijn vervallen.

And heeft het bericht 2 groepen 'ouder'
And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                | nummer | verwerkingssoort |
| samengesteldeNaam    | 3      | Verval           |
| geboorte             | 2      | Verval           |
| geslachtsaanduiding  | 3      | Verval           |
| identificatienummers | 2      | Toevoeging       |
| samengesteldeNaam    | 4      | Toevoeging       |
| geboorte             | 3      | Toevoeging       |
| geslachtsaanduiding  | 4      | Toevoeging       |

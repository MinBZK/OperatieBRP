Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.AA
@regels             R1334,R1335,R1983,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Attendering met plaatsen afnemerindicatie

Narrative:
    Afnemer heeft diensten 'Mutatielevering op afnemerindicatie' en 'Attendering met plaatsen afnemerindicatie'.
    Het attenderingscriterium is ingesteld op een wijziging van de bijhoudingsgemeente. De populatiebeperking
    eist dat de geboorte.woonplaatsnaam gelijk is aan "Giessenlanden", om leveringen op andere testcases uit te sluiten.

    Scenario 1: Persoon komt binnen doelbinding. Attendering plaatst een afnemerindicatie en stuurt een VolledigBericht
    Scenario 2: De betreffende persoon wordt nogmaals bijgehouden maar blijft binnen de doelbinding. Er wordt enkel een MutatieBericht verzonden
    Scenario 3: Persoon wordt geboren en komt in doelbinding van het abonnement terecht en wordt afnemerindicatie geplaatst, maar met Verstrekkingsbeperking voor Partij

Scenario:   1. Persoon wordt geboren en komt in doelbinding van het abonnement terecht en wordt afnemerindicatie geplaatst.
            Logisch testgeval:  R1335_01, R1352_01, R1983_04, R1993_01
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -   Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen Attendering met plaatsing afnemerindicatie
Given de personen 420178648 zijn verwijderd
And de database is gereset voor de personen 306867837, 306741817
And de persoon beschrijvingen:
def adam    = Persoon.uitDatabase(bsn: 306867837)
def eva     = Persoon.uitDatabase(bsn: 306741817)

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Giessenlanden' gemeente 689
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 420178648, anummer: 1729786258
    }
}
slaOp(tester)

When voor persoon 420178648 wordt de laatste handeling geleverd

And het volledigbericht voor abonnement Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut              | verwachteWaardes                           |
| parameters           | soortSynchronisatie    | Volledigbericht                            |
| parameters           | categorieDienst        | Attendering                                |
| parameters           | effectAfnemerindicatie | Plaatsing                                  |
| parameters           | abonnementNaam         | Attendering met plaatsing afnemerindicatie |
| synchronisatie       | partijCode             | 034401, 034401, 034401                     |
| identificatienummers | burgerservicenummer    | 420178648, 306867837, 306741817            |


Scenario:   2. Persoon wordt gewijzigd, blijft binnen doelbinding, afnemerindicatie bestaat reeds
            Logisch testgeval:  R1335_02, R1352_02, R1983_04, R1993_01
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -   Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd
                -  DatumAanvangMaterielePeriode = Komt niet terug in het antwoordbericht
                -  DatumEindeVolgen = Komt niet terug in het antwoordbericht
            Bevinding: R1352 dienst mutatielevering op basis van afnemerindicatie levert geen mutatiebericht in combinatie met dienst attendering
                        JIRA-ISSUE: TEAMBRP-4495

Meta:
@status     Onderhanden

Given de database is aangepast met: update autaut.dienst set attenderingscriterium = 'WAAR' where id=132
Given de database is aangepast met: update autaut.dienst set attenderingscriterium = 'WAAR' where id=133
Given relevante abonnementen Attendering met plaatsing afnemerindicatie
Given de database is aangepast met: update autaut.dienst set attenderingscriterium = 'GEWIJZIGD(oud, nieuw, [bijhouding.bijhoudingspartij])' where id=132
Given de database is aangepast met: update autaut.dienst set attenderingscriterium = NULL where id=133

Given de persoon beschrijvingen:
def tester = uitDatabase bsn: 420178648

Persoon.nieuweGebeurtenissenVoor(tester) {

    naamswijziging(aanvang:20150601) {
            geslachtsnaam(stam:'Smith').wordt(stam:'Vries', voorvoegsel:'van')
    }
}
slaOp(tester)

When voor persoon 420178648 wordt de laatste handeling geleverd

And het mutatiebericht voor abonnement Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                    | attribuut           | verwachteWaardes                              |
| parameters               | soortSynchronisatie | Mutatiebericht                                |
| parameters               | categorieDienst     | Mutatielevering op basis van afnemerindicatie |
| administratieveHandeling | categorie           | Actualisering                                 |
| parameters               | abonnementNaam      | Attendering met plaatsing afnemerindicatie    |
| synchronisatie           | partijCode          | 051201, 051201, 034401, 034401, 051201        |
| identificatienummers     | burgerservicenummer | 420178648                                     |

When het volledigbericht voor leveringautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   3. Persoon wordt geboren en komt in doelbinding van het abonnement terecht en wordt afnemerindicatie geplaatst.
            Verstrekkingsbeperking voor Partij
            Logisch testgeval:  R1335_01, R1352_01, R1983_03, R1993_01, R1342_02
            Verwacht resultaat: Geen bericht
            Bevinding: R1983 verstrekkingsbeperking moet ervoor zorgen dat er niet geleverd wordt, maar dit gebeurt niet
                        JIRA-ISSUE: TEAMBRP-4496


Meta:
@status     Onderhanden

Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'TRUE' where id=347
Given relevante abonnementen Attendering met plaatsing afnemerindicatie
Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'FALSE' where id=347

Given de personen 420178648 zijn verwijderd
And de database is gereset voor de personen 306867837, 306741817
And de persoon beschrijvingen:
def adam    = Persoon.uitDatabase(bsn: 306867837)
def eva     = Persoon.uitDatabase(bsn: 306741817)

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Giessenlanden' gemeente 689
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 420178648, anummer: 1729786258
    }
    verstrekkingsbeperking() {
        registratieBeperkingen( partij: 34401 )
    }
}
slaOp(tester)

When voor persoon 420178648 wordt de laatste handeling geleverd

When het volledigbericht voor leveringautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

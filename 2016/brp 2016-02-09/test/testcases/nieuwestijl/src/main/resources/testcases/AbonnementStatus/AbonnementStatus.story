Meta:
@sprintnummer       81
@epic               PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur             aapos
@jiraIssue          TEAMBRP-3404
@status             Uitgeschakeld
@regels             R2000, R2001
@sleutelwoorden     Abonnement status

Narrative:
            Als beheerder
            wil ik dat er alleen berichten worden aangemaakt
            als Dienst en Abonnement toestand 'Definitief' hebben.

== Controleregels ==
R2000	 	De toestand van de leveringsdienst moet Definitief zijn
R2001	 	De toestand van het abonnement moet Definitief zijn



== Acceptatie Criteria ==
Accepetatiecriteria:
Voor alle Mutatieleveringen en Attenderingen geldt:
- Zolang Abonnement en Dienst toestand Definitief hebben, blijft de bestaande werking overeind (Mutatieberichten / VolledigBerichten / Afnemerindicaties)
- Wanneer Abonnement de toestand <> Definitief krijgt, worden onder dat Abonnement voor geen enkele Dienst meer berichten aangemaakt, en worden er geen afnemerindicaties geplaatst (bij een dienst Attendering met plaatsen)
- Wanneer het Abonnement wel de toestand Definitief houdt, maar een Dienst daaronder <> Definitief is, dan maakt die Dienst geen berichten meer aan, en maakt die dienst geen afnemerindicaties meer aan (bij Attendering met plaatsen). De overige diensten onder dat Abonnement blijven normaal werken.
- Als zowel Abonnement als Dienst <> Definitief zijn: idem als bij Abonnement <> Definitief



Scenario: 0. Check of het abonnement levert wanneer deze toestand 'definitief' heeft
Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut                     | verwachteWaardes          |
| parameters                | categorieDienst               | Attendering               |
| parameters                | effectAfnemerindicatie        | Plaatsing                 |

Scenario: 1. Abonnement met dienst attendering met plaatsen afnemerindicatie heeft toestand <> aan definitief, er worden geen berichten aangemaakt, er worden geen afnemerindicaties geplaatst
Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
And de database is aangepast met: update autaut.abonnement set toestand=2 where naam='Attendering met plaatsing afnemerindicatie Toestand'
Given de cache is herladen
And de database is aangepast met: update autaut.abonnement set toestand=4 where naam='Attendering met plaatsing afnemerindicatie Toestand'
Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 2. Abonnement met dienst attendering met plaatsen afnemerindicatie heeft toestand <> aan definitief, er worden geen berichten aangemaakt, er worden geen afnemerindicaties geplaatst
Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de database is aangepast met: update autaut.abonnement set toestand=1 where naam='Attendering met plaatsing afnemerindicatie Toestand'
Given de cache is herladen
Given de database is aangepast met: update autaut.abonnement set toestand=4 where naam='Attendering met plaatsing afnemerindicatie Toestand'

Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3. Dienst gekoppeld aan het abonnement heeft toestand <> Definitief

Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de database is aangepast met: update autaut.dienst set dateinde=null,toestand=3
                                  where abonnement=(select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')
Given de cache is herladen
Given de database is aangepast met: update autaut.dienst set dateinde=null,toestand=4
                                      where abonnement=(select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')
Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3b. Enkel 1 van de diensten binnen het abonnement heeft een toestand <> definitief

Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de database is aangepast met: update autaut.dienst set dateinde=null,toestand=3
                                  where abonnement=(select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')
                                  and attenderingscriterium is not null
Given de cache is herladen
Given de database is aangepast met: update autaut.dienst set dateinde=null,toestand=4
                                      where abonnement=(select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')

Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 4. Zowel het abonnement als de dienst hebben toestand <> definitief

Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817

Given de database is aangepast met: update autaut.abonnement set toestand=2 where naam='Attendering met plaatsing afnemerindicatie Toestand'
Given de database is aangepast met: update autaut.dienst set toestand=2
                                  where abonnement=(select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')
Given de cache is herladen
Given de database is aangepast met: update autaut.abonnement set toestand=4 where naam='Attendering met plaatsing afnemerindicatie Toestand'
Given de database is aangepast met: update autaut.dienst set toestand=4
                                      where abonnement=(select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')

Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 5. plaats afnemerindicatie door partij met abonnement waarvan status <> definitief
Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de database is aangepast met: update autaut.abonnement set toestand=1 where naam='Attendering met plaatsing afnemerindicatie Toestand'
Given de cache is herladen
Given de database is aangepast met: update autaut.abonnement set toestand=4 where naam='Attendering met plaatsing afnemerindicatie Toestand'

Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand plaatsing_afnemerind.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | R1260 | Binnen het opgegeven abonnement bestaat geen dienst van de aangeroepen soort.
 | BRLV0020 | De toestand van het opgegeven abonnement moet definitief zijn.

Scenario: 6 toegang abonnement heeft datum einde geldigheid in het verleden
Given de personen 409279213 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de database is aangepast met: update autaut.toegangabonnement set dateinde='20140101'
                                  where abonnement = (select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')
Given de cache is herladen
Given de database is aangepast met: update autaut.toegangabonnement set dateinde=null
                                      where abonnement = (select id from autaut.abonnement where naam='Attendering met plaatsing afnemerindicatie Toestand')
Given de persoon beschrijvingen:
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
        identificatienummers bsn: 409279213, anummer: 1584815890
    }
}

slaOp(tester)

When voor persoon 409279213 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie Toestand is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

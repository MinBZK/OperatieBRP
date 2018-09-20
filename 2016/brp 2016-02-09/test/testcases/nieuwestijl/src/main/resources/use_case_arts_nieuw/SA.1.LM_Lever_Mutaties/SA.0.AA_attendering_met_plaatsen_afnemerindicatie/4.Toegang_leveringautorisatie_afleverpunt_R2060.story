Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Onderhanden
@usecase            SA.0.AA
@regels             R1334,R1335,R1983,R1993,R1994,R2000,R2057,R2060,R2062
@sleutelwoorden     Attendering met plaatsen afnemerindicatie

Narrative:
Spontane berichten (het initiatief voor levering ligt bij de BRP) voor een bepaalde partij worden alleen geleverd via de Toegang leveringsautorisatie van deze partij waarbij een Toegang leveringsautorisatie.Afleverpunt is opgenomen.
Opmerking: Dit speelt als er voor een Leveringsautorisatie van een bepaalde partij meerdere Toegang leveringsautorisatie bestaan. Van deze Toegang leveringsautorisatie mag er maar één een Toegang leveringsautorisatie.Afleverpunt hebben.


Scenario: 1 Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
                        Toegang Leveringsautorisaties is >1
                        Toegang leveringsautorisatie afleverpunt = 1
                        Logisch Testgeval: R2060_02
                        Verwacht Resultaat: Niet geautoriseerd, dus geen levering



Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie, /levering_autorisaties/Attendering_met_plaatsing_afnemerindicatie_gemeente_utrecht

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

And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 2 Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
                        Toegang Leveringsautorisaties is >1
                        Toegang leveringsautorisatie afleverpunt > 1
                        Logisch Testgeval: R2060_03
                        Verwacht Resultaat: Niet geautoriseerd, dus geen levering


Given leveringsautorisatie uit /levering_autorisaties/Attendering_met_plaatsing_afnemerindicatie_twee_toegangleveringsautorisaties

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

And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie twee toegangleveringsautorisaties is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 3 Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
                        Toegang Leveringsautorisaties is = 1
                        Toegang leveringsautorisatie afleverpunt is NULL
                        Logisch Testgeval: R2060_04
                        Verwacht Resultaat: Niet geautoriseerd, dus geen levering


Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set afleverpunt = NULL where id=1
Given de cache is herladen

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

And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
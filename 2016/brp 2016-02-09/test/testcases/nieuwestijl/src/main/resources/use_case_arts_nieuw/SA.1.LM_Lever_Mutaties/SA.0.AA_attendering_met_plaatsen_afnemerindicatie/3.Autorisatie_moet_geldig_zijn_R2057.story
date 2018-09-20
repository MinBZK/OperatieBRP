Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Onderhanden
@usecase            SA.0.AA
@regels             R1334,R1335,R1983,R1993,R1994,R2000,R2057,R2060,R2062
@sleutelwoorden     Attendering met plaatsen afnemerindicatie

Narrative:
Bij het leveren naar aanleiding van een Administratieve handeling geldt dat er alleen berichten worden aangemaakt en afnemerindicaties onderhouden als:
1.	de Dienst waarvoor geleverd wordt geldig is,
2.	de Dienstbundel waarin deze dienst zit geldig is,
3.	de Toegang leveringsautorisatie waarvoor geleverd wordt geldig is en
4.	de Leveringsautorisatie waarbij deze Toegang leveringsautorisatie hoort geldig is.

Scenario: 4 Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
            Dienst.Datum ingang > systeemdatum
            Logisch Testgeval: R2057_04
            Verwacht Resultaat: Niet geautoriseerd, dus geen levering

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de database is aangepast met: update

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




Scenario: 5

Scenario: 6

Scenario: 7

Scenario: 8

Scenario: 9

Scenario: 10

Scenario: 11

Scenario: 12

Scenario: 13

Scenario: 14

Scenario: 15

Scenario: 16

Scenario: 17



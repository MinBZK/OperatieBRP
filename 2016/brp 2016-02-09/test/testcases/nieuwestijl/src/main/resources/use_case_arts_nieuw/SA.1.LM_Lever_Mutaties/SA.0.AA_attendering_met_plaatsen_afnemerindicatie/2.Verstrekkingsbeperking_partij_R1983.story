Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.AA, SA.1.LM, LV.1.MB, LV.1.LE
@regels             R1334,R1335,R1983,R1993,R1994,R2000,R2057,R2060,R2062
@sleutelwoorden     Attendering met plaatsen afnemerindicatie, Lever Mutaties, Maak BRP Bericht, Leveren

Narrative:
Indien de centrale voorzieningen op basis van een administratieve handeling of selectie beogen gegevens over een persoon aan een afnemer te leveren, maar de persoon heeft om een verstrekkingsbeperking verzocht voor die afnemer, dan worden de gegevens van de persoon niet aan de betreffende afnemer geleverd. Dit geldt voor de diensten:
•	Attendering
•	Attendering met plaatsen afnemerindicatie
•	Selectie
•	Selectie met plaatsen afnemerindicatie


Scenario:   3. Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
            Verstrekkingsbeperking voor Partij
            Logische testgevallen expliciet: R1983_03, R1342_02
            Logische testgevallen impliciet:  R1335_01, R1352_01, R1993_01, R2000_01, R2057_02, R2060_01, R2062_01
            Verwacht resultaat: Geen bericht geleverd
            Bevinding: R1983 verstrekkingsbeperking moet ervoor zorgen dat er niet geleverd wordt, maar dit gebeurt niet
                        JIRA-ISSUE: TEAMBRP-4496

Meta:
@status     Onderhanden

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'TRUE' where id=348
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
    verstrekkingsbeperking() {
        registratieBeperkingen( partij: 34401 )
    }
}
slaOp(tester)

When voor persoon 420178648 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'FALSE' where id=348
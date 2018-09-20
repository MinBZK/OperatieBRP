Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.MA
@regels             R1314,R1315,R1338,R1343,R1544,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:  Als afnemer
            wil ik een volledigBericht ontvangen, in het geval dat een bijzondere LO3 bijhouding een niet begrijpelijk BRP mutatiebericht zou opleveren,
            zodat het voor mij net zo begrijkpelijk is als bij een BRP bijhouding


Scenario:   7 BRPbijhouding (verhuizing binnenGemeente) met een abonnement o.b.v. afnemerindicatie, waarbij Administratieve handeling.Soort ongelijk is aan CODE 99997 (GBA - Bijhouding overig)
            Logisch Testgeval Use Case: R1338_01
            Verwacht resultaat:
            - Een mutatieBericht

Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 984680457 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'

        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 984680457, anummer: 4909274898
    }
    afnemerindicaties {
                plaatsVoor(afnemer: 17401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie') {
                    datumAanvangMaterielePeriode '2012/05/15'
                }
    }
    verhuizing(aanvang: 20120101) {
            binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Giessenlanden"
    }
}
slaOp(Bruce)

When voor persoon 984680457 wordt de laatste handeling geleverd

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   8 BRPbijhouding (verhuizing binnenGemeente) met een abonnement o.b.v. afnemerindicatie, waarbij Administratieve handeling.Soort gelijk is aan CODE 99997 (GBA - Bijhouding overig)
            Logisch Testgeval Use Case: R1338_02
            Verwacht resultaat:
            - Een volledigBericht i.p.v. een mutatieBericht

Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 984680457 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'

        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 984680457, anummer: 4909274898
    }
    afnemerindicaties {
                plaatsVoor(afnemer: 17401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie') {
                    datumAanvangMaterielePeriode '2012/05/15'
                }
    }
    GBABijhoudingOverig() {
            binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Giessenlanden"
    }
}
slaOp(Bruce)

When voor persoon 984680457 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

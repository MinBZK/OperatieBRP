Meta:
@sprintnummer           74
@epic                   Afhankelijkheden migratie
@auteur                 dadij
@jiraIssue              TEAMBRP-2841
@status                 Onderhanden
@regels                 VR00057, R1333, VR00126, R1338

Narrative:  Als afnemer
            wil ik een volledigBericht ontvangen, in het geval dat een bijzondere LO3 bijhouding een niet begrijpelijk BRP mutatiebericht zou opleveren,
            zodat het voor mij net zo begrijkpelijk is als bij een BRP bijhouding

            R1333	VR00057	Mutatielevering op personen binnen doelgroep abonnement
            R1338	VR00126	Verwerkingslogica dienst Mutatielevering op basis van afnemerindicatie

Scenario:   1. BRPbijhouding (verhuizing binnenGemeente) met een abonnement o.b.v. doelbinding
            Verwacht resultaat:
            - Een mutatieBericht

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_Haarlem_2000-2099
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Haarlem' gemeente 'Haarlem'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'
        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
}
slaOp(Bruce)

def Bruce1 = Persoon.uitDatabase(bsn: 361599882)
nieuweGebeurtenissenVoor(Bruce1) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 19930731, registratieDatum: 19930731) {
        naarGemeente 'Haarlem',
           straat: 'Dorpstraat', nummer: 13, postcode: '2000AA', woonplaats: "Haarlem"
    }
    verhuizing(aanvang: 20120101, registratieDatum: 20120101) {
        binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Haarlem"
    }
}
slaOp(Bruce1)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   2. GBAbijhouding (verhuizing binnenGemeente) met een abonnement o.b.v. doelbinding
            Verwacht resultaat:
            - Een volledigBericht i.p.v. een mutatieBericht

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_Haarlem_2000-2099
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Haarlem' gemeente 'Haarlem'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'
        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
}
slaOp(Bruce)

def Bruce1 = Persoon.uitDatabase(bsn: 361599882)
nieuweGebeurtenissenVoor(Bruce1) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 19930731, registratieDatum: 19930731) {
        naarGemeente 'Haarlem',
           straat: 'Dorpstraat', nummer: 13, postcode: '2000AA', woonplaats: "Haarlem"
    }
    GBABijhoudingOverig() {
        binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Haarlem"
    }
}
slaOp(Bruce1)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het mutatiebericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   3. BRPbijhouding (verhuizing binnenGemeente) met een abonnement o.b.v. afnemerindicatie
            Verwacht resultaat:
            - Een mutatieBericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
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
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
    verhuizing(aanvang: 20120101, registratieDatum: 20120101) {
            binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Giessenlanden"
    }
}
slaOp(Bruce)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 361599882 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   4. GBAbijhouding (verhuizing binnenGemeente) met een abonnement o.b.v. afnemerindicatie
            Verwacht resultaat:
            - Een volledigBericht i.p.v. een mutatieBericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
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
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
    GBABijhoudingOverig() {
            binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Giessenlanden"
    }
}
slaOp(Bruce)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 361599882 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

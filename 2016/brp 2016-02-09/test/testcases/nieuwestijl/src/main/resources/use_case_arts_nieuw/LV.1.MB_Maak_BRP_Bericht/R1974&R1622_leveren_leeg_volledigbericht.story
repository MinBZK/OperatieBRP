Meta:
@sprintnummer           67
@epic                   Change yyyynn: CorLev - Element tabel
@auteur                 dihoe
@jiraIssue              TEAMBRP-2434,TEAMBRP-2581
@status                 Klaar
@regels                 VR00052,VR00081,R1974,R1622

Narrative:
    Als stelselbeheerder
    wil ik dat de gegevensautorisatie blijft werken als de Abonnementen de groepen en attributen aanwijzen via de Elementtabel.
    Deze testscenario test acceptatiecriteria 2.
    In deze test wordt een volledigbericht gecontroleerd waarin geen relevante autorisatie aanwezig is. Dit

Scenario: 1. Afnemer is niet geautoriseerd voor groep samengesteldeNaam, volledigbericht bevat een persoon zonder inhoud.
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_attributen_voor_groep_samengesteldenaam
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 410160441 zijn verwijderd
Given de persoon beschrijvingen:
def Laar    = uitDatabase bsn: 306867837
def Verheul = uitDatabase bsn: 306741817

testpersoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: Verheul, vader: Laar
        identificatienummers bsn: 410160441, anummer: 2489643050
    }
}

slaOp(testpersoon)

When voor persoon 410160441 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo zonder attributen voor groep samengesteldenaam is ontvangen en wordt bekeken
Then heeft het bericht geen kinderen voor xpath //brp:persoon

When het mutatiebericht voor leveringsautorisatie Abo zonder attributen voor groep samengesteldenaam is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


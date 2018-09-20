Meta:
@sprintnummer           67
@epic                   Change yyyynn: CorLev - Element tabel
@auteur                 dihoe
@jiraIssue              TEAMBRP-2434,TEAMBRP-2581
@status                 Klaar
@regels                 VR00052, R1974, VR00081, R1547

Narrative:
    Als stelselbeheerder
    wil ik dat de gegevensautorisatie blijft werken als de Abonnementen de groepen en attributen aanwijzen via de Elementtabel.
    Deze testscenario test acceptatiecriteria 2.
    In deze test wordt een volledigbericht gecontroleerd waarin geen relevante autorisatie aanwezig is.

    R1974	VR00052	Alleen attributen waarvoor autorisatie bestaat worden geleverd
    R1547	VR00081	Leveren van DatumEindeGeldigheid mag alleen bij autorisatie op materiele historie


Scenario: 1. Afnemer is niet geautoriseerd voor groep samengesteldeNaam, volledigbericht bevat een persoon zonder inhoud.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_attributen_voor_groep_samengesteldenaam
Given de persoon beschrijvingen:
def Laar    = uitDatabase bsn: 306867837
def Verheul = uitDatabase bsn: 306741817

testpersoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
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
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig  |
| persoon            | 1      | samengesteldeNaam       | nee       |



Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-4067
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 VR00116,R2063,R2065

Narrative: Stel het onderzoeksdeel samen voor een bericht

Scenario: 1. Onderzoek gestart op een ontbrekende gegeven in persoonsdeel en een onderzoek op een aanwezig gegeven in het persoonsdeel

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 586783593 zijn verwijderd
Given de standaardpersoon Olivia met bsn 586783593 en anr 9098497810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(586783593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap en geboortedatum', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 586783593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde        |
| gegevenInOnderzoek | 1      | elementNaam | Persoon.Geboorte.Datum |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut              | aanwezig |
| onderzoek          | 2      | datumAanvang           | ja       |
| onderzoek          | 2      | verwachteAfhandeldatum | ja       |
| onderzoek          | 2      | omschrijving           | ja       |
| onderzoek          | 3      | datumAanvang           | nee      |
| onderzoek          | 3      | verwachteAfhandeldatum | nee      |
| onderzoek          | 3      | omschrijving           | nee      |
| gegevenInOnderzoek | 2      | elementNaam            | nee      |


Scenario: 2. Onderzoek gestart op een ontbrekende gegeven in persoonsdeel en een niet geautoriseerd attribuut

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 378101961 zijn verwijderd
Given de standaardpersoon Olivia met bsn 378101961 en anr 3584535826 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(378101961)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 378101961 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden




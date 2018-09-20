Meta:
@auteur                 jagol
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-4309
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 R1319

Narrative: Stel het onderzoeksdeel samen voor een bericht, onderzoek op groepen is voorlopig op on hold totdat dit helder is vanuit de bijhouding services voor onderzoek

Scenario: 1. start onderzoek op een object

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 826569353 zijn verwijderd
Given de standaardpersoon Olivia met bsn 826569353 en anr 2084945042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(826569353)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op voornaam object', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Voornaam')
    }

}
slaOp(persoon)

When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                         |
| synchronisatie     | 1      | naam                   | Aanvang onderzoek                       |
| synchronisatie     | 1      | partijCode             | 059401                                  |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                              |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-08-01                              |
| onderzoek          | 2      | omschrijving           | Onderzoek is gestart op voornaam object |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Voornaam                        |
| actie              | 1      | soortNaam              | Registratie onderzoek                   |

Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus1
@status                 Klaar
@regels                 VR00116,R1562,R2063,R2065,R1973,R2051

Narrative: Casus 1. Een administratieve handeling die een persoonsgegeven wijzigt of opvoert en dat gegeven tevens in onderzoek plaatst.

Scenario: 1. start onderzoek gelijktijdig met wijziging van het gegeven dat in onderzoek wordt geplaatst, er wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 826569353 zijn verwijderd
Given de standaardpersoon Olivia met bsn 826569353 en anr 2084945042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(826569353)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op geboortedatum', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                       |
| synchronisatie     | 1      | naam                   | Aanvang onderzoek                     |
| synchronisatie     | 1      | partijCode             | 059401                                |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                            |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-04-01                            |
| onderzoek          | 2      | omschrijving           | Onderzoek is gestart op geboortedatum |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Geboorte.Datum                |
| actie              | 1      | soortNaam              | Registratie onderzoek                 |


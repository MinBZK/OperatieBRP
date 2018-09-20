Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-2572
@sleutelwoorden         onderzoek
@status                 Uitgeschakeld
@regels                 VR00058,R1544

Narrative: Begrens het bericht op datum aanvang materiële periode

Scenario: 1. Volledigbericht met een Onderzoek.DatumEinde groter dan de Datum aanvang materiele periode (2015-07-01) bij de afnemerindicatie, er wordt geleverd
Casus 51

Given leveringsautorisatie uit Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op geboortedatum', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
    onderzoek(partij: 59401) {
        afgeslotenOp(eindDatum:morgen())
    }
}
slaOp(persoon)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand volledigbericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 407124585 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op geboortedatum |
| onderzoek          | 3      | omschrijving | Onderzoek is gestart op geboortedatum |
| onderzoek          | 2      | statusNaam   | Afgesloten                            |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Geboorte.Datum                |

Scenario: 2. Volledigbericht met een Onderzoek.DatumEinde kleiner dan de Datum aanvang materiele periode (vandaag) bij de afnemerindicatie, er wordt geleverd zonder onderzoek
Casus 50

Given leveringsautorisatie uit Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op geboortedatum', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
    onderzoek(partij: 59401) {
        afgeslotenOp(eindDatum: gisteren())
    }
}
slaOp(persoon)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand volledigbericht_datum_aanvang_materiele_periode_bij_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 407124585 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut    | aanwezig |
| onderzoek          | 2      | omschrijving | nee      |
| onderzoek          | 3      | omschrijving | nee      |
| onderzoek          | 2      | statusNaam   | nee      |
| gegevenInOnderzoek | 1      | elementNaam  | nee      |

Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek
@jiraIssue              TEAMBRP-2575
@status                 Klaar
@regels                 VR00083,R1549

Narrative: Onderzoeksdeel: Leveren van ActieInhoud en ActieVerval mag alleen bij autorisatie voor verantwoordingsinformatie
                           Als de bijhouder een ABO-partij betreft, dan moeten de verantwoordingsgegevens wel geleverd worden,
                           zie hiervoor R1545 - Verplicht leveren van ABO-partij en rechtsgrond.

Scenario: 1. Start onderzoek met een bijhouder die een ABO-partij is (rol = 4)

Given de personen 627129705, 304953337, 727168265 zijn verwijderd
Given de standaardpersoon Olivia met bsn 727168265 en anr 1703169042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(727168265)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 710149, registratieDatum: 20150301) {
        gestartOp(aanvangsDatum:'2015-03-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-09-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Scenario: 2. Wijzig onderzoek

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(727168265)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 710149, registratieDatum: 20150701) {
        wijzigOnderzoek(wijzigingsDatum:'2015-07-01', omschrijving:'Omschrijving en verwachte afhandeldatum gewijzigd', aanvangsDatum: '2015-03-01', verwachteAfhandelDatum: '2015-10-01')
    }
}
slaOp(persoon)

Scenario: 3. Beeindig onderzoek

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(727168265)
nieuweGebeurtenissenVoor(persoon) {
     onderzoek(partij: 710149, registratieDatum: 20151001) {
            afgeslotenOp(eindDatum:'2015-10-01')
        }
}
slaOp(persoon)

Scenario: 4. Resultaat attributen actieInhoud en actieVerval worden wel getoond bij Abonnement met groep Nadere verantwoording = Nee
             indien bijhouder een ABO-partij is (rol = 4)
Given leveringsautorisatie uit /levering_autorisaties/Abo_geen_verantwoording
When voor persoon 727168265 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Abo geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut           | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud         | ja       |
| afgeleidAdministratief | 2      | actieVerval         | ja       |
| onderzoek              | 2      | actieInhoud         | ja       |
| onderzoek              | 3      | actieVerval         | ja       |


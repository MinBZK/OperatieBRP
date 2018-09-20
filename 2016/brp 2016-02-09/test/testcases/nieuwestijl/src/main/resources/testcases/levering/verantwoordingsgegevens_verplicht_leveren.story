Meta:
@auteur                 dihoe
@epic                   Levering verantwoordingsinformatie
@jiraIssue              TEAMBRP-2139
@status                 Klaar
@regels                 VR00075,VR00083,R1545,R1549

Narrative: Verplicht leveren van Verantwoordingsgegevens (ABO/Rechtsgrond)
R1545 (VR00075) Verplicht leveren van ABO-partij en rechtsgrond.
R1549 (VR00083) Leveren van ActieInhoud, ActieAanpassingGeldigheid en ActieVerval mag alleen bij autorisatie voor verantwoordingsinformatie

Scenario: 1. Bijhoudings partij heeft rol = 4, abonnement staat open, velden ActieInhoud, ActieAanpassingGeldigheid en ActieVerval worden getoond

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 925828361, 574926537, 439894153 zijn verwijderd
Given de standaardpersoon Nico met bsn 439894153 en anr 4812787986 met extra gebeurtenissen:
verhuizing(aanvang:20150101, registratieDatum: 20150101) {
    naarBuitenland('Canada',
        adres: '363 Poplar Drive, Dartmouth'
    )
}

Given de persoon beschrijvingen:
nico = Persoon.metBsn(439894153)
nieuweGebeurtenissenVoor(nico) {
    naamswijziging(aanvang: 20151001, partij: 710149, registratieDatum: 20151001) {
            document grond:'rechtsgrondomschrijving 1'
            geslachtsnaam(stam:'Dijkshoorn').wordt(stam:'Dijk', voorvoegsel:'van')
    }
}
slaOp(nico)

When voor persoon 439894153 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep             | nummer | attribuut                 | aanwezig |
| samengesteldeNaam | 1      | actieInhoud               | ja       |
| samengesteldeNaam | 2      | actieAanpassingGeldigheid | ja       |
| samengesteldeNaam | 3      | actieVerval               | ja       |


Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut  | verwachteWaarde |
| administratieveHandeling | 1 | partijCode              | 710149                    |
| bron                     | 1 | rechtsgrondomschrijving | rechtsgrondomschrijving 1 |

Scenario: 2. Bijhoudings partij heeft rol = 4, abonnement zonder formele historie en verantwoording, velden ActieInhoud, ActieAanpassingGeldigheid en ActieVerval worden getoond

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_formele_historie_en_verantwoording
Given de personen 925828361, 574926537, 439894153 zijn verwijderd
Given de standaardpersoon Nico met bsn 439894153 en anr 4812787986 met extra gebeurtenissen:
verhuizing(aanvang:20150101, registratieDatum: 20150101) {
    naarBuitenland('Canada',
        adres: '363 Poplar Drive, Dartmouth'
    )
}

Given de persoon beschrijvingen:
nico = Persoon.metBsn(439894153)
nieuweGebeurtenissenVoor(nico) {
    naamswijziging(aanvang: 20151001, partij: 710149, registratieDatum: 20151001) {
            document grond:'rechtsgrondomschrijving 1'
            geslachtsnaam(stam:'Dijkshoorn').wordt(stam:'Dijk', voorvoegsel:'van')
    }
}
slaOp(nico)

When voor persoon 439894153 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo zonder formele historie en verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep             | nummer | attribuut                 | aanwezig |
| samengesteldeNaam | 1      | actieInhoud               | ja       |
| samengesteldeNaam | 2      | actieAanpassingGeldigheid | ja       |
| samengesteldeNaam | 3      | actieVerval               | ja       |


Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut               | verwachteWaarde           |
| administratieveHandeling | 1      | partijCode              | 710149                    |
| bron                     | 1      | rechtsgrondomschrijving | rechtsgrondomschrijving 1 |

Scenario: 3. Bijhoudings partij heeft geen rol = 4, abonnement zonder formele historie en verantwoording, velden ActieInhoud, ActieAanpassingGeldigheid en ActieVerval worden niet getoond

Given de personen 925828361, 574926537, 439894153 zijn verwijderd
Given de standaardpersoon Nico met bsn 439894153 en anr 4812787986 met extra gebeurtenissen:
verhuizing(aanvang:20150101, registratieDatum: 20150101) {
    naarBuitenland('Canada',
        adres: '363 Poplar Drive, Dartmouth'
    )
}

Given de persoon beschrijvingen:
nico = Persoon.metBsn(439894153)
nieuweGebeurtenissenVoor(nico) {
    naamswijziging(aanvang:20151001, partij: 34401, registratieDatum: 20151001) {
            document grond:'rechtsgrondomschrijving 1'
            geslachtsnaam(stam:'Dijkshoorn').wordt(stam:'Dijk', voorvoegsel:'van')
    }
}
slaOp(nico)

When voor persoon 439894153 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo zonder formele historie en verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep             | nummer | attribuut                 | aanwezig |
| samengesteldeNaam | 1      | actieInhoud               | nee      |
| samengesteldeNaam | 2      | actieAanpassingGeldigheid | nee      |
| samengesteldeNaam | 3      | actieVerval               | nee      |

Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus19
@status                 Klaar
@regels                 VR00116,R2063,R2065,R1563,R1962,R1973

Narrative: Casus 19. Een administratieve handeling die een gegeven wijzigt of opvoert, dat reeds in onderzoek staat.

Scenario: 1. er is een lopend onderzoek en er vindt een mutatie plaats op het gegeven in onderzoek, leveren zonder onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 826569353 zijn verwijderd
Given de standaardpersoon Olivia met bsn 826569353 en anr 2084945042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(826569353)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150301) {
        gestartOp(aanvangsDatum:'2015-03-01', omschrijving:'Onderzoek is gestart op samengestelde naam', verwachteAfhandelDatum:'2015-12-01')
        gegevensInOnderzoek('Persoon.SamengesteldeNaam.Geslachtsnaamstam')
    }
}
slaOp(persoon)

Persoon.nieuweGebeurtenissenVoor(persoon) {
    naamswijziging(aanvang:20150601, registratieDatum: 20150601) {
            geslachtsnaam(stam:'Burton').wordt(stam:'Dalen', voorvoegsel:'van')
    }
}
slaOp(persoon)

When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut              | aanwezig |
| onderzoek          | 2      | datumAanvang           | nee      |
| onderzoek          | 2      | verwachteAfhandeldatum | nee      |
| onderzoek          | 2      | omschrijving           | nee      |
| gegevenInOnderzoek | 1      | elementNaam            | nee      |


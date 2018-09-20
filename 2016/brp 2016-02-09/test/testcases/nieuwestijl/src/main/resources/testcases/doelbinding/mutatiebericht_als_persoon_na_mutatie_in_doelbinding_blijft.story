Meta:
@sprintnummer           63
@auteur                 koapp
@epic                   Mutatielevering op basis van doelbinding
@jiraIssue              TEAMBRP-1921
@status                 Klaar
@regels                 VR00057,VR00097,R1333,R1556
@sleutelwoorden         oude_testdata

Narrative:
    In order to correct kunnen leveren aan de hand van een administratieve handeling
    As a afnemer
    I want to een mutatiebericht ontvangen als een persoon bij Mutatielevering op doebinding "in de doelbinding" blijft

Scenario: 1. mutatiebericht als persoon door verhuizing in doelbinding blijft
Given de personen 820055177 zijn verwijderd
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given de standaardpersoon Udo met bsn 820055177 en anr 5425435410 zonder extra gebeurtenissen
Given de database is gereset voor de personen 846514953
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(820055177)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Utrecht', aanvang: 20160101, registratieDatum: 20160102) {
        naarGemeente 'Utrecht',
            straat: 'Stadsplateau', nummer: 1, postcode: '3521AZ', woonplaats: "Utrecht"
    }
}
slaOp(persoon)

When voor persoon 820055177 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Mutaties op personen van wie u de bijhoudingspartij heeft id 347 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep      | attribuut           | verwachteWaardes |
| parameters | soortSynchronisatie | Mutatiebericht   |

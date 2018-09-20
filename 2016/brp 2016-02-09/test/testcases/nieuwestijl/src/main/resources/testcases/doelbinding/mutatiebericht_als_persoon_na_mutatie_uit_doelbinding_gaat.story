Meta:
@sprintnummer           63
@epic                   Mutatielevering op basis van doelbinding
@auteur                 koapp
@jiraIssue              TEAMBRP-1921
@status                 Klaar
@regels                 VR00057,BRLV0028,VR00097,R1333,R1316,R1556

Narrative:
In order to correct kunnen leveren aan de hand van een administratieve handeling
As a afnemer
I want to een mutatiebericht ontvangen als een persoon bij Mutatielevering op doebinding "uit de doelbinding" gaat

Scenario: 1. mutatiebericht als persoon door verhuizing uit doelbinding gaat
!--N.B. dit gebeurt op de dag van het trouwen (handeling 1 = geboorte, handeling 2 = verhuizing , handeling 3 = trouwen, handeling 4 = verhuizing: uit doelbinding, handeling 5 = verhuizing: blijft buiten doelbinding, handeling 6 = indicatie einde)
Given de personen 365503241 zijn verwijderd
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given de database is gereset voor de personen 500142610
Given de standaardpersoon Udo met bsn 365503241 en anr 1278028385 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(365503241)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Almere', aanvang: 20160101, registratieDatum: 20160102) {
        naarGemeente 'Almere',
            straat: 'Stadhuisplein', nummer: 1, postcode: '1315HR', woonplaats: "Almere"
    }
}
slaOp(persoon)

When voor persoon 365503241 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Mutaties op personen van wie u de bijhoudingspartij heeft id 347 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                                                                                                  |
| melding | regelCode | BRLV0028                                                                                                          |
| melding | melding   | De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt. |


Scenario: 2. mutatiebericht als persoon door verhuizing uit doelbinding blijft
!-- N.B. nadat de persoon uit de doelbing is gegaan, volgt er geen mutatiebericht meer
!-- ook geen gelijknamige directory van de scenario

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(365503241)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Almere', aanvang: 20160105, registratieDatum: 20160110) {
        naarGemeente 'Almere',
            straat: 'Stadhuisplein', nummer: 2, postcode: '1315HR', woonplaats: "Almere"
    }
}
slaOp(persoon)

When voor persoon 365503241 wordt de laatste handeling geleverd

Then wacht tot alle berichten zijn ontvangen

When mutatiebericht voor leveringsautorisatie Mutaties op personen van wie u de bijhoudingspartij heeft id 347 wordt bekeken
Then is er geen synchronisatiebericht gevonden

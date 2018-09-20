Meta:
@sprintnummer       86
@epic               Attendering
@auteur             aapos
@jiraIssue          TEAMBRP-4429
@status             Klaar
@regels
@sleutelwoorden     attendering

Narrative:
Als afnemer met dienst attendering wil ik een volledig bericht ontvangen als er een wijziging optreedt die voldoet aan het attenderingscriterium.

Scenario:   1. Attendering - volledig bericht na wijziging postcode

Given leveringsautorisatie uit /levering_autorisaties/attendering/Attenderingen_op_pers_met_gewijzigde_postcode_EN_woonplaatsnaam_(pop.bep.=true)
Given de personen 627129705, 304953337, 729754121 zijn verwijderd
Given de standaardpersoon Olivia met bsn 729754121 en anr 7586367250 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(729754121)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20151017, registratieDatum: 20151017) {
        naarGemeente 'Hillegom',
            straat: 'Dorpsstraat', nummer: 30, postcode: '2180AA', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

When voor persoon 729754121 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attenderingen op pers met gewijzigde postcode EN woonplaatsnaam (pop.bep.=true) is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben de attributen in de groepen de volgende waardes:
| groep          | attribuut           | verwachteWaardes                                                                          |
| parameters     | soortSynchronisatie | Volledigbericht                                                                           |
| synchronisatie | naam                | Verhuizing intergemeentelijk, Olivia, Verhuizing intergemeentelijk, Geboorte in Nederland |


Scenario:   2. Attendering - volledig bericht na wijziging bijhoudingspartij

Given leveringsautorisatie uit /levering_autorisaties/attendering/Attenderingen_op_pers_met_gewijzigde_postcode_EN_woonplaatsnaam_(pop.bep.=true)
Given de personen 627129705, 304953337, 925490441 zijn verwijderd
Given de standaardpersoon Olivia met bsn 925490441 en anr 1828956050 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(925490441)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Groningen', aanvang: 20151010, registratieDatum: 20151010) {
        naarGemeente 'Groningen',
            straat: 'Gedempte Zuiderdiep', nummer: 98, postcode: '9701JB', woonplaats: "Groningen"
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(925490441)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Utrecht', aanvang: 20151017, registratieDatum: 20151017) {
        naarGemeente 'Utrecht',
            straat: 'Stadsplateau', nummer: 1, postcode: '3521AZ', woonplaats: "Utrecht"
    }
}
slaOp(persoon)

When voor persoon 925490441 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attenderingen op pers met gewijzigde postcode EN woonplaatsnaam (pop.bep.=true) is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben de attributen in de groepen de volgende waardes:
| groep          | attribuut           | verwachteWaardes                                                                  |
| parameters     | soortSynchronisatie | Volledigbericht                                                                   |


Scenario: 3. Volledig bericht na verkrijging nederlandse nationaliteit + plaatsing afnemer indicatie
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_geselecteerde_personen_voor_afnemer=505002
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,210010939 zijn verwijderd
Given de standaardpersoon Sandy met bsn 210010939 en anr 9689381650 zonder extra gebeurtenissen

And administratieve handeling van type verkrijgingVreemdeNationaliteit , met de acties registratieNationaliteitNaam
And testdata uit bestand verkrijging_vreemde_nationaliteit_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op geselecteerde personen voor afnemer = 505002 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben de attributen in de groepen de volgende waardes:
| groep      | attribuut              | verwachteWaardes |
| parameters | soortSynchronisatie    | Volledigbericht  |
| parameters | effectAfnemerindicatie | Plaatsing        |

Then is er voor persoon met bsn 210010939 en leveringautorisatie Mutaties op geselecteerde personen voor afnemer = 505002 een afnemerindicatie geplaatst

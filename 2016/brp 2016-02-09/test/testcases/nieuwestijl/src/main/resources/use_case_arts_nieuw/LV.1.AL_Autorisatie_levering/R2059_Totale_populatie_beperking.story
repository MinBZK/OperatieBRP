Meta:
@sprintnummer           91
@epic                   Autenticatie levering
@auteur                 aapos
@jiraIssue              TEAMBRP-4384
@status                 Klaar
@regels                 R2059

Narrative:
Als beheerder wil ik dat leveringen alleen plaats vinden als deze voldoen aan de totale populatie beperking

De 'Totale populatiebeperking' is dan een expressie die bestaat uit de logische EN van:
* A:"Leveringsautorisatie.Populatiebeperking"
* A:"Toegang leveringsautorisatie.Nadere populatiebeperking"
* A:"Dienstbundel.Nadere populatiebeperking"

|                                                        | R2059_L01 | R2059_L02 | R2059_L03 | R2059_L04 | R2059_L05 | R2059_L06 |
| leveringsautorisatie.populatiebeperking                | WAAR      | ONWAAR    | WAAR      | WAAR      | NULL      | WAAR      |
| Toegang leveringsautorisatie.Nadere populatiebeperking | WAAR      | WAAR      | ONWAAR    | WAAR      | NULL      | WAAR      |
| Dienstbundel.Nadere populatiebeperking                 | WAAR      | WAAR      | WAAR      | ONWAAR    | NULL      | ONWAAR    |
| Wordt geleverd?                                        | TRUE      | FALSE     | FALSE     | FALSE     | TRUE      | FALSE     |

Scenario: R2059_L01. Persoon voldoet aan totale populatie beperking, er wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/R2059_TotalePopulatieBeperking/R2059_totale_populatie_beperking
Given de personen 627129705, 304953337, 602714217 zijn verwijderd
Given de standaardpersoon Olivia met bsn 602714217 en anr 9103063826 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(602714217)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 602714217 wordt de laatste handeling geleverd

When het volledigbericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 602714217       |
| geboorte             | 1      | datum               | 1982-12-12      |
| adres                | 1      | postcode            | 2611GW          |

Scenario: R2059_L02. Persoon voldoet niet aan leveringsautorisatie.populatiebeperking, er wordt niet geleverd (persoon heeft geen postcode binnen de pop. bep.)

Given de personen 627129705, 304953337, 602714217 zijn verwijderd
Given de standaardpersoon Olivia met bsn 602714217 en anr 7621808402 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(602714217)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Alkmaar', aanvang: 20160121) {
                naarGemeente 'Alkmaar',
                    straat: 'Mallegatsplein', nummer: 12, postcode: '1815AG', woonplaats: "Alkmaar"
            }
}
slaOp(persoon)

When voor persoon 602714217 wordt de laatste handeling geleverd

When het volledigbericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: R2059_L03. Persoon A voldoet niet aan dienstbundel.Nadere populatiebeperking, er wordt niet geleverd, Persoon B voldoet wel aan dienstbundel.Nadere populatiebeperking, daarvoor wordt wel geleverd
Persoon A voldoet niet aan nadere pop bep. dienstbundel 1 (geboorte.datum ligt voor 1980/12/10)
Persoon B voldoet na verhuizing wel aan nadere pop bep. dienstbundel 2 (geboorte.datum ligt voor 1970/12/10)

Given de personen 627129705, 304953337, 602714217 zijn verwijderd
Given de standaardpersoon Olivia_eerder_geboren met bsn 602714217 en anr 2493601298 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(602714217)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 602714217 wordt de laatste handeling geleverd

When het volledigbericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(627129705)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 627129705 wordt de laatste handeling geleverd

When het volledigbericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 627129705       |
| geboorte             | 1      | datum               | 1960-01-01      |
| adres                | 1      | postcode            | 2611GW          |

Scenario: R2059_L04. Persoon voldoet niet aan toegangleveringsautorisatie.Nadere populatiebeperking, er wordt niet geleverd (persoon.bsn valt niet in doelbinding)

Given de personen 627129705, 304953337, 365532265 zijn verwijderd
Given de standaardpersoon Olivia met bsn 365532265 en anr 1278630290 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(365532265)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 365532265 wordt de laatste handeling geleverd

When het volledigbericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: R2059_L05. Persoon voldoet aan totale populatie beperking, er wordt geleverd (nadere populatie beperking = NULL evalueert naar WAAR)

Given leveringsautorisatie uit /levering_autorisaties/R2059_TotalePopulatieBeperking/R2059_totale_populatie_beperking_NULL
Given de personen 627129705, 304953337, 265211049 zijn verwijderd
Given de standaardpersoon Olivia met bsn 265211049 en anr 2434587410 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(265211049)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 265211049 wordt de laatste handeling geleverd

When het mutatiebericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking NPB is NULL is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 265211049       |
| geboorte             | 1      | datum               | 1982-12-12      |
| adres                | 1      | postcode            | 2611GW          |

Scenario: R2059_L06. Persoon voldoet niet aan totale populatie beperking, er wordt niet geleverd (persoon met onbekende geboorte datum valt niet in doelbinding)
gedeeltelijk bekende geboorte datum voldoet niet aan de strikte evaluatie van de NPB.

Given leveringsautorisatie uit /levering_autorisaties/R2059_TotalePopulatieBeperking/R2059_totale_populatie_beperking
Given de personen 627129705, 304953337, 602714217 zijn verwijderd
Given de standaardpersoon Olivia_onbekende_geboorte_datum met bsn 602714217 en anr 4321367570 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(602714217)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 602714217 wordt de laatste handeling geleverd

When het volledigbericht voor partij 50301 en leveringsautorisatie R2059 totale populatie beperking is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

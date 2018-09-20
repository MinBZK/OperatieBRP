Meta:
@auteur             kedon
@status             Onderhanden
@usecase            LV.1.MB
@regels             R1551
@sleutelwoorden     Maak BRP bericht

Narrative:

In het resultaat van een levering mogen geen 'Verantwoordingsgroep' (R1541) met een 'Actie' worden opgenomen waarnaar geen enkele verwijzing (meer) bestaat
vanuit een 'Inhoudelijke groep' (R1540) of een 'Onderzoeksgroep' (R1543) in hetzelfde resultaat.

(Toelichting: dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander filtermechanisme niet (meer) in het bericht
worden opgenomen, de Actie dus niets meer 'verantwoordt' en zelf ook uit het bericht verwijderd moet worden).


Scenario: 1. Actie verwijst naar verantwoording voor inhoudlijke groep 1 via geautoriseerde leveringautorisatie (op adres),
            Actie verwijst NIET naar verantwoording voor inhoudlijke groep 2 via NIET geautoriseerde leveringautorisatie (op adres)
            Logisch testgeval: R1551_01, R1551_03
            Verwacht Resultaat:
            - Mutatiebericht voor /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem: Adres
            - Geen Mutatiebericht voor /levering_autorisaties/Geen_autorisatie_op_adres:  Geen adres autorisatie waardoor actieinhoud geen verwijzing meer heeft

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem,/levering_autorisaties/Geen_autorisatie_op_adres

Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 7.2_R1547_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut        | aanwezig  |
| adres   | 1      | woonplaatsnaam   | ja        |

When het mutatiebericht voor leveringsautorisatie Geen autorisatie op adres is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 1.2 Controle volledig bericht

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 10.3_R1551_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut        | aanwezig  |
| adres   | 1      | woonplaatsnaam   | ja        |

Given verzoek voor leveringsautorisatie 'Geen autorisatie op adres' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 10.3_R1551_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen autorisatie op adres is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut        | aanwezig  |
| adres   | 1      | woonplaatsnaam   | nee       |


Scenario: 2. Actie verwijst naar verantwoording voor onderzoeksgroep 1 via geautoriseerde leveringautorisatie (op huisnummer),
            Actie verwijst NIET naar verantwoording voor onderzoeksgroep 2 via NIET geautoriseerde leveringautorisatie (op huisnummer)
            Logisch testgeval: R1551_02, R1551_03
            Verwacht Resultaat:
            - Mutatiebericht voor /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding: Huisnummer
            - Geen Mutatiebericht voor /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep:  Geen Huisnummer

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 816823881 zijn verwijderd
Given de standaardpersoon Olivia met bsn 816823881 en anr 7161975058 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(816823881)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20140607, registratieDatum: 20140607) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 40, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(persoon)

nieuweGebeurtenissenVoor(persoon) {
 onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 816823881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut    | aanwezig |
| onderzoek | 2      | datumAanvang | ja       |

Scenario: 2.2 Wijziging onderzoek naar een niet geautoriseerd attribuut

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(816823881)

nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150324) {
        afgeslotenOp(eindDatum:'2015-03-24')
    }
}
slaOp(persoon)

When voor persoon 816823881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut    | aanwezig |
| onderzoek | 2      | statusNaam   | ja       |




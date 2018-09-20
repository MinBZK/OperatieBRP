Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1552
@sleutelwoorden     Maak BRP bericht

Narrative:
In het resultaat van een levering mogen geen 'Verantwoordingsgroep' (R1541)'Administratieve handeling' en onderliggende groepen
Actie \ Bron en Document worden opgenomen als er binnen die Administratieve handeling geen enkele Actie voorkomt waarvoor een
verwijzing bestaat vanuit een 'Inhoudelijke groep' (R1540) uit hetzelfde resultaat..

Scenario: 1. Actie verwijst naar wijziging in Inhoudelijke groep, alleen daarvoor meerdere groepen
            Logisch testgeval: R1552_01, R155_02
            Verwacht Resultaat:
            - Mutatiebericht met wijziging adres

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20020514, registratieDatum: 20020514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groepen 'adres'
Then heeft het bericht 1 groepen 'identificatienummers'
Then heeft het bericht 1 groepen 'samengesteldeNaam'
Then heeft het bericht 1 groepen 'geboorte'
Then hebben attributen in voorkomens de volgende waardes:
| groep                     | nummer | attribuut    | verwachteWaarde                   |
| administratieveHandeling  | 1      | naam         | Verhuizing intergemeentelijk      |

Scenario: R1552_02 Verantwoordingsgroep niet in resultaat levering
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
| groep                    | nummer | attribuut      | aanwezig |
| adres                    | 1      | woonplaatsnaam | ja       |
| administratieveHandeling | 1      | categorie      | ja       |
| bron                     | 1      | document       | ja       |

When het mutatiebericht voor leveringsautorisatie Geen autorisatie op adres is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
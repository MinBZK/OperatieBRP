Meta:
@auteur             aapos
@status             Klaar
@usecase            LV.1.MB
@regels             R1806
@sleutelwoorden     Maak BRP bericht

Narrative:
Als van een groep een repeterende voorkomens van materiele en/of formele historie is opgenomen in een bericht, dan is de sortering als volgt:

Als sorteervolgorde van actuele/historische verschijningen van een gegevensgroep:
a) Verwerkingssoort - Volgens logica sorteren verwerkingssoort
b) "Datum\Tijd verval" - Aflopend
c) DatumAanvangGeldigheid - Aflopend
d) DatumEindeGeldigheid - Aflopend
e) "Datum\Tijd registratie" - Aflopend

Scenario: R1806_L01 Sorteervolgorde verwerkingssoort in mutatie bericht
            Logisch testgeval: R1806_01
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 198758625, 299054457, 743274313 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 198758625 en anr 3213593106 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(198758625)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100313) {
        naarGemeente 'Pijnacker',
            straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BX', woonplaats: "Pijnacker"
    }
}
slaOp(persoon)

When voor persoon 198758625 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                  | nummer | verwerkingssoort |
| afgeleidAdministratief | 1      | Toevoeging       |
| afgeleidAdministratief | 2      | Verval           |
| bijhouding             | 1      | Toevoeging       |
| bijhouding             | 2      | Wijziging        |
| bijhouding             | 3      | Verval           |
| adres                  | 1      | Toevoeging       |
| adres                  | 2      | Wijziging        |
| adres                  | 3      | Verval           |

Then is het synchronisatiebericht gelijk aan /use_case_arts_nieuw/LV.1.MB_Maak_BRP_Bericht/expected_levering_bericht/R1806_expected_mutatiebericht_1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: R1806_L02 Sorteer volgorde in volledig bericht
                Logisch testgeval: R1806_02

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand R1806_Synchroniseer_Persoon_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then is het synchronisatiebericht gelijk aan /use_case_arts_nieuw/LV.1.MB_Maak_BRP_Bericht/expected_levering_bericht/R1806_expected_volledigbericht_1.xml voor expressie //brp:lvg_synVerwerkPersoon

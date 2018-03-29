Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1552
@sleutelwoorden     Maak BRP bericht

Narrative:
In het resultaat van een levering mogen geen 'Verantwoordingsgroep' (R1541)'Administratieve handeling' en onderliggende groepen
Actie \ Bron en Document worden opgenomen als er binnen die Administratieve handeling geen enkele Actie voorkomt waarvoor een
verwijzing bestaat vanuit een 'Inhoudelijke groep' (R1540) uit hetzelfde resultaat.


Scenario:   1.  Libby gaat trouwen.
                LT: R1552_LT01, R1552_LT02
                Verwacht resulaat:
                - Verantwoordingsgroep administratieve handeling inclusief bronnen in resultaat voor leveringsautorisatie: Geen pop.bep. levering op basis van doelbinding
                - GEEN mutatie bericht voor leveringsautorisatie Geen autorisatie op huwelijk, omdat de adm.hand. ACTIE wordt wegefilterd
                Uitwerking:
                - leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is volledig geautoriseerd,
                dus komt huwelijk in het bericht en dus ook de administratieve handelin
                - leveringsautorisatie Geen autorisatie op huwelijk heeft geen autorisatie op huwelijk,
                dus komt huwelijk niet in het bericht, dus komt de administratieve handeling (met bronnen en acties) niet in het bericht.
                Doordat de enige mutatie op huwelijk was komt er in dit geval dus geen bericht.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem,/levering_autorisaties_nieuw/R1552/Geen_autorisatie_op_huwelijk

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep                     | nummer | attribuut    | verwachteWaarde                       |
| administratieveHandeling  | 1      | soortNaam    | Voltrekking huwelijk in Nederland     |
| document                  | 1      | soortNaam    | Huwelijksakte                         |
| actie                     | 1      | soortNaam    | Registratie aanvang huwelijk          |

Then is er geen synchronisatiebericht voor leveringsautorisatie Geen autorisatie op huwelijk

Then is het synchronisatiebericht gelijk aan expecteds/R1552_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

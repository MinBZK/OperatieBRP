Meta:
@status             Klaar
@usecase            SA.0.MD
@regels             R2016, R2129, R1316, R1333, R1343, R1348, R1989, R1990, R1991, R1993, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. doelbinding, Lever Mutaties

Narrative:
Om correct te kunnen leveren aan de hand van een administratieve handeling.
Als een afnemer wil ik een mutatiebericht ontvangen als een persoon bij Mutatielevering op doebinding "in de doelbinding" blijft
'uit de doelbinding' gaat, en 'nieuw in doelbinding' komt.

Scenario:   1.  Persoon komt nieuw in doebinding BRP
                LT: R1262_LT17, R1264_LT13, R2130_LT11, R1348_LT01, R1983_LT25
                Verwacht resultaat: Volledig bericht
                Uitwerking:
                - R1262_LT17: De gevraagde dienst is geldig
                - R1264_LT13: De gevraagde dienst is niet geblokkeerd
                - R2130_LT11: De leveringsautorisatie bevat de gevraagde dienst
                - R1348_LT01: Persoon nieuw in doelbinding

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_huwelijk

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Doelbinding met pop bep huwelijk is ontvangen en wordt bekeken


Scenario:   2. Persoon verlaat doelbinding door verhuizing.
            LT: R1316_LT01, R1333_LT03, R1348_LT06
            Verwacht resultaat: Mutatiebericht
            - Melding: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
            Uitwerking:
            Persoon verlaat door verhuizing de doelbinding

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_geen_huwelijk

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Doelbinding met pop bep geen huwelijk is ontvangen en wordt bekeken

!-- R1316_LT01 Waarschuwing in bericht
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde                                                                                                   |
| melding	            | 1         | regelCode 	        | R1316                                                                                                             |
| melding	            | 1         | melding    	        | De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt. |                                                                                                  |


Scenario:   3. Doelbinding staat op waar, dus voor huwelijk is doelbinding WAAR en na huwelijk is doelbinding WAAR
            LT: R1316_LT04, R1333_LT01, R1348_LT04, R2056_LT13
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = WAAR
            Verwacht resultaat: Mutatiebericht

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario:   4. Verhuizing via GBA bijhouding binnen doelbinding
            LT: R1333_LT02
            Verwacht resultaat: Volledig bericht
            Uitwerking:
            Administratieve handeling P, direct voorafgaande aan Q, evalueerde op WAAR door verhuizing binnen de doelbinding.

Given leveringsautorisatie uit autorisatie/postcode_gebied_2000-5000

Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_Verhuizing_xls

When voor persoon 595891305 wordt de laatste handeling geleverd
!-- R1333_LT02 volledig bericht bij code 99997
When het volledigbericht voor leveringsautorisatie postcode gebied 2000 - 5000 is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut | verwachteWaarde         |
| administratieveHandeling | 1      | soortNaam | GBA - Bijhouding overig |

Scenario: 5.    Persoon nieuw in doelbinding
                LT: R1348_LT02
                Persoonsbeeld oud   = NULL
                Persoonsbeeld nieuw = WAAR
                Verwacht resultaat:
                - Volledig bericht op basis van mutatielevering op basis van doelbinding
                - GEEN Mutatiebericht

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_expressie_postcode
Given persoonsbeelden uit specials:specials/Delta_Verhuizing_NULLnaarWAAR_xls
When voor persoon 229676868 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Levering op basis van doelbinding expressie postcode is ontvangen en wordt bekeken

Scenario: 6.    Persoon niet in doelbinding
                LT: R1348_LT07
                Persoonsbeeld oud   = ONWAAR
                Persoonsbeeld nieuw = NULL
                Verwacht resultaat:
                - Geen bericht aan afnemer

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_expressie_postcode2
Given persoonsbeelden uit specials:specials/Delta_Intergemeentelijke_Verhuizing_Postcode_Onbekend_xls
When voor persoon 229676868 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht voor leveringsautorisatie Levering op basis van doelbinding expressie postcode2

Scenario: 7.    Persoon niet in doelbinding
                LT: R1348_LT08
                Persoonsbeeld oud   = NULL
                Persoonsbeeld nieuw = NULL
                Verwacht resultaat:
                - Geen bericht aan afnemer

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_expressie_postcode
Given persoonsbeelden uit specials:specials/Delta_Verhuizing_Postcode_Onbekend_naarOnbekend_xls
When voor persoon 229676868 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht voor leveringsautorisatie Levering op basis van doelbinding expressie postcode

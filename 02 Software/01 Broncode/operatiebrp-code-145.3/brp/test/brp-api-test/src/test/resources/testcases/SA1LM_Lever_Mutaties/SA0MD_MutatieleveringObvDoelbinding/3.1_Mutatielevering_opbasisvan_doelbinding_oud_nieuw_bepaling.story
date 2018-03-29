Meta:
@status             Klaar
@usecase            SA.0.MD
@regels             R2016, R2129, R1316, R1333, R1343, R1348, R1989, R1990, R1991, R1993, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. doelbinding, Lever Mutaties

Narrative:
Oud nieuw bepaling

Scenario:   1. Door geslachtswijziging uit doelbinding
            LT: R1316_LT01, R1333_LT03, R2550_LT01
            Verwacht resultaat: Mutatiebericht
            - Melding: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
            Uitwerking:
            Administratieve handeling P, direct voorafgaande aan Q, evalueerde op WAAR doordat Persoon eerst een vrouw was en dus binnen de doelbinding viel.
            Na administratieve handeling Q, evalueert de populatiebeperking op ONWAAR, omdat Persoon nu een man is en niet in de doelbinding valt.


Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_op_geslacht_vrouw
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T50_xls
When voor persoon 495922985 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor partij Gemeente Haarlem en leveringsautorisatie Doelbinding op geslacht vrouw is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_2.1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3.    Persoon voor verhuizing in doelbinding (Expressie op postcode is WAAR), daarna Intergemeentelijke verhuizing zonder postcode (Expressie op postcode is NULL)
                LT: R1316_LT02, R1348_LT03
                Persoonsbeeld oud   = WAAR
                Persoonsbeeld nieuw = NULL
                Verwacht resultaat:
                - Volledig bericht op basis van mutatielevering op basis van doelbinding
                - Mutatiebericht
                - Waarschuwing R1316 De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_expressie_postcode

Given persoonsbeelden uit specials:specials/IV_Intergemeentelijke_Verhuizing_xls

When voor persoon 229676868 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering op basis van doelbinding expressie postcode is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_3_volledig_bericht.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatiebericht door administratieve handeling intergemeentelijke verhuizing met waarschuwing uit doelbinding
Given persoonsbeelden uit specials:specials/Delta_Intergemeentelijke_Verhuizing_Postcode_Onbekend_xls
When voor persoon 229676868 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering op basis van doelbinding expressie postcode is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_3_mutatiebericht.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4.    Persoon voor verhuizing buiten doelbinding (Expressie op postcode is NULL), daarna Intergemeentelijke verhuizing buiten doelbinding (Expressie op postcode is ONWAAR)
                LT: R1348_LT05
                Persoonsbeeld oud   = NULL
                Persoonsbeeld nieuw = ONWAAR
                Verwacht resultaat:
                - GEEN Volledig bericht op basis van mutatielevering op basis van doelbinding
                - GEEN Mutatiebericht

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_expressie_postcode
Given persoonsbeelden uit specials:specials/Delta_Intergemeentelijke_Verhuizing_Postcode_ONWAAR_xls
When voor persoon 229676868 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


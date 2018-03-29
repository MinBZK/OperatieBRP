Meta:
@status             Klaar
@regels             R2550, R2551
@sleutelwoorden     Oud Nieuw Bepaling, Doelbinding, Historievormen

Narrative:
Oud nieuw bepaling mbt doelbinding, met controle voor alle historievormen en correcte vulling volledige berichten en mutatieberichten.
In deze story wordt getest of personen correct binnen of buiten de doelbinding vallen na afleiding van de oud nieuw bepaling.
Er wordt getest op:
Oud persoonsbeeld   vs.     Nieuw persoonsbeeld
In                  -       Uit
In                  -       In
Uit                 -       In
Uit                 -       Uit

voor historie vormen:
- Geen
- Materieel
- Materieel Formeel
- Formeel

Correcte vulling groepen volledige berichten (zie documentatie hfd 5):
Voorkomens met IndMutLev = Ja niet worden opgenomen in de inhoud van een VolledigBericht.
- Controle zit in de volledige expecteds bij scenario 3.1, 3.2, 3.3 en 3.4

Correcte vulling groepen mutatie berichten:
In een Mutatiebericht mogen voorkomens met IndMutLev = Ja wel worden opgenomen. Dit zullen altijd vervallen voorkomens zijn.
- Controle zit in de volledige expecteds bij scenario 2.1, 2.2, 2.3 en 2.4

Toepassen van het historiefilter bij Geef details persoon:
- De controle dat actieVerval leeg is bij geef details persoon met een peildatum in het verleden
    (waardoor je terug gaat naar een oud persoonsbeeld, wat op dat moment een nieuw persoonbeeld was)
        Zit in de geef details persoon use case:
            BV0GD_Geef_Details_Persoon
                In story:
                    7.1_R2224.story
                        In scenario: 5 en 6
- De controle op juiste vulling bij geef details persoon met verschillende historievorm zit in onderstaande testen

Scenario:   1.1 Van IN de doelbinding naar UIT de doelbinding (Door geslachtswijziging uit doelbinding)
            LT: R2550_LT01, R2551_LT01
            Verwacht resultaat: Mutatiebericht
            - Melding: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = ONWAAR
            Historievorm:
            - Geen Historie

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_op_geslacht_vrouw_Geen_Historie
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T50_xls
When voor persoon 495922985 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor partij Gemeente Haarlem en leveringsautorisatie Doelbinding op geslacht vrouw is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_1.1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   1.2 Van IN de doelbinding naar UIT de doelbinding (Door geslachtswijziging uit doelbinding)
            LT: R2550_LT01, R2551_LT01
            Verwacht resultaat: Mutatiebericht
            - Melding: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = ONWAAR
            Historievorm:
            - Materieel

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_op_geslacht_vrouw_Materiele_Historie
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T50_xls
When voor persoon 495922985 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor partij Gemeente Haarlem en leveringsautorisatie Doelbinding op geslacht vrouw is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_1.2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   1.3 Van IN de doelbinding naar UIT de doelbinding (Door geslachtswijziging uit doelbinding)
            LT: R2550_LT01, R2551_LT01
            Verwacht resultaat: Mutatiebericht
            - Melding: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = ONWAAR
            Historievorm:
            - MaterieelFormeel

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_op_geslacht_vrouw
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T50_xls
When voor persoon 495922985 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor partij Gemeente Haarlem en leveringsautorisatie Doelbinding op geslacht vrouw is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_1.3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   1.4 Van IN de doelbinding naar UIT de doelbinding (Door geslachtswijziging uit doelbinding)
            LT: R2550_LT01, R2551_LT01
            Verwacht resultaat: Mutatiebericht
            - Melding: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = ONWAAR
            Historievorm:
            - Formeel

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_op_geslacht_vrouw_Formele_Historie
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T50_xls
When voor persoon 495922985 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor partij Gemeente Haarlem en leveringsautorisatie Doelbinding op geslacht vrouw is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_1.4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   2.1 Van IN de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gebleven)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Mutatiebericht
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Geen Historie

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Geen_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_2.1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm Geen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|Geen

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Scenario_2_GDP_Historievorm_Geen.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario:   2.2 Van IN de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gebleven)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Mutatiebericht
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Materieel

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Materiele_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_2.2.xml voor expressie //brp:lvg_synVerwerkPersoon
!-- Historievorm Materieel
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|Materieel

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Scenario_2_GDP_Historievorm_Materieel.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario:   2.3 Van IN de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gebleven)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Mutatiebericht
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - MaterieelFormeel

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_2.3.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm MaterieelFormeel
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|MaterieelFormeel

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Scenario_2_GDP_Historievorm_MaterieelFormeel.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   2.4 Van IN de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gebleven)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Mutatiebericht
            Uitwerking:
            Persoonsbeeld oud   = WAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Formeel

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Formele_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_2.4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   3.1 Van UIT de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Geen

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_huwelijk_Geen_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Doelbinding met pop bep huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_3.1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm Geen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Doelbinding met pop bep huwelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|Geen

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Scenario_3_GDP_Historievorm_Geen.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

!-- R2550 Actieverval is leeg, dus niet in bericht
Then is er voor xpath //brp:actieVerval geen node aanwezig in het antwoord bericht

Scenario:   3.2 Van UIT de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Materieel

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_huwelijk_Materiele_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Doelbinding met pop bep huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_3.2.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm Materieel
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Doelbinding met pop bep huwelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|Materieel

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Scenario_3_GDP_Historievorm_Materieel.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   3.3 Van UIT de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - MaterieelFormeel

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_huwelijk

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Doelbinding met pop bep huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_3.3.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm MaterieelFormeel
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Doelbinding met pop bep huwelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|MaterieelFormeel

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Scenario_3_GDP_Historievorm_MaterieelFormeel.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   3.4 Van UIT de doelbinding naar IN de doelbinding (Na huwelijk in de doelbinding gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Formeel

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_huwelijk_Formele_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Doelbinding met pop bep huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_3.4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   4. Van UIT de doelbinding naar UIT de doelbinding (Na huwelijk buiten de doelbinding gebleven)
            LT: R2550_LT01, R2551_LT01
            Verwacht resultaat:
            - Geen bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = ONWAAR

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_2_huwelijken

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden
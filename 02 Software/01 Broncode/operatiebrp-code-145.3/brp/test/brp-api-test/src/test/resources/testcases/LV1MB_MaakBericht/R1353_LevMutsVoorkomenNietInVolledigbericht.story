Meta:
@status                 Klaar
@usecase                LV.0.MB
@regels                 R1353
@sleutelwoorden         Maak BRP bericht

Narrative:
Een voorkomen van een groep waarin Persoon \ Indicatie.Standaard.Voorkomen tbv levering mutaties? de waarde 'Ja' heeft,
mag alleen worden opgenomen in een MutatieBericht.


Scenario:   1.     Mutatie levering met voorkomen waarvoor geldt IndicatieVoorkomenTbvLeveringMutaties = True en IndicatieVoorkomenTbvLeveringMutaties = Leeg
                    LT: R1353_LT02, R1353_LT04
                    Verwacht resultaat: Mutatiebericht met vulling
                    - 3 groepen adres
                    Het mutatie bericht bevat zowel het adres voorkomen waarvoor geldt IndicatieVoorkomenTbvLeveringMutaties = True (voorkomen met verwerkingssoort "Verval")
                    Als het adres voorkomen waarvoor geldt IndicatieVoorkomenTbvLeveringMutaties = Leeg (Voorkomen met verwerkingssoort "Toevoeging")

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1353_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
!-- voorkomen met IndicatieVoorkomenTbvLeveringMutaties = True in mutatiebericht
Then heeft het bericht 3 groepen 'adres'
Then heeft het bericht 2 groepen 'afgeleidAdministratief'
Then heeft het bericht 3 groepen 'bijhouding'

Then is het synchronisatiebericht gelijk aan expecteds/R1353_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2.     Volledigbericht met voorkomen waarvoor geldt IndicatieVoorkomenTbvLeveringMutaties = True en IndicatieVoorkomenTbvLeveringMutaties = Leeg
                    LT: R1353_LT01, R1353_LT03
                    Verwacht resultaat: Volledigbericht met vulling
                    - 2 groepen adres (voorkomen waarvoor geldt IndicatieVoorkomenTbvLeveringMutaties = True is niet aanwezig in volledigbericht)

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1353_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
!-- voorkomen met IndicatieVoorkomenTbvLeveringMutaties = True niet in volledigbericht
Then heeft het bericht 2 groepen 'adres'
Then heeft het bericht 2 groepen 'afgeleidAdministratief'
Then heeft het bericht 2 groepen 'bijhouding'

Then is het synchronisatiebericht gelijk aan expecteds/R1353_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

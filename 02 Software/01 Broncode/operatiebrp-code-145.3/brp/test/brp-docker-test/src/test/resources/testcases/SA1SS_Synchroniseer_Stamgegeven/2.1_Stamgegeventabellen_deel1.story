Meta:
@status             Klaar
@usecase            SA.0.SS
@regels             R1331,R1332,R1979
@sleutelwoorden     Synchroniseer Stamgegeven

Narrative:
Controle op inhoud van bericht.
Er mag maar 1 stamgegeven geleverd worden per bericht, dus wordt elke tabel los opgevraagd.

Scenario: 1     Adelijketiteltabel code naam mannelijk en naam vrouwelijk
                LT: R1331_LT01, R1332_LT02, R1979_LT02
                Verwacht resultaat: code, naam mannelijk, naam vrouwelijk
                - Geen node met ElementTabel in bericht (R1332_LT02)


Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SS_Synchroniseer_Stamgegeven/Requests/2.1_Stamgegeventabellen_deel1_scenario_1.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is in antwoordbericht de aanwezigheid van 'code' in 'adellijkeTitel' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naamMannelijk' in 'adellijkeTitel' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naamVrouwelijk' in 'adellijkeTitel' nummer 1 ja
Then is het antwoordbericht gelijk aan /testcases/SA1SS_Synchroniseer_Stamgegeven/expected/2.1_Expected_scenario1.4-response.xml voor expressie //brp:lvg_synGeefSynchronisatieStamgegeven_R
!-- R1332_LT02 - geen elementTabel in bericht.
Then is er voor xpath //brp:elementTabel geen node aanwezig in het antwoord bericht


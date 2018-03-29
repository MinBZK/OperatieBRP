Meta:
@usecase            VB.0.AV
@status             Klaar


Narrative:
Als partij wil vrije berichten kunnen uitwisselen met andere partijen die zijn aangesloten op het BRP


Scenario:   1.  XML request van het type vrij bericht
                LT:
                Verwacht resultaat:
                - Geslaagd
                - Ontvangende partij ontvangt het bericht

Given verzoek vrijbericht met xml xml_request/vrijbericht_verzoek.xml transporteur 00000001002220647000 ondertekenaar 00000001002220647000

Then heeft het antwoordbericht verwerking Geslaagd

Then is een vrij bericht voor partij 850012 verstuurd naar afleverpunt http://ergens

Then is het verstuurde vrij bericht gelijk aan expected/vb_scenario_1.xml

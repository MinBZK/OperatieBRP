Meta:
@soapEndpoint   ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace  http://www.bzk.nl/brp/levering/afnemerindicaties/service
@epic           legacy
@auteur         tools
@status         Uitgeschakeld
@regels         VR00090, VR00092
@module         technisch-bezemwagen

Scenario: Scenario_0

!-- A2: Zet de administratieve handelingen klaar voor de bezemwagen # null : status=null
Given de database wordt gereset voor de personen 310027603
Given de database is aangepast met:
update  kern.admhnd set tslev = null where id = 26003;
update  kern.admhnd set tslev = null where id = 29023;
update  kern.admhnd set tslev = null where id = 31004;

!-- A3: happyFlow-Bezemwagen-TC01 # 1 : status=null
Given extra waardes:
| SLEUTEL       | WAARDE    |
| abonnement_id | 5670000   |
| admhnd_id     | 26003     |
| bsn_id        | 260011307 |
Then is binnen 100s de query /sqltemplates/XMLberdataleveringon_ART-Bezemwagen.sql gelijk aan /testcases/art_input_TechnischBezemwagen/data/happyFlow-Bezemwagen-TC01-1-dataresponse.xml voor de expressie //Results

!-- A4: happyFlow-Bezemwagen-TC01 # 2 : status=null
Given extra waardes:
| SLEUTEL   | WAARDE    |
| admhnd_id | 26003     |
| bsn_id    | 260011307 |
Then is de query /sqltemplates/XMLberdataleveringon_ART-Bezemwagen2.sql gelijk aan /testcases/art_input_TechnischBezemwagen/data/happyFlow-Bezemwagen-TC01-2-dataresponse.xml voor de expressie //Results

!-- A5: Verificatie-Mutatiebericht-TC01 # 1 : status=null
Given extra waardes:
| SLEUTEL       | WAARDE    |
| abonnement_id | 5670000   |
| admhnd_id     | 29023     |
| bsn_id        | 290136581 |
Then is de query /sqltemplates/XMLberdataleveringon_ART-Bezemwagen.sql gelijk aan /testcases/art_input_TechnischBezemwagen/data/Verificatie-Mutatiebericht-TC01-1-dataresponse.xml voor de expressie //brp:verificaties

!-- A6: Verantwoording-Mutatiebericht-TC01 # 1 : status=null
Given de database wordt gereset voor de personen 310027603
Given extra waardes:
| SLEUTEL       | WAARDE    |
| abonnement_id | 5670000   |
| admhnd_id     | 31004     |
| bsn_id        | 310027603 |
Then is de query /sqltemplates/XMLberdataleveringon_ART-Bezemwagen.sql gelijk aan /testcases/art_input_TechnischBezemwagen/data/Verantwoording-Mutatiebericht-TC01-1-dataresponse.xml voor de expressie //brp:administratieveHandelingen



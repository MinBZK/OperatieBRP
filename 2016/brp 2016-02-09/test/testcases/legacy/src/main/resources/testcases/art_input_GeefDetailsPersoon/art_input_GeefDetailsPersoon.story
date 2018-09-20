Meta:
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service
@auteur tools
@status Klaar
@epic legacy
@module geef-details-persoon

Scenario: Scenario_0
!-- A2: happyflow-TC01 # 1 : status=null

!-- anjaw: Er lijkt een actieinhoud te ontbreken op betrokkenheden, maar deze actieId komt niet voor in de afgeleid administratief van
!-- de persoon, waardoor het verantwoordingsfilter deze actie uit het bericht filtert.
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/happyflow-TC01-1-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_1
!-- A3: happyflow-TC02 # 1 : status=null

!-- anjaw: Er lijkt een actieinhoud te ontbreken op betrokkenheden, maar deze actieId komt niet voor in de afgeleid administratief van
!-- de persoon, waardoor het verantwoordingsfilter deze actie uit het bericht filtert.
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC02
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/happyflow-TC02-1-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_2
Meta:
@status Uitgeschakeld

!-- A4: BRLV0017-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0017-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0017-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_3
!-- A5: BRLV0018-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0018-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0018-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_4
!-- A6: BRLV0019-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0019-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0019-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_5
Meta:
@status Uitgeschakeld

!-- A7: BRLV0020-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0020-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0020-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_6
!-- A8: BRLV0021-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0021-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0021-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_7
!-- A9: BRLV0029-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0029-TC01
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0029-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_8
!-- A10: BRLV0029-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , BRLV0029-TC02
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_GeefDetailsPersoon/response/BRLV0029-TC02-1-soapresponse.xml voor expressie //brp:meldingen



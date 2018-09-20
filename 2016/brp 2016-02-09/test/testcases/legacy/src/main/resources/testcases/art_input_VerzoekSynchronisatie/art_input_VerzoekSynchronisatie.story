Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service
@auteur tools
@status Klaar
@epic legacy
@module verzoek-synchronisatie

Scenario: Scenario_0
Meta:
@regels VR00050
@status Uitgeschakeld

!-- A2: happyflow-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , happyflow-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/happyflow-TC01-1-soapresponse.xml voor expressie //brp:resultaat

!-- A3: happyflow-TC01 # 2 : status=null


Scenario: Scenario_1

!-- In dit scenario is een kind aanwezig dat geadopteerd is. Op de adoptie datum is er in de database het samengestelde naam record beeindigd
!--dat gekoppeld was aan zijn eerdere ouders. In de testdata ontbreekt een geldig record voor samengestelde naam in de testdata voor het kind, waardoor
!--deze niet getoond wordt (door prerelatie predikaat). Eigenlijk zou er wel een nieuw geldig record moeten bestaan in de testdata van de nieuwe samengestelde
!--naam van het kind.

!-- A4: SyncStructuur-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC01
Given de database wordt gereset voor de personen 210019712
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/SyncStructuur-TC01-1-soapresponse.xml voor expressie //brp:resultaat

!-- A5: SyncStructuur-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000002
abonnement_id | 5670014
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/SyncStructuur-TC01-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon


Scenario: Scenario_2

Meta:
@status Uitgeschakeld

!-- A6: SyncStructuur-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC02
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/SyncStructuur-TC02-1-soapresponse.xml voor expressie //brp:resultaat

!-- A7: SyncStructuur-TC02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000003
abonnement_id | 5670014
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/SyncStructuur-TC02-2-dataresponse.xml voor de expressie //brp:synchronisatie

!-- A8: VP.0-TC01 # 0 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
burgerservicenummer_ipr1 | 110015927
Then de database wordt opgeruimd dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql


Scenario: Scenario_3
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service
@status Uitgeschakeld

!-- A9: VP.0-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given de database wordt gereset voor de personen 110015927
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-500000000001
burgerservicenummer_ipv1 | 110015927
burgerservicenummer_ipr1 | 110015927
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VP.0-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_4
Meta:
@regels VRLV0001, BRLV0016
@status Uitgeschakeld

!-- A10: SyncStructuur-TC03 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC03
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/SyncStructuur-TC03-1-soapresponse.xml voor expressie //brp:resultaat

!-- A11: SyncStructuur-TC03 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000004
abonnement_id | 5670002
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/SyncStructuur-TC03-2-dataresponse.xml voor de expressie //brp:synchronisatie


Scenario: Scenario_5
Meta:
@regels VR00090

!-- A12: VR00090-SyncStructuur-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , VR00090-SyncStructuur-TC04
Given de database wordt gereset voor de personen 310027603
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000005
abonnement_id | 5670041
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VR00090-SyncStructuur-TC04-1-soapresponse.xml voor expressie //brp:resultaat
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/VR00090-SyncStructuur-TC04-1-dataresponse.xml voor de expressie //brp:administratieveHandelingen


Scenario: Scenario_6
Meta:
@regels BRLV0023
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service
@status Uitgeschakeld

!-- A13: BRLV0023-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0023-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0023-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_7
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A14: VerhuizingOndAfnemerInd-TC00 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuizingOndAfnemerInd-TC00
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | []
_objectid$burgerservicenummer_ipv1_ | 110015927
referentienummer_sMM0 | 00000000-0000-0000-0700-500000000253
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VerhuizingOndAfnemerInd-TC00-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_8
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service
@status Uitgeschakeld

!-- A15: plaatsing-afnemerindicatie-1-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-1-TC01
Given extra waardes:
SLEUTEL | WAARDE
afnemerCode_aap1 | 505005
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182
referentienummer_sLB1 | 0000000A-3000-1000-0000-500000000027
datumAanvangMaterielePeriode_aap1 | ${vandaag()}
zendendePartij_zsL1 | 505005
burgerservicenummer_ipv1 | 110015927
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505005
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/plaatsing-afnemerindicatie-1-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_9
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A16: VerhuisUitDoelbinding-TC04 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisUitDoelbinding-TC04
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | []
_objectid$burgerservicenummer_ipv1_ | 110015927
datumAanvangGeldigheid_rar1 | ${vandaag()}
datumAanvangAdreshouding_aap1 | ${vandaag(0,0,-1)}
referentienummer_sMM0 | 00000000-0000-0000-0700-500000000251
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VerhuisUitDoelbinding-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_10
Meta:
@regels BRLV0023
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service
@status Uitgeschakeld

!-- A17: BRLV0023-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0023-TC02
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0023-TC02-1-soapresponse.xml voor expressie //brp:resultaat

!-- A18: BRLV0023-TC02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000007
abonnement_id | 5670005
Then is binnen 15s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/BRLV0023-TC02-2-dataresponse.xml voor de expressie //brp:synchronisatie


Scenario: Scenario_11
Meta:
@regels BRLV0023
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service
@status Uitgeschakeld

!-- A19: BRLV0023-TC03 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0023-TC03
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0023-TC03-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_12
Meta:
@regels BRLV0041
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service
@status Uitgeschakeld

!-- A20: BRLV0041-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0041-TC02
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0041-TC02-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_13
Meta:
@regels BRLV0038
@status Uitgeschakeld

!-- A21: BRLV0038-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0038-TC01
Given de database wordt gereset voor de personen 210015615
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0038-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A22: BRLV0038-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000011
abonnement_id | 5670029
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/BRLV0038-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_14
Meta:
@regels BRLV0041
@status Uitgeschakeld

!-- A23: BRLV0041-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0041-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0041-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A24: BRLV0041-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000012
abonnement_id | 5670031
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/BRLV0041-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_15
Meta:
@regels BRLV0040
@status Uitgeschakeld

!-- A25: BRLV0040-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0040-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0040-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A26: BRLV0040-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000013
abonnement_id | 5670002
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/BRLV0040-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_16
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A27: happy-flow-TC0102 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0102
Given de database wordt gereset voor de personen 677080426, 330298628
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-500000000002
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/happy-flow-TC0102-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then de database wordt opgeruimd met:
 update kern.persverstrbeperking set pers = (select id from kern.pers where bsn = 677080426) where pers in (select id from kern.pers where bsn = 330298628);


Scenario: Scenario_17
Meta:
@regels BRLV0031
@status Uitgeschakeld

!-- A28: BRLV0031-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0031-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0031-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A29: BRLV0031-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000014
abonnement_id | 5670036
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/BRLV0031-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_18
Meta:
@regels BRLV0022
@status Uitgeschakeld

!-- A30: BRLV0022-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0022-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0022-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_19
Meta:
@regels BRLV0006
@status Uitgeschakeld

!-- A31: BRLV0006-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0006-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/BRLV0006-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_20
Meta:
@regels VR00092

!-- A32: VR00092-SyncStructuur-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , VR00092-SyncStructuur-TC01
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VR00092-SyncStructuur-TC01-1-soapresponse.xml voor expressie //brp:resultaat

!-- A33: VR00092-SyncStructuur-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000017
abonnement_id | 5670000
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/VR00092-SyncStructuur-TC01-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:verificaties


Scenario: Scenario_21
Meta:
@regels VR00092

!-- A34: VR00092-SyncStructuur-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , VR00092-SyncStructuur-TC02
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VR00092-SyncStructuur-TC02-1-soapresponse.xml voor expressie //brp:resultaat

!-- A35: VR00092-SyncStructuur-TC02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000018
abonnement_id | 5670000
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/VR00092-SyncStructuur-TC02-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:verificaties


Scenario: Scenario_22
Meta:
@regels VR00092

!-- A36: VR00092-SyncStructuur-TC03 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , VR00092-SyncStructuur-TC03
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_VerzoekSynchronisatie/response/VR00092-SyncStructuur-TC03-1-soapresponse.xml voor expressie //brp:resultaat

!-- A37: VR00092-SyncStructuur-TC03 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-000000000019
abonnement_id | 5670000
Then is binnen 13s de query /sqltemplates/LaatsteSynchroniseerVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_VerzoekSynchronisatie/data/VR00092-SyncStructuur-TC03-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:verificaties



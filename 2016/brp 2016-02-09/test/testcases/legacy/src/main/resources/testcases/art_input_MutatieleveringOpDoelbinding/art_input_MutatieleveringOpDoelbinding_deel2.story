Meta:
@auteur tools
@status Klaar
@epic legacy
@module mutatielevering-op-doelbinding

Scenario: Scenario_24_Controle op verwerkingssoort
Meta:
@regels R1317(VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!--A87: BRBY0032-TC3040-2 # 1 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0032-TC3040-2
Given de database wordt gereset voor de personen 330006575
Given extra waardes:
SLEUTEL | WAARDE
datumAanvangGeldigheid_rOO0 | 1990-12-01
datumEindeGeldigheid_rOO0 | 1992-01-01
referentienummer_sOO0 | 00000000-0000-0000-0055-300000000042
datumAanvangAdreshouding_rOO1 | 1992-01-01
datumAanvangAdreshouding_rOO0 | 1990-12-01
datumAanvangGeldigheid_rOO1 | 1992-01-01
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0032-TC3040-2-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen
Then wacht tot alle berichten zijn ontvangen

!-- A88: BRBY0032-TC3040-2 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-300000000042
abonnement_id | 5670014
_bsn_rOO0_ | 330006575
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0032-TC3040-2-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon

!-- A89: Verwerkingssoort-TC03 # 1 : status=null
!--Given data uit excel /templatedata/art_input_055.xls , Verwerkingssoort-TC03
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-300000000042
abonnement_id | 5670000
_bsn_rOO0_ | 330006575
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC03-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_25_Mutatielevering_ overlijden in Nederland
Meta:
@regels VR00061 (BETROKKENHEID: PARTNER)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgOverlijden
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A90: BRBY0011-TC0101 # 1 : status=null
Given data uit excel /templatedata/art_input_091.xls , BRBY0011-TC0101
Given de database wordt gereset voor de personen 700155223
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sOO0 | 00000000-0000-0000-0091-300000000003
Given de sjabloon /berichtdefinities/KUC091_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0011-TC0101-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen
Then wacht tot alle berichten zijn ontvangen

!-- A91: BRBY0011-TC0101 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-300000000003
abonnement_id | 5670000
_bsn_rOO0_ | 700155223
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0011-TC0101-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]


Scenario: Scenario_26_Overlijden in Buitenland Mutatie levering
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgOverlijden
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A92: BRBY0904-TC0101 # 1 : status=null
Given data uit excel /templatedata/art_input_091_B.xls , BRBY0904-TC0101
Given de database wordt gereset voor de personen 700212280
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sOO0 | 00000000-0000-0000-0091-130000000008
Given de sjabloon /berichtdefinities/KUC091-Buitenland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0904-TC0101-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A93: BRBY0904-TC0101 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-130000000008
abonnement_id | 5670000
_bsn_rOO0_ | 700212280
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0904-TC0101-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]

!-- A94: BRBY0904-TC0101 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-130000000008
abonnement_id | 5670000
_bsn_rOO0_ | 700212280
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0904-TC0101-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:overlijden

!-- A95: BRBY0904-TC0101 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-130000000008
abonnement_id | 5670000
_bsn_rOO0_ | 700212280
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0904-TC0101-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:partner/brp:huwelijk/brp:betrokkenheden/brp:partner/brp:persoon/brp:identificatienummers


Scenario: Scenario_27
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgOverlijden
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A96: BRBY0904-TC0401 # 1 : status=null
Given data uit excel /templatedata/art_input_091_B.xls , BRBY0904-TC0401
Given de database wordt gereset voor de personen 700212280
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sOO0 | 00000000-0000-0000-0091-130000000011
Given de sjabloon /berichtdefinities/KUC091-Buitenland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0904-TC0401-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A97: BRBY0904-TC0401 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-130000000011
abonnement_id | 5670000
_bsn_rOO0_ | 700212280
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0904-TC0401-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]

!-- A98: BRBY0904-TC0401 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-130000000011
abonnement_id | 5670000
_bsn_rOO0_ | 700212280
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0904-TC0401-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]


Scenario: Scenario_28_Mutatie obv doelbinding - Overlijden in buitenland
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgOverlijden
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A99: BRBY0904-TC0501 # 1 : status=null
Given data uit excel /templatedata/art_input_091_B.xls , BRBY0904-TC0501
Given de database wordt gereset voor de personen 700212280
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sOO0 | 00000000-0000-0000-0091-130000000012
Given de sjabloon /berichtdefinities/KUC091-Buitenland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0904-TC0501-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A100: BRBY0904-TC0501 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0091-130000000012
abonnement_id | 5670000
_bsn_rOO0_ | 700212280
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0904-TC0501-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]


Scenario: Scenario_29_Verstrekkingsbeperking_wel mutatie levering
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A101: happy-flow-TC0101 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0101
Given de database wordt gereset voor de personen 330298628
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-300000000001
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/happy-flow-TC0101-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A102: happy-flow-TC0101 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0033-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 330298628
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0101-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie

!-- A103: happy-flow-TC0101 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0033-300000000001
abonnement_id | 5670014
_bsn_rOO0_ | 330298628
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0101-3-dataresponse.xml voor de expressie //Results

!-- A104: happy-flow-TC0101 # 4 : status=null
Then is binnen 120s de query /sqltemplates/selecteerPersonenIndicaties_Verstrekkingsbeperking_ART-MutatieLevering.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0101-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_30_Wijziging gezag_ Mutatie levering
Meta:
@status Uitgeschakeld
!-- enkel de bijhouding werd nog gedaan, er wordt geen mut lev gecontroleerd
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A105: happy-flow-TC0201 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0201
Given de database wordt gereset voor de personen 338451092, 337511597, 337820399
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-300000000003
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/happy-flow-TC0201-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A106: happy-flow-TC0201 # 2 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0033-300000000003
!--abonnement_id | 5670000
!--_bsn_rOO0_ | 338451092
!--Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0201-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie

!-- A107: happy-flow-TC0201 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0033-300000000003
abonnement_id | 5670014
_bsn_rOO0_ | 338451092
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0201-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_31
Meta:
@status Uitgeschakeld
!-- enkel de bijhouding werd nog getest, de mut lev controle staat in quarantaine
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A108: happy-flow-TC0202 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0202
Given de database wordt gereset voor de personen 338451092, 337511597, 337820399
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-300000000004
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/happy-flow-TC0202-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A109: happy-flow-TC0202 # 2 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0033-300000000004
!--abonnement_id | 5670000
!--_bsn_rOO0_ | 338451092
!--Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0202-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie

!-- A110: happy-flow-TC0202 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0033-300000000004
abonnement_id | 5670014
_bsn_rOO0_ | 338451092
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0202-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_32_Wijziging curatele
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A111: happy-flow-TC0301 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0301
Given de database wordt gereset voor de personen 330525979
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-300000000005
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/happy-flow-TC0301-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A112: happy-flow-TC0301 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0033-300000000005
abonnement_id | 5670000
_bsn_rOO0_ | 330525979
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0301-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie

!-- A113: happy-flow-TC0301 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0033-300000000005
abonnement_id | 5670014
_bsn_rOO0_ | 330525979
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/happy-flow-TC0301-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_33
Meta:
@regels VR00088
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgNaamGeslacht
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A114: HF-wijzigingGeslachtsnaam-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_021.xls , HF-wijzigingGeslachtsnaam-TC01
Given de database wordt gereset voor de personen 200010049
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sNB1 | 00000000-0000-0000-0021-300000000001
Given de sjabloon /berichtdefinities/KUC021-NaamGeslacht_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/HF-wijzigingGeslachtsnaam-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A115: HF-wijzigingGeslachtsnaam-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0021-300000000001
abonnement_id | 5670024
_bsn_rOO0_ | 200010049
_objectid$persoon1_ | 200010049
Then is binnen 90s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/HF-wijzigingGeslachtsnaam-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_34
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A116: LT01FT01-32 # null : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-32
Given de database wordt gereset voor de personen 800011375, 800074658, 809468852
Given extra waardes:
SLEUTEL | WAARDE
partijCode_rMM1 | 505003
partijCode_dbr0 | 505003
postcode_aap1 | 2000AA
gemeenteCode_aap0 | 5103
_objectid$burgerservicenummer_ipv1_ | 800011375
woonplaatsnaam_aap1 | SRPUC50151-3-Woonplaats
partijCode_dbr1 | 505003
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/LT01FT01-32--soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
And wacht tot alle berichten zijn ontvangen

Scenario: Scenario_35_Geboorte in nederland_Vul bericht kind, Mutatie Berichten ouders
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@regels VR00090, VR00095

!-- A117: VR01002-TC01 # 1 : status=null
!--Given de database wordt gereset voor de personen 800011375, 800074658, 809468852
Given data uit excel /templatedata/art_input_001.xls , VR01002-TC01
Given extra waardes:
SLEUTEL | WAARDE
geboorteInNederland$partijCode_B00 | 505003
partijCode_B01 | 505003
referentienummer_B00 | 00000000-0000-0000-0001-300000000072
partijCode_B00 | 505003
Given de sjabloon /berichtdefinities/KUC001_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/VR01002-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A118: EIOuderInDoelbinding-TC02 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , EIOuderInDoelbinding-TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000072
abonnement_id | 5670020
_bsn_rOO0_ | 800011375
srtsynchronisatie_id | 2
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/EIOuderInDoelbinding-TC02-1-dataresponse.xml voor de expressie //Results

!-- A119: EIOuderInDoelbinding-TC02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000072
abonnement_id | 5670020
_bsn_rOO0_ | 809468852
srtsynchronisatie_id | 1
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/EIOuderInDoelbinding-TC02-2-dataresponse.xml voor de expressie //Results

!-- A120: VR00090-Vulbericht-TC01 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , VR00090-Vulbericht-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000072
abonnement_id | 5670000
_bsn_rOO0_ | 800074658
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00090-Vulbericht-TC01-1-dataresponse.xml voor de expressie //brp:bijgehoudenActies


Scenario: Scenario_36
Meta:
@regels R1317 (VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A121: RPB-basis-tc02 # 1 : status=null
Given data uit excel /templatedata/art_input_042-PB.xls , RPB-basis-tc02
Given de database wordt gereset voor de personen 190013539
Given extra waardes:
SLEUTEL | WAARDE
datumAanvang_rgr1 | ${vandaag(0,0,-1)}
referentienummer_sHB1 | 00000000-0000-0000-0042-431000000003
datumAanvangGeldigheid_rar1 | ${vandaag(0,0,-1)}
Given de sjabloon /berichtdefinities/KUC042-PartnerschapBuitenland_request_template-042-PB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/RPB-basis-tc02-1-soapresponse.xml voor expressie //brp:resultaat
Then wacht tot alle berichten zijn ontvangen

!-- A122: Verwerkingssoort-TC04 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , Verwerkingssoort-TC04
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0042-431000000003
abonnement_id | 5670000
_bsn_rOO0_ | 190013539
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC04-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_37_Correctie adres, controle op verwerkingssoort in mut lev
Meta:
@regels R1317 (VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A123: BRBY0032-TC3040-2 # 2 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0032-TC3040-2
Given de database wordt gereset voor de personen 330006575
Given extra waardes:
SLEUTEL | WAARDE
datumAanvangGeldigheid_rOO0 | 1989-01-01
datumEindeGeldigheid_rOO0 | 1991-01-01
referentienummer_sOO0 | 00000000-0000-0000-0055-310000000042
datumAanvangAdreshouding_rOO1 | 1991-01-01
datumAanvangAdreshouding_rOO0 | 1989-01-01
datumAanvangGeldigheid_rOO1 | 1991-01-01
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0032-TC3040-2-2-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A124: Verwerkingssoort-TC05 # 1 : status=null
!--Given data uit excel /templatedata/art_input_055.xls , Verwerkingssoort-TC05
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-310000000042
abonnement_id | 5670000
_bsn_rOO0_ | 330006575
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC05-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_38_Correctie adres, controle op verwerkingssoort in mut lev
Meta:
@regels R1317 (VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A125: BRBY0032-TC3040-2 # 3 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0032-TC3040-2
Given de database wordt gereset voor de personen 330006575
Given extra waardes:
SLEUTEL | WAARDE
huisnummer_rOO1 | 13
gemeentedeel_rOO1 | []
gemeenteCode_rOO1 | 0344
datumAanvangGeldigheid_rOO1 | 1990-12-15
postcode_rOO1 | 3512AE
datumAanvangGeldigheid_rOO0 | 1990-11-15
aangeverAdreshoudingCode_rOO1 | P
datumAanvangAdreshouding_rOO1 | 1990-12-15
referentienummer_sOO0 | 00000000-0000-0000-0055-320000000047
woonplaatsnaam_rOO1 | Utrecht
datumAanvangAdreshouding_rOO0 | 1990-11-15
naamOpenbareRuimte_rOO1 | Neude
datumEindeGeldigheid_rOO1 | []
datumEindeGeldigheid_rOO0 | 1990-12-15
redenWijzigingCode_rOO1 | P
afgekorteNaamOpenbareRuimte_rOO1 | Neude
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0032-TC3040-2-3-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A126: Verwerkingssoort-TC06 # 1 : status=null
!--Given data uit excel /templatedata/art_input_055.xls , Verwerkingssoort-TC06
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-320000000047
abonnement_id | 5670000
_bsn_rOO0_ | 330006575
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC06-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_39_Correctie adres, controle op verwerkingssoort in mut lev
Meta:
@regels R1317 (VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A127: BRBY0032-TC3040-2 # 4 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0032-TC3040-2
Given de database wordt gereset voor de personen 330006575
Given extra waardes:
SLEUTEL | WAARDE
huisnummer_rOO1 | 13
gemeentedeel_rOO1 | []
gemeenteCode_rOO1 | 0344
datumAanvangGeldigheid_rOO1 | 1990-12-20
postcode_rOO1 | 3512AE
datumAanvangGeldigheid_rOO0 | 1990-11-11
aangeverAdreshoudingCode_rOO1 | P
datumAanvangAdreshouding_rOO1 | 1990-12-20
referentienummer_sOO0 | 00000000-0000-0000-0055-330000000047
woonplaatsnaam_rOO1 | Utrecht
datumAanvangAdreshouding_rOO0 | 1990-11-11
naamOpenbareRuimte_rOO1 | Neude
datumEindeGeldigheid_rOO1 | []
datumEindeGeldigheid_rOO0 | 1990-12-20
redenWijzigingCode_rOO1 | P
afgekorteNaamOpenbareRuimte_rOO1 | Neude
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0032-TC3040-2-4-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A128: Verwerkingssoort-TC07 # 1 : status=null
!--Given data uit excel /templatedata/art_input_055.xls , Verwerkingssoort-TC07
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-330000000047
abonnement_id | 5670000
_bsn_rOO0_ | 330006575
Then is binnen 90s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC07-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_40_Correctie adres, controle op verwerkingssoort in mut lev
Meta:
@regels R1317 (VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A129: BRBY0032-TC3040-2 # 5 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0032-TC3040-2
Given de database wordt gereset voor de personen 330006575
Given extra waardes:
SLEUTEL | WAARDE
huisnummer_rOO1 | 45
gemeentedeel_rOO1 | []
gemeenteCode_rOO1 | 0344
datumAanvangGeldigheid_rOO1 | 1990-10-01
postcode_rOO1 | 3561VA
datumAanvangGeldigheid_rOO0 | 1989-01-01
aangeverAdreshoudingCode_rOO1 | []
datumAanvangAdreshouding_rOO1 | 1990-10-01
referentienummer_sOO0 | 00000000-0000-0000-0055-340000000047
woonplaatsnaam_rOO1 | Utrecht
datumAanvangAdreshouding_rOO0 | 1989-01-01
naamOpenbareRuimte_rOO1 | Rhonedreef
datumEindeGeldigheid_rOO1 | 1990-11-11
datumEindeGeldigheid_rOO0 | 1990-10-01
redenWijzigingCode_rOO1 | A
afgekorteNaamOpenbareRuimte_rOO1 | Rhonedreef
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0032-TC3040-2-5-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A130: Verwerkingssoort-TC08 # 1 : status=null
!--Given data uit excel /templatedata/art_input_055.xls , Verwerkingssoort-TC08
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-340000000047
abonnement_id | 5670000
_bsn_rOO0_ | 330006575
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC08-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_41__Correctie adres, controle op verwerkingssoort in mut lev
Meta:
@regels R1317 (VR00073)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A131: BRBY0189-B-TC14 # 1 : status=null
Given data uit excel /templatedata/art_input_002.xls , BRBY0189-B-TC14
Given de database wordt gereset voor de personen 900188005, 900100540, 900171868
Given de sjabloon /berichtdefinities/KUC002_request_template_002.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0189-B-TC14-1-soapresponse.xml voor expressie //brp:resultaat
Then wacht tot alle berichten zijn ontvangen

!-- A132: Verwerkingssoort-TC09 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , Verwerkingssoort-TC09
Given extra waardes:
SLEUTEL | WAARDE
srtSync | 1
srtActie | 23
abonnement_id | 5670000
bsn | 900171868
selectItem | id
Given de database is aangepast dmv /sqltemplates/selecteerActieIdBijSoortActieVoorBsn.sql
Then is binnen 120s de query /sqltemplates/selecteerVerantwoordingsinformatieBijAbonnementVoorBsn.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC09-1-dataresponse.xml voor de expressie //brp:bijgehoudenPersonen/brp:persoon[1]


Scenario: Scenario_42
Meta:
@status uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A133: OPB-basis-TC0102 # 1 : status=OOS
!--Given data uit excel /templatedata/art_input_042-PB-Ontbinding.xls , OPB-basis-TC0102
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--adellijkeTitelCode_spp5 | []
!--predicaatCode_spp5 | []
!--_objectid$persoon6_ | 190013539
!--scheidingsteken_spp5 | []
!--_GeregistreerdPartnerschap_ | select r.id from kern.pers p, kern.betr b, kern.relatie r where r.id = b.relatie and b.pers = p.id  and r.srt = 2 and p.bsn in (190013539);
!--referentienummer_sHB1 | 00000000-0000-0000-0042-531000000002
!--_objectid$persoon5_ | []
!--voorvoegsel_spp5 | []
!--voornamen_spp5 | Joep
!--indicatieNamenreeks_spp5 | N
!--geslachtsnaamstam_spp5 | Meloen
!--_objectid$persoon4_ | 190013539
!--Given de sjabloon /berichtdefinities/KUC042-PartnerschapBuitenland-Ontbinding_request_template-042-PB.xml
!--When het bericht is naar endpoint verstuurd
!--Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/OPB-basis-TC0102-1-soapresponse.xml voor expressie //brp:resultaat

!-- A134: Verwerkingssoort-TC0. # 1 : status=OOS
!--Given data uit excel /templatedata/art_input_091.xls , Verwerkingssoort-TC0.
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0091-300000000179
!--abonnement_id | 5670000


Scenario: Scenario_43_Correctie adres mut lev
Meta:
@regels R1804 (VR00090)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A135: BRBY0032-TC3040-2 # 2 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0032-TC3040-2
Given de database wordt gereset voor de personen 310027603
Given extra waardes:
SLEUTEL | WAARDE
_bsn_rOO0_ | 310027603
datumAanvangGeldigheid_rOO1 | 2014-01-01
datumAanvangGeldigheid_rOO0 | 2012-12-12
abonnement_id | 5670000
datumAanvangAdreshouding_rOO1 | 2014-01-01
referentienummer_sOO0 | 00000000-0000-0000-0055-310000000001
datumAanvangAdreshouding_rOO0 | 2012-12-12
datumEindeGeldigheid_rOO0 | 2014-01-01
_bsn_rOO1_ | 310027603
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0032-TC3040-2-2-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A136: VR00090-Mutatie-TC01 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , VR00090-Mutatie-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670000
_bsn_rOO0_ | 310027603
referentienummer_sOO0 | 00000000-0000-0000-0055-310000000001
Then is binnen 90s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00090-Mutatie-TC01-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_44
Meta:
@status Uitgeschakeld
@regels R1554 (VR00093)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgReisdocument
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A137: HF-verkrijgingReisdocument-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_070.xls , HF-verkrijgingReisdocument-TC01
Given de database wordt gereset voor de personen 350019344
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sRB1 | 00000000-0000-0001-0070-000000000004
_objectid$persoon1_ | 350019344
Given de sjabloon /berichtdefinities/KUC070-Reisdocumenten_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/HF-verkrijgingReisdocument-TC01-1-soapresponse.xml voor expressie //brp:resultaat
Then wacht tot alle berichten zijn ontvangen

!-- A138: VR00093-Mutatiebericht-TC01 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , VR00093-Mutatiebericht-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670045
_bsn_rOO0_ | 350019344
referentienummer_sOO0 | 00000000-0000-0001-0070-000000000004
Then is binnen 90s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00093-Mutatiebericht-TC01-1-dataresponse.xml voor de expressie //brp:persoon


Scenario: Scenario_45
Meta:
@status Uitgeschakeld
@regels VR00093
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgReisdocument
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A139: HF-verkrijgingReisdocument-TC01 # 2 : status=null
Given data uit excel /templatedata/art_input_070.xls , HF-verkrijgingReisdocument-TC01
Given de database wordt gereset voor de personen 350019344
Given de database is aangepast met:
 delete from kern.perscache where id = 35007;
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sRB1 | 00000000-0000-0002-0070-000000000004
_objectid$persoon1_ | 350019344
Given de sjabloon /berichtdefinities/KUC070-Reisdocumenten_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/HF-verkrijgingReisdocument-TC01-2-soapresponse.xml voor expressie //brp:resultaat
Then wacht tot alle berichten zijn ontvangen

!-- A140: VR00093-Mutatiebericht-TC02 # 1 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , VR00093-Mutatiebericht-TC02
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670045
_bsn_rOO0_ | 350019344
referentienummer_sOO0 | 00000000-0000-0001-0070-000000000004
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00093-Mutatiebericht-TC02-1-dataresponse.xml voor de expressie //Results

!-- A141: VR00093-Mutatiebericht-TC02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670041
_bsn_rOO0_ | 350019344
referentienummer_sOO0 | 00000000-0000-0001-0070-000000000004
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00093-Mutatiebericht-TC02-2-dataresponse.xml voor de expressie //brp:reisdocumenten



Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service
@auteur tools
@status Klaar
@epic legacy
@module onderhoud-afnemerindicaties

Scenario: Scenario_0
Meta:
@regels VR00050, VR00049 (VOOR ABO DIE ALLE ABONNEMENTSEXPRESSIES HEEFT) LET OP: CHECK OP OF EXPECTED OOK DAADWERKELIJK ALLES BEVAT WAT GELEVERD ZOU MOGEN WORDEN, MOET NOG GEBEUREN!!!
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A2: VP.0-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-100000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/VP.0-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A3: VP.0-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC01-2-dataresponse.xml voor de expressie //Results

!-- A4: VP.0-TC01 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-100000000001
abonnement_id | 5670002
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC01-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon

!-- A5: VP.0-TC01 # 4 : status=null
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_PlaatsVerwAfnind_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC01-4-dataresponse.xml voor de expressie //Results

!-- A6: VP.0-TC01 # 5 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC01-5-dataresponse.xml voor de expressie //Results

!-- A7: VR0050-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VR0050-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-100000000001
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/VR0050-TC01-1-soapresponse.xml voor expressie //brp:stuurgegevens


Scenario: Scenario_1
Meta:
@regels VR00051
VR00058 (MET  DATUMAANVANGMATERIELEPERIODE)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A8: VerhuizingOndAfnemerInd-TC00 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuizingOndAfnemerInd-TC00
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-400000000253
referentienummer_sOO0 |
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/VerhuizingOndAfnemerInd-TC00-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A9: VerhuizingOndAfnemerInd-TC00 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-400000000253
abonnement_id | 5670002
_bsn_rOO0_ | 677080426
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan
/testcases/art_input_OnderhoudAfnemerindicaties/data/VerhuizingOndAfnemerInd-TC00-2-dataresponse.xml voor de expressie //Results

!-- A10: VerhuizingOndAfnemerInd-TC00 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-400000000253
abonnement_id | 5670002
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_Verhuizing_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VerhuizingOndAfnemerInd-TC00-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_2
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A11: VP.0-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC02
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/VP.0-TC02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A12: VP.0-TC02 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC02-2-dataresponse.xml voor de expressie //Results

!-- A13: VP.0-TC02 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670002
referentienr_id |
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_PlaatsVerwAfnind_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC02-3-dataresponse.xml voor de expressie //Results

!-- A14: VP.0-TC02 # 4 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VP.0-TC02-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_3
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A15: VerhuizingOndAfnemerInd-TC00 # 5 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuizingOndAfnemerInd-TC00
Given extra waardes:
SLEUTEL | WAARDE
huisnummer_aap1 | 3
referentienummer_sMM0 | 00000000-0000-0000-0700-410000000253
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/VerhuizingOndAfnemerInd-TC00-5-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A16: VerhuizingOndAfnemerInd-TC00 # 6 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-410000000253
abonnement_id | 5670002
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_Verhuizing_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/VerhuizingOndAfnemerInd-TC00-6-dataresponse.xml voor de expressie //Results


Scenario: Scenario_4
Meta:
@regels BRLV0001
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A17: BRLV0001-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0001-TC01
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0001-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A18: BRLV0001-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0001-TC01-2-dataresponse.xml voor de expressie //Results

!-- A19: BRLV0001-TC01 # 3 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0001-TC01-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_5
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A20: BRLV0003-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0003-TC01
Given de database wordt gereset voor de personen 110015927
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0003-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A21: BRLV0003-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0003-TC01-2-dataresponse.xml voor de expressie //Results

!-- A22: BRLV0003-TC01 # 3 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0003-TC01-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_6
Meta:
@regels BRLV0003
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A23: BRLV0003-TC01 # 4 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0003-TC01
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0003-TC01-4-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A24: BRLV0003-TC01 # 5 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0003-TC01-5-dataresponse.xml voor de expressie //Results

!-- A25: BRLV0003-TC01 # 6 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0003-TC01-6-dataresponse.xml voor de expressie //Results


Scenario: Scenario_7
Meta:
@regels BRLV0011
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A26: BRLV0011-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0011-TC01
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0011-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A27: BRLV0011-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0011-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_8
Meta:
@regels BRLV0013
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A28: BRLV0013-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC01
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A29: BRLV0013-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_9
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A30: BRLV0013-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC02
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A31: BRLV0013-TC02 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC02-2-dataresponse.xml voor de expressie //Results

!-- A32: BRLV0013-TC02 # 3 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC02-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_10
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A33: BRLV0013-TC03 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC03
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC03-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A34: BRLV0013-TC03 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC03-2-dataresponse.xml voor de expressie //Results

!-- A35: BRLV0013-TC03 # 3 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC03-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_11
Meta:
@regels BRLV0011
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A36: BRLV0013-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC04
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC04-1-soapresponse.xml voor expressie //brp:meldingen

!-- A37: BRLV0013-TC04 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC04-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_12
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A38: BRLV0013-TC05 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC05
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC05-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A39: BRLV0013-TC05 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC05-2-dataresponse.xml voor de expressie //Results

!-- A40: BRLV0013-TC05 # 3 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC05-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_13
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A41: BRLV0013-TC06 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC06
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC06-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A42: BRLV0013-TC06 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC06-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_14
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A43: BRLV0013-TC07 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0013-TC07
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0013-TC07-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A44: BRLV0013-TC07 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0013-TC07-2-dataresponse.xml voor de expressie //Results
Then de database wordt opgeruimd met:
 delete from autaut.his_persafnemerindicatie;delete from autaut.persafnemerindicatie;delete from kern.perscache;


Scenario: Scenario_15
Meta:
@regels BRLV0014
@status Uitgeschakeld
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A45: BRLV0014-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0014-TC01
Given de database wordt gereset voor de personen 110015927
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0014-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A46: BRLV0014-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/BRLV0014-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_16
Meta:
@regels BRLV0009
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A47: BRLV009-TC01 # 2 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV009-TC01
Given de database wordt gereset voor de personen 500105856
Given de database is aangepast met:
 update kern.pers set naderebijhaard=4 where naderebijhaard = 7 AND bsn = 500105856;
update kern.his_persbijhouding set naderebijhaard=4 where naderebijhaard=7 AND pers in (select id from kern.pers  where bsn = 500105856);
update kern.pers set bsn= 500105856 where bsn = 500110256;
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV009-TC01-2-soapresponse.xml voor expressie //brp:meldingen
Then de database wordt opgeruimd met:
 update kern.pers set naderebijhaard=7 where naderebijhaard = 4 AND bsn = 500105856;
update kern.his_persbijhouding set naderebijhaard=7 where naderebijhaard=4 AND pers in (select id from kern.pers  where bsn = 500105856);
update kern.pers set bsn= 500110256 where bsn = 500105856 and naderebijhaard<>7;


Scenario: Scenario_18
!-- A49: plaatsing-afnemerindicatie-1-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-1-TC01
Given de database wordt gereset voor de personen 695544457, 912345664, 749262217
Given de database is aangepast met:
 delete from autaut.persafnemerindicatie where pers in (select id from kern.pers where bsn = 695544457);
delete from kern.perscache where pers in (select id from kern.pers where bsn = 695544457);
delete from kern.perscache where pers in (select id from kern.pers where bsn = 749262217);
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/plaatsing-afnemerindicatie-1-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then de database wordt opgeruimd met:
 update Kern.PersIndicatie set pers = (select id from kern.pers where bsn = 695544457) where  srt = 3 and pers in (select id from kern.pers where bsn = 912345664);


Scenario: Scenario_19
!-- A50: plaatsing-afnemerindicatie-2-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-2-TC01
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/plaatsing-afnemerindicatie-2-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_20
!-- A51: plaatsing-afnemerindicatie-3-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-3-TC01
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/plaatsing-afnemerindicatie-3-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_21
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A52: BRBY0401-TC0401 # 1 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRBY0401-TC0401
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sHB0 | 00000000-0000-0000-0042-400000000006
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRBY0401-TC0401-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A53: verstrekkingsbeperking-BRLV0032-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , verstrekkingsbeperking-BRLV0032-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0042-400000000006
abonnement_id | 5670033
ontvangendepartij_id | 22707
Then is binnen 60s de query /sqltemplates/leveringKUC42AfnemerOpAfnemerindicatiePerBSN_ART-AfnemerIndicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/verstrekkingsbeperking-BRLV0032-TC01-1-dataresponse.xml voor de expressie //Results

!-- A54: verstrekkingsbeperking-BRLV0032-TC02 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , verstrekkingsbeperking-BRLV0032-TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0042-400000000006
abonnement_id | 5670034
ontvangendepartij_id | 177
Then is de query /sqltemplates/leveringKUC42AfnemerOpAfnemerindicatiePerBSN_ART-AfnemerIndicaties.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/verstrekkingsbeperking-BRLV0032-TC02-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_22
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A55: happy-flow-TC0102 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0102
Given de database wordt gereset voor de personen 677080426
Given de database is aangepast met:
 update kern.persverstrbeperking set partij = 347, pers = (select id from kern.pers where bsn = 677080426) where pers in (select id from kern.pers where bsn = 330298628)
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-400000000002
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/happy-flow-TC0102-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_23
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A56: happy-flow-TC0103 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0103
Given de database is aangepast met:
 update kern.persverstrbeperking set pers = (select id from kern.pers where bsn = 677080426) where pers in (select id from kern.pers where bsn = 330298628)
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-400000000003
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/happy-flow-TC0103-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_24
!-- A57: plaatsing-afnemerindicatie-4-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-4-TC01
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/plaatsing-afnemerindicatie-4-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_25
Meta:
@status Uitgeschakeld
@regels BRLV0032
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A58: LT01FT01-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-02
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-400000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/LT01FT01-02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A59: verstrekkingsbeperking-op-Sinterklaas-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , verstrekkingsbeperking-op-Sinterklaas-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-400000000001
abonnement_id | 5670035
_bsn_rOO0_ | 677080426
ontvangendepartij_id | 347
referentienummer_sOO0 |
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/data/verstrekkingsbeperking-op-Sinterklaas-TC01-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_26
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A60: happy-flow-TC0102 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0102
Given de database wordt gereset voor de personen 677080426, 330298628
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-410000000002
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/happy-flow-TC0102-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then de database wordt opgeruimd met:
 update kern.persverstrbeperking set pers = (select id from kern.pers where bsn = 677080426) where pers in (select id from kern.pers where bsn = 330298628);
delete from kern.perscache where pers = (select id from kern.pers where bsn = 677080426)


Scenario: Scenario_27
!-- A61: BRLV0031-plaatsing-afnemerindicatie-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0031-plaatsing-afnemerindicatie-TC01
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0031-plaatsing-afnemerindicatie-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_28
Meta:
@regels BRLV0031

!-- A62: BRLV0031-plaatsing-afnemerindicatie-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0031-plaatsing-afnemerindicatie-TC02
Given de database is aangepast met:
 update kern.persindicatie set pers = (select id from kern.pers where bsn = 749262217) where pers in (select id from kern.pers where bsn = 500137857);
delete from autaut.persafnemerindicatie where pers in (select id from kern.pers where bsn = 749262217);
delete from kern.perscache where pers = (select id from kern.pers where bsn = 749262217)
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0031-plaatsing-afnemerindicatie-TC02-1-soapresponse.xml voor expressie //brp:meldingen
Then de database wordt opgeruimd met:
 update kern.persindicatie set pers = (select id from kern.pers where bsn = 500137857) where pers in (select id from kern.pers where bsn = 749262217);


Scenario: Scenario_29
!-- A63: plaatsing-afnemerindicatie-2-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-2-TC01
Given de database wordt gereset voor de personen 749262217
Given de database is aangepast met:
 delete from autaut.persafnemerindicatie where pers in (select id from kern.pers where bsn = 749262217);
delete from kern.perscache where pers in (select id from kern.pers where bsn = 749262217);
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/plaatsing-afnemerindicatie-2-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_30
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A64: happy-flow-TC0101 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0101
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-400000000001
objectid$persoon1 | 749262217
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/happy-flow-TC0101-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_31
!-- A65: BRLV0031-verwijder-afnemerindicatie-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0031-verwijder-afnemerindicatie-TC01
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0031-verwijder-afnemerindicatie-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

Scenario: Scenario_34
Meta:
@regels BRLV0006
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A70: BRLV0006-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , BRLV0006-TC01
Given de database wordt gereset voor de personen 700158431
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_OnderhoudAfnemerindicaties/response/BRLV0006-TC01-1-soapresponse.xml voor expressie //brp:meldingen



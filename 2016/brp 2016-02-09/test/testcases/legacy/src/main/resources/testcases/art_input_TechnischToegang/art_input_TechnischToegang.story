Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service
@auteur tools
@status Klaar
@epic legacy
@module technisch-toegang

Scenario: Scenario_0
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A2: VerhuizingOndAfnemerInd-TC00 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuizingOndAfnemerInd-TC00
Given de database wordt gereset voor de personen 110015927
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | []
_objectid$burgerservicenummer_ipv1_ | 110015927
referentienummer_sMM0 | 00000000-0000-0000-0700-600000000253
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VerhuizingOndAfnemerInd-TC00-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_1
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A3: plaatsing-afnemerindicatie-1-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-1-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182 NIET GELDIG ABO Einddate 20130201 KD sysdat
afnemerCode_aap1 | 505006
referentienummer_sLB1 | 0000000A-3000-1000-0000-600000000027
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505006
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505006
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A4: BRLV0018_plaatsing-afnemerindicatie-1-TC01 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0018_plaatsing-afnemerindicatie-1-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0018_plaatsing-afnemerindicatie-1-TC01-2-soapresponse.xml voor expressie //brp:meldingen/brp:melding[brp:regelCode = 'BRLV0018']
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0018_plaatsing-afnemerindicatie-1-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_2
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A5: plaatsing-afnemerindicatie-1-TC01 # 3 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-1-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182 NIET GELDIG ABO Begindat toekomst GD sysdat
afnemerCode_aap1 | 505006
referentienummer_sLB1 | 0000000A-3000-1000-0000-610000000027
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505006
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505006
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A6: BRLV0019_plaatsing-afnemerindicatie-1-TC01 # 4 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0019_plaatsing-afnemerindicatie-1-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0019_plaatsing-afnemerindicatie-1-TC01-4-soapresponse.xml voor expressie //brp:meldingen/brp:melding[brp:regelCode = 'BRLV0019']
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0019_plaatsing-afnemerindicatie-1-TC01-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_3
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A7: plaatsing-afnemerindicatie-2-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-2-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182 NIET GELDIG DST Einddate 20130201 KD sysdat
afnemerCode_aap1 | 505006
referentienummer_sLB1 | 0000000A-3000-1000-0000-600000000028
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505006
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505006
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A8: BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01-2-soapresponse.xml voor expressie //brp:meldingen
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_4
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A9: plaatsing-afnemerindicatie-2-TC01 # 3 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-2-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182 NIET GELDIG DST Begindat toekomst KD sysdat
afnemerCode_aap1 | 505006
referentienummer_sLB1 | 0000000A-3000-1000-0000-610000000028
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505006
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505006
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A10: BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01 # 4 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01-4-soapresponse.xml voor expressie //brp:meldingen
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0018_BRLV0019_plaatsing-afnemerindicatie-2-TC01-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_5
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A11: plaatsing-afnemerindicatie-3-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-3-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182 TOESTAND ABO OG DEFINITIEF
afnemerCode_aap1 | 505006
referentienummer_sLB1 | 0000000A-3000-1000-0000-600000000029
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505006
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505006
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A12: BRLV0020_plaatsing-afnemerindicatie-3-TC01 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0020_plaatsing-afnemerindicatie-3-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0020_plaatsing-afnemerindicatie-3-TC01-2-soapresponse.xml voor expressie //brp:meldingen
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0020_plaatsing-afnemerindicatie-3-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_6
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A13: plaatsing-afnemerindicatie-3-TC01 # 3 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-3-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Bennebroek 2120 - 2121 TOESTAND DST OG DEFINITIEF
afnemerCode_aap1 | 505006
referentienummer_sLB1 | 0000000A-3000-1000-0000-610000000029
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505006
burgerservicenummer_ipr1 | 110015927
partijCode_pLB1 | 505006
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A14: BRLV0014_BRLV0021_plaatsing-afnemerindicatie-3-TC01 # 4 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0014_BRLV0021_plaatsing-afnemerindicatie-3-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0014_BRLV0021_plaatsing-afnemerindicatie-3-TC01-4-soapresponse.xml voor expressie //brp:meldingen
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0014_BRLV0021_plaatsing-afnemerindicatie-3-TC01-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_7
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A15: VP.0-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
comid$vervalAfnemerindicatie1 | comid.registratieAfnemerindicatie1
burgerservicenummer_ipr1 | 110015927
abonnementNaam_aap2 | Geen pop.bep. levering op basis van afnemerindicatie
toelichtingOntlening_vLB1 | toelichting
referentienummer_sLB1 | 0000000A-3000-1000-0000-600000000001
datumAanvangMaterielePeriode_aap2 | ${vandaag(-1,0,1)}
abonnementNaam_aap1 | Geen pop.bep. levering op basis van afnemerindicatie
datumAanvangMaterielePeriode_aap1 | ${vandaag()}
datumAanvangGeldigheid_vav1 | ${vandaag()}
afnemerCode_aap2 | 017401
afnemerCode_aap1 | []
zendendePartij_zsL1 | 017401
burgerservicenummer_ipv1 | 110015927
partijCode_vLB1 | 017401
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VP.0-TC01-1-soapresponse.xml voor expressie //soap:Fault

!-- A16: VP.0-TC01 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , VP.0-TC01
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/VP.0-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_8
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A17: VP.0-TC01 # 3 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given de database wordt gereset voor de personen 110015927
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | Geen pop.bep. levering van BSN en ANR op basis van doelbinding
afnemerCode_aap1 | 505002
referentienummer_sLB1 | 0000000A-3000-1000-0000-610000000001
datumAanvangMaterielePeriode_aap1 | ${vandaag(-1,0,0)}
burgerservicenummer_ipv1 | 110015927
zendendePartij_zsL1 | 505002
burgerservicenummer_ipr1 | 110015927
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A18: BRLV0017_VP.0-TC01 # 4 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0017_VP.0-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0017_VP.0-TC01-4-soapresponse.xml voor expressie //brp:meldingen
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_TechnischToegang/data/BRLV0017_VP.0-TC01-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_9
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A19: SyncStructuur-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC01
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 037101
referentienummer_slB1 | 0000000A-3000-7000-1000-600000000002
burgerservicenummer_zlB1 | 700137038
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A20: AUTH0001_SyncStructuur-TC01 # 1 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , AUTH0001_SyncStructuur-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/AUTH0001_SyncStructuur-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_10
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A21: SyncStructuur-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC02
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 505006
referentienummer_slB1 | 0000000A-3000-7000-1000-600000000003
abonnementNaam_plB1 | postcode gebied Hillegom 2180 - 2182 NIET GELDIG DST Begindat toekomst KD sysdat
burgerservicenummer_zlB1 | 110015927
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A22: BRLV0018_BRLV0019_SyncStructuur-TC02 # 2 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0018_BRLV0019_SyncStructuur-TC02
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0018_BRLV0019_SyncStructuur-TC02-2-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_11
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A23: SyncStructuur-TC03 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC03
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 505006
referentienummer_slB1 | 0000000A-3000-7000-1000-600000000004
abonnementNaam_plB1 | postcode gebied Hillegom 2180 - 2182 TOESTAND ABO OG DEFINITIEF
burgerservicenummer_zlB1 | 110015927
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A24: BRLV0020_SyncStructuur-TC03 # 1 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0020_SyncStructuur-TC03
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0020_SyncStructuur-TC03-1-soapresponse.xml voor expressie //brp:melding[brp:regelCode/text()="BRLV0020"]


Scenario: Scenario_12
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A25: SyncStructuur-TC03 # 2 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC03
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 505006
referentienummer_slB1 | 0000000A-3000-7000-1000-610000000004
abonnementNaam_plB1 | postcode gebied Bennebroek 2120 - 2121 TOESTAND DST OG DEFINITIEF
burgerservicenummer_zlB1 | 110015927
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A26: BRLV0021_SyncStructuur-TC03 # 2 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0021_SyncStructuur-TC03
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0021_SyncStructuur-TC03-2-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_13
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A27: SyncStructuur-TC03 # 3 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC03
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 034401
referentienummer_slB1 | 0000000A-3000-7000-1000-620000000004
abonnementNaam_plB1 | Attenderingen op personen met gewijzigde bijhoudingspartij (pop.bep.=true)
burgerservicenummer_zlB1 | 110015927
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A28: BRLV0017_SyncStructuur-TC03 # 3 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0017_SyncStructuur-TC03
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0017_SyncStructuur-TC03-3-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_14
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A29: VerhuisDoelbinding-TC00 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisDoelbinding-TC00
Given de database wordt gereset voor de personen 800068464
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap1 | 9906TE
_objectid$burgerservicenummer_ipv0_ | 800068464
afgekorteNaamOpenbareRuimte_aap1 | Hoogwatum
gemeenteCode_aap0 | 0801
_objectid$burgerservicenummer_ipv1_ | 800068464
woonplaatsnaam_aap1 | Bierum
referentienummer_sMM0 | 00000000-0000-0000-0700-600000000247
Given de database is aangepast met:
 update kern.his_persbijhouding set bijhpartij = 11  where bijhpartij = 347 AND pers in (select id from kern.pers where bsn = 800068464 )
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VerhuisDoelbinding-TC00-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_15
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A30: VP.0-TC01 # 5 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-620000000001
burgerservicenummer_ipv1 | 423342563
burgerservicenummer_ipr1 | 423342563
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A31: BRLV0008_VP.0-TC01 # 5 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0008_VP.0-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0008_VP.0-TC01-5-soapresponse.xml voor expressie //brp:meldingen

!-- A32: DLQ-TC01 # 1 : status=null
!--Given data uit excel /templatedata/art_inputVerhuizing.xls , DLQ-TC01


Scenario: Scenario_16
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A33: VP.0-TC01 # 6 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | levering obv afnemerindicatie toegangabonnement niet meer geldig
referentienummer_sLB1 | 0000000A-3000-1000-0000-630000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A34: BRLV0029_VP.0-TC01 # 6 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0029_VP.0-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0029_VP.0-TC01-6-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_17
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A35: VP.0-TC01 # 7 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | levering obv afnemerindicatie toegangabonnement nog niet geldig
referentienummer_sLB1 | 0000000A-3000-1000-0000-640000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A36: BRLV0029_VP.0-TC01 # 7 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0029_VP.0-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0029_VP.0-TC01-7-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_18
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A37: VP.0-TC01 # 8 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-650000000001
burgerservicenummer_ipr1 | 423342564
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd

!-- A38: BRAL0012_VP.0-TC01 # 8 : status=QUARANTAINE
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRAL0012_VP.0-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRAL0012_VP.0-TC01-8-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_19
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A39: VerhuisDoelbinding-TC00 # 2 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisDoelbinding-TC00
Given de database wordt gereset voor de personen 800068464
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | 800068464
gemeenteCode_aap0 | 1702
_objectid$burgerservicenummer_ipv1_ | 800068464
woonplaatsnaam_aap1 | Sint Anthonis
referentienummer_sMM0 | 00000000-0000-0000-0700-610000000247
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VerhuisDoelbinding-TC00-2-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_20
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A40: SyncStructuur-TC01 # 2 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_slB1 | 0000000A-3000-7000-1000-610000000002
abonnementNaam_plB1 | Mutaties op personen van wie u de bijhoudingspartij heeft id 1410
burgerservicenummer_zlB1 | 800068464
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A41: BRLV0029_SyncStructuur-TC01 # 2 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0029_SyncStructuur-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0029_SyncStructuur-TC01-2-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_21
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A42: VerhuisDoelbinding-TC00 # 3 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisDoelbinding-TC00
Given de database wordt gereset voor de personen 800068464
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | 800068464
gemeenteCode_aap0 | 0361
_objectid$burgerservicenummer_ipv1_ | 800068464
woonplaatsnaam_aap1 | Alkmaar
referentienummer_sMM0 | 00000000-0000-0000-0700-620000000247
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VerhuisDoelbinding-TC00-3-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_22
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A43: SyncStructuur-TC01 # 3 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_slB1 | 0000000A-3000-7000-1000-620000000002
abonnementNaam_plB1 | Mutaties op personen van wie u de bijhoudingspartij heeft id 364
burgerservicenummer_zlB1 | 800068464
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A44: BRLV0029_SyncStructuur-TC01 # 3 : status=null
!--Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0029_SyncStructuur-TC01
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0029_SyncStructuur-TC01-3-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_23
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A45: happyflow-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_slB1 | 0000000A-3000-7000-1000-600000000001
abonnementNaam_plB1 | levering obv afnemerindicatie toegangabonnement niet meer geldig
Given de sjabloon /berichtdefinities/LEV_SynchronisatieStamgegeven_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-1-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_24
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A46: happyflow-TC01 # 2 : status=null
Given data uit excel /templatedata/art_input.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_slB1 | 0000000A-3000-7000-1000-610000000001
abonnementNaam_plB1 | levering obv afnemerindicatie toegangabonnement nog niet geldig
Given de sjabloon /berichtdefinities/LEV_SynchronisatieStamgegeven_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-2-soapresponse.xml voor expressie //brp:meldingen


Scenario: Scenario_25
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00086
VR00087
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A47: happyflow-TC01 # 10 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-260000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo GeefDetailsPersoon
peilmomentMaterieelResultaat_zvA0 | []
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-10-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_26
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= NEE
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A48: happyflow-TC01 # 11 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-261000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo materiele en formele historie  voor alle groepen
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-11-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_27
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR FORMELE HISTORIE = NEE)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00086
VR00087
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A49: happyflow-TC01 # 12 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-262000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo met alleen verantwoordingsinfo True
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-12-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_28
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00088
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A50: happyflow-TC01 # 13 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-263000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo alleen identificerende groepen en bijbehorende expressies
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-13-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_29
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= NEE)
VR00078 (VOOR FORMELE HISTORIE = NEE)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A51: happyflow-TC01 # 14 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-264000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo alleen materielehistorie voor alle groepen
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-14-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_30
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE = JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A52: happyflow-TC01 # 15 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-265000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo alleen identificerende groepen maar met alle expressies
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-15-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_31
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= NEE)
VR00078 (VOOR FORMELE HISTORIE = NEE)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE)
VR00088
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A53: happyflow-TC01 # 16 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-266000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo alleen materielehistorie voor identificerende groepen
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-16-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_32
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= NEE)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE)
VR00088
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A54: happyflow-TC01 # 17 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-267000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo materiele en formele historie voor identificerende groepen
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-17-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_33
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00088
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A55: happyflow-TC01 # 18 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-268000000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Attenderingen op personen met gewijzigde postcode (pop.bep.=true)
peilmomentMaterieelResultaat_zvA0 | []
zendendePartij_zsA0 | 034401
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-18-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_34
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A56: RPB-basis-tc02 # 1 : status=null
Given data uit excel /templatedata/art_input_042-PB.xls , RPB-basis-tc02
Given de database wordt gereset voor de personen 190013539
Given extra waardes:
SLEUTEL | WAARDE
datumAanvang_rgr1 | ${vandaag(0,0,-1)}
referentienummer_sHB1 | 00000000-0000-0000-0042-460000000003
datumAanvangGeldigheid_rar1 | ${vandaag(0,0,-1)}
Given de sjabloon /berichtdefinities/KUC042-PartnerschapBuitenland_request_template-042-PB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/RPB-basis-tc02-1-soapresponse.xml voor expressie //brp:resultaat


Scenario: Scenario_35
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A57: happyflow-TC01 # 19 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-269000000001
burgerservicenummer_zvA0 | 190013539
abonnementNaam_zvA0 | Abo GeefDetailsPersoon
peilmomentMaterieelResultaat_zvA0 | []
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-19-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R/brp:personen/brp:persoon/brp:betrokkenheden/brp:partner


Scenario: Scenario_37
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A59: Actie-Nationaliteit TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_002.xls , Actie-Nationaliteit TC02
Given de database wordt gereset voor de personen 900188005, 900100540, 900171868
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_B00 | 00000000-0000-0000-0002-600000000027
Given de sjabloon /berichtdefinities/KUC002_request_template_002.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/Actie-Nationaliteit TC02-1-soapresponse.xml voor expressie //brp:resultaat
Then is binnen 60s de query /sqltemplates/selecteerPersonenenNationGeboorteMetErkenningv2_KUC002.sql gelijk aan /testcases/art_input_TechnischToegang/data/Actie-Nationaliteit TC02-1-dataresponse.xml voor de expressie //Results

Scenario: Scenario_38
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= NEE)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE), VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00078 (VOOR FORMELE HISTORIE = NEE)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00084
VR00086
VR00087, VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00086
VR00087, VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00086
VR00087
VR00088, VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR FORMELE HISTORIE = JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00086
VR00087
VR00088, VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= NEE)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00075 (VOOR VERANTWOORDINGSINFO= JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A66: Actie-Nationaliteit TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_002.xls , Actie-Nationaliteit TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_B00 | 00000000-0000-0000-0002-610000000027
verwerkingswijze_B00 | Prevalidatie
Given de sjabloon /berichtdefinities/KUC002_request_template_002.xml
When het bericht is naar endpoint verstuurd

!-- A67: VerAntwVulberichtGeboorteMetErkenning-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , VerAntwVulberichtGeboorteMetErkenning-TC01
Given extra waardes:
SLEUTEL | WAARDE
srtSync | 2
abonnement_id | 5671001
bsn | 900188005
Then is de query /sqltemplates/selecteerVerantwoordingsinformatieBijAbonnementVoorBsn.sql gelijk aan /testcases/art_input_TechnischToegang/data/VerAntwVulberichtGeboorteMetErkenning-TC01-1-dataresponse.xml voor de expressie //brp:bijgehoudenPersonen/brp:persoon


Scenario: Scenario_39
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A74: BRBY0024-TC3031-2 # 1 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0024-TC3031-2
Given extra waardes:
SLEUTEL | WAARDE
_bsn_rOO0_ | 900188005
_bsn_rOO1_ | 900188005
referentienummer_sOO0 | 00000000-0000-0000-0055-600000000032
_bsn_rOO2_ | 900188005
Given de database is aangepast met:
 update kern.pers set datgeboorte = ${vandaagsql(-10,0,0)} where bsn =900188005;
update kern.his_persgeboorte set datgeboorte = ${vandaagsql(-10,0,0)} where pers in (select id from kern.pers where bsn =900188005);
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRBY0024-TC3031-2-1-soapresponse.xml voor expressie //brp:resultaat


Scenario: Scenario_40
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00088
VR00089, VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE), VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A75: BRBY0024-TC3031-2 # 1 : status=null
Given data uit excel /templatedata/art_input_055.xls , BRBY0024-TC3031-2
Given extra waardes:
SLEUTEL | WAARDE
verwerkingswijze_pOO0 | Prevalidatie
_bsn_rOO0_ | 900188005
_bsn_rOO1_ | 900188005
referentienummer_sOO0 | 00000000-0000-0000-0055-610000000032
_bsn_rOO2_ | 900188005
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A79: VerAntwMutatieberichtCorrectieAdres-TC03 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , VerAntwMutatieberichtCorrectieAdres-TC03
Given extra waardes:
SLEUTEL | WAARDE
srtSync | 1
abonnement_id | 5671005
bsn | 900188005
Then is de query /sqltemplates/selecteerVerantwoordingsinformatieBijAbonnementVoorBsn.sql gelijk aan /testcases/art_input_TechnischToegang/data/VerAntwMutatieberichtCorrectieAdres-TC03-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_41
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00084
VR00088
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A80: BRAL0209-TC0201 # 1 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRAL0209-TC0201
Given de database wordt gereset voor de personen 695544457
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sHB0 | 00000000-0000-0000-0042-260000000078
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRAL0209-TC0201-1-soapresponse.xml voor expressie //brp:resultaat
Then de database wordt opgeruimd met:
 select max(id) from kern.doc where srt = 3 -- 3 = Huwelijksakte

Scenario: Scenario_42
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A85: BRBY0401-TC0402 # 1 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRBY0401-TC0402
Given de database wordt gereset voor de personen 309775838, 929159561
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sHB0 | 00000000-0000-0000-0042-260000000007
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRBY0401-TC0402-1-soapresponse.xml voor expressie //brp:resultaat


Scenario: Scenario_43
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00088, VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
VR00084, VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A86: BRBY0401-TC0402 # 1 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRBY0401-TC0402
Given extra waardes:
SLEUTEL | WAARDE
verwerkingswijze_pHB0 | Prevalidatie
referentienummer_sHB0 | 00000000-0000-0000-0042-261000000007
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd

!-- A88: VerAntwMutatieberichtHuwelijk-TC04 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , VerAntwMutatieberichtHuwelijk-TC04
Given extra waardes:
SLEUTEL | WAARDE
srtSync | 1
srtActie | 32
abonnement_id | 5671001
bsn | 309775838
selectItem | id
Given de database is aangepast dmv /sqltemplates/selecteerActieIdBijSoortActieVoorBsn.sql
Then is binnen 40s de query /sqltemplates/selecteerVerantwoordingsinformatieBijAbonnementVoorBsn.sql gelijk aan /testcases/art_input_TechnischToegang/data/VerAntwMutatieberichtHuwelijk-TC04-1-dataresponse.xml voor de expressie //brp:bijgehoudenPersonen/brp:persoon

Scenario: Scenario_44
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A91: Zelfde-ouders-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Zelfde-ouders-TC04
Given de database wordt gereset voor de personen 110011168,110011296,110012434
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0008-600000000008
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/Zelfde-ouders-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_45
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=NEE)
VR00078 (VOOR MUTATIEBERICHT)
VR00079 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=NEE)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= JA), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00079 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA), VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR MUTATIEBERICHT)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = NEE)
VR00083 (VOOR VERANTWOORDINGSINFO= NEE)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A92: Zelfde-ouders-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Zelfde-ouders-TC04
Given extra waardes:
SLEUTEL | WAARDE
verwerkingswijze_pAB0 | Prevalidatie
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd

Scenario: Scenario_46
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE= JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A96: happyflow-TC01 # 20 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | Materieel
referentienummer_sAA0 | 00000000-0000-0000-0501-260100000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo GeefDetailsPersoon
peilmomentMaterieelResultaat_zvA0 | []
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-20-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_47
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE= JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A97: happyflow-TC01 # 21 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | []
historievorm_zvA0 | []
referentienummer_sAA0 | 00000000-0000-0000-0501-260200000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo GeefDetailsPersoon
peilmomentMaterieelResultaat_zvA0 | []
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-21-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_48
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE= JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A98: happyflow-TC01 # 22 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | ${moment_volgen(-10,0,0)}
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-260300000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo GeefDetailsPersoon
peilmomentMaterieelResultaat_zvA0 | ${vandaag(-10,0,0)}
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-22-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_49
Meta:happyflow-TC01-23-soapresponse.xml
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE= JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A99: happyflow-TC01 # 23 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | ${moment_volgen(-10,0,0)}
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-260400000001
burgerservicenummer_zvA0 | 110015927
abonnementNaam_zvA0 | Abo GeefDetailsPersoon
peilmomentMaterieelResultaat_zvA0 | ${vandaag(-20,0,0)}
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-23-soapresponse.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: Scenario_51
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A101: BRBY0401-TC0402 # 2 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRBY0401-TC0402
Given de database wordt gereset voor de personen 309775838, 873726613
Given extra waardes:
SLEUTEL | WAARDE
burgerservicenummer_ipp1 | 873726613
referentienummer_sHB0 | 00000000-0000-0000-0042-231000000007
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRBY0401-TC0402-2-soapresponse.xml voor expressie //brp:resultaat


Scenario: Scenario_52
Meta:
@regels VR00059 (VOOR MATERIELE HISTORIE=JA)
VR00078 (VOOR FORMELE HISTORIE= JA)
VR00081 (VOOR MATERIELE HISTORIE=JA)
VR00082 (VOOR FORMELE HISTORIE = JA)
VR00083 (VOOR VERANTWOORDINGSINFO= JA)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A102: VerhuisNaarDoelbinding-TC01 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisNaarDoelbinding-TC01
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | 309775838
_objectid$burgerservicenummer_ipv1_ | 309775838
referentienummer_sMM0 | 00000000-0000-0000-0700-200000000248
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VerhuisNaarDoelbinding-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A103: VerhuisNaarDoelbinding-TC01 # 2 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0700-200000000248
!--abonnement_id | 5670004
!--Then is binnen 17s de query /sqltemplates/selecteerLaatsteLeverBerichtOpPersbijAbonnement.sql gelijk aan /testcases/art_input_TechnischToegang/data/VerhuisNaarDoelbinding-TC01-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:partner


Scenario: Scenario_53
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A104: VP.0-TC01 # 11 : status=
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | Blablabla
referentienummer_sLB1 | 0000000A-3000-1000-0000-660000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VP.0-TC01-11-soapresponse.xml voor expressie //brp:meldingen/brp:melding[brp:regelCode = 'BRLV0007']


Scenario: Scenario_54
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A105: VP.0-TC01 # 12 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
afnemerCode_aap1 | 123456
referentienummer_sLB1 | 0000000A-3000-1000-0000-670000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/VP.0-TC01-12-soapresponse.xml voor expressie //brp:meldingen/brp:melding[brp:regelCode = 'BRAL0220']


Scenario: Scenario_55
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A106: happyflow-TC01 # 31 : status=
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_slB1 | 0000000A-3000-7000-1000-600000000001
abonnementNaam_plB1 | Blablabla
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-31-soapresponse.xml voor expressie //brp:meldingen/brp:melding[brp:regelCode = 'BRLV0007']


Scenario: Scenario_56
Meta:
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A107: happyflow-TC01 # 32 : status=
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 123456
referentienummer_slB1 | 0000000A-3000-7000-1000-610000000001
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/happyflow-TC01-32-soapresponse.xml voor expressie //brp:meldingen/brp:melding[brp:regelCode = 'AUTH0001']

!-- A108: BRLV0005-TC01 # 1 : status=OOS
!-- Given data uit excel /templatedata/art_input_TechnischToegang.xls , BRLV0005-TC01
!--Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischToegang/response/BRLV0005-TC01-1-soapresponse.xml voor expressie //brp:meldingen

Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@auteur tools
@status Klaar
@epic legacy
@module mutatielevering-op-doelbinding

Scenario: Scenario_0_Persoon komt nieuw binnen doelbinding, afnemer ontvangt volledig bericht
Meta:
@regels VR00056
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A3: VerhuisNaarDoelbinding-TC01 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisNaarDoelbinding-TC01
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000248
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/VerhuisNaarDoelbinding-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A4: VerhuisNaarDoelbinding-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000248
abonnement_id | 5670004
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisNaarDoelbinding-TC01-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon

!-- A5: VerhuisNaarDoelbinding-TC01 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000248
abonnement_id | 5670004
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisNaarDoelbinding-TC01-3-dataresponse.xml voor de expressie //ABONNEMENT

!-- A6: VerhuisNaarDoelbinding-TC01 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000248
abonnement_id | 5670044
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisNaarDoelbinding-TC01-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_1_mutatie levering bijhouding op persoon in doelbinding
Meta:
@regels VR00073, VR00057
VR00061, R1317, R1333,
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A7: VerhuisBinnenDoelbinding-TC03 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisBinnenDoelbinding-TC03
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap0 | 2100AA
gemeentedeel_aap0 | Heemstede
huisnummer_aap0 | 3
Woonplaats | 1564
woonplaatsnaam_aap0 | Heemstede
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000250
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/VerhuisBinnenDoelbinding-TC03-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A8: VerhuisBinnenDoelbinding-TC03 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000250
abonnement_id | 5670004
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisBinnenDoelbinding-TC03-2-dataresponse.xml voor de expressie //Results

!-- A9: Verwerkingssoort-TC01 # 1 : status=null
!--Given data uit excel /templatedata/art_inputVerhuizing.xls , Verwerkingssoort-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000250
abonnement_id | 5670000
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC01-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_2_ Waarschuwing indien persoon doelbinding verlaat
Meta:
@regels R1333(VR00057), R1316(BRLV0028)
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A10: VerhuisUitDoelbinding-TC04 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisUitDoelbinding-TC04
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap1 | 2000AA
gemeentedeel_aap1 | SRPUC50151-3-Woonplaats
huisnummer_aap1 | 2
gemeenteCode_aap0 | 5103
woonplaatsnaam_aap1 | SRPUC50151-3-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000251
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/VerhuisUitDoelbinding-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A11: VerhuisUitDoelbinding-TC04 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000251
abonnement_id | 5670004
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisUitDoelbinding-TC04-2-dataresponse.xml voor de expressie //brp:meldingen/brp:melding

!-- A12: VerhuisUitDoelbinding-TC04 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000251
abonnement_id | 5670003
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisUitDoelbinding-TC04-3-dataresponse.xml voor de expressie //brp:parameters

!-- A13: VerhuisUitDoelbinding-TC04 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000251
abonnement_id | 5670020
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisUitDoelbinding-TC04-4-dataresponse.xml voor de expressie //brp:stuurgegevens


Scenario: Scenario_3_Persoon verhuist buiten Doelbinding
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A14: VerhuisBuitenDoelbinding-TC05 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisBuitenDoelbinding-TC05
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap0 | 2000AA
gemeentedeel_aap0 | SRPUC50151-3-Woonplaats
woonplaatsnaam_aap0 | SRPUC50151-3-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000252
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/VerhuisBuitenDoelbinding-TC05-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A15: VerhuisBuitenDoelbinding-TC05 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000252
abonnement_id | 5670004
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisBuitenDoelbinding-TC05-2-dataresponse.xml voor de expressie //Results

!-- A16: VerhuisBuitenDoelbinding-TC05 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000252
abonnement_id | 5670003
_bsn_rOO0_ | 677080426
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VerhuisBuitenDoelbinding-TC05-3-dataresponse.xml voor de expressie //brp:parameters


Scenario: Scenario_4_Actualiseer afstamming
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A17: Nieuwe-ouders-TC01P # 0 : status=null
Given data uit excel /templatedata/art_input_008.xls , Nieuwe-ouders-TC01P
Given de database wordt gereset voor de personen 110011296,110012628,110012823,110012434
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/Nieuwe-ouders-TC01P-0-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A18: Nieuwe-ouders-TC01P # 1 : status=null
Given de database is aangepast met:
 update kern.plaats set dataanvgel=null where code = 1702 or code = 3086


Scenario: Scenario_5_mutatie levering doelbinding met nadere populatie beperking op dienst mutatielevering
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A19: Nieuwe-ouders-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Nieuwe-ouders-TC01
Given de database wordt gereset voor de personen 110012628,110012823,110012434
Given de database is aangepast met:
 update kern.persnation set nation = 4 where id in ( select id from kern.persnation where pers in (select id from kern.pers where anr =1417828189));
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0008-300000000002
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/Nieuwe-ouders-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A20: Nieuwe-ouders-TC01 # 2 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , Nieuwe-ouders-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000002
abonnement_id | 5670029
_bsn_rOO0_ | 110012628
srtsynchronisatie_id | 2
partij_id | 347
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Nieuwe-ouders-TC01-2-dataresponse.xml voor de expressie //Results

!-- A21: Nieuwe-ouders-TC01 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000002
abonnement_id | 5670000
_bsn_rOO0_ | 110012628
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Nieuwe-ouders-TC01-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:identificatienummers

!-- A22: Nieuwe-ouders-TC01 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000002
abonnement_id | 5670000
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Nieuwe-ouders-TC01-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:ouder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:kind

!-- A23: Nieuwe-ouders-TC01 # 6 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000002
abonnement_id | 5670014
_bsn_rOO0_ | 110012628
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Nieuwe-ouders-TC01-6-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:identificatienummers

!-- A24: Nieuwe-ouders-TC01 # 7 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000002
abonnement_id | 5670014
_bsn_rOO0_ | 110012628
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Nieuwe-ouders-TC01-7-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:samengesteldeNaam


Scenario: Scenario_6
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A25: Zelfde-ouders-TC04P # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Zelfde-ouders-TC04P
Given de database wordt gereset voor de personen 110011168,110011296,110012434
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/Zelfde-ouders-TC04P-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen


Scenario: Scenario_7
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A26: Zelfde-ouders-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Zelfde-ouders-TC04
Given de database wordt gereset voor de personen 110011168,110011296,110012434
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0008-300000000008
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/Zelfde-ouders-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A27: Zelfde-ouders-TC04 # 2 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0008-300000000008
!--abonnement_id | 5670000
!--_bsn_rOO0_ | 110012434
!--Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Zelfde-ouders-TC04-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen

!-- A28: Zelfde-ouders-TC04 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000008
abonnement_id | 5670029
_bsn_rOO0_ | 110012434
srtsynchronisatie_id | 2
partij_id | 347
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Zelfde-ouders-TC04-3-dataresponse.xml voor de expressie //Results

!-- A29: Zelfde-ouders-TC04 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000008
abonnement_id | 5670014
_bsn_rOO0_ | 110012434
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Zelfde-ouders-TC04-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:identificatienummers

!-- A30: Zelfde-ouders-TC04 # 5 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000008
abonnement_id | 5670014
_bsn_rOO0_ | 110012434
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Zelfde-ouders-TC04-5-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:geboorte

!-- A31: Zelfde-ouders-TC04 # 6 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000008
abonnement_id | 5670014
_bsn_rOO0_ | 110012434
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Zelfde-ouders-TC04-6-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:geslachtsaanduiding

!-- A32: Zelfde-ouders-TC04 # 7 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000008
abonnement_id | 5670014
_bsn_rOO0_ | 110012434
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Zelfde-ouders-TC04-7-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:kind


Scenario: Scenario_8_Mutatie levering obv doelbinding vaststelling ouderschap
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A33: Vaderschap-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Vaderschap-TC01
Given de database wordt gereset voor de personen 110011296,110015927
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0008-300000000021
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/Vaderschap-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A34: Vaderschap-TC01 # 2 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0008-300000000021
!--abonnement_id | 5670000
!--_bsn_rOO0_ | 110011296
!--Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Vaderschap-TC01-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:kind/brp:familierechtelijkeBetrekking/brp:betrokkenheden

!-- A35: Vaderschap-TC01 # 3 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0008-300000000021
!--abonnement_id | 5670000
!--_bsn_rOO0_ | 110011296
!--Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Vaderschap-TC01-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:ouder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:kind/brp:persoon

!-- A36: Vaderschap-TC01 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000021
abonnement_id | 5670000
_bsn_rOO0_ | 110011296
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Vaderschap-TC01-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:nationaliteiten/brp:nationaliteit

!-- A37: Vaderschap-TC01 # 5 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000021
abonnement_id | 5670000
_bsn_rOO0_ | 110011296
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Vaderschap-TC01-5-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:naamgebruik

!-- A38: Vaderschap-TC01 # 6 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , Vaderschap-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000021
abonnement_id | 5670000
_bsn_rOO0_ | 110011296
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Vaderschap-TC01-6-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]

!-- A39: Vaderschap-TC01 # 7 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-300000000021
abonnement_id | 5670014
_bsn_rOO0_ | 110011296
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Vaderschap-TC01-7-dataresponse.xml voor de expressie //ABONNEMENT


Scenario: Scenario_9_Mutatie levering obv doelbinding Geboorte in Nederland met Erkenning
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A40: VR00009-TC0101 # 1 : status=null
Given data uit excel /templatedata/art_input_001_met_Aanschrijving.xls , VR00009-TC0101
Given de database wordt gereset voor de personen 800068750,800068464,800004656
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0001-300000000001
Given de sjabloon /berichtdefinities/KUC001-metAanschrijving_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/VR00009-TC0101-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A41: VR00009-TC0101 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068464
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:identificatienummers

!-- A42: VR00009-TC0101 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068750
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:betrokkenheden/brp:kind/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/brp:identificatienummers

!-- A43: VR00009-TC0101 # 5 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068750
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-5-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:naamgebruik

!-- A44: VR00009-TC0101 # 6 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068464
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-6-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:samengesteldeNaam

!-- A45: VR00009-TC0101 # 7 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068464
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-7-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]/brp:identificatienummers

!-- A46: VR00009-TC0101 # 8 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068464
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-8-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]/brp:geboorte

!-- A47: VR00009-TC0101 # 9 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068464
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-9-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]/brp:geslachtsaanduiding

!-- A48: VR00009-TC0101 # 10 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670000
_bsn_rOO0_ | 800068464
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-10-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon[1]/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]/brp:samengesteldeNaam

!-- A49: VR00009-TC0101 # 19 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-300000000001
abonnement_id | 5670029
_bsn_rOO0_ | 800068464
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00009-TC0101-19-dataresponse.xml voor de expressie //Results


Scenario: Scenario_10_Mutatie levering obv doelbinding Geboorte in nederland met Erkenning
Meta:
@status Uitgeschakeld
@regels VR00061 (BETROKKENHEID: KIND)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A50: BRBY0106-B-TC0601 # 1 : status=null
Given data uit excel /templatedata/art_input_002.xls , BRBY0106-B-TC0601
Given de database wordt gereset voor de personen 900188005,900176106,900171868
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_B00 | 00000000-0000-0000-0002-300000000044
Given de sjabloon /berichtdefinities/KUC002_request_template_002.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0106-B-TC0601-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A51: BRBY0106-B-TC0601 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0002-300000000044
abonnement_id | 5670000
_bsn_rOO0_ | 900188005
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0106-B-TC0601-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]/brp:samengesteldeNaam

!-- A52: BRBY0106-B-TC0601 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0002-300000000044
abonnement_id | 5670000
_bsn_rOO0_ | 900188005
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0106-B-TC0601-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[2]/brp:identificatienummers

!-- A53: BRBY0106-B-TC0601 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0002-300000000044
abonnement_id | 5670000
_bsn_rOO0_ | 900188005
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0106-B-TC0601-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:geslachtsnaamcomponenten

!-- A54: BRBY0106-B-TC0601 # 5 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0002-300000000044
abonnement_id | 5670000
_bsn_rOO0_ | 900188005
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0106-B-TC0601-5-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:betrokkenheden/brp:ouder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:kind

!-- A55: BRBY0106-B-TC0601 # 6 : status=null
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , BRBY0106-B-TC0601
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0002-300000000044
abonnement_id | 5670029
_bsn_rOO0_ | 900188005
partij_id | 347
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0106-B-TC0601-6-dataresponse.xml voor de expressie //Results


Scenario: Scenario_11_Mutatie levering obv doelbinding Controle op verwerkingssoort
Meta:
@regels VR00073 (R1317)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A56: BRAL2011-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_003.xls , BRAL2011-TC04
Given de database wordt gereset voor de personen 100012711,100010258,100010398
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0003-300000000020
Given de sjabloon /berichtdefinities/KUC003_request_template_003.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRAL2011-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A57: Verwerkingssoort-TC02 # 1 : status=null
!--Given data uit excel /templatedata/art_input_003.xls , Verwerkingssoort-TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0003-300000000020
abonnement_id | 5670000
_bsn_rOO0_ | 100012711
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/Verwerkingssoort-TC02-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_12 Mutatie levering obv doelbinding beperkt op bijhoudingspartij
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A58: LT01FT01-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-02
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/LT01FT01-02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A59: LT01FT01-02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000001
abonnement_id | 5670019
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/LT01FT01-02-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen


Scenario: Scenario_13_ Mutatie levering obv doelbinding (beperkt op specifieke bijhoudingspartij)
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A60: LT01FT01-03 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-03
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000002
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/LT01FT01-03-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A61: LT01FT01-03 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000002
abonnement_id | 5670019
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/LT01FT01-03-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon


Scenario: Scenario_14
Meta:
@regels VR00061 (BETROKKENHEID: OUDER IS NIET INGEZETENE)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A62: LT01FT01-04 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-04
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000003
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/LT01FT01-04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A63: LT01FT01-04 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000003
abonnement_id | 5670019
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/LT01FT01-04-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon


Scenario: Scenario_15_Mutatie levering op basis van doelbinding (beperkt tot specifieke bijhoudingspartij)
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A64: LT01FT01-05 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-05
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000004
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/LT01FT01-05-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A65: LT01FT01-05 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000004
abonnement_id | 5670019
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/LT01FT01-05-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon


Scenario: Scenario_16
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A66: BRAL1002-LT01FT104-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , BRAL1002-LT01FT104-02
Given de database wordt gereset voor de personen 103432620
Given extra waardes:
SLEUTEL | WAARDE
gemeenteCode_aap0 | AAAA
_objectid$burgerservicenummer_ipv1_ | 103432620
referentienummer_sMM0 | 00000000-0000-0000-0700-300000000236
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRAL1002-LT01FT104-02-1-soapresponse.xml voor expressie //brp:meldingen
Then wacht tot alle berichten zijn ontvangen

!-- A67: BRAL1002-LT01FT104-02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-300000000236
abonnement_id | 5670019
_bsn_rOO0_ | 103432620
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRAL1002-LT01FT104-02-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_17_ Sortering Verantwoording en Alleen relevante actieverwijzingen opnemen in mutatie levering
Meta:
@regels R1804(VR00090), R1318(VR00095)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A68: BinnenGemeentelijke-verhuizing-basis # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , BinnenGemeentelijke-verhuizing-basis
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-310000000184
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BinnenGemeentelijke-verhuizing-basis-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A69: BinnenGemeentelijke-verhuizing-basis # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-310000000184
abonnement_id | 5670014
_bsn_rOO0_ | 677080426
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BinnenGemeentelijke-verhuizing-basis-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon

!-- A70: VR00095-Mutatie-TC01 # 1 : status=QUARANTAINE
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , VR00095-Mutatie-TC01
!--Given de database is aangepast dmv /sqltemplates/selecteerActieIdBijSoortActieVoorBsn.sql
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--srtSync | 1
!--srtActie | 18
!--bsn | 677080426
!--abonnement_id | 5670000
!--selectItem | id
!--_bsn_rOO0_ | 677080426
!--referentienummer_sOO0 | 00000000-0000-0000-0700-300000000004
!--Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00095-Mutatie-TC01-1-dataresponse.xml voor de expressie //brp:synchronisatie


Scenario: Scenario_18
Meta:
@status Uitgeschakeld
@regels BRLV0028
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A71: HappyFlow # 1 : status=null
Given data uit excel /templatedata/art_input_053.xls , HappyFlow
Given de database wordt gereset voor de personen 250015742
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0053-300000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/HappyFlow-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A72: HappyFlow # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0053-300000000001
abonnement_id | 5670014
_bsn_rOO0_ | 250015742
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/HappyFlow-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]

!-- A73: HappyFlow # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0053-300000000001
abonnement_id | 5670014
_bsn_rOO0_ | 250015742
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/HappyFlow-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:meldingen


Scenario: Scenario_19_Prevalidatie van een Persoon die verhuist naar het buitenland, omdat verwerkingssoort = prevalidatie mag er geen  mut. lev. zijn
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A74: BRAL9027-TC0101-P # 1 : status=null
Given data uit excel /templatedata/art_input_053.xls , BRAL9027-TC0101-P
Given de database wordt gereset voor de personen 250015742
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0053-300000000017
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRAL9027-TC0101-P-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A75: BRAL9027-TC0101-P # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0053-300000000017
abonnement_id | 5670014
_bsn_rOO0_ | 250015742
Then is binnen 30s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRAL9027-TC0101-P-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_20_ Persoon verhuisd naar buitenland, afnemer ontvangt waarschuwing dat persoon doelbinding heeft verlaten
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A76: BRAL9027-TC0101 # 1 : status=null
Given data uit excel /templatedata/art_input_053.xls , BRAL9027-TC0101
Given de database wordt gereset voor de personen 250015742
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0053-300000000018
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRAL9027-TC0101-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A77: BRAL9027-TC0101 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0053-300000000018
abonnement_id | 5670014
_bsn_rOO0_ | 250015742
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRAL9027-TC0101-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:adressen

!-- A78: BRAL9027-TC0101 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0053-300000000018
abonnement_id | 5670014
_bsn_rOO0_ | 250015742
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRAL9027-TC0101-3-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]/brp:identificatienummers/brp:burgerservicenummer

!-- A79: BRAL9027-TC0101 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0053-300000000018
abonnement_id | 5670014
_bsn_rOO0_ | 250015742
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRAL9027-TC0101-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:migratie


Scenario: Scenario_21_mutatie levering obv doelbinding als gevolg van registratie Huwelijk
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A80: BRBY0401-TC0401 # 1 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRBY0401-TC0401
Given de database wordt gereset voor de personen 749262217, 695544457
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sHB0 | 00000000-0000-0000-0042-300000000006
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0401-TC0401-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A81: BRBY0401-TC0401 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0042-300000000006
abonnement_id | 5670014
_bsn_rOO0_ | 695544457
Then is binnen 60s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0401-TC0401-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]


Scenario: Scenario_22
Meta:
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A82: BRBY0403-TC0502 # 1 : status=null
Given data uit excel /templatedata/art_input_042.xls , BRBY0403-TC0502
Given de database wordt gereset voor de personen 912345603, 1234515291
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sHB0 | 00000000-0000-0000-0042-300000000021
Given de sjabloon /berichtdefinities/KUC042-HuwelijkNederland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/BRBY0403-TC0502-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A83: BRBY0403-TC0502 # 2 : status=QUARANTAINE
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--referentienr_id | 00000000-0000-0000-0042-300000000021
!--abonnement_id | 5670014
!--_bsn_rOO0_ | 912345603
!--Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/BRBY0403-TC0502-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]


Scenario: Scenario_23_mutatie levering obv doelbinding als gevolg van registratie Huwelijk
Meta:
@regels VR00090, VR00095
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A84: HB-basis-tc01 # 1 : status=null
Given data uit excel /templatedata/art_input_042-HB.xls , HB-basis-tc01
Given de database wordt gereset voor de personen 150011957, 150012597
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sHB1 | 00000000-0000-0000-0042-300000000001
Given de sjabloon /berichtdefinities/KUC042-HuwelijkBuitenland_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/response/HB-basis-tc01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A85: HB-basis-tc01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0042-300000000001
abonnement_id | 5670014
_bsn_rOO0_ | 150011957
Then is binnen 120s de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/HB-basis-tc01-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[1]

!-- A86: VR00095-Mutatie-TC02 # 1 : status=QUARANTAINE
!--Given data uit excel /templatedata/art_input_MutatieleveringOpDoelbinding.xls , VR00095-Mutatie-TC02
!--Given de database is aangepast dmv /sqltemplates/selecteerActieIdBijSoortActieVoorBsn.sql
!--Given extra waardes:
!--SLEUTEL | WAARDE
!--srtSync | 1
!--srtActie | 32
!--bsn | 150012597
!--abonnement_id | 5670000
!--selectItem | id
!--_bsn_rOO0_ | 150012597
!--referentienummer_sOO0 | 00000000-0000-0000-0042-300000000001
!--Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpDoelbinding/data/VR00095-Mutatie-TC02-1-dataresponse.xml voor de expressie //brp:bijgehoudenPersonen/brp:persoon[2]



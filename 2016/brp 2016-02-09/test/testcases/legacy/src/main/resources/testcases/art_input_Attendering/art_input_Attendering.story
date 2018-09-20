Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@auteur tools
@status Uitgeschakeld
@epic legacy
@module attendering


Scenario: Scenario_0 Volledig bericht als gevolg van wijziging bijhoudingspartij
Meta:
@regels VR00062,VRL0001,VR00088,VR00049
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A2: LT01FT01-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-02
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0702-100000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/LT01FT01-02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A3: LT01FT01-02 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_Attendering.xls , LT01FT01-02
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0702-100000000001
abonnement_id | 5670027
_bsn_rOO0_ | ${_objectid$burgerservicenummer_ipv1_}
Then is binnen 60s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/LT01FT01-02-2-dataresponse.xml voor de expressie //Results

!-- A4: LT01FT01-02 # 3 : status=null
!-- Attendering - Volledig bericht na wijziging postcode
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0702-100000000001
abonnement_id | 5670028
_bsn_rOO0_ | ${_objectid$burgerservicenummer_ipv1_}
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/LT01FT01-02-3-dataresponse.xml voor de expressie //Results

!-- A5: LT01FT01-02 # 4 : status=null
!-- Attendering - Volledig bericht na wijziging bijhoudingspartij
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0702-100000000001
abonnement_id | 5670026
_bsn_rOO0_ | ${_objectid$burgerservicenummer_ipv1_}
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/LT01FT01-02-4-dataresponse.xml voor de expressie //Results


Scenario: Scenario_1 Volledig bericht als gevolg van wijziging postcode
Meta:
@regels VR00088
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A6: LT01FT01-03 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-03
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0702-100000000002
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/LT01FT01-03-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A7: LT01FT01-03 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_Attendering.xls , LT01FT01-03
!-- Attendering - volledig bericht nav gewijzigde postcode
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0702-100000000002
abonnement_id | 5670028
_bsn_rOO0_ | ${_objectid$burgerservicenummer_ipv1_}
Then is binnen 60s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/LT01FT01-03-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_2 Verstrekkingsbeperking voor persoon zonder opgave partij
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A8: happy-flow-TC0102 # 1 : status=null
!--Attendering - Volledig bericht persoon met verstrekkingsbeperking
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0102
Given de database wordt gereset voor de personen 677080426
Given de database is aangepast met:
 update kern.persverstrbeperking set pers = (select id from kern.pers where bsn = 677080426) where pers in (select id from kern.pers where bsn = 330298628)
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-100000000002
objectid$persoon1 | 677080426
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/happy-flow-TC0102-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen


Scenario: Scenario_3 Plaatsen verstrekkingsbeperking voor persoon bij partij 347
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A9: happy-flow-TC0102 # 1 : status=null
Given data uit excel /templatedata/art_input_033.xls , happy-flow-TC0102
Given de database is aangepast met:
 update kern.persverstrbeperking set partij=347, pers = (select id from kern.pers where bsn = 677080426) where pers in (select id from kern.pers where bsn = 330298628)
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sDB1 | 00000000-0000-0000-0033-110000000002
Given de sjabloon /berichtdefinities/KUC033-MededelingVerzoek_request_template-033.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/happy-flow-TC0102-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen


Scenario: Scenario_4 Attendering met verstrekkingsbeperking
Meta:
@regels VR00088
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A10: LT01FT01-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-02
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0712-100000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/LT01FT01-02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A11: controleer-attendering-met-verstrekkingsbeperking # 1 : status=null
!-- Given data uit excel /templatedata/art_input_Attendering.xls , controleer-attendering-met-verstrekkingsbeperking
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0712-100000000001
abonnement_id | 5670028
_bsn_rOO0_ | ${_objectid$burgerservicenummer_ipv1_}
Then is binnen 30s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/controleer-attendering-met-verstrekkingsbeperking-1-dataresponse.xml voor de expressie //Results

!-- A12: controleer-attendering-met-verstrekkingsbeperking # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0712-100000000001
abonnement_id | 5670033
_bsn_rOO0_ | ${_objectid$burgerservicenummer_ipv1_}
Then is binnen 30s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/controleer-attendering-met-verstrekkingsbeperking-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_5 Volledig bericht na geboorte in Nederland
Meta:
@regels VR00088
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld


!-- A13: BRAL0211-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_001.xls , BRAL0211-TC01
Given de database wordt gereset voor de personen 809468657,800011375,800074658
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_B00 | 00000000-0000-0000-0001-200000000090
Given de sjabloon /berichtdefinities/KUC001_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/BRAL0211-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A14: BRAL0211-TC01 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_Attendering.xls , BRAL0211-TC01
!-- Volledig bericht na geboorte in Nederland
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0001-200000000090
abonnement_id | 5670026
_bsn_rOO0_ | ${burgerservicenummer_B00}
Then is binnen 35s de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/BRAL0211-TC01-2-dataresponse.xml voor de expressie //brp:bijgehoudenPersonen/brp:persoon/brp:identificatienummers


Scenario: Scenario_6 Volledig bericht na verkrijging nederlandse nationaliteit + controle of afnemer indicatie is geplaatst
Meta:
@regels VR00062, VR00062
VR00063
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgNationaliteit
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A15: HF-verkrijgingNLNationaliteit-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_062.xls , HF-verkrijgingNLNationaliteit-TC01
Given de database wordt gereset voor de personen 210010939
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sNB1 | 00000000-0000-0000-0062-100000000002
Given de sjabloon /berichtdefinities/KUC062-Nationaliteit_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/HF-verkrijgingNLNationaliteit-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A16: AttenderingMetPlaatsingAfnemerindicatie-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_062.xls , AttenderingMetPlaatsingAfnemerindicatie-TC01
!-- Volledig bericht na verkrijging nederlandse nationaliteit + controle of afnemer indicatie is geplaatst
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670032
ontvangendepartij_id | 25002
Then is binnen 30s de query /sqltemplates/leveringVoorAfnemerPerBSN_ART-Attendering.sql gelijk aan /testcases/art_input_Attendering/data/AttenderingMetPlaatsingAfnemerindicatie-TC01-1-dataresponse.xml voor de expressie //Results

!-- A17: AttenderingMetPlaatsingAfnemerindicatie-TC01 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_Attendering.xls , AttenderingMetPlaatsingAfnemerindicatie-TC01
Then is de query /sqltemplates/selecteerPersafnemerindicatieAndHis_ART-Attendering.sql gelijk aan /testcases/art_input_Attendering/data/AttenderingMetPlaatsingAfnemerindicatie-TC01-2-dataresponse.xml voor de expressie //Results

!-- A18: AttenderingMetPlaatsingAfnemerindicatie-TC01 # 3 : status=VERVALLEN
!-- Given extra waardes:
!-- SLEUTEL | WAARDE
!-- partij_id | 25002
!-- Then is de query /sqltemplates/selecteerSrtAdmhndVoorAfnemerperBSN_ART-Attendering.sql gelijk aan /testcases/art_input_Attendering/data/AttenderingMetPlaatsingAfnemerindicatie-TC01-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_7 Plaatsing afnemer indicatie igv persoon voldoet aan pop. bep.
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgNationaliteit
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A19: HF-verkrijgingNLNationaliteit-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_062.xls , HF-verkrijgingNLNationaliteit-TC01
Given de database is aangepast met:
 update kern.persnation set nation = 1 where nation = 2 and pers in (select id from kern.pers where bsn = 210010939 )
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sNB1 | 00000000-0000-0000-0062-110000000002
Given de sjabloon /berichtdefinities/KUC062-Nationaliteit_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/HF-verkrijgingNLNationaliteit-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A21: AttenderingMetPlaatsingAfnemerindicatie-TC02 # 2 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatieAndHis_ART-Attendering.sql gelijk aan /testcases/art_input_Attendering/data/AttenderingMetPlaatsingAfnemerindicatie-TC02-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_8
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgNationaliteit
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A22: HF-verkrijgingNLNationaliteit-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_062.xls , HF-verkrijgingNLNationaliteit-TC01
Given de database is aangepast met:
 update kern.persnation set nation = 5 where nation = 2 and pers in (select id from kern.pers where bsn = 210010939 );
update autaut.persafnemerindicatie set dataanvmaterieleperiode ='${vandaagsql()}' where pers in (select id from kern.pers where bsn = 210010939 );
update autaut.his_persafnemerindicatie set dataanvmaterieleperiode ='${vandaagsql()}' where persafnemerindicatie in (select id from autaut.persafnemerindicatie where pers in(select id from kern.pers where bsn = 210010939 ));
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sNB1 | 00000000-0000-0000-0062-120000000002
Given de sjabloon /berichtdefinities/KUC062-Nationaliteit_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/HF-verkrijgingNLNationaliteit-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen


Scenario: Scenario_9 Mutatielevering op basis van doelbinding abo
Meta:
@regels VR00088
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld

!-- A24: KUC055-TC31910-2 # 1 : status=null
Given data uit excel /templatedata/art_input_055.xls , KUC055-TC31910-2
Given de database wordt gereset voor de personen 527163703
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sOO0 | 00000000-0000-0000-0055-210000000044
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_Attendering/response/KUC055-TC31910-2-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then wacht tot alle berichten zijn ontvangen

!-- A25: KUC055-TC31910-2 # 2 : status=null
!-- Given data uit excel /templatedata/art_input_Attendering.xls , KUC055-TC31910-2
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0055-210000000044
abonnement_id | 5670024
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnement.sql gelijk aan /testcases/art_input_Attendering/data/KUC055-TC31910-2-2-dataresponse.xml voor de expressie //Results



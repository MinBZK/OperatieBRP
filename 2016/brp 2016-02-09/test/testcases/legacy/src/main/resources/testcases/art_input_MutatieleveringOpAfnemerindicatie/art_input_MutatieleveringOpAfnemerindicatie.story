Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service
@auteur tools
@status Klaar
@epic legacy
@module mutatielevering-op-afnemerindicatie

Scenario: Scenario_0
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A2: plaatsing-afnemerindicatie-1-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-1-TC01
Given de database wordt gereset voor de personen 103432620
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182
afnemerCode_aap1 | 505005
referentienummer_sLB1 | 0000000A-3000-1000-0000-800000000027
burgerservicenummer_ipr1 | 103432620
partijCode_pLB1 | 505005
Given de database is aangepast met:
 delete from kern.his_persgeslachtsaand where id = 1001004;
delete from kern.his_persgeslachtsaand where id = 2001004;
delete from kern.his_persgeboorte where id = 2001004;
delete from kern.his_persgeboorte where id = 1001004;
delete from kern.his_perssamengesteldenaam where id =2001004;
delete from kern.his_perssamengesteldenaam where id = 1001004;
insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indafgeleid, indnreeks, voornamen, geslnaamstam, actieinh) values (1001004, 1001004, 20130101 , now(), 't','f', 'Pierre Jansen', 'Croon', 0);
insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indafgeleid, indnreeks, voornamen, geslnaamstam, actieinh) values (2001004, 2001004, 20130101 , now(), 't','f', 'Pierre2 Jansen2', 'Croon2',0);
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte,gemgeboorte,wplnaamgeboorte, landgebiedgeboorte, actieinh) values (1001004, 1001004, now(), 19621012,950,228,229,0);
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte,gemgeboorte,wplnaamgeboorte, landgebiedgeboorte, actieinh) values (2001004, 2001004, now(), 19631012,950,228,229,0);
insert into kern.his_persgeslachtsaand (id, pers,dataanvgel, tsreg, geslachtsaand, actieinh)values (1001004, 1001004,19621012, now(),2,0);
insert into kern.his_persgeslachtsaand (id, pers,dataanvgel, tsreg, geslachtsaand, actieinh)values (2001004, 2001004,19631012, now(),1,0);
delete from autaut.his_persafnemerindicatie;delete from autaut.persafnemerindicatie;delete from kern.perscache;
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/plaatsing-afnemerindicatie-1-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A3: plaatsing-afnemerindicatie-1-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
burgerservicenummer_ipv1 | []
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-1-TC01-2-dataresponse.xml voor de expressie //Results

!-- A4: plaatsing-afnemerindicatie-1-TC01 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670005
referentienummer_sLB1 | 0000000A-3000-1000-0000-800000000027
_bsn_rOO0_ | 103432620
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-1-TC01-3-dataresponse.xml voor de expressie //Results

!-- A5: plaatsing-afnemerindicatie-1-TC01 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670005
referentienummer_sLB1 | 0000000A-3000-1000-0000-800000000027
_bsn_rOO0_ | 103432620
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_PlaatsVerwAfnind_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-1-TC01-4-dataresponse.xml voor de expressie //Results

!-- A6: plaatsing-afnemerindicatie-1-TC01 # 5 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-1-TC01-5-dataresponse.xml voor de expressie //Results


Scenario: Scenario_1
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A7: VerhuizingOndAfnemerInd-TC00 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuizingOndAfnemerInd-TC00
Given extra waardes:
SLEUTEL | WAARDE
_objectid$burgerservicenummer_ipv0_ | 103432620
gemeentedeel_aap1 | Hillegom
huisnummer_aap1 | 2
_objectid$burgerservicenummer_ipv1_ | 103432620
woonplaatsnaam_aap1 | SRPUC50151-5-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-800000000253
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/VerhuizingOndAfnemerInd-TC00-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A8: VerhuizingOndAfnemerInd-TC00 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000253
abonnement_id | 5670005
_bsn_rOO0_ | 103432620
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuizingOndAfnemerInd-TC00-2-dataresponse.xml voor de expressie //Results

!-- A9: VerhuizingOndAfnemerInd-TC00 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000253
abonnement_id | 5670005
Then is de query /sqltemplates/selecteerLaatsteLeverBerichtOpPersbijAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuizingOndAfnemerInd-TC00-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_2
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A10: plaatsing-afnemerindicatie-2-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-2-TC01
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182
afnemerCode_aap1 | 505005
referentienummer_sLB1 | 0000000A-3000-1000-0000-800000000028
burgerservicenummer_ipv1 | []
burgerservicenummer_ipr1 | 103432620
partijCode_pLB1 | 505005
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/plaatsing-afnemerindicatie-2-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A11: plaatsing-afnemerindicatie-2-TC01 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-2-TC01-2-dataresponse.xml voor de expressie //Results

!-- A12: plaatsing-afnemerindicatie-2-TC01 # 3 : status=null
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-2-TC01-3-dataresponse.xml voor de expressie //Results

!-- A13: plaatsing-afnemerindicatie-2-TC01 # 4 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-800000000028
abonnement_id | 5670005
_bsn_rOO0_ | 103432620
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-2-TC01-4-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie

!-- A14: plaatsing-afnemerindicatie-2-TC01 # 5 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-800000000028
abonnement_id | 5670005
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_PlaatsVerwAfnind_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-2-TC01-5-dataresponse.xml voor de expressie //Results


Scenario: Scenario_3
Meta:
@regels VR00058 (ZONDER AANWEZIGHEID  DATUMAANVANGMATERIELEPERIODE)
VR00061
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A15: VerhuisBinnenDoelbinding-TC03 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisBinnenDoelbinding-TC03
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap0 | 2182ZZ
_objectid$burgerservicenummer_ipv0_ | 103432620
gemeentedeel_aap0 | Hillegom
_objectid$burgerservicenummer_ipv1_ | 103432620
huisnummer_aap0 | 4
Woonplaats | []
woonplaatsnaam_aap0 | SRPUC50151-5-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-800000000250
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

!-- A16: VerhuisBinnenDoelbinding-TC03 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000250
abonnement_id | 5670005
_bsn_rOO0_ | 103432620
Then is binnen 30s de query /sqltemplates/LaatsteMutatieberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisBinnenDoelbinding-TC03-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:identificatienummers

!-- A17: VerhuisBinnenDoelbinding-TC03 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000250
abonnement_id | 5670005
Then is binnen 120s de query /sqltemplates/BerAdmhndAbonnementen_biddesc_Verhuizing_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisBinnenDoelbinding-TC03-3-dataresponse.xml voor de expressie //Results


Scenario: Scenario_4
Meta:
@regels BRLV0027
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@status Uitgeschakeld


!-- A18: VerhuisUitDoelbinding-TC04 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisUitDoelbinding-TC04
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap1 | 2120AB
_objectid$burgerservicenummer_ipv0_ | 103432620
gemeentedeel_aap1 | Bennebroek
huisnummer_aap1 | 5
gemeenteCode_aap0 | 5105
_objectid$burgerservicenummer_ipv1_ | 103432620
woonplaatsnaam_aap1 | SRPUC50151-6-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-800000000251
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/VerhuisUitDoelbinding-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A19: VerhuisUitDoelbinding-TC04 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000251
abonnement_id | 5670005
Then is binnen 30s de query /sqltemplates/LaatsteMutatieberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisUitDoelbinding-TC04-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon

!-- A20: VerhuisUitDoelbinding-TC04 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000251
abonnement_id | 5670005
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_Verhuizing_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisUitDoelbinding-TC04-3-dataresponse.xml voor de expressie //Results

!-- A21: BRLV0027-TC01 # 1 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000251
abonnement_id | 5670005
_bsn_rOO0_ | 103432620
Then is de query /sqltemplates/LaatsteMutatieberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/BRLV0027-TC01-1-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:meldingen


Scenario: Scenario_5
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service



!-- A22: VerhuisBuitenDoelbinding-TC05 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisBuitenDoelbinding-TC05
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap0 | 2121AA
_objectid$burgerservicenummer_ipv0_ | 103432620
gemeentedeel_aap0 | Bennebroek
_objectid$burgerservicenummer_ipv1_ | 103432620
huisnummer_aap0 | 6
woonplaatsnaam_aap0 | SRPUC50151-6-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-800000000252
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/VerhuisBuitenDoelbinding-TC05-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A23: VerhuisBuitenDoelbinding-TC05 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000252
abonnement_id | 5670005
_bsn_rOO0_ | 103432620
Then is binnen 30s de query /sqltemplates/LaatsteMutatieberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisBuitenDoelbinding-TC05-2-dataresponse.xml voor de expressie //brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:identificatienummers

!-- A24: VerhuisBuitenDoelbinding-TC05 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-800000000252
abonnement_id | 5670005
Then is de query /sqltemplates/BerAdmhndAbonnementen_biddesc_Verhuizing_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisBuitenDoelbinding-TC05-3-dataresponse.xml voor de expressie //Results

!-- A25: BRLV0015-TC01 # 1 : status=null
Given extra waardes:
SLEUTEL | WAARDE
burgerservicenummer_ipr1 | 103432620
_objectid$burgerservicenummer_ipv0_ | 103432620
_objectid$burgerservicenummer_ipv1_ | 103432620
Given de database is aangepast met:
 UPDATE
  autaut.persafnemerindicatie
SET dateindevolgen = ${vandaagsql(0,0,-1)}
WHERE pers IN (SELECT p.id FROM kern.pers p WHERE p.bsn = '103432620');

UPDATE
  autaut.his_persafnemerindicatie
SET dateindevolgen = ${vandaagsql(0,0,-1)}
WHERE persafnemerindicatie IN (SELECT ind.id FROM autaut.persafnemerindicatie ind JOIN kern.pers p ON ind.pers = p.id WHERE p.bsn = '103432620');
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/BRLV0015-TC01-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_6
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service


!-- A26: BinnenGemeentelijke-verhuizing-basis # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , BinnenGemeentelijke-verhuizing-basis
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap0 | 2121AA
_objectid$burgerservicenummer_ipv0_ | 103432620
gemeentedeel_aap0 | Bennebroek
_objectid$burgerservicenummer_ipv1_ | 103432620
huisnummer_aap0 | 7
woonplaatsnaam_aap0 | SRPUC50151-6-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-810000000184
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/BinnenGemeentelijke-verhuizing-basis-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A27: BinnenGemeentelijke-verhuizing-basis # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | referentienummer_sMM0=00000000-0000-0000-0700-810000000184
abonnement_id | 5670005
Then is de query /sqltemplates/LaatsteVulberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/BinnenGemeentelijke-verhuizing-basis-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_7
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A28: VP.0-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC02
Given extra waardes:
SLEUTEL | WAARDE
burgerservicenummer_ipr1 | 103432620
abonnementNaam_aap2 | postcode gebied Hillegom 2180 - 2182
referentienummer_sLB1 | 0000000A-3000-1000-0000-800000000002
datumAanvangMaterielePeriode_aap2 | []
abonnementNaam_aap1 | postcode gebied Hillegom 2180 - 2182
datumAanvangMaterielePeriode_aap1 | []
afnemerCode_aap2 | 505005
afnemerCode_aap1 | 505005
burgerservicenummer_ipv1 | 103432620
partijCode_vLB1 | 505005
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/VP.0-TC02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A29: VP.0-TC02 # 2 : status=null
Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VP.0-TC02-2-dataresponse.xml voor de expressie //Results

!-- A30: VP.0-TC02 # 3 : status=null
Given extra waardes:
SLEUTEL | WAARDE
burgerservicenummer_ipr1 | 103432620
Then is de query /sqltemplates/selecteerPersafnemerindicatie_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VP.0-TC02-3-dataresponse.xml voor de expressie //Results
Then de database wordt opgeruimd met:
 delete from autaut.his_persafnemerindicatie where persafnemerindicatie in (select id from autaut.persafnemerindicatie where pers in (select id from kern.pers where bsn = 103432620));delete from autaut.persafnemerindicatie where pers in (select id from kern.pers where bsn = 103432620);


Scenario: Scenario_8_Verhuis buitendoelbinding
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A31: VerhuisBuitenDoelbinding-TC05 # 10 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , VerhuisBuitenDoelbinding-TC05
Given extra waardes:
SLEUTEL | WAARDE
postcode_aap0 | 2121ZZ
_objectid$burgerservicenummer_ipv0_ | 103432620
gemeentedeel_aap0 | Bennebroek
_objectid$burgerservicenummer_ipv1_ | 103432620
huisnummer_aap0 | 8
woonplaatsnaam_aap0 | SRPUC50151-6-Woonplaats
referentienummer_sMM0 | 00000000-0000-0000-0700-810000000252
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

!-- A32: VerhuisBuitenDoelbinding-TC05 # 11 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0701-000000000252
abonnement_id | 5670005
Then is binnen 120s de query /sqltemplates/BerAdmhndAbonnementen_biddesc_Verhuizing_ART-VolgenPersonenNA.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisBuitenDoelbinding-TC05-11-dataresponse.xml voor de expressie //Results

!-- A33: VerhuisBuitenDoelbinding-TC05 # 12 : status=null
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-810000000252
abonnement_id | 5670005
_bsn_rOO0_ | 103432620
Then is binnen 30s de query /sqltemplates/LaatsteVulberichtPerAbonnementZonderAdmhnd.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/VerhuisBuitenDoelbinding-TC05-12-dataresponse.xml voor de expressie //Results


Scenario: Scenario_9
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A34: plaatsing-afnemerindicatie-2-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-2-TC01
Given de database wordt gereset voor de personen 330006575
Given extra waardes:
SLEUTEL | WAARDE
abonnementNaam_aap1 | Abo LO3
afnemerCode_aap1 | 017401
referentienummer_sLB1 | 0000000A-3000-1000-0000-810000000028
burgerservicenummer_ipv1 | []
burgerservicenummer_ipr1 | 330006575
partijCode_pLB1 | 017401
Given de database is aangepast met:
 update kern.pers set scheidingsteken = null where bsn = 330006575;
update kern.pers set scheidingstekennaamgebruik = null where bsn = 330006575;
update kern.his_persgeslnaamcomp set scheidingsteken = null where persgeslnaamcomp in ( select id from kern.persgeslnaamcomp where pers in (select id from kern.pers where bsn = 330006575));
update kern.his_perssamengesteldenaam set scheidingsteken = null where pers in ( select id from kern.pers where bsn = 330006575);
update kern.his_persnaamgebruik set scheidingstekennaamgebruik = null where pers in ( select id from kern.pers where bsn = 330006575);
delete from kern.perscache where pers in (select id from kern.pers where bsn = 330006575);
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/plaatsing-afnemerindicatie-2-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then is de query /sqltemplates/selecteerAbonnement.sql gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/plaatsing-afnemerindicatie-2-TC01-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_10
Meta:
@regels VR00052
@status Uitgeschakeld
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A35: BRBY0011-TC3000-2 # 1 : status=QUARANTAINE
Given data uit excel /templatedata/art_input_055.xls , BRBY0011-TC3000-2
Given extra waardes:
SLEUTEL | WAARDE
_bsn_rOO0_ | 330006575
referentienummer_sOO0 | 00000000-0000-0000-0055-800000000002
Given de database is aangepast met:
 update kern.plaats set dataanvgel=null where code = 1702 or code = 3086
Given de sjabloon /berichtdefinities/KUC055_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/response/BRBY0011-TC3000-2-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking
Then is binnen 14s de query /sqltemplates/MQ::select 'LO3 AG31 bericht op de Queue "queue://lo3Queue" geplaatst voor persoon met BSN 330006575' "LO3-bericht" from public.activemq_msgs where msg like '%Ag31A00000000%' AND msg like '%330006575%' AND container = 'queue://lo3Queue'  AND id = (select max(id) from activemq_msgs) gelijk aan /testcases/art_input_MutatieleveringOpAfnemerindicatie/data/BRBY0011-TC3000-2-1-dataresponse.xml voor de expressie //Results



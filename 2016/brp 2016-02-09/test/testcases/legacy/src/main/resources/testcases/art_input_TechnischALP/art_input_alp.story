Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service
@auteur tools
@status Klaar
@epic legacy
@module technisch-alp

Scenario: Scenario_0
Meta:
@regels VR00053
VR00055, VR00042
VR00044 (LEEG)
VR00045 (LEEG)
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A2: LT01FT01-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-02
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-700000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/LT01FT01-02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A3: Protocollering-mutatielevering-doelbinding-vulbericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-mutatielevering-doelbinding-vulbericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670019
srtsynchronisatie | 2
Then is binnen 45s de query /sqltemplates/ProtocolleringAbo_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Protocollering-mutatielevering-doelbinding-vulbericht-1-dataresponse.xml voor de expressie //Results

!-- A4: Protocollering-attendering-vulbericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-attendering-vulbericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670026
srtsynchronisatie | 2
Then is de query /sqltemplates/ProtocolleringAbo_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Protocollering-attendering-vulbericht-1-dataresponse.xml voor de expressie //Results

!-- A5: Protocollering-mutatielevering-doelbinding-geheim-protocolleringsniveau # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-mutatielevering-doelbinding-geheim-protocolleringsniveau
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670037
srtsynchronisatie | 2
Then is de query /sqltemplates/ProtocolleringAbo_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Protocollering-mutatielevering-doelbinding-geheim-protocolleringsniveau-1-dataresponse.xml voor de expressie //Results

!-- A6: Archivering_doelbindinglevering_vulbericht # 1 : status=null
!--Given data uit excel /templatedata/art_input_alp.xls , Archivering_doelbindinglevering_vulbericht
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-700000000001
abonnement_id | 5670019
inkomendbericht_naam | bhg_vbaRegistreerVerhuizing
srtsynchronisatie_id | 2
leverbericht_naam | lvg_synVerwerkPersoon
Then is de query /sqltemplates/ArchiveringAbo_LeverBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_doelbindinglevering_vulbericht-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_1
Meta:
@regels VR00053
VR00054, VR00053
VR00055, VR00042
VR00044 (LEEG)
VR00045 (LEEG)
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A7: BinnenGemeentelijke-verhuizing-basis # null : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , BinnenGemeentelijke-verhuizing-basis
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-710000000184
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/BinnenGemeentelijke-verhuizing-basis--soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A8: Archivering_request_bijhouding # 1 : status=null
!--Given data uit excel /templatedata/art_input_alp.xls , Archivering_request_bijhouding
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0700-710000000184
inkomendbericht_naam | bhg_vbaRegistreerVerhuizing
Then is binnen 45s de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Archivering_request_bijhouding-1-dataresponse.xml voor de expressie //Results

!-- A9: Protocollering-mutatielevering-doelbinding-mutatiebericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-mutatielevering-doelbinding-mutatiebericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670014
Then is de query /sqltemplates/ProtocolleringAbo_mutatieLeveringDoelbinding_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Protocollering-mutatielevering-doelbinding-mutatiebericht-1-dataresponse.xml voor de expressie //Results

!-- A10: Archivering_doelbindinglevering_mutatiebericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_doelbindinglevering_mutatiebericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670014
inkomendbericht_naam | bhg_vbaRegistreerVerhuizing
srtsynchronisatie_id | 1
leverbericht_naam | lvg_synVerwerkPersoon
Then is de query /sqltemplates/ArchiveringAbo_LeverBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_doelbindinglevering_mutatiebericht-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_3
Meta:
@regels VR00043
VR00044 (LEEG)
VR00045 (DATUMAANVANGMATERIELEPERIODE UIT AFNEMERSINDICATIE(=LEEG))
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND)
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A13: plaatsing-afnemerindicatie-4-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , plaatsing-afnemerindicatie-4-TC01
Given de database wordt gereset voor de personen 677080426
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-700000000030
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/plaatsing-afnemerindicatie-4-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A14: Protocollering-mutatielevering-afnemerindicatie-vulbericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-mutatielevering-afnemerindicatie-vulbericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670035
Then is binnen 45s de query /sqltemplates/ProtocolleringAbo_afnemerindicatie_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Protocollering-mutatielevering-afnemerindicatie-vulbericht-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_4
Meta:
@regels VR00053
VR00054, VR00043
VR00044 (LEEG)
VR00045 (DATUMAANVANGMATERIELEPERIODE UIT AFNEMERSINDICATIE(=GEVULD))
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND), VR00053
VR00055
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A15: VP.0-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-700000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/VP.0-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A16: Protocollering-mutatielevering-afnemerindicatie-vulbericht # 2 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-mutatielevering-afnemerindicatie-vulbericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670002
Then is binnen 45s de query /sqltemplates/ProtocolleringAbo_afnemerindicatie_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Protocollering-mutatielevering-afnemerindicatie-vulbericht-2-dataresponse.xml voor de expressie //Results

!-- A17: Archivering_request_PlaatsAfnInd # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_request_PlaatsAfnInd
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-700000000001
inkomendbericht_naam | lvg_synRegistreerAfnemerindicatie
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_request_PlaatsAfnInd-1-dataresponse.xml voor de expressie //Results

!-- A18: Archivering_response_PlaatsAfnInd-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_response_PlaatsAfnInd-TC01
Given extra waardes:
SLEUTEL | WAARDE
inkomendbericht_naam | lvg_synRegistreerAfnemerindicatie
responsebericht_naam | lvg_synRegistreerAfnemerindicatie_R
Then is de query /sqltemplates/ArchiveringAbo_ResponseBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_response_PlaatsAfnInd-TC01-1-dataresponse.xml voor de expressie //Results

!-- A19: Archivering_levering_PlaatsAfnInd # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_levering_PlaatsAfnInd
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670002
inkomendbericht_naam | lvg_synRegistreerAfnemerindicatie
srtsynchronisatie_id | 2
leverbericht_naam | lvg_synVerwerkPersoon
Then is de query /sqltemplates/ArchiveringAbo_VulBericht_AfnermerIndicatie_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_levering_PlaatsAfnInd-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_5
Meta:
@regels VR00053
VR00055
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A20: VP.0-TC01 # 2 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-710000000001
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/VP.0-TC01-2-soapresponse.xml voor expressie //brp:meldingen

!-- A21: Archivering_response_PlaatsAfnInd-TC02 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_response_PlaatsAfnInd-TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-710000000001
inkomendbericht_naam | lvg_synRegistreerAfnemerindicatie
responsebericht_naam | lvg_synRegistreerAfnemerindicatie_R
Then is de query /sqltemplates/ArchiveringAbo_ResponseBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_response_PlaatsAfnInd-TC02-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_6
Meta:
@regels VR00042
VR00044 (LEEG)
VR00045 (DATUMAANVANGMATERIELEPERIODE UIT AFNEMERSINDICATIE)
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A22: LT01FT01-02 # 1 : status=null
Given data uit excel /templatedata/art_inputVerhuizing.xls , LT01FT01-02
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sMM0 | 00000000-0000-0000-0700-710000000001
Given de sjabloon /berichtdefinities/PUC700-Verhuizing_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/LT01FT01-02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A23: Protocollering-mutatielevering-afnemerindicatie-mutatiebericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-mutatielevering-afnemerindicatie-mutatiebericht
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670035
srtsynchronisatie | 1
Then is binnen 45s de query /sqltemplates/ProtocolleringAbo_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Protocollering-mutatielevering-afnemerindicatie-mutatiebericht-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_7
Meta:
@regels VR00053
VR00055, VR00042
VR00044 (LEEG)
VR00045 (LEEG)
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND)
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgNationaliteit
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A24: HF-verkrijgingNLNationaliteit-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_062.xls , HF-verkrijgingNLNationaliteit-TC01
Given de database wordt gereset voor de personen 210010939
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sNB1 | 00000000-0000-0000-0062-700000000002
Given de sjabloon /berichtdefinities/KUC062-Nationaliteit_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/HF-verkrijgingNLNationaliteit-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A25: Protocollering-attendering-MetPlaatsAfnInd # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-attendering-MetPlaatsAfnInd
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670032
Then is binnen 45s de query /sqltemplates/ProtocolleringAbo_Attendering_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Protocollering-attendering-MetPlaatsAfnInd-1-dataresponse.xml voor de expressie //Results

!-- A26: Archivering_response_bijhouding # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_response_bijhouding
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0062-700000000002
inkomendbericht_naam | bhg_natRegistreerNationaliteit
responsebericht_naam | bhg_natRegistreerNationaliteit_R
Then is de query /sqltemplates/ArchiveringAbo_ResponseBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_response_bijhouding-1-dataresponse.xml voor de expressie //Results

!-- A27: Archivering_attendering_vulbericht # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_attendering_vulbericht
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0062-700000000002
abonnement_id | 5670032
inkomendbericht_naam | bhg_natRegistreerNationaliteit
srtsynchronisatie_id | 2
leverbericht_naam | lvg_synVerwerkPersoon
Then is de query /sqltemplates/ArchiveringAbo_LeverBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_attendering_vulbericht-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_8
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A28: VP.0-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
Given de database wordt gereset voor de personen 110015927
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-720000000001
burgerservicenummer_ipv1 | 110015927
burgerservicenummer_ipr1 | 110015927
Given de database is aangepast dmv /sqltemplates/verwijder_afnemerindicatieVoorBSN.sql
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/VP.0-TC01-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking


Scenario: Scenario_9
Meta:
@regels VR00053
VR00054, VR00053
VR00055, VR00043
VR00044 (LEEG)
VR00045 (DATUMAANVANGMATERIELEPERIODE UIT AFNEMERSINDICATIE)
VR00046 (LEEG)
VR00047 (TIJDREGISTRATIE ADMHND)
VR00048 (TIJDREGISTRATIE ADMHND)
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A29: SyncStructuur-TC03 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , SyncStructuur-TC03
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670002
zendendePartij_slB1 | 017401
referentienummer_slB1 | 0000000A-3000-7000-1000-700000000004
Given de database is aangepast dmv /sqltemplates/ProtocolleringAbo_synchronisatiePersoon_prequery_ART-Technisch-ALP.sql
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/SyncStructuur-TC03-1-soapresponse.xml voor expressie //brp:resultaat

!-- A30: Protocollering-SynPers-mutatielevering-AfnInd # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-SynPers-mutatielevering-AfnInd
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670002
Then is binnen 45s de query /sqltemplates/ProtocolleringAbo_synchronisatiePersoon_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Protocollering-SynPers-mutatielevering-AfnInd-1-dataresponse.xml voor de expressie //Results

!-- A31: Archivering_request_SynPers # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_request_SynPers
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-700000000004
inkomendbericht_naam | lvg_synGeefSynchronisatiePersoon
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_request_SynPers-1-dataresponse.xml voor de expressie //Results

!-- A32: Archivering_levering_SynPers # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_levering_SynPers
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670002
inkomendbericht_naam | lvg_synGeefSynchronisatiePersoon
srtsynchronisatie_id | 2
leverbericht_naam | lvg_synVerwerkPersoon
Then is de query /sqltemplates/ArchiveringAbo_VulBericht_AfnermerIndicatie_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_levering_SynPers-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_10
Meta:
@regels VR00053
VR00054
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A33: happyflow-TC10 # 1 : status=null
Given data uit excel /templatedata/art_input.xls , happyflow-TC10
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_slB1 | 0000000A-3000-7000-1000-700000000010
Given de sjabloon /berichtdefinities/LEV_SynchronisatieStamgegeven_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/happyflow-TC10-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A34: Archivering_request_SynStamgegevens # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_request_SynStamgegevens
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-700000000010
inkomendbericht_naam | lvg_synGeefSynchronisatieStamgegeven
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_request_SynStamgegevens-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_11
Meta:
@regels VR00053
VR00055
@soapEndpoint ${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie
@soapNamespace http://www.bzk.nl/brp/levering/synchronisatie/service

!-- A35: BRLV0038-TC01 # 1 : status=null
Given data uit excel /templatedata/art_input_VerzoekSynchronisatie.xls , BRLV0038-TC01
Given extra waardes:
SLEUTEL | WAARDE
zendendePartij_slB1 | 034401
referentienummer_slB1 | 0000000A-3000-7000-1000-700000000011
Given de sjabloon /berichtdefinities/LEV_VerzoekSynchronisatiePersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/BRLV0038-TC01-1-soapresponse.xml voor expressie //brp:meldingen

!-- A36: Archivering_response_SynPers # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_response_SynPers
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-7000-1000-700000000011
inkomendbericht_naam | lvg_synGeefSynchronisatiePersoon
responsebericht_naam | lvg_synGeefSynchronisatiePersoon_R
Then is de query /sqltemplates/ArchiveringAbo_ResponseBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_response_SynPers-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_12
Meta:
@regels VR00053
VR00054, VR00053
VR00055, VR00041
VR00044 (LEEG)
VR00045 (PEILDATUMMATERIEEL UIT REQUEST)
VR00046 (PEILDATUMMATERIEEL UIT REQUEST+1)
VR00047 (PEILDATUMFORMEEL UIT REQUEST)
VR00048 (PEILDATUMFORMEEL UIT REQUEST)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A37: happyflow-TC01 # 11 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | ${moment_volgen()}
referentienummer_sAA0 | 00000000-0000-0000-0501-700000000001
peilmomentMaterieelResultaat_zvA0 | ${vandaag()}
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/happyflow-TC01-11-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A38: Protocollering-GeefDetailsPersoon # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-GeefDetailsPersoon
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670041
Then is de query /sqltemplates/ProtocolleringAbo_GeefDetailsPersoon_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Protocollering-GeefDetailsPersoon-1-dataresponse.xml voor de expressie //Results

!-- A39: Archivering_request_GeefDetailsPersoon # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_request_GeefDetailsPersoon
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0501-700000000001
inkomendbericht_naam | lvg_bvgGeefDetailsPersoon
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_request_GeefDetailsPersoon-1-dataresponse.xml voor de expressie //Results

!-- A40: Archivering_response_GeefDetailsPersoon # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_response_GeefDetailsPersoon
Given extra waardes:
SLEUTEL | WAARDE
inkomendbericht_naam | lvg_bvgGeefDetailsPersoon
responsebericht_naam | lvg_bvgGeefDetailsPersoon_R
Then is de query /sqltemplates/ArchiveringAbo_ResponseBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_response_GeefDetailsPersoon-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_13
Meta:
@regels VR00041
VR00044 (LEEG)
VR00045 (LEEG)
VR00046 (PEILDATUMMATERIEEL UIT REQUEST+1)
VR00047 (PEILDATUMFORMEEL UIT REQUEST)
VR00048 (PEILDATUMFORMEEL UIT REQUEST)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A41: happyflow-TC01 # 12 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | ${moment_volgen()}
historievorm_zvA0 | Materieel
referentienummer_sAA0 | 00000000-0000-0000-0501-710000000001
peilmomentMaterieelResultaat_zvA0 | ${vandaag()}
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/happyflow-TC01-12-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A42: Protocollering-GeefDetailsPersoon-TC02 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-GeefDetailsPersoon-TC02
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670041
Then is de query /sqltemplates/ProtocolleringAbo_GeefDetailsPersoon_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Protocollering-GeefDetailsPersoon-TC02-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_14
Meta:
@regels VR00041
VR00044 (LEEG)
VR00045 LEEG)
VR00046 PEILDATUMMATERIEEL UIT REQUEST+1)
VR00047 (LEEG)
VR00048 (PEILDATUMFORMEEL UIT REQUEST)
@soapEndpoint ${applications.host}/bevraging/LeveringBevragingService/lvgBevraging
@soapNamespace http://www.bzk.nl/brp/levering/bevraging/service

!-- A43: happyflow-TC01 # 13 : status=null
Given data uit excel /templatedata/art_input_GeefDetailsPersoon.xls , happyflow-TC01
Given extra waardes:
SLEUTEL | WAARDE
peilmomentFormeelResultaat_zvA0 | ${moment_volgen()}
historievorm_zvA0 | MaterieelFormeel
referentienummer_sAA0 | 00000000-0000-0000-0501-720000000001
peilmomentMaterieelResultaat_zvA0 | ${vandaag()}
Given de sjabloon /berichtdefinities/Levering-Bevraging-GeefDetailsPersoon_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/happyflow-TC01-13-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A44: Protocollering-GeefDetailsPersoon-TC03 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Protocollering-GeefDetailsPersoon-TC03
Given extra waardes:
SLEUTEL | WAARDE
abonnement_id | 5670041
Then is de query /sqltemplates/ProtocolleringAbo_GeefDetailsPersoon_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Protocollering-GeefDetailsPersoon-TC03-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_15
Meta:
@regels VR00053
VR00054
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

!-- A45: VP.0-TC02 # 1 : status=null
Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sLB1 | 0000000A-3000-1000-0000-700000000002
Given de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/VP.0-TC02-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A46: Archivering_request_VerwAfnInd # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_request_VerwAfnInd
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 0000000A-3000-1000-0000-700000000002
inkomendbericht_naam | lvg_synRegistreerAfnemerindicatie
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_request_VerwAfnInd-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_16
Meta:
@regels VR00055
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

!-- A47: Zelfde-ouders-TC04 # 1 : status=null
Given data uit excel /templatedata/art_input_008.xls , Zelfde-ouders-TC04
Given de database wordt gereset voor de personen 110011168,110011296,110012434
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0008-700000000008
Given de sjabloon /berichtdefinities/KUC008-actualiseer-afstamming_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/Zelfde-ouders-TC04-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A48: Archivering_levering_meerderePersonen # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_levering_meerderePersonen
!-- geen nadere populatie beperking, kind, moeder en vader worden geleverd
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-700000000008
abonnement_id | 5670014
inkomendbericht_naam | bhg_afsActualiseerAfstamming
srtsynchronisatie_id | 1
leverbericht_naam | lvg_synVerwerkPersoon
Then is binnen 45s de query /sqltemplates/ArchiveringAbo_LeverBericht_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/Archivering_levering_meerderePersonen-1-dataresponse.xml voor de expressie //Results

!-- A49: Archivering_levering_ExlBetrokkenheden # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , Archivering_levering_ExlBetrokkenheden
!-- nadere populatie beperking is "geslachtsaanduiding.geslachtsaanduiding = "M"", de verwachting is dat enkel de vader geleverd wordt
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | 00000000-0000-0000-0008-700000000008
abonnement_id | 5670029
inkomendbericht_naam | bhg_afsActualiseerAfstamming
srtsynchronisatie_id | 1
leverbericht_naam | lvg_synVerwerkPersoon
Then is de query /sqltemplates/ArchiveringAbo_LeverBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/Archivering_levering_ExlBetrokkenheden-1-dataresponse.xml voor de expressie //Results


Scenario: Scenario_17
Meta:
@regels VR00053
VR00054, VR00053
VR00055
@soapEndpoint ${applications.host}/bevraging/BijhoudingBevragingService/bhgBevraging
@soapNamespace http://www.bzk.nl/brp/bijhouding/bevraging/service

!-- A51: LT05FT46 # 1 : status=null
Given data uit excel /templatedata/art_input_DetailsPersoon.xls , LT05FT46
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAA0 | 00000000-0000-0000-0501-700000000139
Given de sjabloon /berichtdefinities/PUC501-OPvB-OpvragenDetailsPersoonEnRelaties_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/LT05FT46-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A52: archiveringBevragingsPersonen-TC01 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , archiveringBevragingsPersonen-TC01
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | ${referentienummer_sAA0}
inkomendbericht_naam | bhg_bvgGeefDetailsPersoon
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC01-1-dataresponse.xml voor de expressie //Results

!-- A53: archiveringBevragingsPersonen-TC01 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
inkomendbericht_naam | bhg_bvgGeefDetailsPersoon
leverbericht_naam | bhg_bvgGeefDetailsPersoon_R
Then is de query /sqltemplates/ArchiveringAbo_UitgaandBijhoudingsBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC01-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_18
Meta:
@regels VR00053
VR00054, VR00053
VR00055
@soapEndpoint ${applications.host}/bevraging/BijhoudingBevragingService/bhgBevraging
@soapNamespace http://www.bzk.nl/brp/bijhouding/bevraging/service

!-- A54: BRPUC50110-LT01FT35-03 # 1 : status=null
Given data uit excel /templatedata/art_input_KV.xls , BRPUC50110-LT01FT35-03
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0501-700000000040
Given de sjabloon /berichtdefinities/PUC501-Kandidaat-Vader_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/BRPUC50110-LT01FT35-03-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A55: archiveringBevragingsPersonen-TC02 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , archiveringBevragingsPersonen-TC02
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | ${referentienummer_sAB0}
inkomendbericht_naam | bhg_bvgBepaalKandidaatVader
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC02-1-dataresponse.xml voor de expressie //Results

!-- A56: archiveringBevragingsPersonen-TC02 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
inkomendbericht_naam | bhg_bvgBepaalKandidaatVader
leverbericht_naam | bhg_bvgBepaalKandidaatVader_R
Then is de query /sqltemplates/ArchiveringAbo_UitgaandBijhoudingsBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC02-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_19
Meta:
@regels VR00041
VR00044 (LEEG)
VR00045 (SYSTEEMDATUM VAN KLAARZETTEN BERICHT)
VR00046 (SYSTEEMDATUM VAN KLAARZETTEN BERICHT+1)
VR00047 (SYSTEEMDATUM VAN KLAARZETTEN BERICH)
VR00048 (SYSTEEMDATUM VAN KLAARZETTEN BERICH), VR00053
VR00054, VR00053
VR00055
@soapEndpoint ${applications.host}/bevraging/BijhoudingBevragingService/bhgBevraging
@soapNamespace http://www.bzk.nl/brp/bijhouding/bevraging/service

!-- A57: SRPUC50151-TC1003 # 1 : status=null
Given data uit excel /templatedata/art_input_PersonenOpAdres.xls , SRPUC50151-TC1003
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0501-700000000006
Given de sjabloon /berichtdefinities/PUC501-OPvB-BepalenPersonenOpAdresMetRelaties_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/SRPUC50151-TC1003-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A58: archiveringBevragingsPersonen-TC03 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , archiveringBevragingsPersonen-TC03
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | ${referentienummer_sAB0}
inkomendbericht_naam | bhg_bvgGeefPersonenOpAdresMetBetrokkenheden
Then is de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC03-1-dataresponse.xml voor de expressie //Results

!-- A59: archiveringBevragingsPersonen-TC03 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
inkomendbericht_naam | bhg_bvgGeefPersonenOpAdresMetBetrokkenheden
leverbericht_naam | bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R
Then is de query /sqltemplates/ArchiveringAbo_UitgaandBijhoudingsBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC03-2-dataresponse.xml voor de expressie //Results


Scenario: Scenario_20
Meta:
@regels VR00053
VR00054, VR00053
VR00055
@soapEndpoint ${applications.host}/bevraging/BijhoudingBevragingService/bhgBevraging
@soapNamespace http://www.bzk.nl/brp/bijhouding/bevraging/service

!-- A61: SRPUC50152-TC1100 # 1 : status=null
Given data uit excel /templatedata/art_input_PersonenOpAdres.xls , SRPUC50152-TC1100
Given extra waardes:
SLEUTEL | WAARDE
referentienummer_sAB0 | 00000000-0000-0000-0501-700000000008
huisnummer_zvA0 | 11111
Given de sjabloon /berichtdefinities/PUC501-OPvB-BepalenPersonenOpAdresMetRelaties_request_template.xml
When het bericht is naar endpoint verstuurd
Then is het antwoordbericht gelijk aan /testcases/art_input_TechnischALP/response/SRPUC50152-TC1100-1-soapresponse.xml voor expressie //brp:resultaat/brp:verwerking

!-- A62: archiveringBevragingsPersonen-TC04 # 1 : status=null
!-- Given data uit excel /templatedata/art_input_alp.xls , archiveringBevragingsPersonen-TC04
Given extra waardes:
SLEUTEL | WAARDE
referentienr_id | ${referentienummer_sAB0}
inkomendbericht_naam | bhg_bvgGeefPersonenOpAdresMetBetrokkenheden
Then is binnen 45s de query /sqltemplates/ArchiveringAbo_InkomendBericht_ART-Technisch-ALP.sql gelijk aan
/testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC04-1-dataresponse.xml voor de expressie //Results

!-- A63: archiveringBevragingsPersonen-TC04 # 2 : status=null
Given extra waardes:
SLEUTEL | WAARDE
inkomendbericht_naam | bhg_bvgGeefPersonenOpAdresMetBetrokkenheden
leverbericht_naam | bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R
Then is de query /sqltemplates/ArchiveringAbo_UitgaandBijhoudingsBericht_ART-Technisch-ALP.sql gelijk aan /testcases/art_input_TechnischALP/data/archiveringBevragingsPersonen-TC04-2-dataresponse.xml voor de expressie //Results


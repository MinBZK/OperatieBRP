-- Tijdelijke tabelen aanmaken voor het versnellen voor het ophalen van geldige huwelijken 
-- en voor het uitzoeken of de man een kind heeft met een ander achternaam dan hij.
-- In het verleden deed de oude query 4 uren voordat uberhaput aan testen kon beginnen.
--
-- Deze 2 tabellen moeten aan het begin gedaan worden (voordat enig test uitgevoerd is).
-- Een probleem met deze methode, is dat bij aanmaken van nieuwe huwelijken en familie, de nieuwe informatie 
-- NIET in deze tabellen zit.
-- Dit is niet een onoverkomelijk bezwaar omdat we de database opnieuw resetten.


DROP SCHEMA IF EXISTS helper CASCADE;
CREATE SCHEMA helper;

DROP TABLE IF EXISTS helper.bli_lopende_huwelijk CASCADE;
create TABLE helper.bli_lopende_huwelijk (
  REL_ID                        Integer,
  SOORT                        	Smallint,
  mBetr_ID                      Integer,
  vBetr_ID                      Integer,
  mPERS_Id                      Integer,
  vPERS_Id                      Integer,
  mBSN                          Integer,
  vBSN                          Integer,
  mGESLAAND         SMALLINT,
  vGESLAAND         SMALLINT,
  mADRES_ID         Integer,
  vADRES_ID         Integer
);

DROP TABLE IF EXISTS helper.bli_familie CASCADE;
create TABLE helper.bli_familie (
  REL_ID                        Integer,
  mBetr_ID                      Integer,
  vBetr_ID                      Integer,
  mPERS_Id                      Integer,
  vPERS_Id                      Integer,
  mBSN                          Integer,
  vBSN                          Integer,
  mGESLAAND         SMALLINT,
  vGESLAAND         SMALLINT,
  kBetr_ID                      Integer,
  kPERS_Id                      Integer,
  kBSN                          Integer,
  kGESLAAND         SMALLINT
);

delete from helper.bli_lopende_huwelijk;
drop index if exists bli_lopende_huwelijk_rel;
drop index if exists bli_lopende_huwelijk_mbsn;
drop index if exists bli_lopende_huwelijk_vbsn;
drop index if exists bli_lopende_huwelijk_mpers_id;
drop index if exists bli_lopende_huwelijk_vpers_id;

delete from helper.bli_familie;
drop index if exists bli_familie_rel;
drop index if exists bli_familie_mbsn;
drop index if exists bli_familie_vbsn;
drop index if exists bli_familie_kbsn;
drop index if exists bli_familie_mpers_id;
drop index if exists bli_familie_vpers_id;
drop index if exists bli_familie_kpers_id;



INSERT INTO helper.bli_lopende_huwelijk
SELECT REL_ID, rel.srt,
(CASE WHEN GESLAAND1 = 2 THEN BETR2_ID ELSE BETR1_ID END) mBetr_ID, 
(CASE WHEN GESLAAND2 = 2 THEN BETR2_ID ELSE BETR1_ID END) vBetr_ID, 
(CASE WHEN GESLAAND1 = 2 THEN PERS2_ID ELSE PERS1_ID END) mPERS_Id, 
(CASE WHEN GESLAAND2 = 2 THEN PERS2_ID ELSE PERS1_ID END) vPERS_Id, 
(CASE WHEN GESLAAND1 = 2 THEN BSN2 ELSE BSN1 END) mBSN, 
(CASE WHEN GESLAAND2 = 2 THEN BSN2 ELSE BSN1 END) vBSN, 
(CASE WHEN GESLAAND1 = 2 THEN GESLAAND2 ELSE GESLAAND1 END) mGESLAAND, 
(CASE WHEN GESLAAND2 = 2 THEN GESLAAND2 ELSE GESLAAND1 END) vGESLAAND, 
(CASE WHEN GESLAAND1 = 2 THEN ADRES2_ID ELSE ADRES1_ID END) mADRES_ID, 
(CASE WHEN GESLAAND2 = 2 THEN ADRES2_ID ELSE ADRES1_ID END) mADRES_ID
from 
(select r.id REL_ID, r.srt from kern.relatie r where r.srt in (1,2) and r.dateinde is null) rel
inner join (select betr.id BETR1_ID, betr.id, betr.relatie, pers.id AS PERS1_ID, pers.bsn BSN1, pers.geslachtsaand GESLAAND1, persadres.id ADRES1_ID from kern.betr, kern.pers, kern.persadres 
	WHERE betr.pers = pers.id and persadres.pers = pers.id and persadres.srt=1) bm ON bm.relatie = rel.REL_ID
inner join (select betr.id BETR2_ID, betr.id, betr.relatie, pers.id AS PERS2_ID, pers.bsn BSN2, pers.geslachtsaand GESLAAND2, persadres.id ADRES2_ID from kern.betr, kern.pers, kern.persadres 
	WHERE betr.pers = pers.id and persadres.pers = pers.id and persadres.srt=1) bv ON bv.relatie = rel.REL_ID
AND bm.id < bv.id
;

INSERT INTO helper.bli_familie
SELECT REL_ID, 
bm.BETR2_ID ,
bv.BETR1_ID,
bm.PERS2_ID,
bv.PERS1_ID,
bm.BSN2,
bv.BSN1,
bm.GESLAAND2,
bv.GESLAAND1,
bk.BETR3_ID, bk.PERS3_ID kPers_ID, bk.BSN3 kBSN, bk.GESLAAND3 kGESLAAND
from 
(select r.id REL_ID from kern.relatie r where r.srt = 3) rel
inner join (select betr.id BETR1_ID, betr.relatie, pers.id AS PERS1_ID, pers.bsn BSN1, pers.geslachtsaand GESLAAND1 from kern.betr, kern.pers
	WHERE betr.pers = pers.id and betr.rol=2 and pers.geslachtsaand=2) bv ON bv.relatie = rel.REL_ID
inner join (select betr.id BETR3_ID, betr.relatie, pers.id AS PERS3_ID, pers.bsn BSN3, pers.geslachtsaand GESLAAND3 from kern.betr, kern.pers
	WHERE betr.pers = pers.id and betr.rol=1) bk ON bk.relatie = rel.REL_ID
LEFT OUTER join (select betr.id BETR2_ID, betr.relatie, pers.id AS PERS2_ID, pers.bsn BSN2, pers.geslachtsaand GESLAAND2 from kern.betr, kern.pers
	WHERE betr.pers = pers.id and betr.rol=2 and pers.geslachtsaand=1) bm ON bm.relatie = rel.REL_ID
;


create index bli_lopende_huwelijk_rel on helper.bli_lopende_huwelijk (rel_id);
create index bli_lopende_huwelijk_mbsn on helper.bli_lopende_huwelijk (mbsn);
create index bli_lopende_huwelijk_vbsn on helper.bli_lopende_huwelijk (vbsn);
create index bli_lopende_huwelijk_mpers_id on helper.bli_lopende_huwelijk (mpers_id);
create index bli_lopende_huwelijk_vpers_id on helper.bli_lopende_huwelijk (vpers_id);

create index bli_familie_rel on helper.bli_familie (rel_id);
create index bli_familie_mbsn on helper.bli_familie (mbsn);
create index bli_familie_vbsn on helper.bli_familie (vbsn);
create index bli_familie_kbsn on helper.bli_familie (kbsn);
create index bli_familie_mpers_id on helper.bli_familie (mpers_id);
create index bli_familie_vpers_id on helper.bli_familie (vpers_id);
create index bli_familie_kpers_id on helper.bli_familie (kpers_id);

vacuum;

analyze;

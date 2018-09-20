--
-- create info tabel,is een hulp tabel om test scenario af te lopen.
--

DROP TABLE IF EXISTS info_ids_leveranciers ;
CREATE TABLE info_ids_leveranciers
 (pers INTEGER,
  P_nummer_personage INTEGER,
  bsn INTEGER,
  anr BIGINT,
  functionelecode_gem SMALLINT,
  wplnaam CHARACTER VARYING(80),
  postcode CHARACTER VARYING(6),
  nor CHARACTER VARYING(80),
  huisnummer INTEGER,
  huisnrtoevoeging CHAR(3)   
 );

DELETE FROM info_ids_leveranciers;

INSERT INTO info_ids_leveranciers
SELECT A.id, MOD(MOD(A.id, 20000000), 720), bsn, anr, gem, wplnaam, postcode, nor, huisnr, huisnrtoevoeging
FROM kern.persadres A, kern.pers B
WHERE A.pers = B.id;
  

UPDATE info_ids_leveranciers A SET wplnaam = (SELECT code FROM kern.plaats B WHERE A.wplnaam = B.naam);
UPDATE info_ids_leveranciers A SET functionelecode_gem = (SELECT code FROM kern.gem B WHERE A.functionelecode_gem=B.id);


DROP TABLE IF EXISTS info;
CREATE TABLE info AS (SELECT * FROM info_ids_leveranciers ORDER by 1);
UPDATE info SET P_nummer_personage = P_nummer_personage + 60 WHERE huisnrtoevoeging LIKE 'B%' AND P_nummer_personage <= 60;

-- end

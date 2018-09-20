drop table if exists meting.tussenResultaat;
drop schema if exists meting cascade;

CREATE SCHEMA meting;

CREATE TABLE meting.tussenResultaat (
    bericht_id Varchar(36) NOT NULL, /* */
    status varchar(60) NOT NULL,
    indicatie_beheerder boolean NOT NULL,
    CONSTRAINT meting_tussen_resultaat_prim_key PRIMARY KEY (bericht_id, status));

/* Selecteren: */
/*NOK:*/
/*1. activiteit met gebeurtenis 1103 */
INSERT INTO meting.tussenResultaat select DISTINCT lo3_bericht.lo3_bericht_id as bericht_id, gebeurtenis.gebeurtenis_type as status, true as indicatie_beheerder from activiteit 
    join gebeurtenis on activiteit.activiteit_id = gebeurtenis.activiteit_id
    join lo3_bericht on activiteit.activiteit_id = lo3_bericht.bericht_activiteit_id where gebeurtenis.gebeurtenis_type = 1103;

/* 2. activiteit met 2e gebeurtenis is: 1104 of 1105 of 1106 of 1112 */
INSERT INTO meting.tussenResultaat  select lo3_bericht.lo3_bericht_id as bericht_id, gebeurtenis.gebeurtenis_type as status, false as indicatie_beheerder from activiteit
    join gebeurtenis on activiteit.activiteit_id = gebeurtenis.activiteit_id
    join lo3_bericht on activiteit.activiteit_id = lo3_bericht.bericht_activiteit_id
   where activiteit.toestand = 8000 AND (gebeurtenis.gebeurtenis_type = 1104 OR gebeurtenis.gebeurtenis_type = 1105 OR gebeurtenis.gebeurtenis_type = 1106 OR gebeurtenis.gebeurtenis_type = 1112)
AND gebeurtenis.gebeurtenis_id = (select gebeurtenis_id from gebeurtenis where activiteit_id = activiteit.activiteit_id order by gebeurtenis_id LIMIT 1 OFFSET 1);

/* 3. activiteit met toestand: 9005 of 7005 */
INSERT INTO meting.tussenResultaat  select lo3_bericht.lo3_bericht_id as bericht_id, activiteit.toestand as status, true as indicatie_beheerder from activiteit 
    join lo3_bericht on activiteit.activiteit_id = lo3_bericht.bericht_activiteit_id
   where activiteit.toestand = 7005 OR activiteit.toestand = 9005;

/* OK */
/* 4. activiteit met max 2 gebeurtenissen en 1 daarvan is: 1110, 1111, 1119 */
INSERT INTO meting.tussenResultaat  select lo3_bericht.lo3_bericht_id as bericht_id, 0 as status, false as indicatie_beheerder from activiteit 
    join gebeurtenis on activiteit.activiteit_id = gebeurtenis.activiteit_id
    join lo3_bericht on activiteit.activiteit_id = lo3_bericht.bericht_activiteit_id 
   where activiteit.toestand = 8000 AND 
(gebeurtenis.gebeurtenis_type = 1110 OR gebeurtenis.gebeurtenis_type = 1111 OR gebeurtenis.gebeurtenis_type = 1119)
AND gebeurtenis.gebeurtenis_id = (select gebeurtenis_id from gebeurtenis where activiteit_id = activiteit.activiteit_id order by gebeurtenis_id LIMIT 1 OFFSET 1);

/* Niet opgepakt / verwerkt (NOT)*/
/* 5. bericht zonder activiteit */
INSERT INTO meting.tussenResultaat select lo3_bericht.lo3_bericht_id as bericht_id, 'NOT' as status, false as indicatie_beheerder from 
    lo3_bericht
   where lo3_bericht.bericht_activiteit_id  IS NULL;
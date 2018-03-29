-------------------
-- Test schema en tables
-------------------
DROP SCHEMA IF EXISTS test CASCADE;
CREATE SCHEMA test;

CREATE SEQUENCE test.seq_Referentie;
CREATE TABLE test.referentie
(
  id 				INTEGER NOT NULL DEFAULT nextval('test.seq_Referentie'),
  persid 			INTEGER NOT NULL,
  bsn 				INTEGER,
  dbversie 			VARCHAR(32),
  excelTimestamp 	VARCHAR(32),
  sql 				TEXT,
  CONSTRAINT PK_referentie PRIMARY KEY (ID)
);

CREATE TABLE test.ARTversion (
   ID               smallint       NOT NULL,
   FullVersion  	Varchar(200)   NOT NULL,    /* de volledige omschrijving van de versie om het moment dat de data generator werd gebouwd. */
   ReleaseVersion	Varchar(64),                /* de (svn) release versie. */
   BuildTimestamp	Varchar(32),                 /* de timestamp dat de generator werd gedraaid */
   ExcelTimestamp	Varchar(32),                 /* de timestamp dat de excel sheet is opgeslagen */
   CONSTRAINT PK_ARTVersion PRIMARY KEY (ID)
);

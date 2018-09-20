--------------------------------------------------
--- foreburner
--------------------------------------------------

BEGIN;

-- RvdP Alles wat in de foreburner STOND is verplaatst naar de (normale) stamtabelvullingen. In casu partij is dat de stamgegevensPartijGemeente.sql
-- INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, GemStatusHis, Code) values (2000,$$Migratie voorziening$$,7,20120101,null,'A','A',0);
-- INSERT INTO Kern.His_Partij (ID, Partij, TsReg, DatAanv, DatEinde) values (2000,2000,Now(),20120101,null);


-- bolie:
-- Dit bestand is specifiek voor de ART. Het creert (indien nog niet aanwezig),
-- een tabel waarin de versienummer van de datagenator ingezet wordt.

CREATE TABLE IF NOT EXISTS kern.ARTversion (
   ID               smallint       NOT NULL,
   FullVersion  	Varchar(200)   NOT NULL,    /* de volledige omschrijving van de versie om het moment dat de data generator werd gebouwd. */
   ReleaseVersion	Varchar(64),                /* de (svn) release versie. */
   BuildTimestamp	Varchar(32),                 /* de timestamp dat de generator werd gedraaid */
   ExcelTimestamp	Varchar(32),                 /* de timestamp dat de excel sheet is opgeslagen */
   CONSTRAINT PK_ARTVersion PRIMARY KEY (ID)
);

COMMIT;

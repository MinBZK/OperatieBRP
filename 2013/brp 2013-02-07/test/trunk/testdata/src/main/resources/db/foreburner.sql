--------------------------------------------------
--- foreburner
--------------------------------------------------

BEGIN;

-- RvdP Alles wat in de foreburner STOND is verplaatst naar de (normale) stamtabelvullingen. In casu partij is dat de stamgegevensPartijGemeente.sql
-- INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, GemStatusHis, Code) values (2000,$$Migratie voorziening$$,7,20120101,null,'A','A',0);
-- INSERT INTO Kern.His_Partij (ID, Partij, TsReg, DatAanv, DatEinde) values (2000,2000,Now(),20120101,null);


COMMIT;

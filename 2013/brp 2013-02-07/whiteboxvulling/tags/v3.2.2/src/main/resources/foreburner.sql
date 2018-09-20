--------------------------------------------------
--- foreburner
--------------------------------------------------


BEGIN;

--Antek-- uitgecomment vanwege fout: ERROR [DatabaseResetter.java:55] psql:C:/dev/mgba/tmp/whiteboxfiller.sql:30: ERROR:  duplicate key value violates unique constraint "br4493"
-- INSERT INTO Kern.SrtPartij (ID, Naam) VALUES (7, 'BRP voorziening');

--RvdP-- Toegevoegd: insert statements voor soort actie:
--Antek-- uitgecomment vanwege fout: ERROR:  duplicate key value violates unique constraint "br4401"
-- INSERT INTO Kern.SrtActie (ID, Naam, Categoriesrtactie) VALUES (1, 'Conversie GBA', 1);
--INSERT INTO Kern.SrtActie (ID, Naam, Categoriesrtactie) VALUES (2, 'Inschrijving Geboorte', 2);
--INSERT INTO Kern.SrtActie (ID, Naam, Categoriesrtactie) VALUES (3, 'Verhuizing', 3);

--Antek-- 'Code' toegevoegd en op '0' (onbekend) gezet
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, GemStatusHis, Code) values (2000,$$Migratie voorziening$$,7,20120101,null,'A','A',0);
INSERT INTO Kern.His_Partij (ID, Partij, TsReg, DatAanv, DatEinde) values (2000,2000,Now(),20120101,null);

COMMIT;

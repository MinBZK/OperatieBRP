--- Start  SierraTestdataGenerator 0.0.7-SNAPSHOT-r5660-b81 (11-10-2012 16:25.51) @ vrijdag 12 oktober 2012 8:31:23 uur CEST

---  #####   ###  #######  ######   ######      #
--- #     #   #   #        #     #  #     #    # #
--- #         #   #        #     #  #     #   #   #
---  #####    #   #####    ######   ######   #     #
---       #   #   #        #   #    #   #    #######
--- #     #   #   #        #    #   #    #   #     #
---  #####   ###  #######  #     #  #     #  #     #

--- #######  #######   #####   #######  ######      #     #######     #
---    #     #        #     #     #     #     #    # #       #       # #
---    #     #        #           #     #     #   #   #      #      #   #
---    #     #####     #####      #     #     #  #     #     #     #     #
---    #     #              #     #     #     #  #######     #     #######  
---    #     #        #     #     #     #     #  #     #     #     #     #
---    #     #######   #####      #     ######   #     #     #     #     #

---  #####   #######  #     #  #######  ######      #     #######  #######  ######
--- #     #  #        ##    #  #        #     #    # #       #     #     #  #     #
--- #        #        # #   #  #        #     #   #   #      #     #     #  #     #
--- #  ####  #####    #  #  #  #####    ######   #     #     #     #     #  ######
--- #     #  #        #   # #  #        #   #    #######     #     #     #  #   #
--- #     #  #        #    ##  #        #    #   #     #     #     #     #  #    #
---  #####   #######  #     #  #######  #     #  #     #     #     #######  #     #

--------------------------------------------------
--- Version:    SierraTestdataGenerator 0.0.7-SNAPSHOT-r5660-b81 (11-10-2012 16:25.51)
--- Timestamp:  vrijdag 12 oktober 2012 8:31:23 uur CEST
--- Created by: Arnold
--- XLS file:   SierraTestdataCA.xls
--------------------------------------------------

--------------------------------------------------
--- Included: db/scenario_data_verwijderaar.sql
--------------------------------------------------

BEGIN;

TRUNCATE Kern.His_PersNation CASCADE;
TRUNCATE Kern.PersNation CASCADE;
TRUNCATE Kern.His_PersIndicatie CASCADE;
TRUNCATE Kern.PersIndicatie CASCADE;
TRUNCATE Kern.His_PersOpschorting CASCADE;
TRUNCATE Kern.His_PersAanschr CASCADE;
TRUNCATE Kern.His_PersGeslnaamComp CASCADE;
TRUNCATE Kern.PersGeslnaamComp CASCADE;
TRUNCATE Kern.His_PersVoornaam CASCADE;
TRUNCATE Kern.PersVoornaam CASCADE;
TRUNCATE Kern.His_PersBijhVerantwoordelijk CASCADE;


COMMIT;

BEGIN;

TRUNCATE Kern.His_BetrOuderschap CASCADE;
TRUNCATE Kern.Betr CASCADE;
TRUNCATE Kern.His_Relatie CASCADE;
TRUNCATE Kern.Relatie CASCADE;
TRUNCATE Kern.His_PersBijhGem CASCADE;
TRUNCATE Kern.His_PersAdres CASCADE;
TRUNCATE Kern.PersAdres CASCADE;
TRUNCATE Kern.His_PersOverlijden CASCADE;
TRUNCATE Kern.His_PersGeboorte CASCADE;
TRUNCATE Kern.His_PersSamengesteldeNaam CASCADE;
TRUNCATE Kern.His_PersGeslachtsaand CASCADE;
TRUNCATE Kern.His_PersIds CASCADE;
TRUNCATE Kern.Actie CASCADE;
TRUNCATE Kern.Pers CASCADE;

COMMIT;
--------------------------------------------------
--- Included: foreburner.sql
--------------------------------------------------

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
--------------------------------------------------
--- START Generated from: SierraTestdataCA.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: 0 'pers'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: pers
--------------------------------------------------

begin;

INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3001','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3002','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3003','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3004','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3005','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3006','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3007','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3008','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3009','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3010','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3011','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3012','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3013','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3014','1','A','A','A','A','A','A','X','X','A','A','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3015','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3016','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3017','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3018','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3019','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3020','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3021','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3022','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3023','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3024','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3025','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3026','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3027','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3028','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3029','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3030','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3031','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3032','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3033','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3034','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3035','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3036','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3037','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3038','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3039','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3040','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3041','1','A','A','A','A','A','X','X','X','A','A','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3042','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3043','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3044','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');
INSERT INTO kern.pers (id,srt,idsstatushis,geslachtsaandstatushis,samengesteldenaamstatushis,aanschrstatushis,geboortestatushis,overlijdenstatushis,uitslnlkiesrstatushis,euverkiezingenstatushis,bijhverantwoordelijkheidstat,opschortingstatushis,bijhgemstatushis,pkstatushis,inschrstatushis,tijdstiplaatstewijz,verblijfsrstatushis,immigratiestatushis) VALUES('3045','1','A','A','A','A','A','X','X','X','A','X','A','X','A','now()','X','X');

commit;


--------------------------------------------------
--- EIND
--- Sheet: 0 'pers'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 45
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 2 'brp actie'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: brp actie
--------------------------------------------------

begin;

INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3001','1','2000','19811002');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3002','1','2000','19820528');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3003','1','2000','19660302');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3004','1','2000','19691227');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3005','1','2000','19710717');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3006','1','2000','19720928');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3007','1','2000','19800616');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3008','1','2000','19810418');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3009','1','2000','20031226');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3010','1','2000','19691231');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3011','1','2000','19690516');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3012','1','2000','19771210');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3013','1','2000','19550602');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3014','1','2000','19780611');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3015','1','2000','19880425');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3016','1','2000','19881210');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3017','1','2000','19960523');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3018','1','2000','19941005');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3019','1','2000','19941005');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3020','1','2000','19760103');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3021','1','2000','19961003');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3022','1','2000','19951003');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3023','1','2000','19941003');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3024','1','2000','19770401');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3025','1','2000','19880611');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3026','1','2000','19910721');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3027','1','2000','19910911');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3028','1','2000','19900722');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3029','1','2000','19200430');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3030','1','2000','19220419');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3031','1','2000','19920219');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3032','1','2000','19510923');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3033','1','2000','19531116');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3034','1','2000','19390402');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3035','1','2000','19841203');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3036','1','2000','19851011');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3037','1','2000','20050112');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3038','1','2000','20051201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3039','1','2000','20050112');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3040','1','2000','20051201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3041','1','2000','19680809');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3042','1','2000','19580921');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3043','1','2000','19951004');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3044','1','2000','20051201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3045','1','2000','20050112');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3500','1','2000','20031226');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3501','1','2000','20030327');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3502','1','2000','19901111');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3503','1','2000','19970605');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3504','1','2000','20031024');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3505','1','2000','19960304');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3506','1','2000','20111111');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3507','1','2000','20050328');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3508','1','2000','20030327');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3509','1','2000','19811002');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3510','1','2000','20050328');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3511','1','2000','20030327');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3512','1','2000','19820528');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3513','1','2000','19901201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3514','1','2000','19901111');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3515','1','2000','19660302');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3516','1','2000','19901201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3517','1','2000','19901111');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3518','1','2000','19691227');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3519','1','2000','19981201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3520','1','2000','19970605');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3521','1','2000','19710717');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3522','1','2000','19981201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3523','1','2000','19970605');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3524','1','2000','19720928');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3525','1','2000','20110205');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3526','1','2000','20031024');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3527','1','2000','19800616');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3528','1','2000','20110205');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3529','1','2000','20031024');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3530','1','2000','19810418');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3531','1','2000','20110205');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3532','1','2000','20031226');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3533','1','2000','20090928');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3534','1','2000','19960304');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3535','1','2000','19691231');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3536','1','2000','20090928');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3537','1','2000','19960304');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3538','1','2000','19690516');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3539','1','2000','20000228');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3540','1','2000','19771210');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3541','1','2000','19920508');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3542','1','2000','19550602');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3543','1','2000','19780611');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3544','1','2000','20110425');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3545','1','2000','19880425');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3546','1','2000','20100626');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3547','1','2000','19880425');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3548','1','2000','18991210');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3549','1','2000','20030327');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3550','1','2000','19820523');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3551','1','2000','19911002');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3552','1','2000','19840411');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3553','1','2000','19901111');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3554','1','2000','19741026');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3555','1','2000','19820528');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3556','1','2000','19760103');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3557','1','2000','20010328');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3558','1','2000','19920516');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3559','1','2000','20101002');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3560','1','2000','19951110');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3561','1','2000','19970605');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3562','1','2000','19680310');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3563','1','2000','19790928');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3564','1','2000','19770401');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3565','1','2000','19950929');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3566','1','2000','19880611');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3567','1','2000','20000101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3568','1','2000','19910721');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3569','1','2000','20031024');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3570','1','2000','19910911');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3571','1','2000','19990616');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3572','1','2000','19900722');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3573','1','2000','20120620');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3574','1','2000','20091201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3575','1','2000','20060201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3576','1','2000','19220419');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3577','1','2000','20120306');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3578','1','2000','20010926');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3579','1','2000','20000827');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3580','1','2000','19920219');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3581','1','2000','20120621');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3582','1','2000','20010301');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3583','1','2000','19670601');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3584','1','2000','19510923');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3585','1','2000','20120621');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3586','1','2000','20010301');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3587','1','2000','19670601');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3588','1','2000','19531116');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3589','1','2000','20090701');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3590','1','2000','20080218');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3591','1','2000','20070406');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3592','1','2000','19390402');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3593','1','2000','20090301');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3594','1','2000','20081101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3595','1','2000','20050101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3596','1','2000','19841203');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3597','1','2000','20090301');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3598','1','2000','20081101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3599','1','2000','20050101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3600','1','2000','19851011');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3601','1','2000','20090301');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3602','1','2000','20081101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3603','1','2000','20050112');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3604','1','2000','20090301');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3605','1','2000','20081101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3606','1','2000','20051201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3607','1','2000','20120522');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3608','1','2000','20111201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3609','1','2000','20101201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3610','1','2000','19910315');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3611','1','2000','20010517');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3612','1','2000','20090201');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3613','1','2000','20060523');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3614','1','2000','20040819');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3615','1','2000','20010517');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3616','1','2000','20010328');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3617','1','2000','19580921');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3618','1','2000','19820528');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3619','1','2000','19590922');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3620','1','2000','20010328');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3621','1','2000','19601221');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3622','1','2000','19911002');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3623','1','2000','19611222');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3624','1','2000','19800101');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3625','1','2000','19900501');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3626','1','2000','19900722');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3627','1','2000','19910911');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3628','1','2000','20001022');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3629','1','2000','20050103');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3630','1','2000','19800610');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3631','1','2000','19810425');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3632','1','2000','19760701');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3633','1','2000','20060315');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3634','1','2000','20050112');
INSERT INTO kern.actie (id,srt,partij,tijdstipreg) VALUES('3635','1','2000','20051201');

commit;


--------------------------------------------------
--- EIND
--- Sheet: 2 'brp actie'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 181
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 3 'his_persids'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persids
--------------------------------------------------

begin;

INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3001','3001','19811002','19811002','3001','5143672754','527163703');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3002','3002','19820528','19820528','3002','8146315790','972220112');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3003','3003','19660302','19660302','3003','3925852957','743682683');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3004','3004','19691227','19691227','3004','7362592532','330006575');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3005','3005','19710717','19710717','3005','4035862817','168787726');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3006','3006','19720928','19720928','3006','4942819684','096478081');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3007','3007','19800616','19800616','3007','1202801305','015947713');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3008','3008','19810418','19810418','3008','3426213698','602361692');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3009','3009','20031226','20031226','3009','5051729816','298795474');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3010','3010','19691231','19691231','3010','1010162452','126477735');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3011','3011','19690516','19690516','3011','4071624360','064258099');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3012','3012','19771210','19771210','3012','9173658014','826610559');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3013','3013','19550602','19550602','3013','3040101490','422034344');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3014','3014','19780611','19780611','3014','3203098071','251716697');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3015','3015','19880425','19880425','3015','1659120893','189407955');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3016','3016','19881210','19881210','3016','8493093694','133707192');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3017','3017','19960523','19960523','3017','5805431585','961488347');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3018','3018','19941005','19941005','3018','1872520289','890160600');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3019','3019','19941005','19941005','3019','5041043745','661622174');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3020','3020','19760103','19760103','3020','7696386849','257046057');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3021','3021','19961003','19961003','3021','7965490465','196727315');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3022','3022','19951003','19951003','3022','6078409505','510833457');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3023','3023','19941003','19941003','3023','9075875617','639234239');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3024','3024','19770401','19770401','3024','6142019361','226658120');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3025','3025','19880611','19880611','3025','9639286561','054330373');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3026','3026','19910721','19910721','3026','2120978465','982011635');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3027','3027','19910911','19910911','3027','6176380193','754573102');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3028','3028','19900722','19900722','3028','6282345761','340997084');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3029','3029','19200430','19200430','3029','6282345761','278678348');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3030','3030','19220419','19220419','3030','1375602497','865003129');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3031','3031','19920219','19920219','3031','2808956321','603784483');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3032','3032','19510923','19510923','3032','1530896705','061660735');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3033','3033','19531116','19531116','3033','6245265185','999341091');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3034','3034','19390402','19390402','3034','4175803169','827023340');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3035','3035','19841203','19841203','3035','5151579425','699585818');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3036','3036','19851011','19851011','3036','6780497185','285909691');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3037','3037','20050112','20050112','3037','8379307297','600014733');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3038','3038','20051201','20051201','3038','2169713825','548795095');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3039','3039','20050112','20050112','3039','6861308705','557264728');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3040','3040','20051201','20051201','3040','9342160161','153698603');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3041','3041','19680809','19680809','3041','8250270497','082370965');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3042','3042','19580921','19580921','3042','4782084641','910051124');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3043','3043','19951004','19951004','3043','9823847201','781513686');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3044','3044','20051201','20051201','3044','5278395169','378937571');
INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn) VALUES('3045','3045','20050112','20050112','3045','7951861025','106619822');










commit;


--------------------------------------------------
--- EIND
--- Sheet: 3 'his_persids'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 54
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 4 'his_persgeslachtsaand'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persgeslachtsaand
--------------------------------------------------

begin;

INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3001','3001','19811002',null,'19811002',null,'3001',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3002','3002','19820528',null,'19820528',null,'3002',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3003','3003','19660302',null,'19660302',null,'3003',null,null,'3');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3004','3004','19691227',null,'19691227',null,'3004',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3005','3005','19710717',null,'19710717',null,'3005',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3006','3006','19720928',null,'19720928',null,'3006',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3007','3007','19800616',null,'19800616',null,'3007',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3008','3008','19810418',null,'19810418',null,'3008',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3009','3009','20031226',null,'20031226',null,'3009',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3010','3010','19691231',null,'19691231',null,'3010',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3011','3011','19690516',null,'19690516',null,'3011',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3012','3012','19771210',null,'19771210',null,'3012',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3013','3013','19550602',null,'19550602',null,'3013',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3014','3014','19780611',null,'19780611',null,'3014',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3015','3015','19880425',null,'19880425',null,'3015',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3016','3016','19881210',null,'19881210',null,'3016',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3017','3017','19960523',null,'19960523',null,'3017',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3018','3018','19941005',null,'19941005',null,'3018',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3019','3019','19941005',null,'19941005',null,'3019',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3020','3020','19760103',null,'19760103',null,'3020',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3021','3021','19961003',null,'19961003',null,'3021',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3022','3022','19951003',null,'19951003',null,'3022',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3023','3023','19941003',null,'19941003',null,'3023',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3024','3024','19770401',null,'19770401',null,'3024',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3025','3025','19880611',null,'19880611',null,'3025',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3026','3026','19910721',null,'19910721',null,'3026',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3027','3027','19910911',null,'19910911',null,'3027',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3028','3028','19900722',null,'19900722',null,'3028',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3029','3029','19200430',null,'19200430',null,'3029',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3030','3030','19220419',null,'19220419',null,'3030',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3031','3031','19920219',null,'19920219',null,'3031',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3032','3032','19510923',null,'19510923',null,'3032',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3033','3033','19531116',null,'19531116',null,'3033',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3034','3034','19390402',null,'19390402',null,'3034',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3035','3035','19841203',null,'19841203',null,'3035',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3036','3036','19851011',null,'19851011',null,'3036',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3037','3037','20050112',null,'20050112',null,'3037',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3038','3038','20051201',null,'20051201',null,'3038',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3039','3039','20050112',null,'20050112',null,'3039',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3040','3040','20051201',null,'20051201',null,'3040',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3041','3041','19680809',null,'19680809',null,'3041',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3042','3042','19580921',null,'19580921',null,'3042',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3043','3043','19951004',null,'19951004',null,'3043',null,null,'2');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3044','3044','20051201',null,'20051201',null,'3044',null,null,'1');
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand) VALUES('3045','3045','20050112',null,'20050112',null,'3045',null,null,'2');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 4 'his_persgeslachtsaand'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 46
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 5 'persgeslnaamcomp'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: persgeslnaamcomp
--------------------------------------------------

begin;

INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3001','3001',null,'-','Vlag',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3002','3002','de',null,'Prins',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3003','3003','van der','-','Vermeer',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3004','3004',null,'-','Crooy',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3005','3005',null,'-','Laar',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3006','3006',null,'-','Verheul',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3007','3007',null,'-','Vermeulen',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3008','3008',null,'-','Jansma',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3009','3009',null,'-','Jansma',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3010','3010','de','-','Bloëmsma',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3011','3011','de','-','Snijder',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3012','3012',null,'-','Cuykelaer',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3013','3013','van','-','Roos',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3014','3014',null,'-','Moes',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3015','3015',null,'-','Brest naar Kempen',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3016','3016',null,'-','Ophuijsen',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3017','3017',null,'-','Addams',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3018','3018',null,'-','Verhoeven',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3019','3019','de','-','Slot',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3020','3020','van','-','Guirten',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3021','3021','ter','-','Stassen',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3022','3022','van','-','Zon',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3023','3023','van','-','Verhaar',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3024','3024','de','-','Klaassen',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3025','3025',null,'-','Valstar',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3026','3026','ter','-','Botje',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3027','3027',null,'-','Karlson',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3028','3028','van','-','Hoog',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3029','3029',null,'-','Karlson',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3030','3030','de','-','Vlonder',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3031','3031','de','-','Zatte',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3032','3032',null,'-','Stuurman',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3033','3033','de','-','Vriend',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3034','3034','de','-','Renner',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3035','3035','de','-','Muurbloem',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3036','3036',null,'-','Appelo',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3037','3037',null,'-','Appel',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3038','3038','ter','-','Aar',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3039','3039','in het','-','Veld',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3040','3040','van','-','Penderloo',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3041','3041','de','-','Bakker',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3042','3042',null,'-','Karlson',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3043','3043','ter','-','Nater',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3044','3044','van','-','Hoog',null,null,'A','1');
INSERT INTO kern.persgeslnaamcomp (id                    ,pers,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ,persgeslnaamcompstatushis,volgnr          ) VALUES('3045','3045','de','-','Hoop',null,null,'A','1');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 5 'persgeslnaamcomp'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 46
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 6 'his_persgeslnaamcomp'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persgeslnaamcomp
--------------------------------------------------

begin;

INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3001','3001','19811002',null,'19811002',null,'3001',null,null,null,'-','Vlag',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3002','3002','19820528',null,'19820528',null,'3002',null,null,'de',null,'Prins',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3003','3003','19660302',null,'19660302',null,'3003',null,null,'van der','-','Vermeer',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3004','3004','19691227',null,'19691227',null,'3004',null,null,null,'-','Crooy',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3005','3005','19710717',null,'19710717',null,'3005',null,null,null,'-','Laar',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3006','3006','19720928',null,'19720928',null,'3006',null,null,null,'-','Verheul',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3007','3007','19800616',null,'19800616',null,'3007',null,null,null,'-','Vermeulen',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3008','3008','19810418',null,'19810418',null,'3008',null,null,null,'-','Jansma',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3009','3009','20031226',null,'20031226',null,'3009',null,null,null,'-','Jansma',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3010','3010','19691231',null,'19691231',null,'3010',null,null,'de','-','Bloëmsma',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3011','3011','19690516',null,'19690516',null,'3011',null,null,'de','-','Snijder',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3012','3012','19771210',null,'19771210',null,'3012',null,null,null,'-','Cuykelaer',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3013','3013','19550602',null,'19550602',null,'3013',null,null,'van','-','Roos',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3014','3014','19780611',null,'19780611',null,'3014',null,null,null,'-','Moes',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3015','3015','19880425',null,'19880425',null,'3015',null,null,null,'-','Brest naar Kempen',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3016','3016','19881210',null,'19881210',null,'3016',null,null,null,'-','Ophuijsen',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3017','3017','19960523',null,'19960523',null,'3017',null,null,null,'-','Addams',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3018','3018','19941005',null,'19941005',null,'3018',null,null,null,'-','Verhoeven',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3019','3019','19941005',null,'19941005',null,'3019',null,null,'de','-','Slot',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3020','3020','19760103',null,'19760103',null,'3020',null,null,'van','-','Guirten',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3021','3021','19961003',null,'19961003',null,'3021',null,null,'ter','-','Stassen',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3022','3022','19951003',null,'19951003',null,'3022',null,null,'van','-','Zon',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3023','3023','19941003',null,'19941003',null,'3023',null,null,'van','-','Verhaar',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3024','3024','19770401',null,'19770401',null,'3024',null,null,'de','-','Klaassen',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3025','3025','19880611',null,'19880611',null,'3025',null,null,null,'-','Valstar',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3026','3026','19910721',null,'19910721',null,'3026',null,null,'ter','-','Botje',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3027','3027','19910911',null,'19910911',null,'3027',null,null,null,'-','Karlson',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3028','3028','19900722','20001022','19900722',null,'3028',null,null,'van','-','Hoog',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3029','3029','19200430',null,'19200430',null,'3029',null,null,null,'-','Karlson',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3030','3030','19220419',null,'19220419',null,'3030',null,null,'de','-','Vlonder',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3031','3031','19920219',null,'19920219',null,'3031',null,null,'de','-','Zatte',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3032','3032','19510923',null,'19510923',null,'3032',null,null,null,'-','Stuurman',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3033','3033','19531116',null,'19531116',null,'3033',null,null,'de','-','Vriend',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3034','3034','19390402',null,'19390402',null,'3034',null,null,'de','-','Renner',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3035','3035','19841203',null,'19841203',null,'3035',null,null,'de','-','Muurbloem',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3036','3036','19851011',null,'19851011',null,'3036',null,null,null,'-','Appelo',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3037','3037','20050112',null,'20050112',null,'3037',null,null,null,'-','Appel',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3038','3038','20051201',null,'20051201',null,'3038',null,null,'ter','-','Aar',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3039','3039','20050112',null,'20050112',null,'3039',null,null,'in het','-','Veld',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3040','3040','20051201',null,'20051201',null,'3040',null,null,'van','-','Penderloo',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3041','3041','19680809',null,'19680809',null,'3041',null,null,'de','-','Bakker',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3042','3042','19580921',null,'19580921',null,'3042',null,null,null,'-','Karlson',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3043','3043','19951004',null,'19951004',null,'3043',null,null,'ter','-','Nater',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3044','3044','20051201',null,'20051201',null,'3044',null,null,'van','-','Hoog',null,null);
INSERT INTO kern.his_persgeslnaamcomp (id                    ,persgeslnaamcomp    ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,voorvoegsel         ,scheidingsteken     ,naam                ,predikaat           ,adellijketitel      ) VALUES('3045','3045','20050112',null,'20050112',null,'3045',null,null,'de','-','Hoop',null,null);


commit;


--------------------------------------------------
--- EIND
--- Sheet: 6 'his_persgeslnaamcomp'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 46
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 7 'his_perssamengesteldenaam'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_perssamengesteldenaam
--------------------------------------------------

begin;

INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3001','3001','19811002',null,'19811002',null,'3001',null,null,null,'Cees',null,'-',null,'Vlag','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3002','3002','19820528',null,'19820528',null,'3002',null,null,null,'Paula','de',null,null,'Prins','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3003','3003','19660302',null,'19660302',null,'3003',null,null,null,'Christoffel','van der','-',null,'Vermeer','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3004','3004','19691227',null,'19691227',null,'3004',null,null,null,'Eleonora Françoise',null,'-',null,'Crooy','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3005','3005','19710717',null,'19710717',null,'3005',null,null,null,'Albert',null,'-',null,'Laar','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3006','3006','19720928',null,'19720928',null,'3006',null,null,null,'Anne-Marie',null,'-',null,'Verheul','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3007','3007','19800616',null,'19800616',null,'3007',null,null,null,'Henk',null,'-',null,'Vermeulen','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3008','3008','19810418',null,'19810418',null,'3008',null,null,null,'Anna Maria Petra',null,'-',null,'Jansma','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3009','3009','20031226',null,'20031226',null,'3009',null,null,null,'Miranda',null,'-',null,'Jansma','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3010','3010','19691231',null,'19691231',null,'3010',null,null,null,'John','de','-',null,'Bloëmsma','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3011','3011','19690516',null,'19690516',null,'3011',null,null,null,'Dorothea','de','-',null,'Snijder','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3012','3012','19771210',null,'19771210',null,'3012',null,null,null,'Bas',null,'-',null,'Cuykelaer','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3013','3013','19550602',null,'19550602',null,'3013',null,null,null,'Hendrika Isabella Gerarda Johanna','van','-',null,'Roos','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3014','3014','19780611',null,'19780611',null,'3014',null,null,null,'Jan',null,'-',null,'Moes','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3015','3015','19880425',null,'19880425',null,'3015',null,null,null,'Petronella',null,'-',null,'Brest naar Kempen','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3016','3016','19881210',null,'19881210',null,'3016',null,null,null,'Maria Christina',null,'-',null,'Ophuijsen','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3017','3017','19960523',null,'19960523',null,'3017',null,null,null,'Pieter',null,'-',null,'Addams','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3018','3018','19941005',null,'19941005',null,'3018',null,null,null,'Petronellaatje',null,'-',null,'Verhoeven','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3019','3019','19941005',null,'19941005',null,'3019',null,null,null,'Sanne','de','-',null,'Slot','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3020','3020','19760103',null,'19760103',null,'3020',null,null,null,'Hanna','van','-',null,'Guirten','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3021','3021','19961003',null,'19961003',null,'3021',null,null,null,'Sander','ter','-',null,'Stassen','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3022','3022','19951003',null,'19951003',null,'3022',null,null,null,'Ellen','van','-',null,'Zon','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3023','3023','19941003',null,'19941003',null,'3023',null,null,null,'Christian','van','-',null,'Verhaar','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3024','3024','19770401',null,'19770401',null,'3024',null,null,null,'Anja','de','-',null,'Klaassen','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3025','3025','19880611',null,'19880611',null,'3025',null,null,null,'Karel',null,'-',null,'Valstar','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3026','3026','19910721',null,'19910721',null,'3026',null,null,null,'Helen','ter','-',null,'Botje','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3027','3027','19910911',null,'19910911',null,'3027',null,null,null,'Sander',null,'-',null,'Karlson','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3028','3028','19900722','20001022','19900722',null,'3028',null,null,null,'Violet','van','-',null,'Hoog','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3029','3029','19200430',null,'19200430',null,'3029',null,null,null,'Violetta',null,'-',null,'Karlson','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3030','3030','19220419',null,'19220419',null,'3030',null,null,null,'Tanja','de','-',null,'Vlonder','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3031','3031','19920219',null,'19920219',null,'3031',null,null,null,'Berend','de','-',null,'Zatte','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3032','3032','19510923',null,'19510923',null,'3032',null,null,null,'Mike',null,'-',null,'Stuurman','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3033','3033','19531116',null,'19531116',null,'3033',null,null,null,'Sanne','de','-',null,'Vriend','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3034','3034','19390402',null,'19390402',null,'3034',null,null,null,'Josje','de','-',null,'Renner','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3035','3035','19841203',null,'19841203',null,'3035',null,null,null,'Anna','de','-',null,'Muurbloem','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3036','3036','19851011',null,'19851011',null,'3036',null,null,null,'Gerard',null,'-',null,'Appelo','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3037','3037','20050112',null,'20050112',null,'3037',null,null,null,'Albert',null,'-',null,'Appel','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3038','3038','20051201',null,'20051201',null,'3038',null,null,null,'Marijke','ter','-',null,'Aar','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3039','3039','20050112',null,'20050112',null,'3039',null,null,null,'Geerte','in het','-',null,'Veld','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3040','3040','20051201',null,'20051201',null,'3040',null,null,null,'Gert','van','-',null,'Penderloo','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3041','3041','19680809',null,'19680809',null,'3041',null,null,null,'Gijs','de','-',null,'Bakker','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3042','3042','19580921',null,'19580921',null,'3042',null,null,null,'Guido',null,'-',null,'Karlson','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3043','3043','19951004',null,'19951004',null,'3043',null,null,null,'Annemieke','ter','-',null,'Nater','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3044','3044','20051201',null,'20051201',null,'3044',null,null,null,'Maarten','van','-',null,'Hoog','false','true');
INSERT INTO kern.his_perssamengesteldenaam (id                    ,pers                    ,dataanvgel              ,dateindegel             ,tsreg                   ,tsverval                ,actieinh                ,actieverval             ,actieaanpgel            ,predikaat               ,voornamen               ,voorvoegsel             ,scheidingsteken         ,adellijketitel          ,geslnaam                ,indnreeksalsgeslnaam    ,indalgoritmischafgeleid ) VALUES('3045','3045','20050112',null,'20050112',null,'3045',null,null,null,'Marion','de','-',null,'Hoop','false','true');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 7 'his_perssamengesteldenaam'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 46
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 8 'his_persaanschr'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persaanschr
--------------------------------------------------

begin;

INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3001','3001','19811002',null,'19811002',null,'3001',null,null,null,'false','true',null,'Cees',null,'-',null,'Vlag');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3002','3002','19820528',null,'19820528',null,'3002',null,null,null,'false','true',null,'Paula','de',null,null,'Prins');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3003','3003','19660302',null,'19660302',null,'3003',null,null,null,'false','true',null,'Christoffel','van der','-',null,'Vermeer');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3004','3004','19691227',null,'19691227',null,'3004',null,null,null,'false','true',null,'Eleonora Françoise',null,'-',null,'Crooy');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3005','3005','19710717',null,'19710717',null,'3005',null,null,null,'false','true',null,'Albert',null,'-',null,'Laar');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3006','3006','19720928',null,'19720928',null,'3006',null,null,null,'false','true',null,'Anne-Marie',null,'-',null,'Verheul');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3007','3007','19800616',null,'19800616',null,'3007',null,null,null,'false','true',null,'Henk',null,'-',null,'Vermeulen');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3008','3008','19810418',null,'19810418',null,'3008',null,null,null,'false','true',null,'Anna Maria Petra',null,'-',null,'Jansma');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3009','3009','20031226',null,'20031226',null,'3009',null,null,null,'false','true',null,'Miranda',null,'-',null,'Jansma');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3010','3010','19691231',null,'19691231',null,'3010',null,null,null,'false','true',null,'John','de','-',null,'Bloëmsma');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3011','3011','19690516',null,'19690516',null,'3011',null,null,null,'false','true',null,'Dorothea','de','-',null,'Snijder');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3012','3012','19771210',null,'19771210',null,'3012',null,null,null,'false','true',null,'Bas',null,'-',null,'Cuykelaer');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3013','3013','19550602',null,'19550602',null,'3013',null,null,null,'false','true',null,'Hendrika Isabella Gerarda Johanna','van','-',null,'Roos');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3014','3014','19780611',null,'19780611',null,'3014',null,null,null,'false','true',null,'Jan',null,'-',null,'Moes');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3015','3015','19880425',null,'19880425',null,'3015',null,null,null,'false','true',null,'Petronella',null,'-',null,'Brest naar Kempen');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3016','3016','19881210',null,'19881210',null,'3016',null,null,null,'false','true',null,'Maria Christina',null,'-',null,'Ophuijsen');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3017','3017','19960523',null,'19960523',null,'3017',null,null,null,'false','true',null,'Pieter',null,'-',null,'Addams');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3018','3018','19941005',null,'19941005',null,'3018',null,null,null,'false','true',null,'Petronellaatje',null,'-',null,'Verhoeven');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3019','3019','19941005',null,'19941005',null,'3019',null,null,null,'false','true',null,'Sanne','de','-',null,'Slot');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3020','3020','19760103',null,'19760103',null,'3020',null,null,null,'false','true',null,'Hanna','van','-',null,'Guirten');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3021','3021','19961003',null,'19961003',null,'3021',null,null,null,'false','true',null,'Sander','ter','-',null,'Stassen');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3022','3022','19951003',null,'19951003',null,'3022',null,null,null,'false','true',null,'Ellen','van','-',null,'Zon');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3023','3023','19941003',null,'19941003',null,'3023',null,null,null,'false','true',null,'Christian','van','-',null,'Verhaar');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3024','3024','19770401',null,'19770401',null,'3024',null,null,null,'false','true',null,'Anja','de','-',null,'Klaassen');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3025','3025','19880611',null,'19880611',null,'3025',null,null,null,'false','true',null,'Karel',null,'-',null,'Valstar');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3026','3026','19910721',null,'19910721',null,'3026',null,null,null,'false','true',null,'Helen','ter','-',null,'Botje');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3027','3027','19910911',null,'19910911',null,'3027',null,null,null,'false','true',null,'Sander',null,'-',null,'Karlson');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3028','3028','19900722','20001022','19900722',null,'3028',null,null,null,'false','true',null,'Violet','van','-',null,'Hoog');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3029','3029','19200430',null,'19200430',null,'3029',null,null,null,'false','true',null,'Violet',null,'-',null,'Karlson');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3030','3030','19220419',null,'19220419',null,'3030',null,null,null,'false','true',null,'Tanja','de','-',null,'Vlonder');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3031','3031','19920219',null,'19920219',null,'3031',null,null,null,'false','true',null,'Berend','de','-',null,'Zatte');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3032','3032','19510923',null,'19510923',null,'3032',null,null,null,'false','true',null,'Mike',null,'-',null,'Stuurman');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3033','3033','19531116',null,'19531116',null,'3033',null,null,null,'false','true',null,'Sanne','de','-',null,'Vriend');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3034','3034','19390402',null,'19390402',null,'3034',null,null,null,'false','true',null,'Josje','de','-',null,'Renner');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3035','3035','19841203',null,'19841203',null,'3035',null,null,null,'false','true',null,'Anna','de','-',null,'Muurbloem');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3036','3036','19851011',null,'19851011',null,'3036',null,null,null,'false','true',null,'Gerard',null,'-',null,'Appelo');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3037','3037','20050112',null,'20050112',null,'3037',null,null,null,'false','true',null,'Albert',null,'-',null,'Appel');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3038','3038','20051201',null,'20051201',null,'3038',null,null,null,'false','true',null,'Marijke','ter','-',null,'Aar');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3039','3039','20050112',null,'20050112',null,'3039',null,null,null,'false','true',null,'Geerte','in het','-',null,'Veld');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3040','3040','20051201',null,'20051201',null,'3040',null,null,null,'false','true',null,'Gert','van','-',null,'Penderloo');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3041','3041','19680809',null,'19680809',null,'3041',null,null,null,'false','true',null,'Gijs','de','-',null,'Bakker');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3042','3042','19580921',null,'19580921',null,'3042',null,null,null,'false','true',null,'Guido',null,'-',null,'Karlson');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3043','3043','19951004',null,'19951004',null,'3043',null,null,null,'false','true',null,'Annemieke','ter','-',null,'Nater');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3044','3044','20051201',null,'20051201',null,'3044',null,null,null,'false','true',null,'Maarten','van','-',null,'Hoog');
INSERT INTO kern.his_persaanschr (id                          ,pers                        ,dataanvgel                  ,dateindegel                 ,tsreg                       ,tsverval                    ,actieinh                    ,actieverval                 ,actieaanpgel                ,gebrgeslnaamegp             ,indaanschrmetadellijketitels,indaanschralgoritmischafgele,predikaataanschr            ,voornamenaanschr            ,voorvoegselaanschr          ,scheidingstekenaanschr      ,adellijketitelaanschr       ,geslnaamaanschr             ) VALUES('3045','3045','20050112',null,'20050112',null,'3045',null,null,null,'false','true',null,'Marion','de','-',null,'Hoop');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 8 'his_persaanschr'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 46
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 9 'his_persgeboorte'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persgeboorte
--------------------------------------------------

begin;

INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3001','3001','19811002',null,'3001',null,'19811002','522','1657',null,null,'229','omschrijving geboorte locatie Cees');
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3002','3002','19820528',null,'3002',null,'19820528','522','1657',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3003','3003','19660302',null,'3003',null,'19660302','522','1657',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3004','3004','19691227',null,'3004',null,'19691227','950','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3005','3005','19710717',null,'3005',null,'19710717','348','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3006','3006','19720928',null,'3006',null,'19720928','522','1657',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3007','3007','19800616',null,'3007',null,'19800616','603','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3008','3008','19810418',null,'3008',null,'19810418','974','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3009','3009','20031226',null,'3009',null,'20031226','348','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3010','3010','19691231',null,'3010',null,'19691231','776','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3011','3011','19690516',null,'3011',null,'19690516','397','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3012','3012','19771210',null,'3012',null,'19771210','397','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3013','3013','19550602',null,'3013',null,'19550602','396','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3014','3014','19780611',null,'3014',null,'19780611','251','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3015','3015','19880425',null,'3015',null,'19880425','522','1657',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3016','3016','19881210',null,'3016',null,'19881210','1267','228',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3017','3017','19960523',null,'3017',null,'19960523','91','1426',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3018','3018','19941005',null,'3018',null,'19941005','780','11',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3019','3019','19941005',null,'3019',null,'19941005','615','2028',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3020','3020','19760103',null,'3020',null,'19760103','625','16',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3021','3021','19961003',null,'3021',null,'19961003','345','14',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3022','3022','19951003',null,'3022',null,'19951003','625','16',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3023','3023','19941003',null,'3023',null,'19941003','365','42',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3024','3024','19770401',null,'3024',null,'19770401','365','42',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3025','3025','19880611',null,'3025',null,'19880611','91','1426',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3026','3026','19910721',null,'3026',null,'19910721','365','42',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3027','3027','19910911',null,'3027',null,'19910911','625','16',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3028','3028','19900722',null,'3028',null,'19900722','625','16',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3029','3029','19200430',null,'3029',null,'19200430','1239','1685',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3030','3030','19220419',null,'3030',null,'19220419','1239','1685',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3031','3031','19920219',null,'3031',null,'19920219','977','1043',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3032','3032','19510923',null,'3032',null,'19510923','522','1657',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3033','3033','19531116',null,'3033',null,'19531116','506','1136',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3034','3034','19390402',null,'3034',null,'19390402','403','2051',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3035','3035','19841203',null,'3035',null,'19841203','342','1139',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3036','3036','19851011',null,'3036',null,'19851011','342','1139',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3037','3037','20050112',null,'3037',null,'20050112','91','1426',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3038','3038','20051201',null,'3038',null,'20051201','96','1612',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3039','3039','20050112',null,'3039',null,'20050112','63','1581',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3040','3040','20051201',null,'3040',null,'20051201','99','2293',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3041','3041','19680809',null,'3041',null,'19680809','99','2293',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3042','3042','19580921',null,'3042',null,'19580921','91','1426',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3043','3043','19951004',null,'3043',null,'19951004','91','1426',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3044','3044','20051201',null,'3044',null,'20051201','365','42',null,null,'229',null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats ,blregiogeboorte  ,landgeboorte,omsgeboorteloc) VALUES('3045','3045','20050112',null,'3045',null,'20050112','365','42',null,null,'229',null);


commit;


--------------------------------------------------
--- EIND
--- Sheet: 9 'his_persgeboorte'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 46
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 10 'his_persoverlijden'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persoverlijden
--------------------------------------------------

begin;

INSERT INTO kern.his_persoverlijden (id,pers,tsreg,tsverval,actieinh,actieverval,datoverlijden,gemoverlijden,wploverlijden        ,blplaatsoverlijden   ,blregiooverlijden    ,landoverlijden,omslocoverlijden     ) VALUES('3001','3014','20111111',null,'3506',null,'20111111','397','1596',null,null,'229',null);


commit;


--------------------------------------------------
--- EIND
--- Sheet: 10 'his_persoverlijden'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 2
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 12 'persadres'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: persadres
--------------------------------------------------

begin;

INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3001','3001','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3002','3002','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3003','3003','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3004','3004','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3005','3005','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3006','3006','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3007','3007','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3008','3008','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3009','3009','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3010','3010','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3011','3011','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3012','3012','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3013','3013','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3014','3014','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3015','3015','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3016','3016','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3017','3017','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3018','3018','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3019','3019','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3020','3020','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3021','3021','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3022','3022','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3023','3023','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3024','3024','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3025','3025','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3026','3026','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3027','3027','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3028','3028','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3030','3030','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3031','3031','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3032','3032','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3033','3033','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3034','3034','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3035','3035','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3036','3036','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3037','3037','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3038','3038','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3039','3039','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3040','3040','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3041','3041','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3042','3042','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3043','3043','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3044','3044','A');
INSERT INTO kern.persadres (id,pers,persadresstatushis) VALUES('3045','3045','A');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 12 'persadres'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 45
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 13 'his_persadres'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persadres
--------------------------------------------------

begin;

INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3507','3001','20050328',null,'20050328',null,'3507',null,null,'1','1','7','20050328',null,null,'347','Vredenburg','Vredenburg',null,'3',null,null,'3511BA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3508','3001','20030327','20050328','20030327',null,'3508',null,'3507','1','1','3','20030327',null,null,'229','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3509','3001','19811002','20030327','19811002',null,'3509',null,'3508','1','2',null,'19811002',null,null,'521','Langnekstraat','Langnekstraat',null,'9',null,null,'2572JN',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3510','3002','20050328',null,'20050328',null,'3510',null,null,'1','1','3','20050328',null,null,'347','Vredenburg','Vredenburg',null,'3',null,null,'3511BA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3511','3002','20030327','20050328','20030327',null,'3511',null,'3510','1','1','7','20030327',null,null,'229','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3512','3002','19820528','20030327','19820528',null,'3512',null,'3511','1','2',null,'19820528',null,null,'602','Stuart Millstraat','Stuart Millstraat',null,'24',null,null,'2554AJ',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3513','3003','19901201',null,'19901201',null,'3513',null,null,'1','1','3','19901201',null,null,'347','Neude','Neude',null,'11',null,null,'3512AE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3514','3003','19901111','19901201','19901111',null,'3514',null,'3513','1','1','3','19901111',null,null,'950','Achillesstraat','Achillesstraat',null,'34',null,null,'6165RA',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3515','3003','19660302','19901111','19660302',null,'3515',null,'3514','1','2',null,'19660302',null,null,'521','Langnekstraat','Langnekstraat',null,'7',null,null,'2572JN',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3516','3004','19901201',null,'19901201',null,'3516',null,null,'1','1','7','19901201',null,null,'347','Neude','Neude',null,'13',null,null,'3512AE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3517','3004','19901111','19901201','19901111',null,'3517',null,'3516','1','1','7','19901111',null,null,'950','Achillesstraat','Achillesstraat',null,'34',null,null,'6165RA',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3518','3004','19691227','19901111','19691227',null,'3518',null,'3517','1','2',null,'19691227',null,null,'347','Rhônedreef','Rhônedreef',null,'45',null,null,'3561VA',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3519','3005','19981201',null,'19981201',null,'3519',null,null,'1','1','3','19981201',null,null,'347','Biltstraat','Biltstraat',null,'5',null,null,'3572AA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3520','3005','19970605','19981201','19970605',null,'3520',null,'3519','1','1','7','19970605',null,null,'403','Anthonij Miechielsz van Voorthuijsenstraat','A M v Voorthuijsenstr',null,'67',null,null,'1785LG',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3521','3005','19710717','19970605','19710717',null,'3521',null,'3520','1','2',null,'19710717',null,null,'347','Westduëlstraat','Westduëlstraat',null,'23',null,null,'3082RV',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3522','3006','19981201',null,'19981201',null,'3522',null,null,'1','1','7','19981201',null,null,'347','Biltstraat','Biltstraat',null,'5',null,null,'3572AA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3523','3006','19970605','19981201','19970605',null,'3523',null,'3522','1','1','3','19970605',null,null,'403','Anthonij Miechielsz van Voorthuijsenstraat','A M v Voorthuijsenstr',null,'67',null,null,'1785LG',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3524','3006','19720928','19970605','19720928',null,'3524',null,'3523','1','2',null,'19720928',null,null,'366','Bonairestraat','Bonairestraat',null,'23',null,null,'1058XC',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3525','3007','20110205',null,'20110205',null,'3525',null,null,'1','1','7','20110205',null,null,'347','Vredenburg','Vredenburg',null,'22',null,null,'3511BB','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3526','3007','20031024','20110205','20031024',null,'3526',null,'3525','1','1','7','20031024',null,null,'1415','Rafaëlweg','Rafaëlweg',null,'8',null,null,'6114BZ',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3527','3007','19800616','20031024','19800616',null,'3527',null,'3526','1','2',null,'19800616',null,null,'602','Jan Steenstraat','Jan Steenstraat',null,'132',null,null,'3042AL',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3528','3008','20110205',null,'20110205',null,'3528',null,null,'1','1','3','20110205',null,null,'347','Vredenburg','Vredenburg',null,'22',null,null,'3511BB','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3529','3008','20031024','20110205','20031024',null,'3529',null,'3528','1','1','3','20031024',null,null,'1415','Rafaëlweg','Rafaëlweg',null,'8',null,null,'6114BZ',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3530','3008','19810418','20031024','19810418',null,'3530',null,'3529','1','2',null,'19810418',null,null,'347','Westerkade','Westerkade',null,'412',null,null,'3511HB',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3531','3009','20110205',null,'20110205',null,'3531',null,null,'1','1','6','20110205',null,null,'347','Vredenburg','Vredenburg',null,'22',null,null,'3511BB','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3532','3009','20031226','20110205','20031226',null,'3532',null,'3531','1','2',null,'20031226',null,null,'1415','Rafaëlweg','Rafaëlweg',null,'7',null,null,'6114BZ',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3533','3010','20090928',null,'20090928',null,'3533',null,null,'1','1','3','20090928',null,null,'347','Neude','Neude',null,'17',null,null,'3512AE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3534','3010','19960304','20090928','19960304',null,'3534',null,'3533','1','1','3','19960304',null,null,'775','Beatrix de Rijkweg','Beatrix de Rijkweg',null,'5',null,null,'5657EG',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3535','3010','19691231','19960304','19691231',null,'3535',null,'3534','1','2',null,'19691231',null,null,'602','Goudsesingel','Goudsesingel',null,'7',null,null,'3011KD',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3536','3011','20090928',null,'20090928',null,'3536',null,null,'1','1','7','20090928',null,null,'347','Neude','Neude',null,'17',null,null,'3512AE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3537','3011','19960304','20090928','19960304',null,'3537',null,'3536','1','1','7','19960304',null,null,'775','Beatrix de Rijkweg','Beatrix de Rijkweg',null,'5',null,null,'5657EG',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3538','3011','19690516','19960304','19690516',null,'3538',null,'3537','1','2',null,'19690516',null,null,'602','Goudsesingel','Goudsesingel',null,'9',null,null,'3011KD',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3539','3012','20000228',null,'20000228',null,'3539',null,null,'1','1','3','20000228',null,null,'347','Biltstraat','Biltstraat',null,'7',null,null,'3572AA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3540','3012','19771210','20000228','19771210',null,'3540',null,'3539','1','2',null,'19771210',null,null,'629','Baron Schimmelpenninck van der Oyelaan','S vd Oyeln',null,'1',null,null,'2252EB',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3541','3013','19920508',null,'19920508',null,'3541',null,null,'1','1','3','19920508',null,null,'347','Vredenburg','Vredenburg',null,'27',null,null,'3511BC','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3542','3013','19550602','19920508','19550602',null,'3542',null,'3541','1','2',null,'19550602',null,null,'991','Weerterbeekweg','Weerterbeekweg',null,'22',null,null,'6001VH',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3543','3014','19780611',null,'19780611',null,'3543',null,null,'1','2',null,'19780611',null,null,'347','Neude','Neude',null,'19',null,null,'3512AD','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3544','3015','20110425',null,'20110425',null,'3544',null,null,'1','1','3','20110425',null,null,'347','Vredenburg','Vredenburg',null,'3',null,null,'3511BA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3545','3015','19880425','20110425','19880425',null,'3545',null,'3544','1','2',null,'19880425',null,null,'94','Franekervaart','Franekervaart',null,'22',null,null,'8602AA',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3546','3016','20100626',null,'20100626',null,'3546',null,null,'1','1','3','20100626',null,null,'347','Biltstraat','Biltstraat',null,'5',null,null,'3572AA','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3547','3016','19880425','20100626','19880425',null,'3547',null,'3546','1','1','3','19880425',null,null,'741','Raadhuisstraat','Raadhuisstraat',null,'1',null,null,'4266EB',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3548','3016','18991210','19880425','18991210',null,'3548',null,'3547','1','2',null,'18991210',null,null,'521','Nieuwe Parklaan','Nieuwe Parklaan',null,'56',null,null,'2597LD',null,null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3549','3017','20030327',null,'20030327',null,'3549',null,null,'1','1','3','20030327',null,null,'347','Biltstraat','Biltstraat',null,'152',null,null,'3572BM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3550','3017','19820523','20030327','19820523',null,'3550',null,'3549','1','2',null,'19820523',null,null,'403','Anthonij Miechielsz van Voorthuijsenstraat','A M v Voorthuijsenstr',null,'66',null,null,'1785LG','2051',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3551','3018','19911002',null,'19911002',null,'3551',null,null,'1','1','3','19911002',null,null,'347','Biltstraat','Biltstraat',null,'152',null,null,'3572BM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3552','3018','19840411','19911002','19840411',null,'3552',null,'3551','1','2',null,'19840411',null,null,'508','Nieuwe Haven','Nieuwe Haven',null,'13',null,null,'3311AP','1349',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3553','3019','19901111',null,'19901111',null,'3553',null,null,'1','1','3','19901111',null,null,'347','Biltstraat','Biltstraat',null,'172',null,null,'3572BP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3554','3019','19741026','19901111','19741026',null,'3554',null,'3553','1','2',null,'19741026',null,null,'522','Marktplein','Marktplein',null,'18',null,null,'2691BV','1657',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3555','3020','19820528',null,'19820528',null,'3555',null,null,'1','1','3','19820528',null,null,'347','Biltstraat','Biltstraat',null,'172',null,null,'3572BP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3556','3020','19760103','19820528','19760103',null,'3556',null,'3555','1','2',null,'19760103',null,null,'403','Middenweg','Middenweg ',null,'159',null,null,'1782BE','2051',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3557','3021','20010328',null,'20010328',null,'3557',null,null,'1','1','3','20010328',null,null,'347','Vredenburg ','Vredenburg ',null,'81',null,null,'3511BD','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3558','3021','19920516','20010328','19920516',null,'3558',null,'3557','1','2',null,'19920516',null,null,'342','Molenstraat','Molenstraat',null,'27',null,null,'3927AB','1139',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3559','3022','20101002',null,'20101002',null,'3559',null,null,'1','1','3','20101002',null,null,'347','Biltstraat','Biltstraat',null,'119',null,null,'3572AP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3560','3022','19951110','20101002','19951110',null,'3560',null,'3559','1','2',null,'19951110',null,null,'508','Nieuwe Haven','Nieuwe Haven',null,'29',null,null,'3311AP','1349',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3561','3023','19970605',null,'19970605',null,'3561',null,null,'1','1','3','19970605',null,null,'347','Vredenburg ','Vredenburg ',null,'83',null,null,'3511BD','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3562','3023','19680310','19970605','19680310',null,'3562',null,'3561','1','2',null,'19680310',null,null,'403','Anthonij Miechielsz van Voorthuijsenstraat','A M v Voorthuijsenstr',null,'66',null,null,'1785LG','2051',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3563','3024','19790928',null,'19790928',null,'3563',null,null,'1','1','3','19790928',null,null,'347','Biltstraat','Biltstraat',null,'121',null,null,'3572AP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3564','3024','19770401','19790928','19770401',null,'3564',null,'3563','1','2',null,'19770401',null,null,'366','Bonairestraat','Bonairestraat',null,'23',null,null,'1058XC','25',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3565','3025','19950929',null,'19950929',null,'3565',null,null,'1','1','3','19950929',null,null,'347','Vredenburg ','Vredenburg ',null,'42',null,null,'3511BD','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3566','3025','19880611','19950929','19880611',null,'3566',null,'3565','1','2',null,'19880611',null,null,'366','Bonairestraat','Bonairestraat',null,'37',null,null,'1058XC','25',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3567','3026','20000101',null,'20000101',null,'3567',null,null,'1','1','3','20000101',null,null,'347','Biltstraat','Biltstraat',null,'115',null,null,'3572AP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3568','3026','19910721','20000101','19910721',null,'3568',null,'3567','1','2',null,'19910721',null,null,'506','Markt','Markt',null,'80',null,null,'2611GW','1136',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3569','3027','20031024',null,'20031024',null,'3569',null,null,'1','1','3','20031024',null,null,'347','Biltstraat','Biltstraat',null,'117',null,null,'3572AP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3570','3027','19910911','20031024','19910911',null,'3570',null,'3569','1','2',null,'19910911',null,null,'1415','Rafaëlweg','Rafaëlweg',null,'8',null,null,'6114BZ','1043',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3571','3028','19990616',null,'19990616',null,'3571',null,null,'1','1','3','19990616',null,null,'347','Biltstraat','Biltstraat',null,'117',null,null,'3572AP','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3572','3028','19900722','19990616','19900722',null,'3572',null,'3571','1','2',null,'19900722',null,null,'977','Marktstraat ','Marktstraat',null,'16',null,null,'6114HS','1043',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3573','3030','20120620',null,'20120620',null,'3573',null,null,'1','1','3','20120620',null,null,'347','Biltstraat','Biltstraat',null,'95',null,null,'3572AL','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3574','3030','20091201','20120620','20091201',null,'3574',null,'3573','1','1','3','20091201',null,null,'347','Neude','Neude',null,'30',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3575','3030','20060201','20091201','20060201',null,'3575',null,'3574','1','1','3','20060201',null,null,'347','Vredenburg ','Vredenburg ',null,'112',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3576','3030','19220419','20060201','19220419',null,'3576',null,'3575','1','2',null,'19220419',null,null,'1239','Zuidweg','Zuidweg',null,'8',null,null,'4316AA','1685',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3577','3031','20120306',null,'20120306',null,'3577',null,null,'1','1','3','20120306',null,null,'347','Vredenburg ','Vredenburg ',null,'114',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3578','3031','20010926','20120306','20010926',null,'3578',null,'3577','1','1','3','20010926',null,null,'347','Biltstraat','Biltstraat',null,'97',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3579','3031','20000827','20010926','20000827',null,'3579',null,'3578','1','1','3','20000827',null,null,'347','Neude','Neude',null,'32',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3580','3031','19920219','20000827','19920219',null,'3580',null,'3579','1','2',null,'19920219',null,null,'977','Marktstraat ','Marktstraat',null,'16',null,null,'6114HS','1043',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3581','3032','20120621',null,'20120621',null,'3581',null,null,'1','1','3','20120621',null,null,'347','Neude','Neude',null,'34',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3582','3032','20010301','20120621','20010301',null,'3582',null,'3581','1','1','3','20010301',null,null,'347','Vredenburg ','Vredenburg ',null,'116',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3583','3032','19670601','20010301','19670601',null,'3583',null,'3582','1','1','3','19670601',null,null,'347','Biltstraat','Biltstraat',null,'99',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3584','3032','19510923','19670601','19510923',null,'3584',null,'3583','1','2',null,'19510923',null,null,'522','Marktplein','Marktplein',null,'18',null,null,'2691BV','1657',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3585','3033','20120621',null,'20120621',null,'3585',null,null,'1','1','3','20120621',null,null,'347','Neude','Neude',null,'34',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3586','3033','20010301','20120621','20010301',null,'3586',null,'3585','1','1','3','20010301',null,null,'347','Vredenburg ','Vredenburg ',null,'116',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3587','3033','19670601','20010301','19670601',null,'3587',null,'3586','1','1','3','19670601',null,null,'347','Biltstraat','Biltstraat',null,'99',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3588','3033','19531116','19670601','19531116',null,'3588',null,'3587','1','2',null,'19531116',null,null,'506','Markt','Markt',null,'80',null,null,'2611GW','1136',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3589','3034','20090701',null,'20090701',null,'3589',null,null,'1','1','3','20090701',null,null,'347','Biltstraat','Biltstraat',null,'101',null,null,'3572AL','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3590','3034','20080218','20090701','20080218',null,'3590',null,'3589','1','1','3','20080218',null,null,'347','Vredenburg ','Vredenburg ',null,'118',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3591','3034','20070406','20080218','20070406',null,'3591',null,'3590','1','1','3','20070406',null,null,'347','Neude','Neude',null,'36',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3592','3034','19390402','20070406','19390402',null,'3592',null,'3591','1','2',null,'19390402',null,null,'403','Middenweg','Middenweg ',null,'159',null,null,'1782BE','2051',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3593','3035','20090301',null,'20090301',null,'3593',null,null,'1','1','3','20090301',null,null,'347','Vredenburg ','Vredenburg ',null,'120',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3594','3035','20081101','20090301','20081101',null,'3594',null,'3593','1','1','3','20081101',null,null,'347','Neude','Neude',null,'31',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3595','3035','20050101','20081101','20050101',null,'3595',null,'3594','1','1','3','20050101',null,null,'347','Biltstraat','Biltstraat',null,'103',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3596','3035','19841203','20050101','19841203',null,'3596',null,'3595','1','2',null,'19841203',null,null,'342','Molenstraat','Molenstraat',null,'25',null,null,'3927AB','1139',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3597','3036','20090301',null,'20090301',null,'3597',null,null,'1','1','3','20090301',null,null,'347','Vredenburg ','Vredenburg ',null,'120',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3598','3036','20081101','20090301','20081101',null,'3598',null,'3597','1','1','3','20081101',null,null,'347','Neude','Neude',null,'31',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3599','3036','20050101','20081101','20050101',null,'3599',null,'3598','1','1','3','20050101',null,null,'347','Biltstraat','Biltstraat',null,'103',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3600','3036','19851011','20050101','19851011',null,'3600',null,'3599','1','2',null,'19851011',null,null,'342','Molenstraat','Molenstraat',null,'27',null,null,'3927AB','1139',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3601','3037','20090301',null,'20090301',null,'3601',null,null,'1','1','3','20090301',null,null,'347','Vredenburg ','Vredenburg ',null,'120',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3602','3037','20081101','20090301','20081101',null,'3602',null,'3601','1','1','3','20081101',null,null,'347','Neude','Neude',null,'31',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3603','3037','20050112','20081101','20050112',null,'3603',null,'3602','1','2',null,'20050112',null,null,'347','Biltstraat','Biltstraat',null,'103',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3604','3038','20090301',null,'20090301',null,'3604',null,null,'1','1','3','20090301',null,null,'347','Vredenburg ','Vredenburg ',null,'120',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3605','3038','20081101','20090301','20081101',null,'3605',null,'3604','1','1','3','20081101',null,null,'347','Neude','Neude',null,'31',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3606','3038','20051201','20081101','20051201',null,'3606',null,'3605','1','2',null,'20051201',null,null,'347','Biltstraat','Biltstraat',null,'103',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3607','3039','20120522',null,'20120522',null,'3607',null,null,'1','1','3','20120522',null,null,'347','Neude','Neude',null,'33',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3608','3039','20111201','20120522','20111201',null,'3608',null,'3607','1','1','3','20111201',null,null,'347','Vredenburg ','Vredenburg ',null,'122',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3609','3039','20101201','20111201','20101201',null,'3609',null,'3608','1','1','3','20101201',null,null,'347','Biltstraat','Biltstraat',null,'105',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3610','3039','19910315','20101201','19910315',null,'3610',null,'3609','1','2',null,'19910315',null,null,'347','Neude','Neude',null,'33',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3611','3040','20010517',null,'20010517',null,'3611',null,null,'1','2',null,'20010517',null,null,'347','Biltstraat','Biltstraat',null,'107',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3612','3041','20090201',null,'20090201',null,'3612',null,null,'1','1','3','20090201',null,null,'347','Vredenburg ','Vredenburg ',null,'124',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3613','3041','20060523','20090201','20060523',null,'3613',null,'3612','1','1','3','20060523',null,null,'347','Neude','Neude',null,'35',null,null,'3512AG','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3614','3041','20040819','20060523','20040819',null,'3614',null,'3613','1','1','3','20040819',null,null,'347','Vredenburg ','Vredenburg ',null,'126',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3615','3041','20010517','20040819','20010517',null,'3615',null,'3614','1','2',null,'20010517',null,null,'347','Biltstraat','Biltstraat',null,'107',null,null,'3572AM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3616','3042','20010328',null,'20010328',null,'3616',null,null,'1','1','7','20010328',null,null,'347','Biltstraat','Biltstraat',null,'142',null,null,'3572BM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3617','3042','19580921','20010328','19580921',null,'3617',null,'3616','1','2',null,'19580921',null,null,'342','Molenstraat','Molenstraat',null,'27',null,null,'3927AB','1139',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3618','3043','19820528',null,'19820528',null,'3618',null,null,'1','1','3','19820528',null,null,'347','Biltstraat','Biltstraat',null,'142',null,null,'3572BM','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3619','3043','19590922','19820528','19590922',null,'3619',null,'3618','1','2',null,'19590922',null,null,'403','Middenweg','Middenweg ',null,'159',null,null,'1782BE','2051',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3620','3044','20010328',null,'20010328',null,'3620',null,null,'1','1','3','20010328',null,null,'347','Vredenburg','Vredenburg',null,'114',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3621','3044','19601221','20010328','19601221',null,'3621',null,'3620','1','2',null,'19601221',null,null,'342','Molenstraat','Molenstraat',null,'27',null,null,'3927AB','1139',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3622','3045','19911002',null,'19911002',null,'3622',null,null,'1','1','7','19911002',null,null,'347','Vredenburg','Vredenburg',null,'114',null,null,'3511BE','2289',null,null,null,null,null,null,null,null,'229',null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject    ,identcodenraand        ,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging       ,postcode,wpl,loctovadres            ,locoms                 ,bladresregel1          ,bladresregel2          ,bladresregel3          ,bladresregel4          ,bladresregel5          ,bladresregel6          ,land,datvertrekuitnederland ) VALUES('3623','3045','19611222','19911002','19611222',null,'3623',null,'3622','1','2',null,'19611222',null,null,'508','Nieuwe Haven','Nieuwe Haven',null,'13',null,null,'3311AP','1349',null,null,null,null,null,null,null,null,'229',null);


commit;


--------------------------------------------------
--- EIND
--- Sheet: 13 'his_persadres'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 118
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 16 'his_persbijhgem'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persbijhgem
--------------------------------------------------

begin;

INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('1','3001','20050328',null,'20050328',null,'3507',null,null,'347','20050328','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('101','3001','20030327','20050328','20030327',null,'3508',null,'3507','229','20030327','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('201','3001','19811002','20030327','19811002',null,'3509',null,'3508','521','19811002','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('2','3002','20050328',null,'20050328',null,'3510',null,null,'347','20050328','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('102','3002','20030327','20050328','20030327',null,'3511',null,'3510','229','20030327','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('202','3002','19820528','20030327','19820528',null,'3512',null,'3511','602','19820528','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('3','3003','19901201',null,'19901201',null,'3513',null,null,'347','19901201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('103','3003','19901111','19901201','19901111',null,'3514',null,'3513','950','19901111','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('203','3003','19660302','19901111','19660302',null,'3515',null,'3514','521','19660302','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('4','3004','19901201',null,'19901201',null,'3516',null,null,'347','19901201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('104','3004','19901111','19901201','19901111',null,'3517',null,'3516','950','19901111','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('204','3004','19691227','19901111','19691227',null,'3518',null,'3517','347','19691227','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('5','3005','19981201',null,'19981201',null,'3519',null,null,'347','19981201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('105','3005','19970605','19981201','19970605',null,'3520',null,'3519','403','19970605','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('205','3005','19710717','19970605','19710717',null,'3521',null,'3520','347','19710717','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('6','3006','19981201',null,'19981201',null,'3522',null,null,'347','19981201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('106','3006','19970605','19981201','19970605',null,'3523',null,'3522','403','19970605','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('206','3006','19720928','19970605','19720928',null,'3524',null,'3523','366','19720928','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('7','3007','20110205',null,'20110205',null,'3525',null,null,'347','20110205','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('107','3007','20031024','20110205','20031024',null,'3526',null,'3525','1415','20031024','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('207','3007','19800616','20031024','19800616',null,'3527',null,'3526','602','19800616','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('8','3008','20110205',null,'20110205',null,'3528',null,null,'347','20110205','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('108','3008','20031024','20110205','20031024',null,'3529',null,'3528','1415','20031024','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('208','3008','19810418','20031024','19810418',null,'3530',null,'3529','347','19810418','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('9','3009','20110205',null,'20110205',null,'3531',null,null,'347','20110205','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('109','3009','20031226','20110205','20031226',null,'3532',null,'3531','1415','20031226','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('10','3010','20090928',null,'20090928',null,'3533',null,null,'347','20090928','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('110','3010','19960304','20090928','19960304',null,'3534',null,'3533','775','19960304','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('210','3010','19691231','19960304','19691231',null,'3535',null,'3534','602','19691231','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('11','3011','20090928',null,'20090928',null,'3536',null,null,'347','20090928','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('111','3011','19960304','20090928','19960304',null,'3537',null,'3536','775','19960304','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('211','3011','19690516','19960304','19690516',null,'3538',null,'3537','602','19690516','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('12','3012','20000228',null,'20000228',null,'3539',null,null,'347','20000228','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('112','3012','19771210','20000228','19771210',null,'3540',null,'3539','629','19771210','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('13','3013','19920508',null,'19920508',null,'3541',null,null,'347','19920508','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('113','3013','19550602','19920508','19550602',null,'3542',null,'3541','991','19550602','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('14','3014','19780611',null,'19780611',null,'3543',null,null,'347','19780611','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('15','3015','20110425',null,'20110425',null,'3544',null,null,'347','20110425','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('115','3015','19880425','20110425','19880425',null,'3545',null,'3544','94','19880425','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('16','3016','20100626',null,'20100626',null,'3546',null,null,'347','20100626','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('116','3016','19880425','20100626','19880425',null,'3547',null,'3546','741','19880425','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('216','3016','18991210','19880425','18991210',null,'3548',null,'3547','521','18991210','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('17','3017','20030327',null,'20030327',null,'3549',null,null,'347','20030327','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('117','3017','19820523','20030327','19820523',null,'3550',null,'3549','403','19820523','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('18','3018','19911002',null,'19911002',null,'3551',null,null,'347','19911002','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('118','3018','19840411','19911002','19840411',null,'3552',null,'3551','508','19840411','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('19','3019','19901111',null,'19901111',null,'3553',null,null,'347','19901111','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('119','3019','19741026','19901111','19741026',null,'3554',null,'3553','522','19741026','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('20','3020','19820528',null,'19820528',null,'3555',null,null,'347','19820528','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('120','3020','19760103','19820528','19760103',null,'3556',null,'3555','403','19760103','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('21','3021','20010328',null,'20010328',null,'3557',null,null,'347','20010328','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('121','3021','19920516','20010328','19920516',null,'3558',null,'3557','342','19920516','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('22','3022','20101002',null,'20101002',null,'3559',null,null,'347','20101002','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('122','3022','19951110','20101002','19951110',null,'3560',null,'3559','508','19951110','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('23','3023','19970605',null,'19970605',null,'3561',null,null,'347','19970605','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('123','3023','19680310','19970605','19680310',null,'3562',null,'3561','403','19680310','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('24','3024','19790928',null,'19790928',null,'3563',null,null,'347','19790928','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('124','3024','19770401','19790928','19770401',null,'3564',null,'3563','366','19770401','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('25','3025','19950929',null,'19950929',null,'3565',null,null,'347','19950929','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('125','3025','19880611','19950929','19880611',null,'3566',null,'3565','366','19880611','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('26','3026','20000101',null,'20000101',null,'3567',null,null,'347','20000101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('126','3026','19910721','20000101','19910721',null,'3568',null,'3567','506','19910721','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('27','3027','20031024',null,'20031024',null,'3569',null,null,'347','20031024','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('127','3027','19910911','20031024','19910911',null,'3570',null,'3569','1415','19910911','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('28','3028','19990616',null,'19990616',null,'3571',null,null,'347','19990616','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('128','3028','19900722','19990616','19900722',null,'3572',null,'3571','977','19900722','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('30','3030','20120620',null,'20120620',null,'3573',null,null,'347','20120620','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('130','3030','20091201','20120620','20091201',null,'3574',null,'3573','347','20091201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('230','3030','20060201','20091201','20060201',null,'3575',null,'3574','347','20060201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('330','3030','19220419','20060201','19220419',null,'3576',null,'3575','1239','19220419','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('31','3031','20120306',null,'20120306',null,'3577',null,null,'347','20120306','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('131','3031','20010926','20120306','20010926',null,'3578',null,'3577','347','20010926','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('231','3031','20000827','20010926','20000827',null,'3579',null,'3578','347','20000827','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('331','3031','19920219','20000827','19920219',null,'3580',null,'3579','977','19920219','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('32','3032','20120621',null,'20120621',null,'3581',null,null,'347','20120621','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('132','3032','20010301','20120621','20010301',null,'3582',null,'3581','347','20010301','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('232','3032','19670601','20010301','19670601',null,'3583',null,'3582','347','19670601','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('332','3032','19510923','19670601','19510923',null,'3584',null,'3583','522','19510923','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('33','3033','20120621',null,'20120621',null,'3585',null,null,'347','20120621','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('133','3033','20010301','20120621','20010301',null,'3586',null,'3585','347','20010301','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('233','3033','19670601','20010301','19670601',null,'3587',null,'3586','347','19670601','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('333','3033','19531116','19670601','19531116',null,'3588',null,'3587','506','19531116','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('34','3034','20090701',null,'20090701',null,'3589',null,null,'347','20090701','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('134','3034','20080218','20090701','20080218',null,'3590',null,'3589','347','20080218','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('234','3034','20070406','20080218','20070406',null,'3591',null,'3590','347','20070406','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('334','3034','19390402','20070406','19390402',null,'3592',null,'3591','403','19390402','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('35','3035','20090301',null,'20090301',null,'3593',null,null,'347','20090301','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('135','3035','20081101','20090301','20081101',null,'3594',null,'3593','347','20081101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('235','3035','20050101','20081101','20050101',null,'3595',null,'3594','347','20050101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('335','3035','19841203','20050101','19841203',null,'3596',null,'3595','342','19841203','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('36','3036','20090301',null,'20090301',null,'3597',null,null,'347','20090301','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('136','3036','20081101','20090301','20081101',null,'3598',null,'3597','347','20081101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('236','3036','20050101','20081101','20050101',null,'3599',null,'3598','347','20050101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('336','3036','19851011','20050101','19851011',null,'3600',null,'3599','342','19851011','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('37','3037','20090301',null,'20090301',null,'3601',null,null,'347','20090301','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('137','3037','20081101','20090301','20081101',null,'3602',null,'3601','347','20081101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('237','3037','20050112','20081101','20050112',null,'3603',null,'3602','347','20050112','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('38','3038','20090301',null,'20090301',null,'3604',null,null,'347','20090301','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('138','3038','20081101','20090301','20081101',null,'3605',null,'3604','347','20081101','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('238','3038','20051201','20081101','20051201',null,'3606',null,'3605','347','20051201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('39','3039','20120522',null,'20120522',null,'3607',null,null,'347','20120522','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('139','3039','20111201','20120522','20111201',null,'3608',null,'3607','347','20111201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('239','3039','20101201','20110901','20101201',null,'3609',null,'3608','347','20101201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('339','3039','19910315','20101201','19910315',null,'3610',null,'3609','347','19910315','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('40','3040','20010517',null,'20010517',null,'3611',null,null,'347','20010517','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('41','3041','20090201',null,'20090201',null,'3612',null,null,'347','20090201','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('141','3041','20060523','20090201','20060523',null,'3613',null,'3612','347','20060523','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('241','3041','20040819','20060523','20040819',null,'3614',null,'3613','347','20040819','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('341','3041','20010517','20040819','20010517',null,'3615',null,'3614','347','20010517','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('42','3042','20010328',null,'20010328',null,'3616',null,null,'347','20010328','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('142','3042','19580921','20010328','19580921',null,'3617',null,'3616','342','19580921','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('43','3043','19820528',null,'19820528',null,'3618',null,null,'347','19820528','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('143','3043','19590922','19820528','19590922',null,'3619',null,'3618','403','19590922','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('44','3044','20010328',null,'20010328',null,'3620',null,null,'347','20010328','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('144','3044','19601221','20010328','19601221',null,'3621',null,'3620','342','19601221','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('45','3045','19911002',null,'19911002',null,'3622',null,null,'347','19911002','false');
INSERT INTO kern.his_persbijhgem (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,bijhgem,datinschringem,indonverwdocaanw) VALUES('145','3045','19611222','19911002','19611222',null,'3623',null,'3622','508','19611222','false');











commit;


--------------------------------------------------
--- EIND
--- Sheet: 16 'his_persbijhgem'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 127
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 18 'relatie'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: relatie
--------------------------------------------------

begin;

INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3001','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3002','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3003','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3004','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3005','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3006','3','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3007','3','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3008','3','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3009','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3010','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3011','1','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3012','3','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3013','3','A');
INSERT INTO kern.relatie (id,srt,relatiestatushis) VALUES('3014','1','A');











commit;


--------------------------------------------------
--- EIND
--- Sheet: 18 'relatie'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 24
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 19 'his_relatie'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_relatie
--------------------------------------------------

begin;

INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3001','3001','20030327',null,'3501',null,'20030327',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3002','3002','19901111',null,'3502',null,'19901111',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3003','3003','19970605',null,'3503',null,'19970605',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3004','3004','20031024',null,'3504',null,'20031024',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3005','3005','19960304',null,'3505',null,'19960304',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3009','3009','19800610',null,'3630',null,'19800610',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3010','3010','19810425',null,'3631',null,'19810425',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3011','3011','19760701',null,'3632',null,'19760701',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3012','3012','20050112',null,'3634',null,'20050112',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3013','3013','20051201',null,'3635',null,'20051201',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);
INSERT INTO kern.his_relatie (id,relatie,tsreg,tsverval,actieinh,actieverval,dataanv,gemaanv,wplaanv             ,blplaatsaanv        ,blregioaanv         ,landaanv            ,omslocaanv,rdneinde            ,dateinde            ,gemeinde            ,wpleinde            ,blplaatseinde       ,blregioeinde        ,landeinde           ,omsloceinde         ) VALUES('3014','3014','20060315',null,'3633',null,'20060315',null,null,null,null,'255','Modernodam',null,null,null,null,null,null,null,null);











commit;


--------------------------------------------------
--- EIND
--- Sheet: 19 'his_relatie'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 21
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 20 'betr'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: betr
--------------------------------------------------

begin;

INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3001','3001','1','3001',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3002','3001','1','3002',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3003','3002','1','3003',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3004','3002','1','3004',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3005','3003','1','3005',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3006','3003','1','3006',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3007','3004','1','3007',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3008','3004','1','3008',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3009','3005','1','3010',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3010','3005','1','3011',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3011','3006','2','3007','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3012','3006','2','3008','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3013','3006','3','3009',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3014','3007','2','3042','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3015','3007','2','3043','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3016','3007','3','3027',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3017','3008','2','3042','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3018','3008','2','3043','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3019','3008','3','3028',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3020','3008','2','3044',null,'M','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3021','3008','2','3045',null,'M','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3022','3009','1','3042',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3023','3009','1','3043',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3024','3010','1','3044',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3025','3010','1','3045',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3026','3011','1','3032',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3027','3011','1','3033',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3028','3012','2','3035','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3029','3012','2','3036','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3030','3012','3','3037',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3031','3013','2','3035','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3032','3013','2','3036','true','A','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3033','3013','3','3038',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3034','3014','1','3035',null,'X','X');
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,ouderschapstatushis,ouderlijkgezagstatushis) VALUES('3035','3014','1','3036',null,'X','X');











commit;


--------------------------------------------------
--- EIND
--- Sheet: 20 'betr'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 45
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 21 'his_betrouderlijkgezag'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_betrouderlijkgezag
--------------------------------------------------

begin;

INSERT INTO kern.his_betrouderlijkgezag (id,betr                ,dataanvgel          ,dateindegel         ,tsreg               ,tsverval            ,actieinh            ,actieverval         ,actieaanpgel        ,indouderheeftgezag  ) VALUES('3011','3011','20031226',null,'20031226',null,'3500',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3012','3012','20031226',null,'20031226',null,'3500',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3014','3014','19910911',null,'19910911',null,'3627',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3015','3015','19910911',null,'19910911',null,'3627',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3017','3017','20001022',null,'20001022',null,'3628',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3018','3018','20001022',null,'20001022',null,'3628',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3020','3020','19900722',null,'19900722','20001022','3626','3628',null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3120','3020','19900722','20001022','20001022',null,'3626',null,'3628','true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3021','3021','19900722',null,'19900722','20001022','3626','3628',null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3121','3021','19900722','20001022','20001022',null,'3626',null,'3628','true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3028','3028','20050112',null,'20050112',null,'3634',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3029','3029','20050112',null,'20050112',null,'3634',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3031','3031','20051201',null,'20051201',null,'3635',null,null,'true');
INSERT INTO kern.his_betrouderlijkgezag (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval         ,actieaanpgel,indouderheeftgezag  ) VALUES('3032','3032','20051201',null,'20051201',null,'3635',null,null,'true');










commit;


--------------------------------------------------
--- EIND
--- Sheet: 21 'his_betrouderlijkgezag'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 23
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 22 'his_betrouderschap'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_betrouderschap
--------------------------------------------------

begin;

INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3011','3011','20031226',null,'20031226',null,'3500',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3012','3012','20031226',null,'20031226',null,'3500',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3014','3014','19910911',null,'19910911',null,'3627',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3015','3015','19910911',null,'19910911',null,'3627',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3017','3017','20001022',null,'20001022',null,'3628',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3018','3018','20001022',null,'20001022',null,'3628',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3020','3020','19900722',null,'19900722','20001022','3626','3628',null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3120','3020','19900722','20001022','20001022',null,'3626',null,'3628','true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3021','3021','19900722',null,'19900722','20001022','3626','3628',null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3121','3021','19900722','20001022','20001022',null,'3626',null,'3628','true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3028','3028','20050112',null,'20050112',null,'3634',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3029','3029','20050112',null,'20050112',null,'3634',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3031','3031','20051201',null,'20051201',null,'3635',null,null,'true');
INSERT INTO kern.his_betrouderschap (id,betr,dataanvgel,dateindegel ,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder) VALUES('3032','3032','20051201',null,'20051201',null,'3635',null,null,'true');










commit;


--------------------------------------------------
--- EIND
--- Sheet: 22 'his_betrouderschap'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 23
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 24 'persindicatie'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: persindicatie
--------------------------------------------------

begin;

INSERT INTO Kern.PersIndicatie (id,pers,srt,PersIndicatieStatusHis) VALUES('3013','3013','3','A');
INSERT INTO Kern.PersIndicatie (id,pers,srt,PersIndicatieStatusHis) VALUES('3023','3023','2','A');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 24 'persindicatie'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 3
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 25 'his_persindicatie'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persindicatie
--------------------------------------------------

begin;

INSERT INTO kern.his_persindicatie (id,persIndicatie,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,waarde) VALUES('3013','3013','19550602',null,'19550602',null,'3624',null,null,'true');
INSERT INTO kern.his_persindicatie (id,persIndicatie,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,waarde) VALUES('3023','3023','19941003',null,'19941003',null,'3625',null,null,'true');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 25 'his_persindicatie'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 3
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 26 'his_persopschorting'
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

--------------------------------------------------
--- Sheet: his_persopschorting
--------------------------------------------------

begin;

INSERT INTO kern.his_persopschorting (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval, actieaanpgel              ,RdnOpschortingBijhouding) VALUES('3506','3014','20111111',null,'20111111',null,'3506',null,null,'1');
INSERT INTO kern.his_persopschorting (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval, actieaanpgel              ,RdnOpschortingBijhouding) VALUES('3629','3041','20050103',null,'20050103',null,'3629',null,null,'3');


commit;


--------------------------------------------------
--- EIND
--- Sheet: 26 'his_persopschorting'
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: 3
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: 27 '-end-'
--- Handler: SheetHandlerImplTimestamp
--------------------------------------------------

--------------------------------------------------
--- Sheet: -end-
--------------------------------------------------

begin;

DELETE FROM kern.ARTversion;
INSERT INTO kern.ARTversion (ID, FullVersion, ReleaseVersion, BuildTimestamp, ExcelTimestamp) VALUES (1, 'SierraTestdataGenerator 0.0.7-SNAPSHOT-r5660-b81 (11-10-2012 16:25.51)','0.0.7-SNAPSHOT','11-10-2012 16:25.51','12-10-2012 08:31:15');

commit;


--------------------------------------------------
--- EIND
--- Sheet: 27 '-end-'
--- Handler: SheetHandlerImplTimestamp
--- Aantal: 1
--------------------------------------------------
--------------------------------------------------
--- END Generated from: SierraTestdataCA.xls
--------------------------------------------------
--------------------------------------------------
--- Included: afterburner.sql
--------------------------------------------------

--RvdP-- Versie d.d. 16 augustus 2012, aangepast voor september 2012.
--RvdP-- Laatste aanpassing: iedereen die nederlandse nationaliteit heeft, heeft deze wegens reden 'artikel 3 lid 1'.
--RvdP--
--RvdP-- Samenvoegen van verschillende scripts:
--RvdP-- Scripts van Magnus, voor vullen van verschillende tabellen;
--RvdP-- Scripts geschreven door Roel, voor vullen van andere tabellen & updates.
--RvdP-- Script voor updaten van de sequences, zodat ze allemaal hoog genoeg beginnen, en er geen reeds gebruikte ID's worden uitgegeven.
--RvdP-- Commentaar Roel gemarkeerd met '--RvdP--'
--RvdP-- Alle 'resultaat' select statements zijn ook verwijderd, omdat Bas er last van had ;-0
--RvdP-- Ook moeilijk waren de 'truncate' -- weghalen...
--RvdP-- Nog afwezig was ook: update van kern.relatie adhv kern.his_relatie. Deze toegevoegd...

-- ------------------------------------------------------------------------------- -- 
-- Totaal script om vanuit een situatie waarbij een aantal basis tabellen in       --
-- het schemer [kern] gevuld zijn, de aanleunende tabellen te herleiden            --
-- ------------------------------------------------------------------------------- --
-- De volgende tabellen moeten zijn gevuld                                         --
-- * kern.pers                                                                     --
-- * kern.his_perssamengesteldenaam                                                --
-- * kern.his_persids                                                              --
-- * kern.his_persgeslachtsaand                                                    --
-- * kern.his_persgeboorte                                                         --
-- * kern.his_persoverlijden                                                       --
-- * kern.his_persadres                                                            --
-- * kern.persbijhgem                                                              --
-- * kern.persadres (basis rijen met id's zonder adres data)                       --
-- *                                                                               --
-- ------------------------------------------------------------------------------- --
-- Magnus Rolf, DBA BRP / mGBA - 2012                                              --
-- ChangeLog                                                                       --
-- YYYY-MM-DD  Auteur.......  Wijziging........................................... --
--                                                                                 --
-- ------------------------------------------------------------------------------- --
--
-- Stappen
-- ==================================================================================
-- Stap-I01 - vullen kern.pers                        (eventueel met backup)
-- Stap-I02 - vullen kern.his_perssamengesteldenaam   (idem)
-- Stap-I03 - vullen kern.his_persids
-- Stap-I04 - vullen kern.his_persgeslachtsaand
-- Stap-I05 - vullen kern.his_persgeboorte
-- Stap-I06 - vullen kern.his_persoverlijden
-- Stap-I07 - vullen kern.his_persadres
-- Stap-I08 - vullen kern.persbijhgem
-- Stap-I09 - vullen kern.persadres (basis id's zonder adres data)
--
-- Opmerking: Stappen I01 t/m I09 zijn voorwaardelijk voor de verdere UPDATES
--            en worden door Bas verzorgd.
--
-- ----------------------------------------------------------------------------------
-- Stap-01 - Bijwerken kern.persvoornaam          adhv kern.his_perssamengesteldenaam
-- Stap-02 - Bijwerken kern.his_persvoornaam      adhv kern.his_perssamengesteldenaam
-- Stap-03 - Bijwerken kern.persgeslnaamcomp      adhv kern.his_perssamengesteldenaam
-- Stap-04 - Bijwerken kern.his_persgeslnaamcomp  adhv kern.his_perssamengesteldenaam
--
-- Stap-05 - Update kern.persadres                mbv [kern.his_persadres]
-- Stap-06 - Update kern.persoon                  mbv [kern.his_persids]
-- Stap-07 - Update kern.persoon                  mbv [kern.his_persgelachtsaand]
-- Stap-08 - Update kern.persoon                  mbv [kern.his_perssamengesteldenaam]
-- Stap-09 - Update kern.persoon                  mbv [kern.his_persaanschr] -- Geen DATA aangeleverd !!!
-- Stap-10 - Update kern.persoon                  mbv [kern.his_persgeboorte]
-- Stap-11 - Update kern.persoon                  mbv [kern.his_persoverlijden]
--
--
-- <Stap-01>
-- opnieuw vullen van [persvoornaam] vanuit his_samengesteldenaam
-- van dubbele namen wordt alleen de eerste naam gebruikt
-- -1- verwijdere alle rijen in persvoornaam
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------


--RvdP-- Tussenstap:
--RvdP-- Bijhoudingsverantwoordelijkheid: iedereen die nu in de database zit als ingeSCHRevene, heeft per geboortedatum
--RvdP-- een College als verantwoordelijke. Hiertoe aanpassen:
--RvdP-- 1) his_bijhoudingsverantwoordelijkheid, 

BEGIN;

insert into kern.his_persbijhverantwoordelijk
(id, pers, dataanvgel, tsreg, actieinh, verantwoordelijke) select id, pers, datgeboorte, tsreg, actieinh, 1 from kern.his_persgeboorte
where pers in (select id from kern.pers where srt=1);
--RvdP-- 2) EN aanpassen actueel=college, althans: voor soort=1!
update kern.pers set verantwoordelijke = 1 where srt=1;
--RvdP-- Einde tussenstap.


TRUNCATE "kern"."persvoornaam" CASCADE;
COMMIT;
BEGIN;

-- RvdP -- We hebben een terugkerend probleem rondom de stamtabelvulling 'soort betrokkenheid'.
-- Nu wederom(!) gepatched: omdraaien van Kind en Partner (POK=>KOP).
update kern.betr set rol = (4-rol) where not rol is null;
--NB: Deze regel moet aan het begin, aan het eind van deze afterburner wordt uitgegaan van KOP bij toevoegen Adam & Eva.

COMMIT;
BEGIN;
ALTER SEQUENCE "kern"."seq_persvoornaam" RESTART WITH 1;

INSERT INTO "kern"."persvoornaam"
  (
--RvdP-- Aangepast: ook id vullen; overlaten aan postgres gaat fout. Bron: 'pers' van HSN:
  id
  , pers
  , volgnr
  , naam
  , persvoornaamstatushis
  )
SELECT 
--RvdP-- Aangepast: ook id vullen; overlaten aan postgres gaat fout. Bron: 'pers' van HSN:
 HSN.pers,		--RvdP-- Bron voor id.
  HSN.pers,                     -- pers,
  1 as volgnr,                  -- volgnr,
  CASE
    WHEN position(' ' in HSN.voornamen) = 0
         THEN HSN.voornamen
    ELSE substr(HSN.voornamen, 0, (position(' ' in HSN.voornamen) + 1) ) 
       
  END as voornaam , 
  'A' as persvoornaamstatushis  -- persvoornaamstatushis
FROM "kern"."his_perssamengesteldenaam" HSN where HSN.tsverval is null and HSN.dateindegel is null
order by 1;
--
INSERT INTO "kern"."persvoornaam"
  (
--KHG-- Aangepast: ook namen die vervallen zijn, maar dan 'X' voor persvoornaamstatushis
  id
  , pers
  , volgnr
  , naam
  , persvoornaamstatushis
  )
SELECT 
 HSN.pers,		--RvdP-- Bron voor id.
  HSN.pers,                     -- pers,
  1 as volgnr,                  -- volgnr,
  CASE
    WHEN position(' ' in HSN.voornamen) = 0
         THEN HSN.voornamen
    ELSE substr(HSN.voornamen, 0, (position(' ' in HSN.voornamen) + 1) ) 
       
  END as voornaam , 
  'X' as persvoornaamstatushis  -- persvoornaamstatushis
FROM "kern"."his_perssamengesteldenaam" HSN where HSN.tsverval is not null or HSN.dateindegel is not null
order by 1;


-- end-of-script-part (01)

-- <Stap-02>
-- opnieuw vullen van [his_persvoornaam] vanuit his_samengesteldenaam
-- van dubbele namen wordt alleen de eerste naam gebruikt
-- -1- verwijdere alle rijen in persvoornaam
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------
TRUNCATE "kern"."his_persvoornaam" CASCADE;
COMMIT;
BEGIN;
ALTER SEQUENCE "kern"."seq_his_persvoornaam" RESTART WITH 1;

INSERT INTO "kern"."his_persvoornaam" 
  (      id , 
    persvoornaam,
    dataanvgel,
    dateindegel,
    tsreg,
    tsverval,
    actieinh,
    actieverval,
    actieaanpgel,
    naam
   ) 
SELECT
	hsn.id as id,
  hsn.pers         as persvoornaam, 
  HSN.dataanvgel   as dataanvgel,
  HSN.dateindegel  as dateindegel,
  HSN.tsreg        as tsreg,
  HSN.tsverval     as tsverval,
  HSN.actieinh     as actieinh,
  HSN.actieverval  as actieverval,
  HSN.actieaanpgel as actieaanpgel,
  CASE
    WHEN position(' ' in HSN.voornamen) = 0
         THEN HSN.voornamen
    ELSE substr(HSN.voornamen, 0, (position(' ' in HSN.voornamen) + 1) ) 
       
  END as naam  
-- , HSN.voornamen    as naam
FROM 
  "kern"."his_perssamengesteldenaam" HSN ;
--
  
-- end-of-script-part (02)

-- <Stap-03>
-- opnieuw vullen van [persgeslnaamcomp] vanuit his_perssamengesteldenaam
-- dubbele achternamen worden samengesteld
-- -1- verwijdere alle rijen in persgeslnaamcomp
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------
--TRUNCATE "kern"."persgeslnaamcomp" CASCADE;
--COMMIT;
--BEGIN;
--ALTER SEQUENCE "kern"."seq_persgeslnaamcomp" RESTART WITH 1;

--INSERT INTO "kern"."persgeslnaamcomp" 
--  ( -- id , -- wordt gegenereerd a.d.h.v. sequence
--RvdP-- aangepast: ook id vullen...
--	id,
--  pers,
--    volgnr,
--    voorvoegsel,
--    scheidingsteken,
--    naam,
--    predikaat,
--    adellijketitel,
--    persgeslnaamcompstatushis
--   ) 
--SELECT 
  --  id,
  --RvdP-- Aangepast: bron voor id is pers...
--  pers			   as id,
--  pers             as pers,
--  1                as volgnr,
--  voorvoegsel      as voorvoegsel,
--  scheidingsteken  as scheidingsteken,
--  geslnaam         as naam,
--  predikaat        as predikaat,
--  adellijketitel   as adellijketitel,
--  'A'              as persgeslnaamcompstatushis
--FROM 
--  "kern"."his_perssamengesteldenaam" where tsverval is null and dateindegel is null
--  order by 1 ;
  
-- end-of-script-part (03)

-- <Stap-04>
-- opnieuw vullen van [his_persgeslnaamcomp] vanuit his_perssamengesteldenaam
-- 
-- -1- verwijdere alle rijen in his_persgeslnaamcomp
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------
--TRUNCATE "kern"."his_persgeslnaamcomp" CASCADE;
--COMMIT;
--BEGIN;
--ALTER SEQUENCE "kern"."seq_his_persgeslnaamcomp" RESTART WITH 1;

--INSERT INTO "kern"."his_persgeslnaamcomp"
--        ( id,  -- id wordt gegenereerd vanuit ssquence
--          persgeslnaamcomp,
--          dataanvgel,
--          dateindegel,
--          tsreg,
--          tsverval,
--          actieinh,
--          actieverval,
--          actieaanpgel,
--          voorvoegsel,
--          scheidingsteken,
--          naam,
--          predikaat,
--          adellijketitel
--        )
  
--SELECT 
--  HPS.id as id,
--  HPS.pers                    as persgeslnaamcomp,
--  HPS.dataanvgel              as dataanvgel,
--  HPS.dateindegel             as dateindegel,
--  HPS.tsreg                   as tsreg,
--  HPS.tsverval                as tsverval,
--  HPS.actieinh                as actieinh,
--  HPS.actieverval             as actieverval,
--  HPS.actieaanpgel            as actieaanpgel,
--  HPS.voorvoegsel             as voorvoegsel,
--  HPS.scheidingsteken         as scheidingsteken,
--  HPS.geslnaam                as naam,
--  HPS.predikaat               as predikaat,
--  HPS.adellijketitel          as adellijketitel
--FROM 
--  "kern"."his_perssamengesteldenaam" HPS
--order by HPS.id;
-- end-of-script-part (04)

--COMMIT;
BEGIN;

-- <Stap-05>
-- Wijzigen tabel [kern.persadres] met inhoud van [kern.his_persadres]
-- 
-- Update [kern.persadres] - 00 - adhv [kern.his_persadres] 
--RvdP-- Code hieronder werkte niet c.q. was fout: niet 'where U.persadres=pers' maar 'where U.persadres=kern.persadres.id'
UPDATE "kern"."persadres" SET srt = ( Select U.srt from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , rdnwijz = ( Select U.rdnwijz from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , aangadresh = ( Select U.aangadresh from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , dataanvadresh = ( Select U.dataanvadresh from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , adresseerbaarobject = ( Select U.adresseerbaarobject from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , identcodenraand = ( Select U.identcodenraand from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , gem = ( Select U.gem from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , nor = ( Select U.nor from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , afgekortenor = ( Select U.afgekortenor from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , gemdeel = ( Select U.gemdeel from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , huisnr = ( Select U.huisnr from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , huisletter = ( Select U.huisletter from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , huisnrtoevoeging = ( Select U.huisnrtoevoeging from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL)
                            , postcode = ( Select U.postcode from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , wpl = ( Select U.wpl from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , loctovadres = ( Select U.loctovadres from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , locoms = ( Select U.locoms from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel1 = ( Select U.bladresregel1 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel2 = ( Select U.bladresregel2 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel3 = ( Select U.bladresregel3 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel4 = ( Select U.bladresregel4 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel5 = ( Select U.bladresregel5 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel6 = ( Select U.bladresregel6 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , land = ( Select U.land from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , datvertrekuitnederland = ( Select U.datvertrekuitnederland from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL)
WHERE EXISTS ( Select 1 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (05)

COMMIT;
BEGIN;

-- <Stap-06>
-- 
-- Update [kern.pers] - 01 - adhv [kern.his_persids]                                                                           
UPDATE "kern"."pers" SET idsstatushis = 'X' WHERE idsstatushis = 'A';                                                   
UPDATE "kern"."pers" SET bsn = ( Select U.bsn from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , anr = ( Select U.anr from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , idsstatushis = 'A'
WHERE EXISTS ( Select 1 from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (06)

COMMIT;
BEGIN;

-- <Stap-07>
-- 
-- Update [kern.per] adhv [kern.his_persgelachtsaand]                                                                                                                                                                                                                                                                                                                
UPDATE "kern"."pers" SET geslachtsaandstatushis = 'X' WHERE geslachtsaandstatushis = 'A';                                                                                                                                                                                                                         
UPDATE "kern"."pers" SET geslachtsaand = ( Select U.geslachtsaand from "kern"."his_persgeslachtsaand" U where U.pers = pers.id AND U.dateindegel IS NULL) 
                       , geslachtsaandstatushis = 'A' 
WHERE EXISTS ( Select 1 from "kern"."his_persgeslachtsaand" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (07)

COMMIT;
BEGIN;

-- <Stap-08>
-- 
-- Update [kern.per] adhv [kern.his_perssamengesteldenaam]                                                                                                                                                                                                                                                                                                         
UPDATE "kern"."pers" SET samengesteldenaamstatushis = 'X' WHERE samengesteldenaamstatushis = 'A';                                                                                                                                                                                                              
UPDATE "kern"."pers" SET predikaat = ( Select U.predikaat from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , voornamen = ( Select U.voornamen from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , voorvoegsel = ( Select U.voorvoegsel from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , scheidingsteken = ( Select U.scheidingsteken from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , adellijketitel = ( Select U.adellijketitel from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , geslnaam = ( Select U.geslnaam from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , indnreeksalsgeslnaam = ( Select U.indnreeksalsgeslnaam from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , indalgoritmischafgeleid = ( Select U.indalgoritmischafgeleid from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , samengesteldenaamstatushis = 'A' 
WHERE EXISTS ( Select 1 from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (08)

COMMIT;
BEGIN;

--RvdP--
--RvdP-- Tussengevoegd: insert statement voor persaanschrijving.
--INSERT INTO kern.his_persaanschr
--( id,  pers,  dataanvgel, dateindegel,  tsreg,  tsverval,  actieinh,  actieverval,  actieaanpgel,  gebrgeslnaamegp,  indaanschrmetadellijketitels,  indaanschralgoritmischafgele,
--  predikaataanschr,   voornamenaanschr,  voorvoegselaanschr,  scheidingstekenaanschr,  geslnaamaanschr)
--SELECT
-- id,  pers,  dataanvgel,  dateindegel,  tsreg,  tsverval,  actieinh,  actieverval,  actieaanpgel,   1 as gebrgeslnaamegp, null,
--  indalgoritmischafgeleid,  predikaat,  voornamen,  voorvoegsel,  scheidingsteken,  geslnaam
--FROM  kern.his_perssamengesteldenaam where kern.his_perssamengesteldenaam.pers in (select pers.id from kern.pers where srt=1) ORDER BY ID ;  
--RvdP-- Einde simpele kopieeractie van samengestelde naam naar aanschrijving. Moet alleen gebeuren voor personen van 'soort=1'

-- <Stap-09>   LET OP! Van [kern.his_persaanschr] is geen data aangeleveerd !!!
--RvdP-- De data voor persaanschrijving is boven toegevoegd
-- 
-- Update [kern.per] adhv [kern.his_persaanschr] 
UPDATE "kern"."pers" SET aanschrstatushis = 'X' WHERE aanschrstatushis = 'A';
UPDATE "kern"."pers" SET gebrgeslnaamegp = ( Select U.gebrgeslnaamegp from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , indaanschrmetadellijketitels = ( Select U.indaanschrmetadellijketitels from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , indaanschralgoritmischafgele = ( Select U.indaanschralgoritmischafgele from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , predikaataanschr = ( Select U.predikaataanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , voornamenaanschr = ( Select U.voornamenaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , voorvoegselaanschr = ( Select U.voorvoegselaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , adellijketitelaanschr = ( Select U.adellijketitelaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , scheidingstekenaanschr = ( Select U.scheidingstekenaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , geslnaamaanschr = ( Select U.geslnaamaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
                       , aanschrstatushis = 'A' 
WHERE EXISTS ( Select 1 from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (09)

COMMIT;
BEGIN;

-- <Stap-10>
-- 
-- Update [kern.per] adhv [kern.his_persgeboorte] 
UPDATE "kern"."pers" SET geboortestatushis = 'X' WHERE geboortestatushis = 'A';
UPDATE "kern"."pers" SET datgeboorte = ( Select U.datgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , gemgeboorte = ( Select U.gemgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , wplgeboorte = ( Select U.wplgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , blgeboorteplaats = ( Select U.blgeboorteplaats from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , blregiogeboorte = ( Select U.blregiogeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , landgeboorte = ( Select U.landgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , omsgeboorteloc = ( Select U.omsgeboorteloc from "kern"."his_persgeboorte" U where U.pers = pers.id )
                       , geboortestatushis = 'A' 
WHERE EXISTS ( Select 1 from "kern"."his_persgeboorte" U where U.pers = pers.id ) ;
-- end-of-script-part (10)

COMMIT;
BEGIN;

-- <Stap-11>
-- 
-- Update [kern.per] adhv [kern.his_persoverlijden]
-- Update [kern.pers] - 01 - adhv [kern.his_persoverlijden]    
UPDATE "kern"."pers" SET overlijdenstatushis = 'X' WHERE overlijdenstatushis = 'A';
UPDATE "kern"."pers" SET datoverlijden = ( Select U.datoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , gemoverlijden = ( Select U.gemoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , wploverlijden = ( Select U.wploverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , blplaatsoverlijden = ( Select U.blplaatsoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , blregiooverlijden = ( Select U.blregiooverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , landoverlijden = ( Select U.landoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , omslocoverlijden = ( Select U.omslocoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
                       , overlijdenstatushis = 'A' 
WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ; 
-- end-of-script-part (11)

COMMIT;
BEGIN;

-- RvdP Was vergeten: update van de actuele laag van persoonindicatie op basis van his_persindicatie.
UPDATE "kern"."persindicatie" SET persindicatiestatushis = 'A' where exists (select 1 from kern.his_persindicatie U where U.dateindegel is null and U.tsverval is null);
-- Gebruik maken van feit dat 'waarde' van de indicatie alleen maar 'true' kan zijn, en dat dus ook is als de status gelijk is aan 'A':
UPDATE "kern"."persindicatie" SET waarde = TRUE where persindicatiestatushis = 'A';
-- <End-of-Script>





-- Update [kern.pers] - 01 - adhv [kern.his_persoverlijden]    
UPDATE "kern"."pers" SET overlijdenstatushis = 'X' WHERE overlijdenstatushis = 'A';
UPDATE "kern"."pers" SET datoverlijden = ( Select U.datoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET gemoverlijden = ( Select U.gemoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET wploverlijden = ( Select U.wploverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET blplaatsoverlijden = ( Select U.blplaatsoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET blregiooverlijden = ( Select U.blregiooverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET landoverlijden = ( Select U.landoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET omslocoverlijden = ( Select U.omslocoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
UPDATE "kern"."pers" SET overlijdenstatushis = 'A' WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;


COMMIT;
BEGIN;


--RvdP-- Update van kern.relatie waar aanwezig kern.his_relatie
UPDATE "kern"."relatie" SET omslocaanv= (Select H.omslocaanv from kern.his_relatie H where H.relatie=relatie.id) where exists (select 1 from kern.his_relatie H where H.relatie=relatie.id);
UPDATE "kern"."relatie" SET landaanv= (Select H.landaanv from kern.his_relatie H where H.relatie=relatie.id) where exists (select 1 from kern.his_relatie H where H.relatie=relatie.id);
UPDATE "kern"."relatie" SET dataanv = (select dataanv from kern.his_relatie H where H.relatie=relatie.id) where exists (select 1 from kern.his_relatie H where H.relatie=relatie.id);
 

COMMIT;
BEGIN;


--RvdP-- We hebben nu ook opschorting wegens 'fout'. Deze ook doen!
update kern.pers A set rdnopschortingbijhouding = (select rdnopschortingbijhouding from kern.his_persopschorting B where B.pers = A.id);
update kern.pers set opschortingstatushis='A' where rdnopschortingbijhouding is not null;


COMMIT;
BEGIN;


--RvdP-- VOLGENDE BLOK NIET MEER NODIG: WE VULLEN NU EXPLICIET WOONPLAATSCODE!
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--RvdP-- Zoals beloofd op de 15e dient ook de woonplaatscode gevuld te zijn.
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--RvdP-- De queries hiervoor zijn:
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- update kern.persadres set wpl = null;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- update kern.his_persadres set wpl=null;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Haarlem:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=1902 where gem=395;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- end Haarlem
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Utrecht:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=2289 where gem= 347;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Arnhem:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=297 where gem= 205;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Groningen:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=71 where gem= 17;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Rotterdam:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=2081 where gem= 602;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Den Haag:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=246 where gem= 521 ;
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.his_persadres A set wpl = B.wpl from kern.persadres B
--RvdP-- VOLGENDE BLOK NIET MEER NODIGwhere B.id = A.persadres and A.dateindegel is null and A.tsverval is null;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--  -- specifiek persoon: P1 van Utrecht: bijbehorend kern.persadres id is 1000007
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--  update kern.his_persadres set wpl=2289 where id = 1000007;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--RvdP-- Einde update woonplaats

--RvdP-- Nu overgaan naar de niet ingeschreven personen: eerst Adam van Modernodam, dan Eva van Modernodam
insert into kern.pers (id, srt, idsstatushis, geslachtsaandstatushis, samengesteldenaamstatushis, aanschrstatushis, geboortestatushis, overlijdenstatushis, uitslnlkiesrstatushis, euverkiezingenstatushis, bijhverantwoordelijkheidstat, opschortingstatushis, bijhgemstatushis, verblijfsrstatushis, pkstatushis, inschrstatushis, tijdstiplaatstewijz, immigratiestatushis, voornamen, voorvoegsel, scheidingsteken, geslnaam, indnreeksalsgeslnaam, geslachtsaand) select id+1000000, 2, 'X', 'A', 'A', 'X', 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X','X', now(), 'X', 'Adam', 'van', ' ', 'Modernodam', false, 1 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
--RvdP: Voor iedereen die nog niet betrokken was als 'kind' in een relatie, komt er een 'adam' als vader...

COMMIT;
BEGIN;
insert into kern.pers (id, srt, idsstatushis, geslachtsaandstatushis, samengesteldenaamstatushis, aanschrstatushis, geboortestatushis, overlijdenstatushis, uitslnlkiesrstatushis, euverkiezingenstatushis, bijhverantwoordelijkheidstat, opschortingstatushis, bijhgemstatushis, verblijfsrstatushis, pkstatushis, inschrstatushis, tijdstiplaatstewijz, immigratiestatushis, voornamen, voorvoegsel, scheidingsteken, geslnaam, indnreeksalsgeslnaam, geslachtsaand) select id+2000000, 2, 'X', 'A', 'A', 'X', 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X','X', now(), 'X', 'Eva', 'van', ' ', 'Modernodam', false,2 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);

COMMIT;
BEGIN;

--RvdP-- Eva en Adam zijn met 'actie 0' begonnen, op 1 januari  1980
insert into kern.actie (id, srt, partij, tijdstipreg) values (0,1, 2000, '19800101');

COMMIT;
BEGIN;

--RvdP-- Geboorteactie van Adam en Eva zijn de actie waarmee betreffende persoon is geboren; hiermee is ook de relatie=famrechtbetr ontstaan:
insert into kern.Relatie(id, srt, relatiestatushis) select 33000000+id, 3, 'A' from kern.pers;

COMMIT;
BEGIN;

--RvdP-- eerst de betrokkenheden van Adam en Eva: bron 'personen soort 1 zonder ouders'
insert into kern.betr(id, relatie, rol, Pers, indouder, ouderschapstatushis, ouderlijkgezagstatushis)
   select 1000000+id, 33000000+id, 2, id+1000000, true, 'A', 'X' from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;
   
COMMIT;
BEGIN;

insert into kern.betr(id, relatie, rol, Pers, indouder, ouderschapstatushis, ouderlijkgezagstatushis)
   select 2000000+id, 33000000+id, 2, id+2000000, true, 'A', 'X' from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;

   
COMMIT;
BEGIN;

--RvdP-- nu de betrokkenheden voor iedereen die NOG GEEN OUDERS heeft (hierna heeft iedereen ouders...)
insert into kern.betr(id, relatie, rol, Pers, ouderschapstatushis, ouderlijkgezagstatushis)
	select 3000000+id, 33000000+id, 1, id, 'X', 'X' from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;

COMMIT;
BEGIN;
	
--RvdP-- Betrokkenheid -- historie
insert into kern.his_betrouderschap (id, betr, dataanvgel, tsreg, actieinh, indouder)
	select A.id, A.id, B.datgeboorte, B.tsreg, B.actieinh, true
	from kern.betr A, kern.his_persgeboorte B
	where not exists (select 42 from kern.pers where srt=1 AND A.Pers=pers.id)
	-- Ok, alleen de Adam's en Eva's...
	and exists (select 42 from kern.betr C where C.relatie = A.relatie AND C.Pers=B.pers);
	-- Ok, alleen voor personen die een his_geboorte hebben, die betrokken is in dezelfde relatie.
		
	
--RvdP-- Vergeten was: Nationaliteit.
--RvdP-- Gelukkig is dit een simpele: IEDEREEN (van soort=1) is nederlander, per datum geboorte.
--RvdP-- Dus: kopieer kern.pers naar kern.persnation voor soort=1; kopieer his_persgeboorte naar kern.his_persnation
--RvdP-- Reden verkrijging is: reden nr 18 (code: 17).
insert into kern.persnation (id, pers, nation, persnationstatushis, rdnverk)
	select id, id, 2, 'A', 18 from kern.pers where srt=1;

COMMIT;
BEGIN;

-- RvdP-- Bijhoudingsgemeente wordt blijkbaar niet gekopieerd van historie naar actueel...
-- RvdP -- Alsnog toegevoegd:

update kern.pers A set bijhgemstatushis = 'A' where exists (select 1 from kern.his_persbijhgem B where B.pers=A.id and B.dateindegel is null);
update kern.pers A set bijhgem = (select bijhgem from kern.his_persbijhgem B where B.pers=A.id and B.dateindegel is null);
update kern.pers A set datinschringem = (select datinschringem from kern.his_persbijhgem B where B.pers=A.id and B.dateindegel is null);
update kern.pers A set indonverwdocaanw = (select indonverwdocaanw from kern.his_persbijhgem B where B.pers=A.id and B.dateindegel is null);


	
insert into kern.his_persnation (id, persnation, dataanvgel, tsreg, actieinh, rdnverk)
	select A.id, A.id, B.datgeboorte, B.tsreg, B.actieinh, 18 from kern.persnation A, kern.his_persgeboorte B
	where B.pers=A.id and exists (select 42 from kern.pers where srt=1 AND pers.id=A.pers);
	
COMMIT;
BEGIN;
-- RvdP-- Niet gevuld: groep inschrijving...
-- RvdP -- Alsnog toegevoegd, en A-laag van persoon bijgewerkt:
insert into kern.his_persinschr (id, pers, tsreg, actieinh, datinschr, versienr)
select id, pers, tsreg, actieinh, datgeboorte, 1 from kern.his_persgeboorte HG where
HG.pers in (select id from kern.pers where srt = 1);
update kern.pers set datinschr = datgeboorte where srt=1;
update kern.pers set versienr = 1 where srt=1;
-- RvdP -- Updaten van status his is niet nodig: deze blijkt al te kloppen (c.q. voor iedereen van 'soort 1' gelijk aan A te zijn en rest gelijk X
COMMIT;

-- RvdP: Specifiek voor de tabel his_persadres geldt dat we opteren voor de 'vastlegging zoals gebeurd door BRP' ipv
-- vastlegging zoals zou zijn gedaan door migratie vanuit GBA-V. Dat betekent dat de 'glazen bol records' (die "weten" wanneer ze in de toekomst
-- worden aangepast) worden vervangen door TWEE records: een vervallen EN een ingekort record.
-- Stap 1: de glazen-bol records worden eerst vervallen verklaard:
update kern.his_persadres A
set tsverval = (select tsreg from kern.his_persadres B
where A.persadres = B.persadres and B.id = A.id - 1)
where not dateindegel is null 
; -- het minteken: de volgorde is precies verkeerd om: nieuwe records eerst ipv oude eerst

update kern.his_persadres A
set actieverval = (select actieinh from kern.his_persadres B
where A.persadres = B.persadres and B.id = A.id - 1)
where not dateindegel is null 
;

-- De glazen bol records zijn nu vervallen.
-- Stap 2: cre�er records met inhoud van de records die vervallen zijn (tsverval <> null),
-- met actieinh hetzelfde, en actieverval wordt actieaanpassingeldigheid
insert into kern.his_persadres
(persadres ,  dataanvgel ,  dateindegel ,  tsreg ,  tsverval ,  actieinh ,  actieverval ,  actieaanpgel ,  srt ,
  rdnwijz ,  aangadresh ,  dataanvadresh ,  adresseerbaarobject ,  identcodenraand ,  gem ,  nor ,  afgekortenor ,
  gemdeel ,  huisnr ,  huisletter ,  huisnrtoevoeging ,  postcode ,  wpl ,  loctovadres ,  locoms ,  bladresregel1 ,
  bladresregel2 ,  bladresregel3 ,  bladresregel4 ,  bladresregel5 ,  bladresregel6 ,  land ,  datvertrekuitnederland )
  select
  persadres ,  dataanvgel ,  dateindegel ,  tsverval ,  null ,  actieinh ,  null ,  actieverval ,  srt ,
  rdnwijz ,  aangadresh ,  dataanvadresh ,  adresseerbaarobject ,  identcodenraand ,  gem ,  nor ,  afgekortenor ,
  gemdeel ,  huisnr ,  huisletter ,  huisnrtoevoeging ,  postcode ,  wpl ,  loctovadres ,  locoms ,  bladresregel1 ,
  bladresregel2 ,  bladresregel3 ,  bladresregel4 ,  bladresregel5 ,  bladresregel6 ,  land ,  datvertrekuitnederland

  from kern.his_persadres where not tsverval is null;

update kern.his_persadres
set dateindegel=null where tsverval is not null;

-- De glazenbol records HADDEN al een datum aanpassing geldigheid. Die wordt leeggemaakt:
update kern.his_persadres set actieaanpgel = null where actieaanpgel = actieverval;



--RvdP-- Wens leveranciers: wegnemen van verwarrend feit dat ouders van partners dezelfde naam hebben (maar niet dezelfde persoon zijn).
--RvdP-- Via de volgende queries wordt dit opgelost: hierna heet 'Adam' niet meer 'Adam', maar 'Adam-Xxxx' met Xxxx de naam van zijn zoon of dochter...
--RvdP-- NB: Deze hernoemactie moet NA dat Eva en Adam als 'ouder' zijn gekoppeld. Hierom verplaatst naar (logisch) einde van de afterburner:
begin;
create table tempA as
select A.id, A.voornamen as ouder, B.voornamen as kind from
kern.pers A, kern.pers B, kern.betr C, kern.betr D
where A.id=C.pers and D.relatie=C.relatie and C.id <> D.id and D.pers=B.id and D.rol=1 and A.srt <>1;
commit;

begin;
alter table tempA add PRIMARY KEY (id);
commit;

begin;
update kern.pers P
set voornamen = voornamen || (select '-' || kind from tempA A where A.id = P.id)
where P.srt <> 1;
commit;

begin;
drop table tempA;
commit;
--RvdP-- Einde van hernoemactie
--RvdP-- TODO: ook his_samengestelde naam vullen...


--RvdP-- In de whiteboxfiller programmatuur (dd 13 september 2012) werd het ingevulde scheidingsteken niet correct
--RvdP-- overgenomen uit het excelsheet. Daarom hier hersteld.
--RvdP-- Te herstellen in:
--RvdP 1) persoonstabel, op twee plaatsen;
--RvdP 2) in his_persaanschr;
--RvdP 3) in his_perssamengesteldenaam;
--RvdP 4) in persgeslachtsnaamcomponent;
--RvdP 5) in his_persgeslachtsnaamcomponent.
--update kern.pers set scheidingsteken = ' ', scheidingstekenaanschr = ' ' where voorvoegsel is not null;
--update kern.his_persaanschr set scheidingstekenaanschr = ' ' where voorvoegselaanschr is not null;
--update kern.his_perssamengesteldenaam set scheidingsteken = ' ' where voorvoegsel is not null;
--update kern.persgeslnaamcomp set scheidingsteken = ' ' where voorvoegsel is not null;
--update kern.his_persgeslnaamcomp set scheidingsteken = ' ' where voorvoegsel is not null;









BEGIN;
	
--RvdP-- Toegevoegd: laatste versie van de queries die de sequences ophoogt, zoals gemaild door Magnus d.d. 31 mei 22:45:54
-- SELECT  'SELECT SETVAL('''||TA.schemaname||'.'||S.relname||''', MAX(' ||(C.attname)|| ') ) FROM "'||TA.schemaname||'"."'||T.relname||'" ;'
-- FROM pg_class AS S
--    , pg_depend AS D
--    , pg_class AS T
--    , pg_attribute AS C
--    , pg_tables AS TA
-- WHERE S.relkind       = 'S'
--     AND S.oid         = D.objid
--     AND D.refobjid    = T.oid
--     AND D.refobjid    = C.attrelid
--     AND D.refobjsubid = C.attnum
--     AND T.relname     = TA.tablename
-- ORDER BY S.relname;


-- RESULTAAT van bovenstaande query:

COMMIT;

BEGIN;

SELECT SETVAL('lev.seq_abonnement', MAX(id) ) FROM "lev"."abonnement" ;
SELECT SETVAL('lev.seq_abonnementgegevenselement', MAX(id) ) FROM "lev"."abonnementgegevenselement" ;
SELECT SETVAL('lev.seq_abonnementsrtber', MAX(id) ) FROM "lev"."abonnementsrtber" ;
SELECT SETVAL('kern.seq_actie', MAX(id) ) FROM "kern"."actie" ;
SELECT SETVAL('autaut.seq_authenticatiemiddel', MAX(id) ) FROM "autaut"."authenticatiemiddel" ;
SELECT SETVAL('autaut.seq_autorisatiebesluit', MAX(id) ) FROM "autaut"."autorisatiebesluit" ;
SELECT SETVAL('ber.seq_ber', MAX(id) ) FROM "ber"."ber" ;
SELECT SETVAL('kern.seq_betr', MAX(id) ) FROM "kern"."betr" ;
SELECT SETVAL('autaut.seq_bijhautorisatie', MAX(id) ) FROM "autaut"."bijhautorisatie" ;
SELECT SETVAL('autaut.seq_bijhsituatie', MAX(id) ) FROM "autaut"."bijhsituatie" ;
SELECT SETVAL('kern.seq_bron', MAX(id) ) FROM "kern"."bron" ;
SELECT SETVAL('autaut.seq_certificaat', MAX(id) ) FROM "autaut"."certificaat" ;
SELECT SETVAL('kern.seq_doc', MAX(id) ) FROM "kern"."doc" ;
SELECT SETVAL('autaut.seq_doelbinding', MAX(id) ) FROM "autaut"."doelbinding" ;
SELECT SETVAL('autaut.seq_doelbindinggegevenselement', MAX(id) ) FROM "autaut"."doelbindinggegevenselement" ;
SELECT SETVAL('kern.seq_gegeveninonderzoek', MAX(id) ) FROM "kern"."gegeveninonderzoek" ;
SELECT SETVAL('lev.seq_his_abonnement', MAX(id) ) FROM "lev"."his_abonnement" ;
SELECT SETVAL('lev.seq_his_abonnementsrtber', MAX(id) ) FROM "lev"."his_abonnementsrtber" ;
SELECT SETVAL('autaut.seq_his_authenticatiemiddel', MAX(id) ) FROM "autaut"."his_authenticatiemiddel" ;
SELECT SETVAL('autaut.seq_his_autorisatiebesluit', MAX(id) ) FROM "autaut"."his_autorisatiebesluit" ;
SELECT SETVAL('autaut.seq_his_autorisatiebesluitbijhau', MAX(id) ) FROM "autaut"."his_autorisatiebesluitbijhau" ;
SELECT SETVAL('kern.seq_his_betrouderschap', MAX(id) ) FROM "kern"."his_betrouderschap" ;
SELECT SETVAL('kern.seq_his_betrouderlijkgezag', MAX(id) ) FROM "kern"."his_betrouderlijkgezag" ;
SELECT SETVAL('autaut.seq_his_bijhautorisatie', MAX(id) ) FROM "autaut"."his_bijhautorisatie" ;
SELECT SETVAL('autaut.seq_his_bijhsituatie', MAX(id) ) FROM "autaut"."his_bijhsituatie" ;
SELECT SETVAL('kern.seq_his_doc', MAX(id) ) FROM "kern"."his_doc" ;
SELECT SETVAL('autaut.seq_his_doelbinding', MAX(id) ) FROM "autaut"."his_doelbinding" ;
SELECT SETVAL('kern.seq_his_partijgem', MAX(id) ) FROM "kern"."his_partijgem" ;
SELECT SETVAL('kern.seq_his_multirealiteitregel', MAX(id) ) FROM "kern"."his_multirealiteitregel" ;
SELECT SETVAL('kern.seq_his_onderzoek', MAX(id) ) FROM "kern"."his_onderzoek" ;
SELECT SETVAL('kern.seq_his_partij', MAX(id) ) FROM "kern"."his_partij" ;
SELECT SETVAL('kern.seq_his_persaanschr', MAX(id) ) FROM "kern"."his_persaanschr" ;
SELECT SETVAL('kern.seq_his_persadres', MAX(id) ) FROM "kern"."his_persadres" ;
SELECT SETVAL('kern.seq_his_persbijhgem', MAX(id) ) FROM "kern"."his_persbijhgem" ;
SELECT SETVAL('kern.seq_his_persbijhverantwoordelijk', MAX(id) ) FROM "kern"."his_persbijhverantwoordelijk" ;
SELECT SETVAL('kern.seq_his_perseuverkiezingen', MAX(id) ) FROM "kern"."his_perseuverkiezingen" ;
SELECT SETVAL('kern.seq_his_persgeboorte', MAX(id) ) FROM "kern"."his_persgeboorte" ;
SELECT SETVAL('kern.seq_his_persgeslachtsaand', MAX(id) ) FROM "kern"."his_persgeslachtsaand" ;
SELECT SETVAL('kern.seq_his_persgeslnaamcomp', MAX(id) ) FROM "kern"."his_persgeslnaamcomp" ;
SELECT SETVAL('kern.seq_his_persids', MAX(id) ) FROM "kern"."his_persids" ;
SELECT SETVAL('kern.seq_his_persimmigratie', MAX(id) ) FROM "kern"."his_persimmigratie" ;
SELECT SETVAL('kern.seq_his_persindicatie', MAX(id) ) FROM "kern"."his_persindicatie" ;
SELECT SETVAL('kern.seq_his_persinschr', MAX(id) ) FROM "kern"."his_persinschr" ;
SELECT SETVAL('kern.seq_his_persnation', MAX(id) ) FROM "kern"."his_persnation" ;
SELECT SETVAL('kern.seq_his_persopschorting', MAX(id) ) FROM "kern"."his_persopschorting" ;
SELECT SETVAL('kern.seq_his_persoverlijden', MAX(id) ) FROM "kern"."his_persoverlijden" ;
SELECT SETVAL('kern.seq_his_perspk', MAX(id) ) FROM "kern"."his_perspk" ;
SELECT SETVAL('kern.seq_his_persreisdoc', MAX(id) ) FROM "kern"."his_persreisdoc" ;
SELECT SETVAL('kern.seq_his_perssamengesteldenaam', MAX(id) ) FROM "kern"."his_perssamengesteldenaam" ;
SELECT SETVAL('kern.seq_his_persuitslnlkiesr', MAX(id) ) FROM "kern"."his_persuitslnlkiesr" ;
SELECT SETVAL('kern.seq_his_persverblijfsr', MAX(id) ) FROM "kern"."his_persverblijfsr" ;
SELECT SETVAL('kern.seq_his_persverificatie', MAX(id) ) FROM "kern"."his_persverificatie" ;
SELECT SETVAL('kern.seq_his_persvoornaam', MAX(id) ) FROM "kern"."his_persvoornaam" ;
SELECT SETVAL('brm.seq_his_regelsituatie', MAX(id) ) FROM "brm"."his_regelsituatie" ;
SELECT SETVAL('kern.seq_his_relatie', MAX(id) ) FROM "kern"."his_relatie" ;
SELECT SETVAL('autaut.seq_his_uitgeslotene', MAX(id) ) FROM "autaut"."his_uitgeslotene" ;
SELECT SETVAL('lev.seq_lev', MAX(id) ) FROM "lev"."lev" ;
SELECT SETVAL('lev.seq_levcommunicatie', MAX(id) ) FROM "lev"."levcommunicatie" ;
SELECT SETVAL('lev.seq_levpers', MAX(id) ) FROM "lev"."levpers" ;
SELECT SETVAL('kern.seq_multirealiteitregel', MAX(id) ) FROM "kern"."multirealiteitregel" ;
SELECT SETVAL('kern.seq_onderzoek', MAX(id) ) FROM "kern"."onderzoek" ;
SELECT SETVAL('kern.seq_partij', MAX(id) ) FROM "kern"."partij" ;
SELECT SETVAL('kern.seq_partijrol', MAX(id) ) FROM "kern"."partijrol" ;
SELECT SETVAL('kern.seq_pers', MAX(id) ) FROM "kern"."pers" ;
SELECT SETVAL('kern.seq_persadres', MAX(id) ) FROM "kern"."persadres" ;
SELECT SETVAL('kern.seq_persgeslnaamcomp', MAX(id) ) FROM "kern"."persgeslnaamcomp" ;
SELECT SETVAL('kern.seq_persindicatie', MAX(id) ) FROM "kern"."persindicatie" ;
SELECT SETVAL('kern.seq_persnation', MAX(id) ) FROM "kern"."persnation" ;
SELECT SETVAL('kern.seq_persreisdoc', MAX(id) ) FROM "kern"."persreisdoc" ;
SELECT SETVAL('kern.seq_persverificatie', MAX(id) ) FROM "kern"."persverificatie" ;
SELECT SETVAL('kern.seq_persvoornaam', MAX(id) ) FROM "kern"."persvoornaam" ;
SELECT SETVAL('brm.seq_regelsituatie', MAX(id) ) FROM "brm"."regelsituatie" ;
SELECT SETVAL('kern.seq_relatie', MAX(id) ) FROM "kern"."relatie" ;
SELECT SETVAL('kern.seq_sector', MAX(id) ) FROM "kern"."sector" ;
SELECT SETVAL('autaut.seq_uitgeslotene', MAX(id) ) FROM "autaut"."uitgeslotene" ;

COMMIT;

--- END  SierraTestdataGenerator 0.0.7-SNAPSHOT-r5660-b81 (11-10-2012 16:25.51) @ vrijdag 12 oktober 2012 8:31:25 uur CEST

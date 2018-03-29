--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Algemeen Structuur Aanvullend                                 --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

DROP TABLE IF EXISTS public.DBversion;
CREATE TABLE public.DBversion (
   ID             Smallint       NOT NULL,
   FullVersion    Varchar(200)   NOT NULL,    /* de volledige omschrijving van de versie om het moment dat de data generator werd gebouwd. */
   ReleaseVersion Varchar(64),                /* de (svn) release versie. */
   BuildTimestamp Varchar(32),                /* de timestamp dat de generator werd gedraaid */
   CONSTRAINT PK_DBVersion PRIMARY KEY (ID)
);

INSERT INTO public.DBversion (ID, FullVersion, ReleaseVersion, BuildTimestamp) VALUES (1, '${versie} ${build.qualifier} #${build.number}', '${versie}', '${built.on}');

-- Statistieken bijwerken
ANALYZE;


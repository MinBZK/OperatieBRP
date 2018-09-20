/* Main aanroepscript welke alle subscripts aanroept*/

/* Loggen versie */
SELECT version();

/* Bij iedere fout stoppen */
\set ON_ERROR_STOP

-- Forceer psql op UTF-8 lezen, onafhankelijk van client instellingen (.sql bestanden zijn UTF-8)
\encoding UTF-8

\set AUTOCOMMIT OFF
START TRANSACTION;

--------------------------------------------------------------------------------
-- Database instellingen controles:
DO $$
DECLARE
   l_var TEXT;
BEGIN
SHOW timezone INTO l_var;
IF l_var <> 'UTC' THEN
   RAISE EXCEPTION 'Database timezone moet UTC zijn.';
END IF;

SHOW SERVER_ENCODING INTO l_var;
IF l_var <> 'UTF8' THEN
   RAISE EXCEPTION 'Database encoding moet UTF-8 zijn.';
END IF;

SHOW LC_CTYPE INTO l_var;
IF NOT l_var LIKE '%UTF-8' THEN
   RAISE EXCEPTION 'Database LC_CTYPE moet UTF-8 zijn.';
END IF;

SHOW LC_COLLATE INTO l_var;
IF NOT l_var LIKE '%UTF-8' THEN
   RAISE EXCEPTION 'Database LC_COLLATE moet UTF-8 zijn.';
END IF;

END;
$$;

--------------------------------------------------------------------------------
-- Aanroep subscripts:
\echo PostgreSQL - Kern Structuur DDL
\i Kern/Kern_BRP_structuur.sql

\echo PostgreSQL - Kern Indexen DDL
\i Kern/Kern_BRP_indexen.sql

\echo PostgreSQL - Kern Statische Stamgegevens
\i Kern/Kern_BRP_statische_stamgegevens.sql

\echo PostgreSQL - Kern Dynamische Stamgegevens
\i Kern/Kern_BRP_dynamische_stamgegevens.sql

\echo PostgreSQL - Kern Structuur Aanvullend
\i Kern/Kern_BRP_structuur_aanvullend.sql

\echo PostgreSQL - Kern Rechten
\i Kern/Kern_BRP_rechten.sql

\echo PostgreSQL - Kern Custom changes
\i Kern/Kern_BRP_custom_changes.sql


COMMIT;

\echo Alle scripts uitgevoerd.
\echo
\echo Aantal verwachte tabellen per schema
\echo AutAut: 26
\echo BRM: 5
\echo Conv: 11
\echo IST: 4
\echo Kern: 109
\echo MigBlok: 2
\echo VerConv: 9
\echo
\echo Controle query:
select schemaname, count(*) from pg_catalog.pg_tables where schemaname in ('autaut','brm','conv','ist','kern','migblok','verconv') group by schemaname order by schemaname;
\echo 'select schemaname, count(*) from pg_catalog.pg_tables where schemaname in (''autaut'',''brm'',''conv'',''ist'',''kern'',''migblok'',''verconv'') group by schemaname order by schemaname;'

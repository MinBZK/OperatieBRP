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
\echo PostgreSQL - Algemeen Structuur Aanvullend
\i Alg/Algemeen_BRP_structuur_aanvullend.sql

\echo PostgreSQL - Algemeen Custom changes
\i Alg/Algemeen_BRP_custom_changes.sql


COMMIT;

\echo Alle scripts uitgevoerd.
\echo

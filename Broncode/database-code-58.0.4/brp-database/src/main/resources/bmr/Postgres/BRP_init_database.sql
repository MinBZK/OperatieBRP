--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Initialisatie database                                        --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION set_currentdatabase_to_utc() RETURNS integer AS
   $$
   DECLARE Result integer;
   BEGIN
   EXECUTE 'ALTER DATABASE "' || current_database() || '"' || ' SET TIMEZONE TO ''UTC''';
   RETURN 1;
   END
   $$ LANGUAGE plpgsql;

SELECT set_currentdatabase_to_utc();
DROP FUNCTION set_currentdatabase_to_utc();

-- Rollen aanmaken:
CREATE OR REPLACE FUNCTION CREATE_ROLE_IF_NOT_EXISTS(rolename TEXT) RETURNS VOID AS
$$
BEGIN
   IF NOT EXISTS (SELECT * FROM pg_roles WHERE lower(rolname) = lower(rolename)) THEN
      EXECUTE 'CREATE ROLE ' || rolename;
      RAISE NOTICE 'Role % created.', rolename;
   ELSE
      RAISE NOTICE 'Role % already exists.', rolename;
   END IF;
END;
$$ LANGUAGE plpgsql;

SELECT CREATE_ROLE_IF_NOT_EXISTS('BRP_Bijhouding');
SELECT CREATE_ROLE_IF_NOT_EXISTS('BRP_Lezen');
SELECT CREATE_ROLE_IF_NOT_EXISTS('BRP_Beheer');
DROP FUNCTION CREATE_ROLE_IF_NOT_EXISTS(rolename TEXT);


-- Unaccent
CREATE EXTENSION IF NOT EXISTS unaccent;

ALTER TEXT SEARCH DICTIONARY unaccent (RULES='brp_unaccent');

CREATE OR REPLACE FUNCTION brp_unaccent(text) RETURNS text AS
   $$
   SELECT UPPER(public.unaccent('public.unaccent', $1));
   $$  LANGUAGE SQL IMMUTABLE STRICT COST 1;


--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Rechten                                              --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

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
SELECT CREATE_ROLE_IF_NOT_EXISTS('BRP_ActiveMQ');
DROP FUNCTION CREATE_ROLE_IF_NOT_EXISTS(rolename TEXT);

-- Rechten toekennen
GRANT USAGE ON SCHEMA Prot TO BRP_Lezen;
GRANT USAGE ON SCHEMA Prot TO BRP_Bijhouding;

GRANT SELECT ON Prot.Levsaantek TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Prot.Levsaantek TO BRP_Bijhouding;
GRANT USAGE ON Prot.seq_Levsaantek TO BRP_Bijhouding;
GRANT SELECT ON Prot.LevsaantekPers TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Prot.LevsaantekPers TO BRP_Bijhouding;
GRANT USAGE ON Prot.seq_LevsaantekPers TO BRP_Bijhouding;

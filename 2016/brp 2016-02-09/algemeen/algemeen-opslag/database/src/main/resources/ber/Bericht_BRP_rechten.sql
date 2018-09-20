--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Rechten                                               --
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
GRANT USAGE ON SCHEMA Ber TO BRP_Lezen;
GRANT USAGE ON SCHEMA Ber TO BRP_Bijhouding;

GRANT SELECT ON Ber.Ber TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Ber TO BRP_Bijhouding;
GRANT USAGE ON Ber.seq_Ber TO BRP_Bijhouding;
GRANT SELECT ON Ber.BerPers TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.BerPers TO BRP_Bijhouding;
GRANT USAGE ON Ber.seq_BerPers TO BRP_Bijhouding;
GRANT SELECT ON Ber.Bijhresultaat TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Bijhresultaat TO BRP_Bijhouding;
GRANT SELECT ON Ber.Bijhsituatie TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Bijhsituatie TO BRP_Bijhouding;
GRANT SELECT ON Ber.Historievorm TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Historievorm TO BRP_Bijhouding;
GRANT SELECT ON Ber.Richting TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Richting TO BRP_Bijhouding;
GRANT SELECT ON Ber.SrtBer TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.SrtBer TO BRP_Bijhouding;
GRANT SELECT ON Ber.SrtMelding TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.SrtMelding TO BRP_Bijhouding;
GRANT SELECT ON Ber.SrtSynchronisatie TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.SrtSynchronisatie TO BRP_Bijhouding;
GRANT SELECT ON Ber.Verwerkingsresultaat TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Verwerkingsresultaat TO BRP_Bijhouding;
GRANT SELECT ON Ber.Verwerkingswijze TO BRP_Lezen;
GRANT SELECT, INSERT, UPDATE, DELETE ON Ber.Verwerkingswijze TO BRP_Bijhouding;

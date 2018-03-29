--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Rechten                                               --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

-- Rechten toekennen
GRANT USAGE ON SCHEMA Ber TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;

GRANT SELECT ON Ber.Ber TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Ber.Ber TO BRP_Bijhouding;
GRANT USAGE ON Ber.seq_Ber TO BRP_Bijhouding;
GRANT SELECT ON Ber.BerPers TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Ber.BerPers TO BRP_Bijhouding;

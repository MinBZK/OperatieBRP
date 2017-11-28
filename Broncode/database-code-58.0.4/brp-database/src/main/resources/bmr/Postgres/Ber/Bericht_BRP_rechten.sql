--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Rechten                                               --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

-- Rechten toekennen
GRANT USAGE ON SCHEMA Ber TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;

GRANT SELECT ON Ber.Ber TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Ber.Ber TO BRP_Bijhouding;
GRANT USAGE ON Ber.seq_Ber TO BRP_Bijhouding;
GRANT SELECT ON Ber.BerPers TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Ber.BerPers TO BRP_Bijhouding;

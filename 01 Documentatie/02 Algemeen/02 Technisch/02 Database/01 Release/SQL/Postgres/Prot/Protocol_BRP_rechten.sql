--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Rechten                                              --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

-- Rechten toekennen
GRANT USAGE ON SCHEMA Prot TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;

GRANT SELECT ON Prot.Levsaantek TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Prot.Levsaantek TO BRP_Bijhouding;
GRANT USAGE ON Prot.seq_Levsaantek TO BRP_Bijhouding;
GRANT SELECT ON Prot.LevsaantekPers TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Prot.LevsaantekPers TO BRP_Bijhouding;
GRANT SELECT ON Prot.ScopePatroon TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Prot.ScopePatroon TO BRP_Bijhouding;
GRANT USAGE ON Prot.seq_ScopePatroon TO BRP_Bijhouding;
GRANT SELECT ON Prot.ScopePatroonElement TO BRP_Lezen, BRP_Bijhouding, BRP_Beheer;
GRANT INSERT, UPDATE, DELETE ON Prot.ScopePatroonElement TO BRP_Bijhouding;
GRANT USAGE ON Prot.seq_ScopePatroonElement TO BRP_Bijhouding;

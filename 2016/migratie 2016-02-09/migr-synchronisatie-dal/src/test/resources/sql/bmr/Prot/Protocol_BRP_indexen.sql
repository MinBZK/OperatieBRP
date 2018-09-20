--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Indexen DDL                                          --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_Levsaantek_ToegangLevsautorisatie ON Prot.Levsaantek (ToegangLevsautorisatie); -- Index door foreign key
CREATE INDEX ix_Levsaantek_Dienst ON Prot.Levsaantek (Dienst); -- Index door foreign key
CREATE INDEX ix_Levsaantek_AdmHnd ON Prot.Levsaantek (AdmHnd); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LevsaantekPers_Levsaantek ON Prot.LevsaantekPers (Levsaantek); -- Index door foreign key
CREATE INDEX ix_LevsaantekPers_Pers ON Prot.LevsaantekPers (Pers); -- Index door foreign key

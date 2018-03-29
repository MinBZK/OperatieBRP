--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Indexen DDL                                          --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_Levsaantek_TsKlaarzettenLev ON Prot.Levsaantek USING BRIN (TsKlaarzettenLev); -- Index door expliciete index in model
CREATE INDEX ix_LevsaantekPers_Levsaantek ON Prot.LevsaantekPers USING BRIN (Levsaantek); -- Index door expliciete index in model
CREATE INDEX ix_LevsaantekPers_Pers ON Prot.LevsaantekPers (Pers); -- Index door expliciete index in model
CREATE INDEX ix_LevsaantekPers_TsKlaarzettenLev ON Prot.LevsaantekPers USING BRIN (TsKlaarzettenLev); -- Index door expliciete index in model

--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Indexen DDL                                          --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_Levsaantek_TsKlaarzettenLev ON Prot.Levsaantek USING BRIN (TsKlaarzettenLev); -- Index door expliciete index in model
CREATE INDEX ix_LevsaantekPers_Levsaantek ON Prot.LevsaantekPers USING BRIN (Levsaantek); -- Index door expliciete index in model
CREATE INDEX ix_LevsaantekPers_Pers ON Prot.LevsaantekPers (Pers); -- Index door expliciete index in model
CREATE INDEX ix_LevsaantekPers_TsKlaarzettenLev ON Prot.LevsaantekPers USING BRIN (TsKlaarzettenLev); -- Index door expliciete index in model

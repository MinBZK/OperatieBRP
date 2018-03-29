--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Indexen DDL                                           --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_Ber_Referentienr ON Ber.Ber (Referentienr); -- Index door expliciete index in model
CREATE INDEX ix_Ber_TsReg ON Ber.Ber USING BRIN (TsReg); -- Index door expliciete index in model
CREATE INDEX ix_BerPers_Ber ON Ber.BerPers USING BRIN (Ber); -- Index door expliciete index in model
CREATE INDEX ix_BerPers_TsReg ON Ber.BerPers USING BRIN (TsReg); -- Index door expliciete index in model

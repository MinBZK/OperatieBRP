--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Indexen DDL                                           --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_Ber_ZendendePartij ON Ber.Ber (ZendendePartij); -- Index door foreign key
CREATE INDEX ix_Ber_OntvangendePartij ON Ber.Ber (OntvangendePartij); -- Index door foreign key
CREATE INDEX ix_Ber_Levsautorisatie ON Ber.Ber (Levsautorisatie); -- Index door foreign key
CREATE INDEX ix_Ber_Dienst ON Ber.Ber (Dienst); -- Index door foreign key
CREATE INDEX ix_Ber_AdmHnd ON Ber.Ber (AdmHnd); -- Index door foreign key
CREATE INDEX ix_Ber_AntwoordOp ON Ber.Ber (AntwoordOp); -- Index door foreign key
CREATE INDEX ix_Ber_Referentienr ON Ber.Ber (Referentienr); -- Index door expliciete index in model
CREATE INDEX ix_Ber_TsOntv ON Ber.Ber (TsOntv); -- Index door expliciete index in model
CREATE INDEX ix_Ber_TsVerzending ON Ber.Ber (TsVerzending); -- Index door expliciete index in model
CREATE INDEX ix_Ber_ReferentienrU ON Ber.Ber (UPPER(Referentienr)); -- Index door expliciete index in model
CREATE INDEX ix_BerPers_Ber ON Ber.BerPers (Ber); -- Index door foreign key
CREATE INDEX ix_BerPers_Pers ON Ber.BerPers (Pers); -- Index door foreign key

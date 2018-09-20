--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Indexen DDL                                              --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_Dienst_Dienstbundel ON AutAut.Dienst (Dienstbundel); -- Index door foreign key
CREATE INDEX ix_Dienstbundel_Levsautorisatie ON AutAut.Dienstbundel (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelGroep_Dienstbundel ON AutAut.DienstbundelGroep (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelGroepAttr_DienstbundelGroep ON AutAut.DienstbundelGroepAttr (DienstbundelGroep); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelLO3Rubriek_Dienstbundel ON AutAut.DienstbundelLO3Rubriek (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Dienst_Dienst ON AutAut.His_Dienst (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienst_ActieInh ON AutAut.His_Dienst (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienst_ActieVerval ON AutAut.His_Dienst (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienst_ActieVervalTbvLevMuts ON AutAut.His_Dienst (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Dienst_Dienst_TsReg ON AutAut.His_Dienst (Dienst, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_Dienst_Dienst_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_Dienst
            WHERE
               ID <> NEW.ID
               AND Dienst = NEW.Dienst 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienst, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Dienst_Dienst_TsReg_tr ON AutAut.His_Dienst;
CREATE CONSTRAINT TRIGGER uc_His_Dienst_Dienst_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_Dienst DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_Dienst_Dienst_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstAttendering_Dienst ON AutAut.His_DienstAttendering (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstAttendering_ActieInh ON AutAut.His_DienstAttendering (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstAttendering_ActieVerval ON AutAut.His_DienstAttendering (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstAttendering_ActieVervalTbvLevMuts ON AutAut.His_DienstAttendering (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstAttendering_Dienst_TsReg ON AutAut.His_DienstAttendering (Dienst, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstAttendering_Dienst_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_DienstAttendering
            WHERE
               ID <> NEW.ID
               AND Dienst = NEW.Dienst 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienst, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstAttendering_Dienst_TsReg_tr ON AutAut.His_DienstAttendering;
CREATE CONSTRAINT TRIGGER uc_His_DienstAttendering_Dienst_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstAttendering DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstAttendering_Dienst_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstSelectie_Dienst ON AutAut.His_DienstSelectie (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstSelectie_ActieInh ON AutAut.His_DienstSelectie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstSelectie_ActieVerval ON AutAut.His_DienstSelectie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstSelectie_ActieVervalTbvLevMuts ON AutAut.His_DienstSelectie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstSelectie_Dienst_TsReg ON AutAut.His_DienstSelectie (Dienst, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstSelectie_Dienst_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_DienstSelectie
            WHERE
               ID <> NEW.ID
               AND Dienst = NEW.Dienst 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienst, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstSelectie_Dienst_TsReg_tr ON AutAut.His_DienstSelectie;
CREATE CONSTRAINT TRIGGER uc_His_DienstSelectie_Dienst_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstSelectie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstSelectie_Dienst_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Dienstbundel_Dienstbundel ON AutAut.His_Dienstbundel (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienstbundel_ActieInh ON AutAut.His_Dienstbundel (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienstbundel_ActieVerval ON AutAut.His_Dienstbundel (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienstbundel_ActieVervalTbvLevMuts ON AutAut.His_Dienstbundel (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Dienstbundel_Dienstbundel_TsReg ON AutAut.His_Dienstbundel (Dienstbundel, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_Dienstbundel_Dienstbundel_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_Dienstbundel
            WHERE
               ID <> NEW.ID
               AND Dienstbundel = NEW.Dienstbundel 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienstbundel, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Dienstbundel_Dienstbundel_TsReg_tr ON AutAut.His_Dienstbundel;
CREATE CONSTRAINT TRIGGER uc_His_Dienstbundel_Dienstbundel_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_Dienstbundel DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_Dienstbundel_Dienstbundel_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstbundelGroep_DienstbundelGroep ON AutAut.His_DienstbundelGroep (DienstbundelGroep); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroep_ActieInh ON AutAut.His_DienstbundelGroep (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroep_ActieVerval ON AutAut.His_DienstbundelGroep (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroep_ActieVervalTbvLevMuts ON AutAut.His_DienstbundelGroep (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstbundelGroep_DienstbundelGroep_TsReg ON AutAut.His_DienstbundelGroep (DienstbundelGroep, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstbundelGroep_DienstbundelGroep_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_DienstbundelGroep
            WHERE
               ID <> NEW.ID
               AND DienstbundelGroep = NEW.DienstbundelGroep 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.DienstbundelGroep, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstbundelGroep_DienstbundelGroep_TsReg_tr ON AutAut.His_DienstbundelGroep;
CREATE CONSTRAINT TRIGGER uc_His_DienstbundelGroep_DienstbundelGroep_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstbundelGroep DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstbundelGroep_DienstbundelGroep_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstbundelGroepAttr_DienstbundelGroepAttr ON AutAut.His_DienstbundelGroepAttr (DienstbundelGroepAttr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroepAttr_ActieInh ON AutAut.His_DienstbundelGroepAttr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroepAttr_ActieVerval ON AutAut.His_DienstbundelGroepAttr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroepAttr_ActieVervalTbvLevMuts ON AutAut.His_DienstbundelGroepAttr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg ON AutAut.His_DienstbundelGroepAttr (DienstbundelGroepAttr, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_DienstbundelGroepAttr
            WHERE
               ID <> NEW.ID
               AND DienstbundelGroepAttr = NEW.DienstbundelGroepAttr 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.DienstbundelGroepAttr, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg_tr ON AutAut.His_DienstbundelGroepAttr;
CREATE CONSTRAINT TRIGGER uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstbundelGroepAttr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek ON AutAut.His_DienstbundelLO3Rubriek (DienstbundelLO3Rubriek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelLO3Rubriek_ActieInh ON AutAut.His_DienstbundelLO3Rubriek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelLO3Rubriek_ActieVerval ON AutAut.His_DienstbundelLO3Rubriek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelLO3Rubriek_ActieVervalTbvLevMuts ON AutAut.His_DienstbundelLO3Rubriek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg ON AutAut.His_DienstbundelLO3Rubriek (DienstbundelLO3Rubriek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_DienstbundelLO3Rubriek
            WHERE
               ID <> NEW.ID
               AND DienstbundelLO3Rubriek = NEW.DienstbundelLO3Rubriek 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.DienstbundelLO3Rubriek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg_tr ON AutAut.His_DienstbundelLO3Rubriek;
CREATE CONSTRAINT TRIGGER uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstbundelLO3Rubriek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Levsautorisatie_Levsautorisatie ON AutAut.His_Levsautorisatie (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Levsautorisatie_ActieInh ON AutAut.His_Levsautorisatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Levsautorisatie_ActieVerval ON AutAut.His_Levsautorisatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Levsautorisatie_ActieVervalTbvLevMuts ON AutAut.His_Levsautorisatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Levsautorisatie_Levsautorisatie_TsReg ON AutAut.His_Levsautorisatie (Levsautorisatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_Levsautorisatie_Levsautorisatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_Levsautorisatie
            WHERE
               ID <> NEW.ID
               AND Levsautorisatie = NEW.Levsautorisatie 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Levsautorisatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Levsautorisatie_Levsautorisatie_TsReg_tr ON AutAut.His_Levsautorisatie;
CREATE CONSTRAINT TRIGGER uc_His_Levsautorisatie_Levsautorisatie_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_Levsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_Levsautorisatie_Levsautorisatie_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijFiatuitz_ActieInh ON AutAut.His_PartijFiatuitz (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijFiatuitz_ActieVerval ON AutAut.His_PartijFiatuitz (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijFiatuitz_ActieVervalTbvLevMuts ON AutAut.His_PartijFiatuitz (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_PartijFiatuitz_PartijBijhvoorstel ON AutAut.His_PartijFiatuitz (PartijBijhvoorstel); -- Index door foreign key
CREATE INDEX uc_His_PartijFiatuitz_PartijFiatuitz_TsReg ON AutAut.His_PartijFiatuitz (PartijFiatuitz, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_PartijFiatuitz_PartijFiatuitz_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_PartijFiatuitz
            WHERE
               ID <> NEW.ID
               AND PartijFiatuitz = NEW.PartijFiatuitz 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PartijFiatuitz, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijFiatuitz_PartijFiatuitz_TsReg_tr ON AutAut.His_PartijFiatuitz;
CREATE CONSTRAINT TRIGGER uc_His_PartijFiatuitz_PartijFiatuitz_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_PartijFiatuitz DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_PartijFiatuitz_PartijFiatuitz_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangBijhautorisatie_ActieInh ON AutAut.His_ToegangBijhautorisatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangBijhautorisatie_ActieVerval ON AutAut.His_ToegangBijhautorisatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangBijhautorisatie_ActieVervalTbvLevMuts ON AutAut.His_ToegangBijhautorisatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg ON AutAut.His_ToegangBijhautorisatie (ToegangBijhautorisatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_ToegangBijhautorisatie
            WHERE
               ID <> NEW.ID
               AND ToegangBijhautorisatie = NEW.ToegangBijhautorisatie 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.ToegangBijhautorisatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg_tr ON AutAut.His_ToegangBijhautorisatie;
CREATE CONSTRAINT TRIGGER uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_ToegangBijhautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_ToegangLevsautorisatie_ToegangLevsautorisatie ON AutAut.His_ToegangLevsautorisatie (ToegangLevsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangLevsautorisatie_ActieInh ON AutAut.His_ToegangLevsautorisatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangLevsautorisatie_ActieVerval ON AutAut.His_ToegangLevsautorisatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangLevsautorisatie_ActieVervalTbvLevMuts ON AutAut.His_ToegangLevsautorisatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg ON AutAut.His_ToegangLevsautorisatie (ToegangLevsautorisatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.His_ToegangLevsautorisatie
            WHERE
               ID <> NEW.ID
               AND ToegangLevsautorisatie = NEW.ToegangLevsautorisatie 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.ToegangLevsautorisatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg_tr ON AutAut.His_ToegangLevsautorisatie;
CREATE CONSTRAINT TRIGGER uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_ToegangLevsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg();

CREATE OR REPLACE FUNCTION AutAut.uc_Levsautorisatie_Naam() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Naam IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.Levsautorisatie
            WHERE
               ID <> NEW.ID
               AND Naam IS NOT DISTINCT FROM NEW.Naam 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Naam;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_Levsautorisatie_Naam_tr ON AutAut.Levsautorisatie;
CREATE CONSTRAINT TRIGGER uc_Levsautorisatie_Naam_tr AFTER INSERT OR UPDATE ON AutAut.Levsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_Levsautorisatie_Naam();

CREATE INDEX ix_PartijFiatuitz_Partij ON AutAut.PartijFiatuitz (Partij); -- Index door foreign key
CREATE INDEX ix_PartijFiatuitz_PartijBijhvoorstel ON AutAut.PartijFiatuitz (PartijBijhvoorstel); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Geautoriseerde ON AutAut.ToegangBijhautorisatie (Geautoriseerde); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Ondertekenaar ON AutAut.ToegangBijhautorisatie (Ondertekenaar); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Transporteur ON AutAut.ToegangBijhautorisatie (Transporteur); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Geautoriseerde ON AutAut.ToegangLevsautorisatie (Geautoriseerde); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Levsautorisatie ON AutAut.ToegangLevsautorisatie (Levsautorisatie); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Ondertekenaar ON AutAut.ToegangLevsautorisatie (Ondertekenaar); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Transporteur ON AutAut.ToegangLevsautorisatie (Transporteur); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Regelsituatie_ActieInh ON BRM.His_Regelsituatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Regelsituatie_ActieVerval ON BRM.His_Regelsituatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Regelsituatie_ActieVervalTbvLevMuts ON BRM.His_Regelsituatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Regelsituatie_Regelsituatie_TsReg ON BRM.His_Regelsituatie (Regelsituatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION BRM.uc_His_Regelsituatie_Regelsituatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM BRM.His_Regelsituatie
            WHERE
               ID <> NEW.ID
               AND Regelsituatie = NEW.Regelsituatie 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Regelsituatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Regelsituatie_Regelsituatie_TsReg_tr ON BRM.His_Regelsituatie;
CREATE CONSTRAINT TRIGGER uc_His_Regelsituatie_Regelsituatie_TsReg_tr AFTER INSERT OR UPDATE ON BRM.His_Regelsituatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE BRM.uc_His_Regelsituatie_Regelsituatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ConvRNIDeelnemer_Partij ON Conv.ConvRNIDeelnemer (Partij); -- Index door foreign key
CREATE OR REPLACE FUNCTION Conv.uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Aang IS NULL AND NEW.RdnWijzVerblijf IS NULL THEN
            SELECT COUNT(*) 
            FROM Conv.ConvAangifteAdresh
            WHERE
               ID <> NEW.ID
               AND Aang IS NOT DISTINCT FROM NEW.Aang 
               AND RdnWijzVerblijf IS NOT DISTINCT FROM NEW.RdnWijzVerblijf 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Aang, NEW.RdnWijzVerblijf;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf_tr ON Conv.ConvAangifteAdresh;
CREATE CONSTRAINT TRIGGER uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf_tr AFTER INSERT OR UPDATE ON Conv.ConvAangifteAdresh DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Conv.uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf();

CREATE OR REPLACE FUNCTION Conv.uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Geslach() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.AdellijkeTitel IS NULL AND NEW.Predicaat IS NULL THEN
            SELECT COUNT(*) 
            FROM Conv.ConvAdellijkeTitelPredikaat
            WHERE
               ID <> NEW.ID
               AND Geslachtsaand = NEW.Geslachtsaand 
               AND AdellijkeTitel IS NOT DISTINCT FROM NEW.AdellijkeTitel 
               AND Predicaat IS NOT DISTINCT FROM NEW.Predicaat 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Geslachtsaand, NEW.AdellijkeTitel, NEW.Predicaat;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Gesl_tr ON Conv.ConvAdellijkeTitelPredikaat;
CREATE CONSTRAINT TRIGGER uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Gesl_tr AFTER INSERT OR UPDATE ON Conv.ConvAdellijkeTitelPredikaat DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Conv.uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Geslach();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Partij_ActieInh ON Kern.His_Partij (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Partij_ActieVerval ON Kern.His_Partij (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Partij_ActieVervalTbvLevMuts ON Kern.His_Partij (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Partij_Partij_TsReg ON Kern.His_Partij (Partij, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Partij_Partij_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_Partij
            WHERE
               ID <> NEW.ID
               AND Partij = NEW.Partij 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Partij, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Partij_Partij_TsReg_tr ON Kern.His_Partij;
CREATE CONSTRAINT TRIGGER uc_His_Partij_Partij_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Partij DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Partij_Partij_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijBijhouding_ActieInh ON Kern.His_PartijBijhouding (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijBijhouding_ActieVerval ON Kern.His_PartijBijhouding (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijBijhouding_ActieVervalTbvLevMuts ON Kern.His_PartijBijhouding (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PartijBijhouding_Partij_TsReg ON Kern.His_PartijBijhouding (Partij, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PartijBijhouding_Partij_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PartijBijhouding
            WHERE
               ID <> NEW.ID
               AND Partij = NEW.Partij 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Partij, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijBijhouding_Partij_TsReg_tr ON Kern.His_PartijBijhouding;
CREATE CONSTRAINT TRIGGER uc_His_PartijBijhouding_Partij_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PartijBijhouding DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PartijBijhouding_Partij_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijRol_ActieInh ON Kern.His_PartijRol (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijRol_ActieVerval ON Kern.His_PartijRol (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijRol_ActieVervalTbvLevMuts ON Kern.His_PartijRol (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PartijRol_PartijRol_TsReg ON Kern.His_PartijRol (PartijRol, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PartijRol_PartijRol_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PartijRol
            WHERE
               ID <> NEW.ID
               AND PartijRol = NEW.PartijRol 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PartijRol, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijRol_PartijRol_TsReg_tr ON Kern.His_PartijRol;
CREATE CONSTRAINT TRIGGER uc_His_PartijRol_PartijRol_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PartijRol DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PartijRol_PartijRol_TsReg();

CREATE INDEX ix_Partij_OIN ON Kern.Partij (OIN); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_Partij_Naam() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Naam IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.Partij
            WHERE
               ID <> NEW.ID
               AND Naam IS NOT DISTINCT FROM NEW.Naam 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Naam;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_Partij_Naam_tr ON Kern.Partij;
CREATE CONSTRAINT TRIGGER uc_Partij_Naam_tr AFTER INSERT OR UPDATE ON Kern.Partij DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_Partij_Naam();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersAfnemerindicatie_Pers ON AutAut.PersAfnemerindicatie (Pers); -- Index door foreign key
CREATE INDEX ix_PersAfnemerindicatie_Afnemer ON AutAut.PersAfnemerindicatie (Afnemer); -- Index door foreign key
CREATE INDEX ix_PersAfnemerindicatie_Levsautorisatie ON AutAut.PersAfnemerindicatie (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_Stapel_Pers ON IST.Stapel (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_StapelRelatie_Stapel ON IST.StapelRelatie (Stapel); -- Index door foreign key
CREATE INDEX ix_StapelRelatie_Relatie ON IST.StapelRelatie (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_StapelVoorkomen_Stapel ON IST.StapelVoorkomen (Stapel); -- Index door foreign key
CREATE INDEX ix_StapelVoorkomen_AdmHnd ON IST.StapelVoorkomen (AdmHnd); -- Index door foreign key
CREATE INDEX ix_StapelVoorkomen_Partij ON IST.StapelVoorkomen (Partij); -- Index door foreign key
CREATE INDEX ix_Actie_AdmHnd ON Kern.Actie (AdmHnd); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ActieBron_Actie ON Kern.ActieBron (Actie); -- Index door foreign key
CREATE INDEX ix_ActieBron_Doc ON Kern.ActieBron (Doc); -- Index door foreign key
CREATE OR REPLACE FUNCTION Kern.uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Doc IS NULL AND NEW.Rechtsgrond IS NULL AND NEW.Rechtsgrondoms IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.ActieBron
            WHERE
               ID <> NEW.ID
               AND Actie = NEW.Actie 
               AND Doc IS NOT DISTINCT FROM NEW.Doc 
               AND Rechtsgrond IS NOT DISTINCT FROM NEW.Rechtsgrond 
               AND Rechtsgrondoms IS NOT DISTINCT FROM NEW.Rechtsgrondoms 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Actie, NEW.Doc, NEW.Rechtsgrond, NEW.Rechtsgrondoms;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms_tr ON Kern.ActieBron;
CREATE CONSTRAINT TRIGGER uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms_tr AFTER INSERT OR UPDATE ON Kern.ActieBron DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms();

CREATE INDEX ix_AdmHnd_TsLev ON Kern.AdmHnd (TsLev); -- Index door expliciete index in model
CREATE INDEX ix_AdmHnd_TsReg ON Kern.AdmHnd (TsReg); -- Index door expliciete index in model
CREATE INDEX ix_AdmHndGedeblokkeerdeMelding_AdmHnd ON Kern.AdmHndGedeblokkeerdeMelding (AdmHnd); -- Index door foreign key
CREATE INDEX ix_AdmHndGedeblokkeerdeMelding_GedeblokkeerdeMelding ON Kern.AdmHndGedeblokkeerdeMelding (GedeblokkeerdeMelding); -- Index door foreign key
CREATE INDEX ix_Betr_Relatie ON Kern.Betr (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_Betr_Pers ON Kern.Betr (Pers); -- Index door foreign key
CREATE INDEX ix_Betr_Pers ON Kern.Betr (Pers); -- Index door expliciete index in model
CREATE INDEX ix_GegevenInOnderzoek_Onderzoek ON Kern.GegevenInOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_GegevenInTerugmelding_Terugmelding ON Kern.GegevenInTerugmelding (Terugmelding); -- Index door foreign key
CREATE INDEX ix_Onderzoek_AdmHnd ON Kern.Onderzoek (AdmHnd); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PartijOnderzoek_Onderzoek ON Kern.PartijOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_Pers_AdmHnd ON Kern.Pers (AdmHnd); -- Index door foreign key
CREATE INDEX ix_Pers_ANr ON Kern.Pers (ANr); -- Index door expliciete index in model
CREATE INDEX ix_Pers_BSN ON Kern.Pers (BSN); -- Index door expliciete index in model
CREATE INDEX ix_Pers_GeslnaamstamU ON Kern.Pers (UPPER(Geslnaamstam)); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Pers ON Kern.PersAdres (Pers); -- Index door foreign key
CREATE INDEX ix_PersAdres_IdentcodeNraand ON Kern.PersAdres (IdentcodeNraand); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Gem_NOR_Huisnr_Huisletter_Huisnrtoevoeging_SrtU ON Kern.PersAdres (Gem, UPPER(NOR), Huisnr, UPPER(Huisletter), UPPER(Huisnrtoevoeging), Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Postcode_Huisnr_Huisletter_Huisnrtoevoeging_SrtU ON Kern.PersAdres (UPPER(Postcode), Huisnr, UPPER(Huisletter), UPPER(Huisnrtoevoeging), Srt); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersGeslnaamcomp_Pers ON Kern.PersGeslnaamcomp (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersIndicatie_Pers ON Kern.PersIndicatie (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersNation_Pers ON Kern.PersNation (Pers); -- Index door foreign key
CREATE INDEX ix_PersOnderzoek_Pers ON Kern.PersOnderzoek (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersOnderzoek_Onderzoek ON Kern.PersOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_PersReisdoc_Pers ON Kern.PersReisdoc (Pers); -- Index door foreign key
CREATE INDEX ix_PersVerificatie_Geverifieerde ON Kern.PersVerificatie (Geverifieerde); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersVerstrbeperking_Pers ON Kern.PersVerstrbeperking (Pers); -- Index door foreign key
CREATE OR REPLACE FUNCTION Kern.uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Partij IS NULL AND NEW.OmsDerde IS NULL AND NEW.GemVerordening IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.PersVerstrbeperking
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND Partij IS NOT DISTINCT FROM NEW.Partij 
               AND OmsDerde IS NOT DISTINCT FROM NEW.OmsDerde 
               AND GemVerordening IS NOT DISTINCT FROM NEW.GemVerordening 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.Partij, NEW.OmsDerde, NEW.GemVerordening;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening_tr ON Kern.PersVerstrbeperking;
CREATE CONSTRAINT TRIGGER uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening_tr AFTER INSERT OR UPDATE ON Kern.PersVerstrbeperking DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersVoornaam_Pers ON Kern.PersVoornaam (Pers); -- Index door foreign key
CREATE INDEX ix_PersCache_Pers ON Kern.PersCache (Pers); -- Index door foreign key
CREATE INDEX ix_Regelverantwoording_Actie ON Kern.Regelverantwoording (Actie); -- Index door foreign key
CREATE INDEX ix_Terugmelding_Pers ON Kern.Terugmelding (Pers); -- Index door foreign key
CREATE INDEX ix_Terugmelding_Onderzoek ON Kern.Terugmelding (Onderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3AandOuder_Ouder ON VerConv.LO3AandOuder (Ouder); -- Index door foreign key
CREATE INDEX ix_LO3Ber_Pers ON VerConv.LO3Ber (Pers); -- Index door foreign key
CREATE INDEX ix_LO3Ber_ANr ON VerConv.LO3Ber (ANr); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3Melding_LO3Voorkomen ON VerConv.LO3Melding (LO3Voorkomen); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3Voorkomen_LO3Ber ON VerConv.LO3Voorkomen (LO3Ber); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3Voorkomen_Actie ON VerConv.LO3Voorkomen (Actie); -- Index door foreign key
CREATE OR REPLACE FUNCTION VerConv.uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voorkome() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.LO3Stapelvolgnr IS NULL AND NEW.LO3Voorkomenvolgnr IS NULL THEN
            SELECT COUNT(*) 
            FROM VerConv.LO3Voorkomen
            WHERE
               ID <> NEW.ID
               AND LO3Ber = NEW.LO3Ber 
               AND LO3Categorie = NEW.LO3Categorie 
               AND LO3Stapelvolgnr IS NOT DISTINCT FROM NEW.LO3Stapelvolgnr 
               AND LO3Voorkomenvolgnr IS NOT DISTINCT FROM NEW.LO3Voorkomenvolgnr 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.LO3Ber, NEW.LO3Categorie, NEW.LO3Stapelvolgnr, NEW.LO3Voorkomenvolgnr;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voork_tr ON VerConv.LO3Voorkomen;
CREATE CONSTRAINT TRIGGER uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voork_tr AFTER INSERT OR UPDATE ON VerConv.LO3Voorkomen DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE VerConv.uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voorkome();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersAfnemerindicatie_PersAfnemerindicatie ON AutAut.His_PersAfnemerindicatie (PersAfnemerindicatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfnemerindicatie_DienstInh ON AutAut.His_PersAfnemerindicatie (DienstInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfnemerindicatie_DienstVerval ON AutAut.His_PersAfnemerindicatie (DienstVerval); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Betr_Betr ON Kern.His_Betr (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Betr_ActieInh ON Kern.His_Betr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Betr_ActieVerval ON Kern.His_Betr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Betr_ActieVervalTbvLevMuts ON Kern.His_Betr (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Doc_Doc ON Kern.His_Doc (Doc); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Doc_ActieInh ON Kern.His_Doc (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Doc_ActieVerval ON Kern.His_Doc (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Doc_ActieVervalTbvLevMuts ON Kern.His_Doc (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Doc_Doc_TsReg ON Kern.His_Doc (Doc, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Doc_Doc_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_Doc
            WHERE
               ID <> NEW.ID
               AND Doc = NEW.Doc 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Doc, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Doc_Doc_TsReg_tr ON Kern.His_Doc;
CREATE CONSTRAINT TRIGGER uc_His_Doc_Doc_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Doc DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Doc_Doc_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Onderzoek_Onderzoek ON Kern.His_Onderzoek (Onderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Onderzoek_ActieInh ON Kern.His_Onderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Onderzoek_ActieVerval ON Kern.His_Onderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Onderzoek_ActieVervalTbvLevMuts ON Kern.His_Onderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Onderzoek_Onderzoek_TsReg ON Kern.His_Onderzoek (Onderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Onderzoek_Onderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_Onderzoek
            WHERE
               ID <> NEW.ID
               AND Onderzoek = NEW.Onderzoek 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Onderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Onderzoek_Onderzoek_TsReg_tr ON Kern.His_Onderzoek;
CREATE CONSTRAINT TRIGGER uc_His_Onderzoek_Onderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Onderzoek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Onderzoek_Onderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_Onderzoek ON Kern.His_OnderzoekAfgeleidAdminis (Onderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_ActieInh ON Kern.His_OnderzoekAfgeleidAdminis (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_ActieVerval ON Kern.His_OnderzoekAfgeleidAdminis (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_ActieVervalTbvLevMuts ON Kern.His_OnderzoekAfgeleidAdminis (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_AdmHnd ON Kern.His_OnderzoekAfgeleidAdminis (AdmHnd); -- Index door foreign key
CREATE INDEX uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg ON Kern.His_OnderzoekAfgeleidAdminis (Onderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_OnderzoekAfgeleidAdminis
            WHERE
               ID <> NEW.ID
               AND Onderzoek = NEW.Onderzoek 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Onderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg_tr ON Kern.His_OnderzoekAfgeleidAdminis;
CREATE CONSTRAINT TRIGGER uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_OnderzoekAfgeleidAdminis DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OuderOuderlijkGezag_Betr ON Kern.His_OuderOuderlijkGezag (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieInh ON Kern.His_OuderOuderlijkGezag (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieVerval ON Kern.His_OuderOuderlijkGezag (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieAanpGel ON Kern.His_OuderOuderlijkGezag (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieVervalTbvLevMuts ON Kern.His_OuderOuderlijkGezag (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel ON Kern.His_OuderOuderlijkGezag (Betr, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_OuderOuderlijkGezag
            WHERE
               ID <> NEW.ID
               AND Betr = NEW.Betr 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Betr, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel_tr ON Kern.His_OuderOuderlijkGezag;
CREATE CONSTRAINT TRIGGER uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_OuderOuderlijkGezag DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OuderOuderschap_Betr ON Kern.His_OuderOuderschap (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieInh ON Kern.His_OuderOuderschap (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieVerval ON Kern.His_OuderOuderschap (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieAanpGel ON Kern.His_OuderOuderschap (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieVervalTbvLevMuts ON Kern.His_OuderOuderschap (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel ON Kern.His_OuderOuderschap (Betr, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_OuderOuderschap
            WHERE
               ID <> NEW.ID
               AND Betr = NEW.Betr 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Betr, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel_tr ON Kern.His_OuderOuderschap;
CREATE CONSTRAINT TRIGGER uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_OuderOuderschap DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PartijOnderzoek_PartijOnderzoek ON Kern.His_PartijOnderzoek (PartijOnderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijOnderzoek_ActieInh ON Kern.His_PartijOnderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijOnderzoek_ActieVerval ON Kern.His_PartijOnderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijOnderzoek_ActieVervalTbvLevMuts ON Kern.His_PartijOnderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PartijOnderzoek_PartijOnderzoek_TsReg ON Kern.His_PartijOnderzoek (PartijOnderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PartijOnderzoek_PartijOnderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PartijOnderzoek
            WHERE
               ID <> NEW.ID
               AND PartijOnderzoek = NEW.PartijOnderzoek 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PartijOnderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijOnderzoek_PartijOnderzoek_TsReg_tr ON Kern.His_PartijOnderzoek;
CREATE CONSTRAINT TRIGGER uc_His_PartijOnderzoek_PartijOnderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PartijOnderzoek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PartijOnderzoek_PartijOnderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersAfgeleidAdministrati_Pers ON Kern.His_PersAfgeleidAdministrati (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfgeleidAdministrati_ActieInh ON Kern.His_PersAfgeleidAdministrati (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfgeleidAdministrati_ActieVerval ON Kern.His_PersAfgeleidAdministrati (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfgeleidAdministrati_ActieVervalTbvLevMuts ON Kern.His_PersAfgeleidAdministrati (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_PersAfgeleidAdministrati_AdmHnd ON Kern.His_PersAfgeleidAdministrati (AdmHnd); -- Index door foreign key
CREATE INDEX uc_His_PersAfgeleidAdministrati_Pers_TsReg ON Kern.His_PersAfgeleidAdministrati (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersAfgeleidAdministrati_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersAfgeleidAdministrati
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersAfgeleidAdministrati_Pers_TsReg_tr ON Kern.His_PersAfgeleidAdministrati;
CREATE CONSTRAINT TRIGGER uc_His_PersAfgeleidAdministrati_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersAfgeleidAdministrati DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersAfgeleidAdministrati_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersBijhouding_Pers ON Kern.His_PersBijhouding (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieInh ON Kern.His_PersBijhouding (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieVerval ON Kern.His_PersBijhouding (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieAanpGel ON Kern.His_PersBijhouding (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieVervalTbvLevMuts ON Kern.His_PersBijhouding (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersBijhouding_Pers_TsReg_DatAanvGel ON Kern.His_PersBijhouding (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersBijhouding_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersBijhouding
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersBijhouding_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersBijhouding;
CREATE CONSTRAINT TRIGGER uc_His_PersBijhouding_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersBijhouding DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersBijhouding_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_Pers ON Kern.His_PersDeelnEUVerkiezingen (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_ActieInh ON Kern.His_PersDeelnEUVerkiezingen (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_ActieVerval ON Kern.His_PersDeelnEUVerkiezingen (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_ActieVervalTbvLevMuts ON Kern.His_PersDeelnEUVerkiezingen (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersDeelnEUVerkiezingen_Pers_TsReg ON Kern.His_PersDeelnEUVerkiezingen (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersDeelnEUVerkiezingen_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersDeelnEUVerkiezingen
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersDeelnEUVerkiezingen_Pers_TsReg_tr ON Kern.His_PersDeelnEUVerkiezingen;
CREATE CONSTRAINT TRIGGER uc_His_PersDeelnEUVerkiezingen_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersDeelnEUVerkiezingen DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersDeelnEUVerkiezingen_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeboorte_Pers ON Kern.His_PersGeboorte (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeboorte_ActieInh ON Kern.His_PersGeboorte (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeboorte_ActieVerval ON Kern.His_PersGeboorte (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeboorte_ActieVervalTbvLevMuts ON Kern.His_PersGeboorte (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersGeboorte_Pers_TsReg ON Kern.His_PersGeboorte (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeboorte_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersGeboorte
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersGeboorte_Pers_TsReg_tr ON Kern.His_PersGeboorte;
CREATE CONSTRAINT TRIGGER uc_His_PersGeboorte_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersGeboorte DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersGeboorte_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeslachtsaand_Pers ON Kern.His_PersGeslachtsaand (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieInh ON Kern.His_PersGeslachtsaand (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieVerval ON Kern.His_PersGeslachtsaand (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieAanpGel ON Kern.His_PersGeslachtsaand (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieVervalTbvLevMuts ON Kern.His_PersGeslachtsaand (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel ON Kern.His_PersGeslachtsaand (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersGeslachtsaand
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersGeslachtsaand;
CREATE CONSTRAINT TRIGGER uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersGeslachtsaand DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersIDs_Pers ON Kern.His_PersIDs (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieInh ON Kern.His_PersIDs (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieVerval ON Kern.His_PersIDs (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieAanpGel ON Kern.His_PersIDs (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieVervalTbvLevMuts ON Kern.His_PersIDs (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersIDs_Pers_TsReg_DatAanvGel ON Kern.His_PersIDs (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersIDs_ANr ON Kern.His_PersIDs (ANr); -- Index door expliciete index in model
CREATE INDEX ix_His_PersIDs_BSN ON Kern.His_PersIDs (BSN); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersIDs_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersIDs
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersIDs_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersIDs;
CREATE CONSTRAINT TRIGGER uc_His_PersIDs_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersIDs DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersIDs_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersInschr_Pers ON Kern.His_PersInschr (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersInschr_ActieInh ON Kern.His_PersInschr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersInschr_ActieVerval ON Kern.His_PersInschr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersInschr_ActieVervalTbvLevMuts ON Kern.His_PersInschr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersInschr_Pers_TsReg ON Kern.His_PersInschr (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersInschr_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersInschr
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersInschr_Pers_TsReg_tr ON Kern.His_PersInschr;
CREATE CONSTRAINT TRIGGER uc_His_PersInschr_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersInschr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersInschr_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersMigratie_Pers ON Kern.His_PersMigratie (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieInh ON Kern.His_PersMigratie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieVerval ON Kern.His_PersMigratie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieAanpGel ON Kern.His_PersMigratie (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieVervalTbvLevMuts ON Kern.His_PersMigratie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersMigratie_Pers_TsReg_DatAanvGel ON Kern.His_PersMigratie (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersMigratie_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersMigratie
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersMigratie_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersMigratie;
CREATE CONSTRAINT TRIGGER uc_His_PersMigratie_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersMigratie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersMigratie_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersNaamgebruik_Pers ON Kern.His_PersNaamgebruik (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNaamgebruik_ActieInh ON Kern.His_PersNaamgebruik (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNaamgebruik_ActieVerval ON Kern.His_PersNaamgebruik (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNaamgebruik_ActieVervalTbvLevMuts ON Kern.His_PersNaamgebruik (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersNaamgebruik_Pers_TsReg ON Kern.His_PersNaamgebruik (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNaamgebruik_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersNaamgebruik
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersNaamgebruik_Pers_TsReg_tr ON Kern.His_PersNaamgebruik;
CREATE CONSTRAINT TRIGGER uc_His_PersNaamgebruik_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersNaamgebruik DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersNaamgebruik_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersNrverwijzing_Pers ON Kern.His_PersNrverwijzing (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieInh ON Kern.His_PersNrverwijzing (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieVerval ON Kern.His_PersNrverwijzing (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieAanpGel ON Kern.His_PersNrverwijzing (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieVervalTbvLevMuts ON Kern.His_PersNrverwijzing (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel ON Kern.His_PersNrverwijzing (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersNrverwijzing
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersNrverwijzing;
CREATE CONSTRAINT TRIGGER uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersNrverwijzing DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersOverlijden_Pers ON Kern.His_PersOverlijden (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOverlijden_ActieInh ON Kern.His_PersOverlijden (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOverlijden_ActieVerval ON Kern.His_PersOverlijden (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOverlijden_ActieVervalTbvLevMuts ON Kern.His_PersOverlijden (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersOverlijden_Pers_TsReg ON Kern.His_PersOverlijden (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersOverlijden_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersOverlijden
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersOverlijden_Pers_TsReg_tr ON Kern.His_PersOverlijden;
CREATE CONSTRAINT TRIGGER uc_His_PersOverlijden_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersOverlijden DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersOverlijden_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersPK_Pers ON Kern.His_PersPK (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersPK_ActieInh ON Kern.His_PersPK (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersPK_ActieVerval ON Kern.His_PersPK (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersPK_ActieVervalTbvLevMuts ON Kern.His_PersPK (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersPK_Pers_TsReg ON Kern.His_PersPK (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersPK_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersPK
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersPK_Pers_TsReg_tr ON Kern.His_PersPK;
CREATE CONSTRAINT TRIGGER uc_His_PersPK_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersPK DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersPK_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersSamengesteldeNaam_Pers ON Kern.His_PersSamengesteldeNaam (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieInh ON Kern.His_PersSamengesteldeNaam (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieVerval ON Kern.His_PersSamengesteldeNaam (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieAanpGel ON Kern.His_PersSamengesteldeNaam (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieVervalTbvLevMuts ON Kern.His_PersSamengesteldeNaam (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel ON Kern.His_PersSamengesteldeNaam (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersSamengesteldeNaam_GeslnaamstamU ON Kern.His_PersSamengesteldeNaam (UPPER(Geslnaamstam)); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersSamengesteldeNaam
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersSamengesteldeNaam;
CREATE CONSTRAINT TRIGGER uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersSamengesteldeNaam DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersUitslKiesr_Pers ON Kern.His_PersUitslKiesr (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersUitslKiesr_ActieInh ON Kern.His_PersUitslKiesr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersUitslKiesr_ActieVerval ON Kern.His_PersUitslKiesr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersUitslKiesr_ActieVervalTbvLevMuts ON Kern.His_PersUitslKiesr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersUitslKiesr_Pers_TsReg ON Kern.His_PersUitslKiesr (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersUitslKiesr_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersUitslKiesr
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersUitslKiesr_Pers_TsReg_tr ON Kern.His_PersUitslKiesr;
CREATE CONSTRAINT TRIGGER uc_His_PersUitslKiesr_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersUitslKiesr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersUitslKiesr_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVerblijfsr_Pers ON Kern.His_PersVerblijfsr (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerblijfsr_ActieInh ON Kern.His_PersVerblijfsr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerblijfsr_ActieVerval ON Kern.His_PersVerblijfsr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerblijfsr_ActieVervalTbvLevMuts ON Kern.His_PersVerblijfsr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVerblijfsr_Pers_TsReg ON Kern.His_PersVerblijfsr (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVerblijfsr_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersVerblijfsr
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVerblijfsr_Pers_TsReg_tr ON Kern.His_PersVerblijfsr;
CREATE CONSTRAINT TRIGGER uc_His_PersVerblijfsr_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersVerblijfsr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVerblijfsr_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersAdres_PersAdres ON Kern.His_PersAdres (PersAdres); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieInh ON Kern.His_PersAdres (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieVerval ON Kern.His_PersAdres (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieAanpGel ON Kern.His_PersAdres (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieVervalTbvLevMuts ON Kern.His_PersAdres (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersAdres_PersAdres_TsReg_DatAanvGel ON Kern.His_PersAdres (PersAdres, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersAdres_PersAdres_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersAdres
            WHERE
               ID <> NEW.ID
               AND PersAdres = NEW.PersAdres 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersAdres, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersAdres_PersAdres_TsReg_DatAanvGel_tr ON Kern.His_PersAdres;
CREATE CONSTRAINT TRIGGER uc_His_PersAdres_PersAdres_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersAdres DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersAdres_PersAdres_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeslnaamcomp_PersGeslnaamcomp ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieInh ON Kern.His_PersGeslnaamcomp (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieVerval ON Kern.His_PersGeslnaamcomp (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieAanpGel ON Kern.His_PersGeslnaamcomp (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieVervalTbvLevMuts ON Kern.His_PersGeslnaamcomp (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersGeslnaamcomp
            WHERE
               ID <> NEW.ID
               AND PersGeslnaamcomp = NEW.PersGeslnaamcomp 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersGeslnaamcomp, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel_tr ON Kern.His_PersGeslnaamcomp;
CREATE CONSTRAINT TRIGGER uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersGeslnaamcomp DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersIndicatie_PersIndicatie ON Kern.His_PersIndicatie (PersIndicatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieInh ON Kern.His_PersIndicatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieVerval ON Kern.His_PersIndicatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieAanpGel ON Kern.His_PersIndicatie (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieVervalTbvLevMuts ON Kern.His_PersIndicatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel ON Kern.His_PersIndicatie (PersIndicatie, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersIndicatie
            WHERE
               ID <> NEW.ID
               AND PersIndicatie = NEW.PersIndicatie 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersIndicatie, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel_tr ON Kern.His_PersIndicatie;
CREATE CONSTRAINT TRIGGER uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersIndicatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersNation_PersNation ON Kern.His_PersNation (PersNation); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieInh ON Kern.His_PersNation (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieVerval ON Kern.His_PersNation (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieAanpGel ON Kern.His_PersNation (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieVervalTbvLevMuts ON Kern.His_PersNation (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersNation_PersNation_TsReg_DatAanvGel ON Kern.His_PersNation (PersNation, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNation_PersNation_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersNation
            WHERE
               ID <> NEW.ID
               AND PersNation = NEW.PersNation 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersNation, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersNation_PersNation_TsReg_DatAanvGel_tr ON Kern.His_PersNation;
CREATE CONSTRAINT TRIGGER uc_His_PersNation_PersNation_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersNation DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersNation_PersNation_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersOnderzoek_PersOnderzoek ON Kern.His_PersOnderzoek (PersOnderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOnderzoek_ActieInh ON Kern.His_PersOnderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOnderzoek_ActieVerval ON Kern.His_PersOnderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOnderzoek_ActieVervalTbvLevMuts ON Kern.His_PersOnderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersOnderzoek_PersOnderzoek_TsReg ON Kern.His_PersOnderzoek (PersOnderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersOnderzoek_PersOnderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersOnderzoek
            WHERE
               ID <> NEW.ID
               AND PersOnderzoek = NEW.PersOnderzoek 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersOnderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersOnderzoek_PersOnderzoek_TsReg_tr ON Kern.His_PersOnderzoek;
CREATE CONSTRAINT TRIGGER uc_His_PersOnderzoek_PersOnderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersOnderzoek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersOnderzoek_PersOnderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersReisdoc_PersReisdoc ON Kern.His_PersReisdoc (PersReisdoc); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersReisdoc_ActieInh ON Kern.His_PersReisdoc (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersReisdoc_ActieVerval ON Kern.His_PersReisdoc (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersReisdoc_ActieVervalTbvLevMuts ON Kern.His_PersReisdoc (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersReisdoc_PersReisdoc_TsReg ON Kern.His_PersReisdoc (PersReisdoc, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersReisdoc_PersReisdoc_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersReisdoc
            WHERE
               ID <> NEW.ID
               AND PersReisdoc = NEW.PersReisdoc 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersReisdoc, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersReisdoc_PersReisdoc_TsReg_tr ON Kern.His_PersReisdoc;
CREATE CONSTRAINT TRIGGER uc_His_PersReisdoc_PersReisdoc_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersReisdoc DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersReisdoc_PersReisdoc_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVerificatie_PersVerificatie ON Kern.His_PersVerificatie (PersVerificatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerificatie_ActieInh ON Kern.His_PersVerificatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerificatie_ActieVerval ON Kern.His_PersVerificatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerificatie_ActieVervalTbvLevMuts ON Kern.His_PersVerificatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVerificatie_PersVerificatie_TsReg ON Kern.His_PersVerificatie (PersVerificatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVerificatie_PersVerificatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersVerificatie
            WHERE
               ID <> NEW.ID
               AND PersVerificatie = NEW.PersVerificatie 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersVerificatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVerificatie_PersVerificatie_TsReg_tr ON Kern.His_PersVerificatie;
CREATE CONSTRAINT TRIGGER uc_His_PersVerificatie_PersVerificatie_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersVerificatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVerificatie_PersVerificatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVerstrbeperking_PersVerstrbeperking ON Kern.His_PersVerstrbeperking (PersVerstrbeperking); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerstrbeperking_ActieInh ON Kern.His_PersVerstrbeperking (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerstrbeperking_ActieVerval ON Kern.His_PersVerstrbeperking (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerstrbeperking_ActieVervalTbvLevMuts ON Kern.His_PersVerstrbeperking (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg ON Kern.His_PersVerstrbeperking (PersVerstrbeperking, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersVerstrbeperking
            WHERE
               ID <> NEW.ID
               AND PersVerstrbeperking = NEW.PersVerstrbeperking 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersVerstrbeperking, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg_tr ON Kern.His_PersVerstrbeperking;
CREATE CONSTRAINT TRIGGER uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersVerstrbeperking DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVoornaam_PersVoornaam ON Kern.His_PersVoornaam (PersVoornaam); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieInh ON Kern.His_PersVoornaam (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieVerval ON Kern.His_PersVoornaam (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieAanpGel ON Kern.His_PersVoornaam (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieVervalTbvLevMuts ON Kern.His_PersVoornaam (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel ON Kern.His_PersVoornaam (PersVoornaam, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersVoornaam
            WHERE
               ID <> NEW.ID
               AND PersVoornaam = NEW.PersVoornaam 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersVoornaam, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel_tr ON Kern.His_PersVoornaam;
CREATE CONSTRAINT TRIGGER uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersVoornaam DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Relatie_Relatie ON Kern.His_Relatie (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Relatie_ActieInh ON Kern.His_Relatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Relatie_ActieVerval ON Kern.His_Relatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Relatie_ActieVervalTbvLevMuts ON Kern.His_Relatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Relatie_Relatie_TsReg ON Kern.His_Relatie (Relatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Relatie_Relatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_Relatie
            WHERE
               ID <> NEW.ID
               AND Relatie = NEW.Relatie 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Relatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Relatie_Relatie_TsReg_tr ON Kern.His_Relatie;
CREATE CONSTRAINT TRIGGER uc_His_Relatie_Relatie_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Relatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Relatie_Relatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Terugmelding_Terugmelding ON Kern.His_Terugmelding (Terugmelding); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Terugmelding_ActieInh ON Kern.His_Terugmelding (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Terugmelding_ActieVerval ON Kern.His_Terugmelding (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Terugmelding_ActieVervalTbvLevMuts ON Kern.His_Terugmelding (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_Terugmelding_Onderzoek ON Kern.His_Terugmelding (Onderzoek); -- Index door foreign key
CREATE INDEX uc_His_Terugmelding_Terugmelding_TsReg ON Kern.His_Terugmelding (Terugmelding, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Terugmelding_Terugmelding_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_Terugmelding
            WHERE
               ID <> NEW.ID
               AND Terugmelding = NEW.Terugmelding 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Terugmelding, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Terugmelding_Terugmelding_TsReg_tr ON Kern.His_Terugmelding;
CREATE CONSTRAINT TRIGGER uc_His_Terugmelding_Terugmelding_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Terugmelding DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Terugmelding_Terugmelding_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_TerugmeldingContactpers_Terugmelding ON Kern.His_TerugmeldingContactpers (Terugmelding); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_TerugmeldingContactpers_ActieInh ON Kern.His_TerugmeldingContactpers (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_TerugmeldingContactpers_ActieVerval ON Kern.His_TerugmeldingContactpers (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_TerugmeldingContactpers_ActieVervalTbvLevMuts ON Kern.His_TerugmeldingContactpers (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_TerugmeldingContactpers_Terugmelding_TsReg ON Kern.His_TerugmeldingContactpers (Terugmelding, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_TerugmeldingContactpers_Terugmelding_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_TerugmeldingContactpers
            WHERE
               ID <> NEW.ID
               AND Terugmelding = NEW.Terugmelding 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Terugmelding, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_TerugmeldingContactpers_Terugmelding_TsReg_tr ON Kern.His_TerugmeldingContactpers;
CREATE CONSTRAINT TRIGGER uc_His_TerugmeldingContactpers_Terugmelding_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_TerugmeldingContactpers DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_TerugmeldingContactpers_Terugmelding_TsReg();


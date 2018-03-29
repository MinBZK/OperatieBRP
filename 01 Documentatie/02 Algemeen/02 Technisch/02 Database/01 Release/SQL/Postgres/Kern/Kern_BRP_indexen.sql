--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Indexen DDL                                              --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

CREATE INDEX ix_BijhouderFiatuitz_Bijhouder ON AutAut.BijhouderFiatuitz (Bijhouder); -- Index door foreign key
CREATE INDEX ix_BijhouderFiatuitz_BijhouderBijhvoorstel ON AutAut.BijhouderFiatuitz (BijhouderBijhvoorstel); -- Index door foreign key
CREATE OR REPLACE FUNCTION AutAut.uc_Bijhautorisatie_Naam() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Naam IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.Bijhautorisatie
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
DROP TRIGGER IF EXISTS uc_Bijhautorisatie_Naam_tr ON AutAut.Bijhautorisatie;
CREATE CONSTRAINT TRIGGER uc_Bijhautorisatie_Naam_tr AFTER INSERT OR UPDATE ON AutAut.Bijhautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_Bijhautorisatie_Naam();

CREATE INDEX ix_Dienst_Dienstbundel ON AutAut.Dienst (Dienstbundel); -- Index door foreign key
CREATE INDEX ix_Dienstbundel_Levsautorisatie ON AutAut.Dienstbundel (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelGroep_Dienstbundel ON AutAut.DienstbundelGroep (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelGroepAttr_DienstbundelGroep ON AutAut.DienstbundelGroepAttr (DienstbundelGroep); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelLO3Rubriek_Dienstbundel ON AutAut.DienstbundelLO3Rubriek (Dienstbundel); -- Index door foreign key
CREATE INDEX ix_His_BijhouderFiatuitz_BijhouderBijhvoorstel ON AutAut.His_BijhouderFiatuitz (BijhouderBijhvoorstel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Dienst_Dienst ON AutAut.His_Dienst (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstAttendering_Dienst ON AutAut.His_DienstAttendering (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstSel_Dienst ON AutAut.His_DienstSel (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstZoeken_Dienst ON AutAut.His_DienstZoeken (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Dienstbundel_Dienstbundel ON AutAut.His_Dienstbundel (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Levsautorisatie_Levsautorisatie ON AutAut.His_Levsautorisatie (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_ToegangLevsautorisatie_ToegangLevsautorisatie ON AutAut.His_ToegangLevsautorisatie (ToegangLevsautorisatie); -- Index door foreign key
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

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_Seltaak_Dienst ON AutAut.Seltaak (Dienst); -- Index door foreign key
CREATE INDEX ix_Seltaak_ToegangLevsautorisatie ON AutAut.Seltaak (ToegangLevsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ToegangBijhautorisatie_Geautoriseerde ON AutAut.ToegangBijhautorisatie (Geautoriseerde); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Ondertekenaar ON AutAut.ToegangBijhautorisatie (Ondertekenaar); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Transporteur ON AutAut.ToegangBijhautorisatie (Transporteur); -- Index door foreign key
CREATE OR REPLACE FUNCTION AutAut.uc_ToegangBijhautorisatie_Geautoriseerde_Bijhautorisatie_Ondert() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Ondertekenaar IS NULL OR NEW.Transporteur IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.ToegangBijhautorisatie
            WHERE
               ID <> NEW.ID
               AND Geautoriseerde = NEW.Geautoriseerde 
               AND Bijhautorisatie = NEW.Bijhautorisatie 
               AND Ondertekenaar IS NOT DISTINCT FROM NEW.Ondertekenaar 
               AND Transporteur IS NOT DISTINCT FROM NEW.Transporteur 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Geautoriseerde, NEW.Bijhautorisatie, NEW.Ondertekenaar, NEW.Transporteur;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ToegangBijhautorisatie_Geautoriseerde_Bijhautorisatie_Ond_tr ON AutAut.ToegangBijhautorisatie;
CREATE CONSTRAINT TRIGGER uc_ToegangBijhautorisatie_Geautoriseerde_Bijhautorisatie_Ond_tr AFTER INSERT OR UPDATE ON AutAut.ToegangBijhautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_ToegangBijhautorisatie_Geautoriseerde_Bijhautorisatie_Ondert();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ToegangLevsautorisatie_Geautoriseerde ON AutAut.ToegangLevsautorisatie (Geautoriseerde); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Levsautorisatie ON AutAut.ToegangLevsautorisatie (Levsautorisatie); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Ondertekenaar ON AutAut.ToegangLevsautorisatie (Ondertekenaar); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Transporteur ON AutAut.ToegangLevsautorisatie (Transporteur); -- Index door foreign key
CREATE OR REPLACE FUNCTION AutAut.uc_ToegangLevsautorisatie_Geautoriseerde_Levsautorisatie_Ondert() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Ondertekenaar IS NULL OR NEW.Transporteur IS NULL THEN
            SELECT COUNT(*) 
            FROM AutAut.ToegangLevsautorisatie
            WHERE
               ID <> NEW.ID
               AND Geautoriseerde = NEW.Geautoriseerde 
               AND Levsautorisatie = NEW.Levsautorisatie 
               AND Ondertekenaar IS NOT DISTINCT FROM NEW.Ondertekenaar 
               AND Transporteur IS NOT DISTINCT FROM NEW.Transporteur 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Geautoriseerde, NEW.Levsautorisatie, NEW.Ondertekenaar, NEW.Transporteur;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ToegangLevsautorisatie_Geautoriseerde_Levsautorisatie_Ond_tr ON AutAut.ToegangLevsautorisatie;
CREATE CONSTRAINT TRIGGER uc_ToegangLevsautorisatie_Geautoriseerde_Levsautorisatie_Ond_tr AFTER INSERT OR UPDATE ON AutAut.ToegangLevsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_ToegangLevsautorisatie_Geautoriseerde_Levsautorisatie_Ondert();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ConvRNIDeelnemer_Partij ON Conv.ConvRNIDeelnemer (Partij); -- Index door foreign key
CREATE OR REPLACE FUNCTION Conv.uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Aang IS NULL OR NEW.RdnWijzVerblijf IS NULL THEN
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
        IF NEW.AdellijkeTitel IS NULL OR NEW.Predicaat IS NULL THEN
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

CREATE INDEX ix_Partij_OIN ON Kern.Partij (OIN); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersAfnemerindicatie_Pers ON AutAut.PersAfnemerindicatie (Pers); -- Index door foreign key
CREATE INDEX ix_PersAfnemerindicatie_Partij ON AutAut.PersAfnemerindicatie (Partij); -- Index door foreign key
CREATE INDEX ix_PersAfnemerindicatie_Levsautorisatie ON AutAut.PersAfnemerindicatie (Levsautorisatie); -- Index door foreign key
CREATE INDEX ix_VrijBer_IndGelezen ON Beh.VrijBer (IndGelezen) WHERE IndGelezen = 'N'; -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_VrijBerPartij_VrijBer ON Beh.VrijBerPartij (VrijBer); -- Index door foreign key
CREATE INDEX ix_VrijBerPartij_Partij ON Beh.VrijBerPartij (Partij); -- Index door foreign key
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
        IF NEW.Doc IS NULL OR NEW.Rechtsgrond IS NULL OR NEW.Rechtsgrondoms IS NULL THEN
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

CREATE INDEX ix_AdmHnd_StatusLev_InLevering ON Kern.AdmHnd (StatusLev) WHERE StatusLev = 3; -- Index door expliciete index in model
CREATE INDEX ix_AdmHnd_StatusLev_TeLeveren ON Kern.AdmHnd (TsReg) WHERE StatusLev = 1; -- Index door expliciete index in model
CREATE INDEX ix_AdmHnd_TsLev ON Kern.AdmHnd (TsLev); -- Index door expliciete index in model
CREATE INDEX ix_AdmHnd_TsReg ON Kern.AdmHnd (TsReg); -- Index door expliciete index in model
CREATE INDEX ix_AdmHndGedeblokkeerdeRegel_AdmHnd ON Kern.AdmHndGedeblokkeerdeRegel (AdmHnd); -- Index door foreign key
CREATE INDEX ix_Betr_Relatie ON Kern.Betr (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_Betr_Pers ON Kern.Betr (Pers); -- Index door foreign key
CREATE INDEX ix_Betr_Pers ON Kern.Betr (Pers); -- Index door expliciete index in model
CREATE INDEX ix_GegevenInOnderzoek_Onderzoek ON Kern.GegevenInOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_Onderzoek_Pers ON Kern.Onderzoek (Pers); -- Index door foreign key
CREATE INDEX ix_Pers_AdmHnd ON Kern.Pers (AdmHnd); -- Index door foreign key
CREATE INDEX ix_Pers_ANr_Srt ON Kern.Pers (ANr, Srt) WHERE ANr IS NOT NULL; -- Index door expliciete index in model
CREATE INDEX ix_Pers_BSN_Srt ON Kern.Pers (BSN, Srt) WHERE BSN IS NOT NULL; -- Index door expliciete index in model
CREATE INDEX ix_Pers_BLAdresRegel2MigratieU ON Kern.Pers (brp_unaccent(BLAdresRegel2Migratie) varchar_pattern_ops); -- Index door expliciete index in model
CREATE INDEX ix_Pers_DatGeboorte_Geslnaamstam_SrtU ON Kern.Pers (DatGeboorte, brp_unaccent(Geslnaamstam) varchar_pattern_ops, Srt) WHERE DatGeboorte IS NOT NULL; -- Index door expliciete index in model
CREATE INDEX ix_Pers_GeslnaamstamNaamgebruik_SrtU ON Kern.Pers (brp_unaccent(GeslnaamstamNaamgebruik) varchar_pattern_ops, Srt); -- Index door expliciete index in model
CREATE INDEX ix_Pers_Geslnaamstam_SrtU ON Kern.Pers (brp_unaccent(Geslnaamstam) varchar_pattern_ops, Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Pers ON Kern.PersAdres (Pers); -- Index door foreign key
CREATE INDEX ix_PersAdres_AfgekorteNOR_Gem_Huisnr_Huisletter_Huisnrtoevoegin ON Kern.PersAdres (brp_unaccent(AfgekorteNOR) varchar_pattern_ops, Gem, Huisnr, brp_unaccent(Huisletter) varchar_pattern_ops, brp_unaccent(Huisnrtoevoeging) varchar_pattern_ops, Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_AfgekorteNOR_Wplnaam_Huisnr_Huisletter_Huisnrtoevo ON Kern.PersAdres (brp_unaccent(AfgekorteNOR) varchar_pattern_ops, brp_unaccent(Wplnaam) varchar_pattern_ops, Huisnr, brp_unaccent(Huisletter) varchar_pattern_ops, brp_unaccent(Huisnrtoevoeging) varchar_pattern_ops, Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_BLAdresRegel2U ON Kern.PersAdres (brp_unaccent(BLAdresRegel2) varchar_pattern_ops); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_IdentcodeAdresseerbaarObjectU ON Kern.PersAdres (brp_unaccent(IdentcodeAdresseerbaarObject) varchar_pattern_ops); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_IdentcodeNraandU ON Kern.PersAdres (brp_unaccent(IdentcodeNraand) varchar_pattern_ops); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Locoms_GemU ON Kern.PersAdres (brp_unaccent(Locoms) varchar_pattern_ops, Gem); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_NOR_Gem_Huisnr_Huisletter_Huisnrtoevoeging_SrtU ON Kern.PersAdres (brp_unaccent(NOR) varchar_pattern_ops, Gem, Huisnr, brp_unaccent(Huisletter) varchar_pattern_ops, brp_unaccent(Huisnrtoevoeging) varchar_pattern_ops, Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_NOR_Wplnaam_Huisnr_Huisletter_Huisnrtoevoeging_Srt ON Kern.PersAdres (brp_unaccent(NOR) varchar_pattern_ops, brp_unaccent(Wplnaam) varchar_pattern_ops, Huisnr, brp_unaccent(Huisletter) varchar_pattern_ops, brp_unaccent(Huisnrtoevoeging) varchar_pattern_ops, Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Postcode_Huisnr_Huisletter_Huisnrtoevoeging_SrtU ON Kern.PersAdres (brp_unaccent(Postcode) varchar_pattern_ops, Huisnr, brp_unaccent(Huisletter) varchar_pattern_ops, brp_unaccent(Huisnrtoevoeging) varchar_pattern_ops, Srt); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersBLPersnr_Pers ON Kern.PersBLPersnr (Pers); -- Index door foreign key
CREATE INDEX ix_PersBLPersnr_NrU ON Kern.PersBLPersnr (brp_unaccent(Nr) varchar_pattern_ops); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersGeslnaamcomp_Pers ON Kern.PersGeslnaamcomp (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersIndicatie_Pers ON Kern.PersIndicatie (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersNation_Pers ON Kern.PersNation (Pers); -- Index door foreign key
CREATE INDEX ix_PersReisdoc_Pers ON Kern.PersReisdoc (Pers); -- Index door foreign key
CREATE INDEX ix_PersReisdoc_Nr_Srt ON Kern.PersReisdoc (Nr, Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersVerificatie_Pers ON Kern.PersVerificatie (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersVerstrbeperking_Pers ON Kern.PersVerstrbeperking (Pers); -- Index door foreign key
CREATE OR REPLACE FUNCTION Kern.uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Partij IS NULL OR NEW.OmsDerde IS NULL OR NEW.GemVerordening IS NULL THEN
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
        IF NEW.LO3Stapelvolgnr IS NULL OR NEW.LO3Voorkomenvolgnr IS NULL THEN
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
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_GegevenInOnderzoek_GegevenInOnderzoek ON Kern.His_GegevenInOnderzoek (GegevenInOnderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_GegevenInOnderzoek_ActieInh ON Kern.His_GegevenInOnderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_GegevenInOnderzoek_ActieVerval ON Kern.His_GegevenInOnderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_GegevenInOnderzoek_ActieVervalTbvLevMuts ON Kern.His_GegevenInOnderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
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

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OuderOuderlijkGezag_Betr ON Kern.His_OuderOuderlijkGezag (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieInh ON Kern.His_OuderOuderlijkGezag (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieVerval ON Kern.His_OuderOuderlijkGezag (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieVervalTbvLevMuts ON Kern.His_OuderOuderlijkGezag (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieAanpGel ON Kern.His_OuderOuderlijkGezag (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel ON Kern.His_OuderOuderlijkGezag (Betr, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_OuderOuderlijkGezag
            WHERE
               ID <> NEW.ID
               AND Betr = NEW.Betr 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieVervalTbvLevMuts ON Kern.His_OuderOuderschap (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieAanpGel ON Kern.His_OuderOuderschap (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel ON Kern.His_OuderOuderschap (Betr, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_OuderOuderschap
            WHERE
               ID <> NEW.ID
               AND Betr = NEW.Betr 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieVervalTbvLevMuts ON Kern.His_PersBijhouding (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieAanpGel ON Kern.His_PersBijhouding (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersBijhouding_Pers_TsReg_DatAanvGel ON Kern.His_PersBijhouding (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersBijhouding_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersBijhouding
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieVervalTbvLevMuts ON Kern.His_PersGeslachtsaand (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieAanpGel ON Kern.His_PersGeslachtsaand (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel ON Kern.His_PersGeslachtsaand (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersGeslachtsaand
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieVervalTbvLevMuts ON Kern.His_PersIDs (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieAanpGel ON Kern.His_PersIDs (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersIDs_Pers_TsReg_DatAanvGel ON Kern.His_PersIDs (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersIDs_ANr ON Kern.His_PersIDs (ANr, TsVerval) WHERE ANr IS NOT NULL; -- Index door expliciete index in model
CREATE INDEX ix_His_PersIDs_BSN ON Kern.His_PersIDs (BSN, TsVerval) WHERE BSN IS NOT NULL; -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersIDs_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersIDs
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieVervalTbvLevMuts ON Kern.His_PersMigratie (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieAanpGel ON Kern.His_PersMigratie (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersMigratie_Pers_TsReg_DatAanvGel ON Kern.His_PersMigratie (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersMigratie_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersMigratie
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieVervalTbvLevMuts ON Kern.His_PersNrverwijzing (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieAanpGel ON Kern.His_PersNrverwijzing (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel ON Kern.His_PersNrverwijzing (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersNrverwijzing
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieVervalTbvLevMuts ON Kern.His_PersSamengesteldeNaam (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieAanpGel ON Kern.His_PersSamengesteldeNaam (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel ON Kern.His_PersSamengesteldeNaam (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersSamengesteldeNaam_GeslnaamstamU ON Kern.His_PersSamengesteldeNaam (brp_unaccent(Geslnaamstam) varchar_pattern_ops, TsVerval); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersSamengesteldeNaam
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieVervalTbvLevMuts ON Kern.His_PersAdres (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieAanpGel ON Kern.His_PersAdres (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersAdres_PersAdres_TsReg_DatAanvGel ON Kern.His_PersAdres (PersAdres, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersAdres_IdentcodeAdresseerbaarObjectU ON Kern.His_PersAdres (brp_unaccent(IdentcodeAdresseerbaarObject) varchar_pattern_ops, TsVerval); -- Index door expliciete index in model
CREATE INDEX ix_His_PersAdres_IdentcodeNraandU ON Kern.His_PersAdres (brp_unaccent(IdentcodeNraand) varchar_pattern_ops, TsVerval); -- Index door expliciete index in model
CREATE INDEX ix_His_PersAdres_Postcode_Huisnr_Huisletter_Huisnrtoevoeging_Sr ON Kern.His_PersAdres (brp_unaccent(Postcode) varchar_pattern_ops, Huisnr, brp_unaccent(Huisletter) varchar_pattern_ops, brp_unaccent(Huisnrtoevoeging) varchar_pattern_ops, Srt, TsVerval); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersAdres_PersAdres_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersAdres
            WHERE
               ID <> NEW.ID
               AND PersAdres = NEW.PersAdres 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND TsReg IS DISTINCT FROM TsVerval
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

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersBLPersnr_PersBLPersnr ON Kern.His_PersBLPersnr (PersBLPersnr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBLPersnr_ActieInh ON Kern.His_PersBLPersnr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBLPersnr_ActieVerval ON Kern.His_PersBLPersnr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBLPersnr_ActieVervalTbvLevMuts ON Kern.His_PersBLPersnr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersBLPersnr_PersBLPersnr_TsReg ON Kern.His_PersBLPersnr (PersBLPersnr, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersBLPersnr_PersBLPersnr_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersBLPersnr
            WHERE
               ID <> NEW.ID
               AND PersBLPersnr = NEW.PersBLPersnr 
               AND TsReg = NEW.TsReg 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersBLPersnr, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersBLPersnr_PersBLPersnr_TsReg_tr ON Kern.His_PersBLPersnr;
CREATE CONSTRAINT TRIGGER uc_His_PersBLPersnr_PersBLPersnr_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersBLPersnr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersBLPersnr_PersBLPersnr_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeslnaamcomp_PersGeslnaamcomp ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieInh ON Kern.His_PersGeslnaamcomp (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieVerval ON Kern.His_PersGeslnaamcomp (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieVervalTbvLevMuts ON Kern.His_PersGeslnaamcomp (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieAanpGel ON Kern.His_PersGeslnaamcomp (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersGeslnaamcomp
            WHERE
               ID <> NEW.ID
               AND PersGeslnaamcomp = NEW.PersGeslnaamcomp 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieVervalTbvLevMuts ON Kern.His_PersIndicatie (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieAanpGel ON Kern.His_PersIndicatie (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel ON Kern.His_PersIndicatie (PersIndicatie, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersIndicatie
            WHERE
               ID <> NEW.ID
               AND PersIndicatie = NEW.PersIndicatie 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieVervalTbvLevMuts ON Kern.His_PersNation (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieAanpGel ON Kern.His_PersNation (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersNation_PersNation_TsReg_DatAanvGel ON Kern.His_PersNation (PersNation, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNation_PersNation_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersNation
            WHERE
               ID <> NEW.ID
               AND PersNation = NEW.PersNation 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieVervalTbvLevMuts ON Kern.His_PersVoornaam (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieAanpGel ON Kern.His_PersVoornaam (ActieAanpGel); -- Index door foreign key
CREATE INDEX uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel ON Kern.His_PersVoornaam (PersVoornaam, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL AND (NEW.DatEindeGel IS NULL OR NEW.DatAanvGel <> NEW.DatEindeGel) AND (NEW.TsReg IS DISTINCT FROM NEW.TsVerval) THEN
            SELECT COUNT(*) 
            FROM Kern.His_PersVoornaam
            WHERE
               ID <> NEW.ID
               AND PersVoornaam = NEW.PersVoornaam 
               AND TsReg = NEW.TsReg 
               AND DatAanvGel = NEW.DatAanvGel 
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts 
               AND (DatEindeGel IS NULL OR DatAanvGel <> DatEindeGel)
               AND TsReg IS DISTINCT FROM TsVerval
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


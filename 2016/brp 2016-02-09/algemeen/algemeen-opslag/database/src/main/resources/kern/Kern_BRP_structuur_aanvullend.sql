--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Structuur Aanvullend                                     --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

-- Index op OGM velden
CREATE INDEX ix_His_PersAfnemerindicatie_TsReg_TsVerval ON AutAut.His_PersAfnemerindicatie (TsReg, TsVerval);

-- Door het gebruikt van standaard ORM software om het opslaan en lezen van objecten uit de database te realiseren,
-- is er voor GegevenInOnderzoek behoefte aan een extra kolom 'TblGegeven' die een 1-op-1 mapping representeert
-- tussen entiteit classen in de software en een waarde in de TblGegeven kolom in de database.
-- Deze kolom voegt geen extra informatie toe ten opzichte van de SrtGegeven kolom, en wordt daarom gerealiseerd
-- als een 'writeable view' gebaseerd op de tabellen GegevenInOnderzoek en Element.

CREATE VIEW Kern.GegevenInOnderzoekOrmMapping (ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven, TblObject, TblVoorkomen) AS
  SELECT ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven,
           COALESCE( (SELECT Tabel FROM Kern.Element E WHERE E.ID = GIO.Element), Element) TblObject,
           COALESCE( (SELECT HisTabel FROM Kern.Element E WHERE E.ID = GIO.Element), Element) TblVoorkomen
  FROM Kern.GegevenInOnderzoek GIO;

ALTER TABLE Kern.GegevenInOnderzoekOrmMapping ALTER COLUMN Id SET DEFAULT nextval('kern.seq_gegeveninonderzoek');

CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_INSERT AS ON INSERT TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
   INSERT INTO Kern.GegevenInOnderzoek (Id, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven) VALUES (NEW.Id, NEW.Onderzoek, NEW.Element, NEW.ObjectSleutelGegeven, NEW.VoorkomenSleutelGegeven)
);
CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_DELETE AS ON DELETE TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
  DELETE FROM kern.GegevenInOnderzoek WHERE Id = OLD.Id
);
CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_UPDATE AS ON UPDATE TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
  UPDATE kern.GegevenInOnderzoek SET Onderzoek = NEW.Onderzoek, Element = NEW.Element, ObjectSleutelGegeven = NEW.ObjectSleutelGegeven, VoorkomenSleutelGegeven = NEW.VoorkomenSleutelGegeven, Id = NEW.Id WHERE Id = OLD.Id
);


-- Opschonen objecten (onterecht) gemaakt in public schema door vorige releases (<BMR 35).
CREATE OR REPLACE FUNCTION BMR_drop_oude_objecten() RETURNS void AS $$
   DECLARE SQL TEXT;
BEGIN
   FOR SQL IN
      SELECT 'DROP FUNCTION IF EXISTS ' || ns.nspname || '.' || proname || '(' || oidvectortypes(proargtypes) || ');'
      FROM pg_proc proc
           JOIN pg_namespace ns ON (proc.pronamespace = ns.oid)
      WHERE
         ns.nspname = 'public'
         AND proname like 'block_dubbel\_%'
         AND NOT EXISTS(select * from pg_depend dep where dep.refobjid = proc.oid)
      ORDER BY proname
   LOOP
      EXECUTE SQL;
   END LOOP;
END $$ LANGUAGE plpgsql;

SELECT BMR_drop_oude_objecten();

DROP FUNCTION BMR_drop_oude_objecten();

-- Sequences van stamtabellen (her)zetten naar de juiste waarden
SELECT setval('AutAut.seq_BijhautorisatieSrtAdmHnd', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.BijhautorisatieSrtAdmHnd), false);
SELECT setval('AutAut.seq_Dienst', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Dienst), false);
SELECT setval('AutAut.seq_Dienstbundel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Dienstbundel), false);
SELECT setval('AutAut.seq_DienstbundelGroep', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelGroep), false);
SELECT setval('AutAut.seq_DienstbundelGroepAttr', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelGroepAttr), false);
SELECT setval('AutAut.seq_DienstbundelLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelLO3Rubriek), false);
SELECT setval('AutAut.seq_EffectAfnemerindicaties', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.EffectAfnemerindicaties), false);
SELECT setval('AutAut.seq_His_Dienst', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Dienst), false);
SELECT setval('AutAut.seq_His_DienstAttendering', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstAttendering), false);
SELECT setval('AutAut.seq_His_DienstSelectie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstSelectie), false);
SELECT setval('AutAut.seq_His_Dienstbundel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Dienstbundel), false);
SELECT setval('AutAut.seq_His_DienstbundelGroep', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstbundelGroep), false);
SELECT setval('AutAut.seq_His_DienstbundelGroepAttr', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstbundelGroepAttr), false);
SELECT setval('AutAut.seq_His_DienstbundelLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstbundelLO3Rubriek), false);
SELECT setval('AutAut.seq_His_Levsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Levsautorisatie), false);
SELECT setval('AutAut.seq_His_PartijFiatuitz', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_PartijFiatuitz), false);
SELECT setval('AutAut.seq_His_ToegangBijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_ToegangBijhautorisatie), false);
SELECT setval('AutAut.seq_His_ToegangLevsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_ToegangLevsautorisatie), false);
SELECT setval('AutAut.seq_Levsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Levsautorisatie), false);
SELECT setval('AutAut.seq_PartijFiatuitz', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.PartijFiatuitz), false);
SELECT setval('AutAut.seq_ToegangBijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.ToegangBijhautorisatie), false);
SELECT setval('AutAut.seq_ToegangLevsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.ToegangLevsautorisatie), false);
SELECT setval('BRM.seq_His_Regelsituatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM BRM.His_Regelsituatie), false);
SELECT setval('BRM.seq_Regelsituatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM BRM.Regelsituatie), false);
SELECT setval('Conv.seq_ConvAandInhingVermissingReis', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvAandInhingVermissingReis), false);
SELECT setval('Conv.seq_ConvAangifteAdresh', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvAangifteAdresh), false);
SELECT setval('Conv.seq_ConvAdellijkeTitelPredikaat', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvAdellijkeTitelPredikaat), false);
SELECT setval('Conv.seq_ConvLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvLO3Rubriek), false);
SELECT setval('Conv.seq_ConvRNIDeelnemer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRNIDeelnemer), false);
SELECT setval('Conv.seq_ConvRdnBeeindigenNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnBeeindigenNation), false);
SELECT setval('Conv.seq_ConvRdnOntbindingHuwelijkGer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnOntbindingHuwelijkGer), false);
SELECT setval('Conv.seq_ConvRdnOpnameNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnOpnameNation), false);
SELECT setval('Conv.seq_ConvRdnOpschorting', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnOpschorting), false);
SELECT setval('Conv.seq_ConvSrtNLReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvSrtNLReisdoc), false);
SELECT setval('Conv.seq_ConvVoorvoegsel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvVoorvoegsel), false);
SELECT setval('IST.seq_Autorisatietabel', (SELECT COALESCE(MAX(ID)+1, 1) FROM IST.Autorisatietabel), false);
SELECT setval('Kern.seq_AandInhingVermissingReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AandInhingVermissingReisdoc), false);
SELECT setval('Kern.seq_AandVerblijfsr', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AandVerblijfsr), false);
SELECT setval('Kern.seq_Aang', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Aang), false);
SELECT setval('Kern.seq_AdellijkeTitel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AdellijkeTitel), false);
SELECT setval('Kern.seq_AuttypeVanAfgifteReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AuttypeVanAfgifteReisdoc), false);
SELECT setval('Kern.seq_Gem', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Gem), false);
SELECT setval('Kern.seq_His_Partij', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_Partij), false);
SELECT setval('Kern.seq_His_PartijBijhouding', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijBijhouding), false);
SELECT setval('Kern.seq_His_PartijRol', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijRol), false);
SELECT setval('Kern.seq_Koppelvlak', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Koppelvlak), false);
SELECT setval('Kern.seq_LandGebied', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.LandGebied), false);
SELECT setval('Kern.seq_Nation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Nation), false);
SELECT setval('Kern.seq_Partij', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Partij), false);
SELECT setval('Kern.seq_PartijRol', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.PartijRol), false);
SELECT setval('Kern.seq_Plaats', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Plaats), false);
SELECT setval('Kern.seq_Predicaat', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Predicaat), false);
SELECT setval('Kern.seq_RdnEindeRelatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnEindeRelatie), false);
SELECT setval('Kern.seq_RdnVerkNLNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnVerkNLNation), false);
SELECT setval('Kern.seq_RdnVerliesNLNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnVerliesNLNation), false);
SELECT setval('Kern.seq_RdnWijzVerblijf', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnWijzVerblijf), false);
SELECT setval('Kern.seq_Rechtsgrond', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Rechtsgrond), false);
SELECT setval('Kern.seq_SrtDoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtDoc), false);
SELECT setval('Kern.seq_SrtNLReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtNLReisdoc), false);
SELECT setval('Kern.seq_Voorvoegsel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Voorvoegsel), false);
SELECT setval('MigBlok.seq_RdnBlokkering', (SELECT COALESCE(MAX(ID)+1, 1) FROM MigBlok.RdnBlokkering), false);

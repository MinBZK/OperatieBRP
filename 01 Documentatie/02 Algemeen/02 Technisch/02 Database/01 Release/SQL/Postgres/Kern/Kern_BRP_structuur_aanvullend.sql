--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Structuur Aanvullend                                     --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

-- Index op OGM velden
CREATE INDEX ix_His_PersAfnemerindicatie_TsReg_TsVerval ON AutAut.His_PersAfnemerindicatie (TsReg, TsVerval);

-- Door het gebruikt van standaard ORM software om het opslaan en lezen van objecten uit de database te realiseren,
-- is er voor GegevenInOnderzoek behoefte aan een extra kolom 'TblGegeven' die een 1-op-1 mapping representeert
-- tussen entiteit classen in de software en een waarde in de TblGegeven kolom in de database.
-- Deze kolom voegt geen extra informatie toe ten opzichte van de SrtGegeven kolom, en wordt daarom gerealiseerd
-- als een 'writeable view' gebaseerd op de tabellen GegevenInOnderzoek en Element.

CREATE VIEW Kern.GegevenInOnderzoekOrmMapping (ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven, TblObject, TblVoorkomen, IndAG) AS
  SELECT ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven,
           COALESCE( (SELECT Tabel FROM Kern.Element E WHERE E.ID = GIO.Element), Element) TblObject,
           COALESCE( (SELECT HisTabel FROM Kern.Element E WHERE E.ID = GIO.Element), Element) TblVoorkomen,
           IndAG
  FROM Kern.GegevenInOnderzoek GIO;

ALTER TABLE Kern.GegevenInOnderzoekOrmMapping ALTER COLUMN Id SET DEFAULT nextval('kern.seq_gegeveninonderzoek');

CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_INSERT AS ON INSERT TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
   INSERT INTO Kern.GegevenInOnderzoek (ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven, IndAG) VALUES (NEW.ID, NEW.Onderzoek, NEW.Element, NEW.ObjectSleutelGegeven, NEW.VoorkomenSleutelGegeven, NEW.IndAG)
);
CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_DELETE AS ON DELETE TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
  DELETE FROM Kern.GegevenInOnderzoek WHERE ID = OLD.ID
);
CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_UPDATE AS ON UPDATE TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
  UPDATE Kern.GegevenInOnderzoek SET Onderzoek = NEW.Onderzoek, Element = NEW.Element, ObjectSleutelGegeven = NEW.ObjectSleutelGegeven, VoorkomenSleutelGegeven = NEW.VoorkomenSleutelGegeven, ID = NEW.ID, IndAG = NEW.IndAG WHERE ID = OLD.ID
);


-- Sequences van stamtabellen (her)zetten naar de juiste waarden
SELECT setval('AutAut.seq_Bijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Bijhautorisatie), false);
SELECT setval('AutAut.seq_BijhautorisatieSrtAdmHnd', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.BijhautorisatieSrtAdmHnd), false);
SELECT setval('AutAut.seq_BijhouderFiatuitz', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.BijhouderFiatuitz), false);
SELECT setval('AutAut.seq_Dienst', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Dienst), false);
SELECT setval('AutAut.seq_Dienstbundel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Dienstbundel), false);
SELECT setval('AutAut.seq_DienstbundelGroep', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelGroep), false);
SELECT setval('AutAut.seq_DienstbundelGroepAttr', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelGroepAttr), false);
SELECT setval('AutAut.seq_DienstbundelLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelLO3Rubriek), false);
SELECT setval('AutAut.seq_EenheidSelinterval', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.EenheidSelinterval), false);
SELECT setval('AutAut.seq_EffectAfnemerindicaties', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.EffectAfnemerindicaties), false);
SELECT setval('AutAut.seq_His_Bijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Bijhautorisatie), false);
SELECT setval('AutAut.seq_His_BijhautorisatieSrtAdmHnd', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_BijhautorisatieSrtAdmHnd), false);
SELECT setval('AutAut.seq_His_BijhouderFiatuitz', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_BijhouderFiatuitz), false);
SELECT setval('AutAut.seq_His_Dienst', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Dienst), false);
SELECT setval('AutAut.seq_His_DienstAttendering', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstAttendering), false);
SELECT setval('AutAut.seq_His_DienstSel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstSel), false);
SELECT setval('AutAut.seq_His_DienstZoeken', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstZoeken), false);
SELECT setval('AutAut.seq_His_Dienstbundel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Dienstbundel), false);
SELECT setval('AutAut.seq_His_Levsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Levsautorisatie), false);
SELECT setval('AutAut.seq_His_Seltaak', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Seltaak), false);
SELECT setval('AutAut.seq_His_SeltaakStatus', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_SeltaakStatus), false);
SELECT setval('AutAut.seq_His_ToegangBijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_ToegangBijhautorisatie), false);
SELECT setval('AutAut.seq_His_ToegangLevsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_ToegangLevsautorisatie), false);
SELECT setval('AutAut.seq_LeverwijzeSel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.LeverwijzeSel), false);
SELECT setval('AutAut.seq_Levsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Levsautorisatie), false);
SELECT setval('AutAut.seq_Selrun', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Selrun), false);
SELECT setval('AutAut.seq_Seltaak', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Seltaak), false);
SELECT setval('AutAut.seq_SeltaakStatus', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.SeltaakStatus), false);
SELECT setval('AutAut.seq_SrtSel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.SrtSel), false);
SELECT setval('AutAut.seq_ToegangBijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.ToegangBijhautorisatie), false);
SELECT setval('AutAut.seq_ToegangLevsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.ToegangLevsautorisatie), false);
SELECT setval('Beh.seq_SrtBerVrijBer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Beh.SrtBerVrijBer), false);
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
SELECT setval('Kern.seq_AutVanAfgifteBLPersnr', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AutVanAfgifteBLPersnr), false);
SELECT setval('Kern.seq_AuttypeVanAfgifteReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AuttypeVanAfgifteReisdoc), false);
SELECT setval('Kern.seq_Gem', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Gem), false);
SELECT setval('Kern.seq_His_Partij', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_Partij), false);
SELECT setval('Kern.seq_His_PartijBijhouding', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijBijhouding), false);
SELECT setval('Kern.seq_His_PartijRol', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijRol), false);
SELECT setval('Kern.seq_His_PartijVrijBer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijVrijBer), false);
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
SELECT setval('Kern.seq_SrtActieBrongebruik', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtActieBrongebruik), false);
SELECT setval('Kern.seq_SrtBerElement', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtBerElement), false);
SELECT setval('Kern.seq_SrtDoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtDoc), false);
SELECT setval('Kern.seq_SrtNLReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtNLReisdoc), false);
SELECT setval('Kern.seq_SrtPartij', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtPartij), false);
SELECT setval('Kern.seq_SrtVrijBer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtVrijBer), false);
SELECT setval('Kern.seq_VersieStufBG', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.VersieStufBG), false);
SELECT setval('Kern.seq_VertalingBersrtBRP', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.VertalingBersrtBRP), false);
SELECT setval('Kern.seq_Verwerkingssrt', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Verwerkingssrt), false);
SELECT setval('Kern.seq_Voorvoegsel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Voorvoegsel), false);
SELECT setval('Kern.seq_Zoekoptie', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Zoekoptie), false);
SELECT setval('MigBlok.seq_RdnBlokkering', (SELECT COALESCE(MAX(ID)+1, 1) FROM MigBlok.RdnBlokkering), false);

-- Statistieken
ALTER TABLE Kern.Actie SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 54000000, autovacuum_analyze_threshold = 54000000);
ALTER TABLE Kern.ActieBron SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 54000000, autovacuum_analyze_threshold = 54000000);
ALTER TABLE Kern.AdmHnd SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 46000, autovacuum_analyze_threshold = 46000);
ALTER TABLE Kern.Betr SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 224000, autovacuum_analyze_threshold = 224000);
ALTER TABLE Kern.Doc SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 54000000, autovacuum_analyze_threshold = 54000000);
ALTER TABLE Kern.GegevenInOnderzoek SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 80000, autovacuum_analyze_threshold = 80000);
ALTER TABLE VerConv.LO3AandOuder SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 880000, autovacuum_analyze_threshold = 880000);
ALTER TABLE VerConv.LO3Ber SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 4800000, autovacuum_analyze_threshold = 4800000);
ALTER TABLE VerConv.LO3Melding SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 370000, autovacuum_analyze_threshold = 370000);
ALTER TABLE VerConv.LO3Voorkomen SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 660000, autovacuum_analyze_threshold = 660000);
ALTER TABLE Kern.Onderzoek SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 200, autovacuum_analyze_threshold = 200);
ALTER TABLE Kern.Pers ALTER COLUMN Srt SET statistics 2000;
ALTER TABLE Kern.Pers SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE Kern.PersAdres ALTER COLUMN Pers SET statistics 2000;
ALTER TABLE Kern.PersAdres SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE AutAut.PersAfnemerindicatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 8000000, autovacuum_analyze_threshold = 8000000);
ALTER TABLE Kern.PersBLPersnr SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 18500, autovacuum_analyze_threshold = 18500);
ALTER TABLE Kern.PersGeslnaamcomp ALTER COLUMN Pers SET statistics 2000;
ALTER TABLE Kern.PersGeslnaamcomp SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE Kern.PersIndicatie SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 5000, autovacuum_analyze_threshold = 5000);
ALTER TABLE Kern.PersNation SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE Kern.PersReisdoc ALTER COLUMN Srt SET statistics 2000;
ALTER TABLE Kern.PersReisdoc SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 88000, autovacuum_analyze_threshold = 88000);
ALTER TABLE Kern.PersVerificatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE Kern.PersVoornaam SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1500000, autovacuum_analyze_threshold = 1500000);
ALTER TABLE Kern.PersCache SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE Kern.Relatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 900000, autovacuum_analyze_threshold = 900000);
ALTER TABLE IST.Stapel SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1700000, autovacuum_analyze_threshold = 1700000);
ALTER TABLE IST.StapelRelatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1700000, autovacuum_analyze_threshold = 1700000);
ALTER TABLE IST.StapelVoorkomen SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 2100000, autovacuum_analyze_threshold = 2100000);
ALTER TABLE Kern.His_Betr SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 2240000, autovacuum_analyze_threshold = 2240000);
ALTER TABLE Kern.His_OuderOuderschap SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 800000, autovacuum_analyze_threshold = 800000);
ALTER TABLE Kern.His_OuderOuderlijkGezag SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 400000, autovacuum_analyze_threshold = 400000);
ALTER TABLE Kern.His_GegevenInOnderzoek SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 84000, autovacuum_analyze_threshold = 84000);
ALTER TABLE Kern.His_Onderzoek SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 8000, autovacuum_analyze_threshold = 8000);
ALTER TABLE Kern.His_PersAfgeleidAdministrati SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
ALTER TABLE Kern.His_PersIDs ALTER COLUMN Pers SET statistics 1000;
ALTER TABLE Kern.His_PersIDs SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1000000, autovacuum_analyze_threshold = 1000000);
ALTER TABLE Kern.His_PersSamengesteldeNaam SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 800000, autovacuum_analyze_threshold = 800000);
ALTER TABLE Kern.His_PersGeboorte SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
ALTER TABLE Kern.His_PersGeslachtsaand SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
ALTER TABLE Kern.His_PersInschr SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
ALTER TABLE Kern.His_PersNrverwijzing SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 1800, autovacuum_analyze_threshold = 1800);
ALTER TABLE Kern.His_PersBijhouding SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 600000, autovacuum_analyze_threshold = 600000);
ALTER TABLE Kern.His_PersOverlijden SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 200000, autovacuum_analyze_threshold = 200000);
ALTER TABLE Kern.His_PersNaamgebruik SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 800000, autovacuum_analyze_threshold = 800000);
ALTER TABLE Kern.His_PersMigratie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 40000, autovacuum_analyze_threshold = 40000);
ALTER TABLE Kern.His_PersVerblijfsr SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 140000, autovacuum_analyze_threshold = 140000);
ALTER TABLE Kern.His_PersUitslKiesr SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 400000, autovacuum_analyze_threshold = 400000);
ALTER TABLE Kern.His_PersDeelnEUVerkiezingen SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 40000, autovacuum_analyze_threshold = 40000);
ALTER TABLE Kern.His_PersPK SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
ALTER TABLE Kern.His_PersAdres ALTER COLUMN PersAdres SET statistics 1000;
ALTER TABLE Kern.His_PersAdres SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1000000, autovacuum_analyze_threshold = 1000000);
ALTER TABLE AutAut.His_PersAfnemerindicatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 8400000, autovacuum_analyze_threshold = 8400000);
ALTER TABLE Kern.His_PersGeslnaamcomp ALTER COLUMN PersGeslnaamcomp SET statistics 1000;
ALTER TABLE Kern.His_PersGeslnaamcomp SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 800000, autovacuum_analyze_threshold = 800000);
ALTER TABLE Kern.His_PersIndicatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 100000, autovacuum_analyze_threshold = 100000);
ALTER TABLE Kern.His_PersNation SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
ALTER TABLE Kern.His_PersReisdoc SET (autovacuum_vacuum_scale_factor = 0.0005, autovacuum_vacuum_threshold = 88000, autovacuum_analyze_threshold = 88000);
ALTER TABLE Kern.His_PersVerificatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 50000, autovacuum_analyze_threshold = 50000);
ALTER TABLE Kern.His_PersVoornaam SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1500000, autovacuum_analyze_threshold = 1500000);
ALTER TABLE Kern.His_Relatie SET (autovacuum_vacuum_scale_factor = 0.05, autovacuum_vacuum_threshold = 1200000, autovacuum_analyze_threshold = 1200000);


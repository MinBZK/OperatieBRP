-- script voor tijdelijke wijzigingen die voorlopig op bmr releases
-- PRE113
INSERT INTO VerConv.LO3SrtMelding (ID, Code, Oms, CategorieMelding) VALUES (937, 'PRE113', 'Element 01.10 A-nummer uit een niet onjuiste categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind mag niet overeenkomen met 01.01.10 A-nummer.', 2);
-- AUT012
INSERT INTO VerConv.LO3SrtMelding (ID, Code, Oms, CategorieMelding) VALUES (938, 'AUT012', 'Autorisaties: Er mogen niet meerdere lege einddatums binnen een stapel van een autorisatie zijn voor een afnemer.', 2);

COMMIT;


-- Volgorde in scripts niet correct. Het herstellen van de sequences moet gebeuren na de vulling van de tabellen
-- SQL inhoud van bestand: bmr/Kern/Kern_BRP_structuur_aanvullend.sql
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

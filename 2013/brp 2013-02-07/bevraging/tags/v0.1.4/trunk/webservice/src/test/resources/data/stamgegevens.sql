--
-- File: stamgegevens.sql
-- Doel: Vulling van de statische stamtabellen.
--

-- Functie / Adres
DELETE FROM kern.functieadres;
INSERT INTO kern.functieadres (id,code,naam) VALUES (1,'W','Woonadres');
INSERT INTO kern.functieadres (id,code,naam) VALUES (2,'B','Briefadres');

-- Geslachtsaanduiding
DELETE FROM kern.geslachtsaand;
INSERT INTO kern.geslachtsaand (id,code,naam,oms) VALUES (1,'M','Man',null);
INSERT INTO kern.geslachtsaand (id,code,naam,oms) VALUES (2,'V','Vrouw',null);
INSERT INTO kern.geslachtsaand (id,code,naam,oms) VALUES (3,'O','Onbekend',null);

-- Land
DELETE FROM kern.land;
INSERT INTO Kern.Land (ID, landcode, dataanvgel, dateindegel, iso31661alpha2, naam) values(5,6023,null,null,$$AF$$,$$Afganistan$$);
INSERT INTO Kern.Land (ID, landcode, dataanvgel, dateindegel, iso31661alpha2, naam) values(229,6030,null,null,$$NL$$,$$Nederland$$);
INSERT INTO Kern.Land (ID, landcode, dataanvgel, dateindegel, iso31661alpha2, naam) values(32,5010,null,null,$$BE$$,$$België$$);
INSERT INTO Kern.Land (ID, landcode, dataanvgel, dateindegel, iso31661alpha2, naam) values(111,5002,null,null,$$FR$$,$$Frankrijk$$);
INSERT INTO Kern.Land (ID, landcode, dataanvgel, dateindegel, iso31661alpha2, naam) values(40,9089,19490801,null,$$DE$$,$$Bondsrepubliek Duitsland$$);
INSERT INTO Kern.Land (ID, landcode, dataanvgel, dateindegel, iso31661alpha2, naam) values(134,6039,null,null,$$GB$$,$$Grootbrittannië$$);

-- Reden opschorting
DELETE FROM kern.rdnopschorting;
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (1,'O','Overleden');
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (2,'M','Ministerieel besluit');
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (3,'F','Fout');
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (4,'?','Onbekend');

-- Soort indicatie
DELETE FROM kern.srtindicatie;
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (1,'Derde heeft gezag?',true);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (2,'Onder curatele?',true);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (3,'Uitsluiting NL kiesrecht?',false);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (4,'Deelname EU verkiezingen?',false);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (5,'Verstrekkingsbeperking?',false);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (6,'Geprivilegieerde?',false);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (7,'Vastgesteld niet Nederlander?',true);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (8,'Behandeld als Nederlander?',true);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (9,'Belemmering verstrekking reisdocument?',false);
INSERT INTO kern.srtindicatie (id,naam,indmaterielehistorievantoep) VALUES (10,'Bezit buitenlands reisdocument?',false);

-- Soort partij
DELETE FROM kern.srtpartij;
INSERT INTO kern.srtpartij (id,naam,dataanvgel,dateindegel) VALUES (1,'Gemeente',null,null);
INSERT INTO kern.srtpartij (id,naam,dataanvgel,dateindegel) VALUES (2,'Minister',null,null);
INSERT INTO kern.srtpartij (id,naam,dataanvgel,dateindegel) VALUES (3,'Aangewezen Bestuursorgaan',null,null);
INSERT INTO kern.srtpartij (id,naam,dataanvgel,dateindegel) VALUES (4,'Privaatrechtelijk rechtspersoon',null,null);
INSERT INTO kern.srtpartij (id,naam,dataanvgel,dateindegel) VALUES (5,'Samenwerkingsverband',null,null);

-- Soort persoon
DELETE FROM kern.srtpers;
INSERT INTO kern.srtpers (id,code,naam,oms,dataanvgel,dateindegel) VALUES (1,'I','Ingeschrevene',null,20112011,null);
INSERT INTO kern.srtpers (id,code,naam,oms,dataanvgel,dateindegel) VALUES (2,'N','Niet ingeschrevene',null,20112011,null);
INSERT INTO kern.srtpers (id,code,naam,oms,dataanvgel,dateindegel) VALUES (3,'A', 'Alternatieve realiteit',null,20112011,null);

-- Rol
DELETE FROM kern.rol;
INSERT INTO kern.rol (id, naam, dataanvgel, dateindegel) VALUES (1, 'Afnemer', 20110101, null);
INSERT INTO kern.rol (id, naam, dataanvgel, dateindegel) VALUES (2, 'Bevoegdheidstoedeler', 20110101, null);
INSERT INTO kern.rol (id, naam, dataanvgel, dateindegel) VALUES (3, 'Bijhoudingsorgaan College', 20110101, null);
INSERT INTO kern.rol (id, naam, dataanvgel, dateindegel) VALUES (4, 'Bijhoudingsorgaan Minister', 20110101, null);
INSERT INTO kern.rol (id, naam, dataanvgel, dateindegel) VALUES (5, 'Stelselbeheerder', 20110101, null);
INSERT INTO kern.rol (id, naam, dataanvgel, dateindegel) VALUES (6, 'Toezichthouder', 20110101, null);

-- Soort levering
DELETE FROM lev.srtlev;
INSERT INTO lev.srtlev (id, naam) VALUES (1, 'BRP_BEVRAGING');
INSERT INTO lev.srtlev (id, naam) VALUES (2, 'BRP_MUTATIE');
INSERT INTO lev.srtlev (id, naam) VALUES (3, 'BRP_SELECTIE');

-- Soort abonnement
DELETE FROM lev.srtabonnement;
INSERT INTO lev.srtabonnement (id, naam) VALUES (1, 'Levering');

-- Richting
INSERT INTO Ber.Richting (ID, Naam, Oms) VALUES (1, 'Ingaand', 'Naar de centrale voorzieningen van de BRP toe.');
INSERT INTO Ber.Richting (ID, Naam, Oms) VALUES (2, 'Uitgaand', 'Van de centrale voorzieningen van de BRP af.');

-- Soort bericht
DELETE FROM ber.srtber;
INSERT INTO ber.srtber (id, naam) VALUES(1, 'OpvragenPersoonVraag');
INSERT INTO ber.srtber (id, naam) VALUES(2, 'OpvragenPersoonAntwoord');

-- Soort autorisatiebesluit
DELETE FROM autaut.srtautorisatiebesluit;
INSERT INTO autaut.srtautorisatiebesluit (id, naam, oms, dataanvgel, dateindegel) VALUES (1, 'Leveringsautorisatie', '00000000', 20110101, null);

-- Protocolleringsniveau
DELETE FROM autaut.protocolleringsniveau;
INSERT INTO autaut.protocolleringsniveau (id, code, naam, oms, dataanvgel, dateindegel) VALUES (1, 1,'Geen beperking', 'Geen beperking', 20110101, null);
INSERT INTO autaut.protocolleringsniveau (id, code, naam, oms, dataanvgel, dateindegel) VALUES (2, 2,'Beperking', 'Beperking', 20110101, null);
INSERT INTO autaut.protocolleringsniveau (id, code, naam, oms, dataanvgel, dateindegel) VALUES (3, 3,'Geheim', 'Geheim', 20110101, null);

-- Partij (gemeenten)
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (364,$$Amsterdam$$,1,null,null,'A',0363,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (600,$$Rotterdam$$,1,null,null,'A',0599,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (519,$$'s-Gravenhage$$,1,null,null,'A',0518,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (345,$$Utrecht$$,1,null,null,'A',0344,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (773,$$Eindhoven$$,1,null,null,'A',0772,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (856,$$Tilburg$$,1,null,null,'A',0855,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (35,$$Almere$$,1,19840101,null,'A',0034,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (15,$$Groningen$$,1,null,null,'A',0014,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (759,$$Breda$$,1,null,null,'A',0758,'A');
INSERT INTO Kern.Partij (ID, Naam, Srt, DatAanv, DatEinde, PartijStatusHis, Gemcode, GemStatusHis) values (269,$$Nijmegen$$,1,null,null,'A',0268,'A');

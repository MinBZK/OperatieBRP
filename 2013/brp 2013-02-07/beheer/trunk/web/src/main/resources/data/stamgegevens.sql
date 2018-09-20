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
INSERT INTO kern.land (id,naam,iso31661alpha2,landcode,dataanvgel,dateindegel) VALUES (1,'Afghanistan','AF',1,null,null);
INSERT INTO kern.land (id,naam,iso31661alpha2,landcode,dataanvgel,dateindegel) VALUES (2,'Nederland','NL',2,null,null);
INSERT INTO kern.land (id,naam,iso31661alpha2,landcode,dataanvgel,dateindegel) VALUES (3,'België','BE',3,null,null);
INSERT INTO kern.land (id,naam,iso31661alpha2,landcode,dataanvgel,dateindegel) VALUES (4,'Frankrijk','FR',4,null,null);
INSERT INTO kern.land (id,naam,iso31661alpha2,landcode,dataanvgel,dateindegel) VALUES (5,'Duitsland','DE',5,null,null);
INSERT INTO kern.land (id,naam,iso31661alpha2,landcode,dataanvgel,dateindegel) VALUES (6,'Verenigd Koninkrijk','GB',5,null,null);

-- Reden opschorting
DELETE FROM kern.rdnopschorting;
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (1,'O','Overleden');
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (2,'M','Ministerieel besluit');
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (3,'F','Fout');
INSERT INTO kern.rdnopschorting (id,code,naam) VALUES (4,'?','Onbekend');

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
INSERT INTO kern.srtpers (id,code,naam,oms,dataanvgel,dateindegel) VALUES (3,'A','Alternatieve realiteit',null,20112011,null);

-- Rol
DELETE FROM kern.rol;
INSERT INTO kern.rol (id,naam,dataanvgel,dateindegel) VALUES (1,'Afnemer',null,null);
INSERT INTO kern.rol (id,naam,dataanvgel,dateindegel) VALUES (2,'Bevoegdheidstoedeler',null,null);
INSERT INTO kern.rol (id,naam,dataanvgel,dateindegel) VALUES (3,'Bijhoudingsorgaan College',null,null);
INSERT INTO kern.rol (id,naam,dataanvgel,dateindegel) VALUES (4,'Bijhoudingsorgaan Minister',null,null);
INSERT INTO kern.rol (id,naam,dataanvgel,dateindegel) VALUES (5,'Stelselbeheerder',null,null);
INSERT INTO kern.rol (id,naam,dataanvgel,dateindegel) VALUES (6,'Toezichthouder',null,null);
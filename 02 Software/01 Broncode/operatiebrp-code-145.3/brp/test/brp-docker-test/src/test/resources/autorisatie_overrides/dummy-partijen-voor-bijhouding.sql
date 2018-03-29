--------------------------------------------------
---
--- Toevoegen van dummy partijen en gemeenten voor bijhouding + alles wat nodig is om deze partijen als bijhouders aan te merken
--------------------------------------------------

BEGIN;
-- Verwijderen van reeds bestaande gemeentes en partijen en partijrollen
DELETE FROM kern.gem WHERE ID between 7011 and 8024;
DELETE FROM kern.his_partijrol where partijrol in (select id from kern.partijrol where partij between 27011 and 28024);
DELETE FROM kern.partijrol where partij between 27011 and 28024;
DELETE FROM kern.his_partij WHERE partij between 27011 and 28024;
DELETE FROM kern.partij WHERE ID between 27011 and 28024;
delete from kern.plaats where naam in ('Testdorp','Verlopenplaats','LaterVerlopenplaats','VerlopenplaatsIn2016');
DELETE from kern.landgebied where code = '9096';


-- Partij Set #1
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27011','507012','Gemeente GBA 1','19940101',null,'false' ,null, null);
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27012','507013','Gemeente BRP 1','20160101',null,'false','true','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27013','507014','Gemeente BRP handmatig fiat 1','20160101',null,'false','false','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27014','507015','Gemeente BRP auto fiat met uitzondering 1','20160101',null,'false','true','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27015','507016','Gemeente BRP beeindigd 1','20150101','20160101','false','true','20150101');

-- Partij Historie Set #1
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27011','27011','1994-01-01 00:00:00','Gemeente GBA 1','19940101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27012','27012','2016-01-01 00:00:00','Gemeente BRP 1','20160101', 'false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27013','27013','2016-01-01 00:00:00','Gemeente BRP handmatig fiat 1','20160101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27014','27014','2016-01-01 00:00:00','Gemeente BRP auto fiat met uitzondering 1','20160101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, dateinde, indverstrbeperkingmogelijk) VALUES('27015','27015','2015-01-01 00:00:00','Gemeente BRP beeindigd 1','20150101','20160101','false');

-- Partij Set #2
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27016','507017','Gemeente GBA 2','19940101',null,'false' ,null, null);
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27017','507018','Gemeente BRP 2','20160101',null,'false','true','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27018','507019','Gemeente BRP handmatig fiat 2','20160101',null,'false','false','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27019','507020','Gemeente BRP auto fiat met uitzondering 2','20160101',null,'false','true','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27020','507021','Gemeente BRP beeindigd 2','20150101','20160101','false','true','20150101');

-- Partij Historie Set #2
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27016','27016','1994-01-01 00:00:00','Gemeente GBA 2','19940101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27017','27017','2016-01-01 00:00:00','Gemeente BRP 2','20160101', 'false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27018','27018','2016-01-01 00:00:00','Gemeente BRP handmatig fiat 2','20160101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27019','27019','2016-01-01 00:00:00','Gemeente BRP auto fiat met uitzondering 2','20160101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, dateinde, indverstrbeperkingmogelijk) VALUES('27020','27020','2015-01-01 00:00:00','Gemeente BRP beeindigd 2','20150101','20160101','false');

-- Overige partijen
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27021','507022','Gemeente BRP 3','20160101',null,'false','true','20160101');
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27022','507023','Gemeente GBA beeindigd','19940101','19950101','false' ,null, null);
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27023','507024','Gemeente GBA wordt BRP in 2099','19940101',null,'false' ,null, '20990101');

-- Overige partijen historie
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27021','27021','2016-01-01 00:00:00','Gemeente BRP 3','20160101','false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, dateinde, indverstrbeperkingmogelijk) VALUES('27022','27022','1994-01-01 00:00:00','Gemeente GBA beeindigd','19940101','19950101', 'false');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27023','27023','1994-01-01 00:00:00','Gemeente GBA wordt BRP in 2099','19940101','false');

-- Gemeente Set #1 horende bij Partij Set #1
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7011','7111','Gemeente GBA 1','27011',null,'19940101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7012','7112','Gemeente BRP 1','27012',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7013','7113','Gemeente BRP handmatig fiat 1','27013',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7014','7114','Gemeente BRP auto fiat met uitzondering 1','27014',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7015','7115','Gemeente BRP beeindigd 1','27015',null,'20150101','20160101');

-- Gemeente Set #2 horende bij Partij Set #2
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7016','7116','Gemeente GBA 2','27016',null,'19940101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7017','7117','Gemeente BRP 2','27017',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7018','7118','Gemeente BRP handmatig fiat 2','27018',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7019','7119','Gemeente BRP auto fiat met uitzondering 2','27019',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7020','7120','Gemeente BRP beeindigd 2','27020',null,'20150101','20160101');

-- Gemeente horende bij Overige Partij
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7021','7121','Gemeente BRP 3','27021',null,'20160101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7022','7122','Gemeente GBA beeindigd','27022',null,'19940101','19950101');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7023','7123','Gemeente GBA wordt BRP in 2099','27023',null,'19940101',null);

-- Overige partijen deze gem is gekoppeld aan 'Gemeente BRP 1' als voortzettende gemeente
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27024','507025','Gemeente wordt voortgezet door BRP 1','19940101','19950101','false' ,null, '20160101');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27024','27024','1994-01-01 00:00:00','Gemeente wordt voortgezet door BRP 1','19940101','false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7024','7124','Gemeente wordt voortgezet door BRP 1','27023',7012,'19940101','19950101');

-- Overige partijen deze gemeente voor autorisatietest
INSERT INTO kern.partij (id,code,naam,datingang,dateinde,indverstrbeperkingmogelijk, indautofiat, datovergangnaarbrp) VALUES('27026','507026','Gemeente voor autorisatietest','19940101',null,'false' ,null, '20160101');
INSERT INTO kern.his_partij (id, partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) VALUES('27026','27026','1994-01-01 00:00:00','Gemeente voor autorisatietest','19940101','false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7026','7126','Gemeente voor autorisatietest','27026',7012,'19940101',null);


-- Rol '"Bijhoudingsorgaan College"' toevoegen aan partijen
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27011', '2', '19940101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27012', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27013', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27014', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27015', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27016', '2', '19940101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27017', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27018', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27019', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang, dateinde) VALUES ('27020', '2','20150101', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27021', '2', '20160101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27022', '2', '19940101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27023', '2', '19940101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ('27026', '2', '19940101');

insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27011), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27012), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27013), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27014), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27015), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27016), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27017), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27018), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27019), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27020), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27021), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27022), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27023), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = 27026), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = (select id from kern.partij where rol = 2 and naam ='Gemeente Tiel')), (now() at time zone 'UTC'),19900101);
insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = (select id from kern.partij where rol = 2 and naam ='Gemeente Amsterdam')), (now() at time zone 'UTC'),20160101);
--insert into kern.his_partijrol (partijrol,tsreg,datingang)values ((select id from kern.partijrol where partij = (select id from kern.partij where rol = 2 and naam ='Gemeente Eindhoven')), (now() at time zone 'UTC'),20160101);


-- OIN's toekennen aan de partijen
UPDATE kern.partij SET oin='12345' WHERE id = 27011;
UPDATE kern.partij SET oin='54321' WHERE id = 27012;
UPDATE kern.partij SET oin='67890' WHERE id = 27013;
UPDATE kern.partij SET oin='09876' WHERE id = 27014;
UPDATE kern.partij SET oin='12378' WHERE id = 27015;
UPDATE kern.partij SET oin='87321' WHERE id = 27016;
UPDATE kern.partij SET oin='45690' WHERE id = 27017;
UPDATE kern.partij SET oin='09654' WHERE id = 27018;
UPDATE kern.partij SET oin='78912' WHERE id = 27019;
UPDATE kern.partij SET oin='21987' WHERE id = 27020;
UPDATE kern.partij SET oin='98721' WHERE id = 27021;
UPDATE kern.partij SET oin='12789' WHERE id = 27022;
UPDATE kern.partij SET oin='45612' WHERE id = 27023;
UPDATE kern.partij SET oin='27026' WHERE id = 27026;
--UPDATE kern.partij SET oin='077201' WHERE naam = 'Gemeente Eindhoven';

-- OIN's toekennen aan de partij historie
UPDATE kern.his_partij SET oin='12345' WHERE id = 27011;
UPDATE kern.his_partij SET oin='54321' WHERE id = 27012;
UPDATE kern.his_partij SET oin='67890' WHERE id = 27013;
UPDATE kern.his_partij SET oin='09876' WHERE id = 27014;
UPDATE kern.his_partij SET oin='12378' WHERE id = 27015;
UPDATE kern.his_partij SET oin='87321' WHERE id = 27016;
UPDATE kern.his_partij SET oin='45690' WHERE id = 27017;
UPDATE kern.his_partij SET oin='09654' WHERE id = 27018;
UPDATE kern.his_partij SET oin='78912' WHERE id = 27019;
UPDATE kern.his_partij SET oin='21987' WHERE id = 27020;
UPDATE kern.his_partij SET oin='98721' WHERE id = 27021;
UPDATE kern.his_partij SET oin='12789' WHERE id = 27022;
UPDATE kern.his_partij SET oin='45612' WHERE id = 27023;
UPDATE kern.his_partij SET oin='27026' WHERE id = 27026;
--UPDATE kern.his_partij SET oin='077201' WHERE naam = 'Gemeente Eindhoven';

insert into kern.plaats (naam) values ('Testdorp');
insert into kern.plaats (naam) values ('Verlopenplaats');
insert into kern.plaats (naam) values ('LaterVerlopenplaats');
insert into kern.plaats (naam) values ('VerlopenplaatsIn2016');

--Landgebied toegevoegd voor OHBL01C30T20
insert into kern.landgebied (code,naam,dataanvgel,dateindegel) values ('9096','Verzonnenland',20160201,20161001);

COMMIT;


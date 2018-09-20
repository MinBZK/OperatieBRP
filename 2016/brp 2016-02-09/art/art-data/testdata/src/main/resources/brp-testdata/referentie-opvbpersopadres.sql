
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieOPvBpersopadres.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--------------------------------------------------

begin;

DELETE from kern.landgebied WHERE ID=15006;
DELETE from kern.landgebied WHERE ID=15005;
DELETE from kern.landgebied WHERE ID=15004;
DELETE from kern.landgebied WHERE ID=15003;
DELETE from kern.landgebied WHERE ID=15002;
DELETE from kern.landgebied WHERE ID=15001;
DELETE from kern.plaats WHERE ID=5030;
DELETE from kern.plaats WHERE ID=5029;
DELETE from kern.plaats WHERE ID=5028;
DELETE from kern.plaats WHERE ID=5027;
DELETE from kern.plaats WHERE ID=5026;
DELETE from kern.plaats WHERE ID=5025;
DELETE from kern.plaats WHERE ID=5024;
DELETE from kern.plaats WHERE ID=5023;
DELETE from kern.plaats WHERE ID=5022;
DELETE from kern.plaats WHERE ID=5021;
DELETE from kern.plaats WHERE ID=5020;
DELETE from kern.plaats WHERE ID=5019;
DELETE from kern.plaats WHERE ID=5018;
DELETE from kern.plaats WHERE ID=5017;
DELETE from kern.plaats WHERE ID=5016;
DELETE from kern.plaats WHERE ID=5015;
DELETE from kern.plaats WHERE ID=5014;
DELETE from kern.plaats WHERE ID=5013;
DELETE from kern.plaats WHERE ID=5012;
DELETE from kern.plaats WHERE ID=5011;
DELETE from kern.plaats WHERE ID=5010;
DELETE from kern.plaats WHERE ID=5009;
DELETE from kern.plaats WHERE ID=5008;
DELETE from kern.plaats WHERE ID=5007;
DELETE from kern.plaats WHERE ID=5006;
DELETE from kern.plaats WHERE ID=5005;
DELETE from kern.plaats WHERE ID=5004;
DELETE from kern.plaats WHERE ID=5003;
DELETE from kern.plaats WHERE ID=5002;
DELETE from kern.plaats WHERE ID=5001;
DELETE from kern.gem WHERE ID=5006;
DELETE from kern.gem WHERE ID=5005;
DELETE from kern.gem WHERE ID=5004;
DELETE from kern.gem WHERE ID=5003;
DELETE from kern.gem WHERE ID=5002;
DELETE from kern.gem WHERE ID=5001;
DELETE from kern.partij WHERE ID=25006;
DELETE from kern.partij WHERE ID=25005;
DELETE from kern.partij WHERE ID=25004;
DELETE from kern.partij WHERE ID=25003;
DELETE from kern.partij WHERE ID=25002;
DELETE from kern.partij WHERE ID=25001;
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('25001',null,'505001','SRPUC50151-1-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('25002',null,'505002','SRPUC50151-2-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('25003',null,'505003','SRPUC50151-3-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('25004',null,'505004','SRPUC50151-4-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('25005',null,'505005','SRPUC50151-5-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('25006',null,'505006','SRPUC50151-6-Partij','19540101',null,'false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('5001','5101','SRPUC50151-1-Gemeente','25001',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('5002','5102','SRPUC50151-2-Gemeente','25002',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('5003','5103','SRPUC50151-3-Gemeente','25003',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('5004','5104','SRPUC50151-4-Gemeente','25004',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('5005','5105','SRPUC50151-5-Gemeente','25005',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('5006','5106','SRPUC50151-6-Gemeente','25006',null,'19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5001','5101','SRPUC50151-1-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5002','5102','SRPUC50151-2-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5003','5103','SRPUC50151-3-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5004','5104','SRPUC50151-4-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5005','5105','SRPUC50151-5-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5006','5106','SRPUC50151-6-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5007','5107','SRPUC50151-7-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5008','5108','SRPUC50151-8-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5009','5109','SRPUC50151-9-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5010','5110','SRPUC50151-10-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5011','5111','SRPUC50151-11-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5012','5112','SRPUC50151-12-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5013','5113','SRPUC50151-13-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5014','5114','SRPUC50151-14-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5015','5115','SRPUC50151-15-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5016','5116','SRPUC50151-16-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5017','5117','SRPUC50151-17-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5018','5118','SRPUC50151-18-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5019','5119','SRPUC50151-19-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5020','5120','SRPUC50151-20-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5021','5121','SRPUC50151-21-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5022','5122','SRPUC50151-22-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5023','5123','SRPUC50151-23-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5024','5124','SRPUC50151-24-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5025','5125','SRPUC50151-25-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5026','5126','SRPUC50151-26-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5027','5127','SRPUC50151-27-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5028','5128','SRPUC50151-28-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5029','5129','SRPUC50151-29-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('5030','5130','SRPUC50151-30-Woonplaats','19540101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('15001','1511','AKr-1-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('15002','1512','AKr-2-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('15003','1513','AKr-3-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('15004','1514','AKr-4-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('15005','1515','AKr-5-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('15006','1516','AKr-6-land','AK','20010101',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--- Aantal: (52)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieOPvBpersopadres.xls
--------------------------------------------------
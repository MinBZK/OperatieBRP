
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieHP.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--------------------------------------------------

begin;

DELETE from kern.landgebied WHERE ID=10006;
DELETE from kern.landgebied WHERE ID=10005;
DELETE from kern.landgebied WHERE ID=10004;
DELETE from kern.landgebied WHERE ID=10003;
DELETE from kern.landgebied WHERE ID=10002;
DELETE from kern.landgebied WHERE ID=10001;
DELETE from kern.plaats WHERE ID=9007;
DELETE from kern.plaats WHERE ID=9006;
DELETE from kern.plaats WHERE ID=9005;
DELETE from kern.plaats WHERE ID=9004;
DELETE from kern.plaats WHERE ID=9003;
DELETE from kern.plaats WHERE ID=9002;
DELETE from kern.plaats WHERE ID=9001;
DELETE from kern.gem WHERE ID=2006;
DELETE from kern.gem WHERE ID=2005;
DELETE from kern.gem WHERE ID=2004;
DELETE from kern.gem WHERE ID=2003;
DELETE from kern.gem WHERE ID=2002;
DELETE from kern.gem WHERE ID=2001;
DELETE from kern.partij WHERE ID=22006;
DELETE from kern.partij WHERE ID=22005;
DELETE from kern.partij WHERE ID=22004;
DELETE from kern.partij WHERE ID=22003;
DELETE from kern.partij WHERE ID=22002;
DELETE from kern.partij WHERE ID=22001;
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22001',null,'502001','BRBY0429-1-Partij','0',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22002',null,'502002','BRBY0429-2-Partij','0','20121004','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22003',null,'502003','BRBY0429-3-Partij','20121005',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22004',null,'502004','BRBY0429-4-Partij','20121003','20121005','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22005',null,'502005','BRBY0429-5-Partij','20121003','20121006','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22006',null,'502006','BRBY0429-6-Partij','20121005','20121007','false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2001','2101','BRBY0429-1-Gemeente','22001',null,'0',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2002','2202','BRBY0429-2-Gemeente','22002',null,'0','20121004');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2003','2303','BRBY0429-3-Gemeente','22003',null,'20121005',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2004','2404','BRBY0429-4-Gemeente','22004',null,'20121003','20121005');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2005','2505','BRBY0429-5-Gemeente','22005',null,'20121003','20121006');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2006','2606','BRBY0429-6-Gemeente','22006',null,'20121005','20121007');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9001','9101','BRBY0428-1-Woonplaats','0',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9002','9202','BRBY0428-2-Woonplaats','0','20121004');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9003','9303','BRBY0428-3-Woonplaats','20121005',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9004','9404','BRBY0428-4-Woonplaats','20121003','20121005');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9005','9505','BRBY0428-5-Woonplaats','20121003','20121006');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9006','9606','BRBY0428-6-Woonplaats','20121005','20121007');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('9007','9707','BRBY0428-7-Woonplaats','20121005',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('10001','1101','Greet-1-land','TL','0',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('10002','1202','Greet-2-land','TL','0','20121004');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('10003','1303','Greet-3-land','TL','20121005',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('10004','1404','Greet-4-land','TL','20121003','20121005');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('10005','1505','Greet-5-land','TL','20121003','20121006');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('10006','1606','Greet-6-land','TL','20121005','20121007');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--- Aantal: (31)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieHP.xls
--------------------------------------------------
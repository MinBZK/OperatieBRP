
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieKUC055.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--------------------------------------------------

begin;

DELETE from kern.plaats WHERE ID=3006;
DELETE from kern.plaats WHERE ID=3005;
DELETE from kern.plaats WHERE ID=3004;
DELETE from kern.plaats WHERE ID=3003;
DELETE from kern.plaats WHERE ID=3002;
DELETE from kern.plaats WHERE ID=3001;
DELETE from kern.gem WHERE ID=3006;
DELETE from kern.gem WHERE ID=3005;
DELETE from kern.gem WHERE ID=3004;
DELETE from kern.gem WHERE ID=3003;
DELETE from kern.gem WHERE ID=3002;
DELETE from kern.gem WHERE ID=3001;
DELETE from kern.partij WHERE ID=23006;
DELETE from kern.partij WHERE ID=23005;
DELETE from kern.partij WHERE ID=23004;
DELETE from kern.partij WHERE ID=23003;
DELETE from kern.partij WHERE ID=23002;
DELETE from kern.partij WHERE ID=23001;
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('23001',null,'503001','KUC055-1-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('23002',null,'503002','KUC055-2-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('23003',null,'503003','KUC055-3-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('23004',null,'503004','KUC055-4-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('23005',null,'503005','KUC055-5-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('23006',null,'503006','KUC055-6-Partij','19540101',null,'false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('3001','3101','KUC055-1-Gemeente','23001',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('3002','3102','KUC055-2-Gemeente','23002',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('3003','3103','KUC055-3-Gemeente','23003',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('3004','3104','KUC055-4-Gemeente','23004',null,'20050101','20070101');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('3005','3105','KUC055-5-Gemeente','23005',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('3006','3106','KUC055-6-Gemeente','23006',null,'19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('3001','3701','KUC055-1-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('3002','3702','KUC055-2-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('3003','3703','KUC055-3-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('3004','3704','KUC055-4-Woonplaats','20080123','20110407');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('3005','3705','KUC055-5-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('3006','3706','KUC055-6-Woonplaats','19540101',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--- Aantal: (21)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieKUC055.xls
--------------------------------------------------
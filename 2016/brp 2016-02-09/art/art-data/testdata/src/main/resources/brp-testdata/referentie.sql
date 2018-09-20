
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/Referentie.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--------------------------------------------------

begin;

DELETE from kern.voorvoegsel WHERE ID=1003;
DELETE from kern.voorvoegsel WHERE ID=1002;
DELETE from kern.voorvoegsel WHERE ID=1001;
DELETE from kern.voorvoegsel WHERE ID=1000;
DELETE from kern.plaats WHERE ID=2708;
DELETE from kern.plaats WHERE ID=2707;
DELETE from kern.gem WHERE ID=2708;
DELETE from kern.gem WHERE ID=2707;
DELETE from kern.partij WHERE ID=22708;
DELETE from kern.partij WHERE ID=22707;
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22707',null,'502707','KUC033-PartijVerstrekkingsbeperking',null,null,'true');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22708',null,'502708','KUC033-PartijVerstrekkingsbeperking2','20010101','20140201','true');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2707','2807','KUC033 Gemeente','22707',null,null,null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2708','2808','KUC033 Gemeente - 2','22708',null,null,null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2707','3807','KUC033 Woonplaats',null,null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2708','3808','KUC033 Woonplaats-2',null,null);
INSERT INTO kern.voorvoegsel (id,voorvoegsel,scheidingsteken) VALUES('1000','de','-');
INSERT INTO kern.voorvoegsel (id,voorvoegsel,scheidingsteken) VALUES('1001','den','-');
INSERT INTO kern.voorvoegsel (id,voorvoegsel,scheidingsteken) VALUES('1002','ten','-');
INSERT INTO kern.voorvoegsel (id,voorvoegsel,scheidingsteken) VALUES('1003','voorvoeg','+');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--- Aantal: (15)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/Referentie.xls
--------------------------------------------------
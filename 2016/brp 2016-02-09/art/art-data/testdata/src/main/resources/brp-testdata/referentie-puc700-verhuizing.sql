--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentiePUC700 Afh bijh - Verhuizing.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--------------------------------------------------

begin;

DELETE from kern.landgebied WHERE ID=2707;
DELETE from kern.landgebied WHERE ID=2706;
DELETE from kern.landgebied WHERE ID=2705;
DELETE from kern.landgebied WHERE ID=2704;
DELETE from kern.landgebied WHERE ID=2703;
DELETE from kern.landgebied WHERE ID=2702;
DELETE from kern.landgebied WHERE ID=2701;
DELETE from kern.plaats WHERE ID=2706;
DELETE from kern.plaats WHERE ID=2705;
DELETE from kern.plaats WHERE ID=2704;
DELETE from kern.plaats WHERE ID=2703;
DELETE from kern.plaats WHERE ID=2702;
DELETE from kern.plaats WHERE ID=2701;
DELETE from kern.gem WHERE ID=2706;
DELETE from kern.gem WHERE ID=2705;
DELETE from kern.gem WHERE ID=2704;
DELETE from kern.gem WHERE ID=2703;
DELETE from kern.gem WHERE ID=2702;
DELETE from kern.gem WHERE ID=2701;
DELETE from kern.partij WHERE ID=22706;
DELETE from kern.partij WHERE ID=22705;
DELETE from kern.partij WHERE ID=22704;
DELETE from kern.partij WHERE ID=22703;
DELETE from kern.partij WHERE ID=22702;
DELETE from kern.partij WHERE ID=22701;
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22701',null,'502701','PUC700 Afh bijh - Verhuizing-1-Partij','0',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22702',null,'502702','PUC700 Afh bijh - Verhuizing-2-Partij','0','20150101','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22703',null,'502703','PUC700 Afh bijh - Verhuizing-3-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22704',null,'502704','PUC700 Afh bijh - Verhuizing-4-Partij','19540101','20150101','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22705',null,'502705','PUC700 Afh bijh - Verhuizing-5-Partij','19540101',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('22706',null,'502706','PUC700 Afh bijh - Verhuizing-6-Partij','19540101',null,'false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2701','2801','PUC700 Afh bijh - Verhuizing-1-Gemeente','22701',null,'19540101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2702','2802','PUC700 Afh bijh - Verhuizing-2-Gemeente','22702',null,'19540101','20130101');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2703','2803','PUC700 Afh bijh - Verhuizing-3-Gemeente','22703',null,'20200101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2704','2804','PUC700 Afh bijh - Verhuizing-4-Gemeente','22704',null,'20130101',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2705','2805','PUC700 Afh bijh - Verhuizing-5-Gemeente','22705',null,'0','20130101');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('2706','2806','PUC700 Afh bijh - Verhuizing-6-Gemeente','22706',null,'19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2701','3801','PUC700 Afh bijh - Verhuizing-1-Woonplaats','19540101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2702','3802','PUC700 Afh bijh - Verhuizing-2-Woonplaats','19540101','20130101');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2703','3803','PUC700 Afh bijh - Verhuizing-3-Woonplaats','20150101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2704','3804','PUC700 Afh bijh - Verhuizing-4-Woonplaats','20130101',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2705','3805','PUC700 Afh bijh - Verhuizing-5-Woonplaats','0','20130101');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('2706','3806','PUC700 Afh bijh - Verhuizing-6-Woonplaats','0',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2701','3801','PUC700 Afh bijh - Verhuizing-1-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2702','3802','PUC700 Afh bijh - Verhuizing-2-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2703','3803','PUC700 Afh bijh - Verhuizing-3-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2704','3804','PUC700 Afh bijh - Verhuizing-4-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2705','3805','PUC700 Afh bijh - Verhuizing-5-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2706','3806','PUC700 Afh bijh - Verhuizing-6-land','AK','20010101',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('2707','3807','PUC700 Afh bijh - Verhuizing-7-land','AK','20010101','20140201');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--- Aantal: (29)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentiePUC700 Afh bijh - Verhuizing.xls
--------------------------------------------------
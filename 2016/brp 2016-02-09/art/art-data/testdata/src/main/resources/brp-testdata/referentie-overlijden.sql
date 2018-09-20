
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieOVERLIJDEN.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--------------------------------------------------

begin;

DELETE from kern.landgebied WHERE ID=8001;
DELETE from kern.landgebied WHERE ID=7009;
DELETE from kern.landgebied WHERE ID=7008;
DELETE from kern.landgebied WHERE ID=7007;
DELETE from kern.landgebied WHERE ID=7006;
DELETE from kern.landgebied WHERE ID=7005;
DELETE from kern.landgebied WHERE ID=7004;
DELETE from kern.landgebied WHERE ID=7003;
DELETE from kern.landgebied WHERE ID=7002;
DELETE from kern.landgebied WHERE ID=7001;
DELETE from kern.plaats WHERE ID=8105;
DELETE from kern.plaats WHERE ID=8104;
DELETE from kern.plaats WHERE ID=8103;
DELETE from kern.plaats WHERE ID=8102;
DELETE from kern.plaats WHERE ID=8101;
DELETE from kern.plaats WHERE ID=7907;
DELETE from kern.plaats WHERE ID=7906;
DELETE from kern.plaats WHERE ID=7905;
DELETE from kern.plaats WHERE ID=7904;
DELETE from kern.plaats WHERE ID=7903;
DELETE from kern.plaats WHERE ID=7902;
DELETE from kern.plaats WHERE ID=7901;
DELETE from kern.gem WHERE ID=8013;
DELETE from kern.gem WHERE ID=8012;
DELETE from kern.gem WHERE ID=8001;
DELETE from kern.gem WHERE ID=7019;
DELETE from kern.gem WHERE ID=7018;
DELETE from kern.gem WHERE ID=7017;
DELETE from kern.gem WHERE ID=7016;
DELETE from kern.gem WHERE ID=7015;
DELETE from kern.gem WHERE ID=7014;
DELETE from kern.gem WHERE ID=7013;
DELETE from kern.gem WHERE ID=7012;
DELETE from kern.gem WHERE ID=7011;
DELETE from kern.partij WHERE ID=28003;
DELETE from kern.partij WHERE ID=28002;
DELETE from kern.partij WHERE ID=28001;
DELETE from kern.partij WHERE ID=27019;
DELETE from kern.partij WHERE ID=27018;
DELETE from kern.partij WHERE ID=27017;
DELETE from kern.partij WHERE ID=27016;
DELETE from kern.partij WHERE ID=27015;
DELETE from kern.partij WHERE ID=27014;
DELETE from kern.partij WHERE ID=27013;
DELETE from kern.partij WHERE ID=27012;
DELETE from kern.partij WHERE ID=27011;
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27011',null,'507011','Gemeente overlijden 1',null,null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27012',null,'507012','Gemeente overlijden 2',null,'20121004','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27013',null,'507013','Gemeente overlijden 3','20121005',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27014',null,'507014','Gemeente overlijden 4','20121002','20121004','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27015',null,'507015','Gemeente overlijden 5','20121003','20121006','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27016',null,'507016','Gemeente overlijden 6','20121005','20121006','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27017',null,'507017','Gemeente overlijden 7',null,'20111004','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27018',null,'507018','Gemeente overlijden 8',null,'20131004','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('27019',null,'507019','Gemeente overlijden 9','20111004',null,'false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('28001',null,'508001','Gemeente overlijden 10','20111004','20111231','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('28002',null,'508002','Gemeente overlijden 11','20111004','20121231','false');
INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('28003',null,'508003','Gemeente overlijden 12','20111004','20121231','false');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7011','7111','Gemeente overlijden 1','27011',null,null,null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7012','7212','Gemeente overlijden 2','27012',null,null,'20121004');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7013','7313','Gemeente overlijden 3','27013',null,'20121005',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7014','7414','Gemeente overlijden 4','27014',null,'20121002','20121004');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7015','7515','Gemeente overlijden 5','27015',null,'20121003','20121006');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7016','7616','Gemeente overlijden 6','27016',null,'20121005','20121006');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7017','7717','Gemeente overlijden 7','27017',null,null,'20111004');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7018','7818','Gemeente overlijden 8','27018',null,null,'20131004');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('7019','7919','Gemeente overlijden 9','27019',null,'20111004',null);
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('8001','8111','Gemeente overlijden 10','28001',null,'20111004','20111231');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('8012','8212','Gemeente overlijden 11','28002',null,'20111004','20121231');
INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('8013','8313','Gemeente overlijden 12','28003',null,'20111004','20121231');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7901','7911','BRBY0428-10-Woonplaats',null,null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7902','7922','BRBY0428-11-Woonplaats',null,'20121004');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7903','7933','BRBY0428-12-Woonplaats','20121005',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7904','7944','BRBY0428-13-Woonplaats','20121003','20121004');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7905','7955','BRBY0428-14-Woonplaats','20121003','20121006');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7906','7966','BRBY0428-15-Woonplaats','20121005','20121006');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('7907','7977','BRBY0428-16-Woonplaats','20121005',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('8101','8111','BRBY0428-20-Woonplaats','20121005','20121006');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('8102','8122','BRBY0428-21-Woonplaats','20111005',null);
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('8103','8133','BRBY0428-22-Woonplaats','20111005','20111231');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('8104','8144','BRBY0428-23-Woonplaats','20111005','20121231');
INSERT INTO kern.plaats (id,code,naam,dataanvgel,dateindegel) VALUES('8105','8155','BRBY0428-24-Woonplaats','20111005','20131231');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7001','7101','Overlijden-1-land','TL',null,null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7002','7202','Overlijden-2-land','TL',null,'20121004');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7003','7303','Overlijden-3-land','TL','20121005',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7004','7404','Overlijden-4-land','TL','20121003','20121004');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7005','7505','Overlijden-5-land','TL','20121003','20121006');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7006','7606','Overlijden-6-land','TL','20121005','20121006');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7007','7707','Overlijden-7-land','TL',null,'20111004');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7008','7808','Overlijden-8-land','TL','20111005',null);
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('7009','7909','Overlijden-9-land','TL','20111005','20121005');
INSERT INTO kern.landgebied (id,code,naam,iso31661alpha2,dataanvgel,dateindegel) VALUES('8001','8101','Overlijden-10-land','TL','20111004','20131231');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (Referentiedata)
--- Handler: SheetHandlerImplReferentieInsert
--- Aantal: (51)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/referentie-data/ReferentieOVERLIJDEN.xls
--------------------------------------------------
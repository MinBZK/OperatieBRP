--
-- File: src/main/resources/data/testdata.sql
--

-- Plaats
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (1,'Amsterdam',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (2,'Rotterdam',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (3,'Den Haag',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (4,'Utrecht',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (5,'Eindhoven',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (6,'Tilburg',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (7,'Almere',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (8,'Groningen',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (9,'Breda',null,null);
INSERT INTO kern.plaats (id,naam,dataanvgel,dateindegel) VALUES (10,'Nijmegen',null,null);

-- Persoon
INSERT INTO kern.pers (id,srt,bsn,anr,idsstatushis,geslachtsaand,geslachtsaandstatushis,predikaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaam,indnreeksalsgeslnaam,indalgoritmischafgeleid,samengesteldenaamstatushis,gebrgeslnaamegp,indaanschrmetadellijketitel,indaanschralgoritmischafgele,predikaataanschr,voornamenaanschr,voorvoegselaanschr,scheidingstekenaanschr,geslnaamaanschr,aanschrstatushis,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats,blregiogeboorte,landgeboorte,omsgeboorteloc,geboortestatushis,datoverlijden,gemoverlijden,wploverlijden,blplaatsoverlijden,blregiooverlijden,landoverlijden,omslocoverlijden,overlijdenstatushis,verblijfsr,dataanvverblijfsr,datvoorzeindeverblijfsr,dataanvaaneenslverblijfsr,verblijfsrstatushis,induitslnlkiesr,dateindeuitslnlkiesr,uitslnlkiesrstatushis,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezi,dateindeuitsleukiesr,euverkiezingenstatushis,verantwoordelijke,BijhverantwoordelijkheidStat,rdnopschortingbijhouding,opschortingstatushis,bijhgem,datinschringem,indonverwdocaanw,bijhgemstatushis,gempk,indpkvollediggeconv,pkstatushis,landvanwaargevestigd,datvestiginginnederland,immigratiestatushis,datinschr,versienr,vorigepers,volgendepers,inschrstatushis,tijdstiplaatstewijz,indgegevensinonderzoek)
VALUES
(1,1,123456789,1234567890,'A',1,'A',null,'Ludwig Josef Johann',null,',',null,'Wittgenstein',false,false,'A',null,false,false,null,null,null,null,null,'A',18890426,364,1,null,null,229,'52.429222,2.790527','A',null,null,null,null,null,null,null,'A',null,null,null,null,'A',false,null,'A',false,null,null,'A',null,'A',1,'A',364,null,false,'A',null,false,'A',null,null,'A',null,null,null,null,'A',null,false);
INSERT INTO kern.pers (id,srt,bsn,anr,idsstatushis,geslachtsaand,geslachtsaandstatushis,predikaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaam,indnreeksalsgeslnaam,indalgoritmischafgeleid,samengesteldenaamstatushis,gebrgeslnaamegp,indaanschrmetadellijketitel,indaanschralgoritmischafgele,predikaataanschr,voornamenaanschr,voorvoegselaanschr,scheidingstekenaanschr,geslnaamaanschr,aanschrstatushis,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats,blregiogeboorte,landgeboorte,omsgeboorteloc,geboortestatushis,datoverlijden,gemoverlijden,wploverlijden,blplaatsoverlijden,blregiooverlijden,landoverlijden,omslocoverlijden,overlijdenstatushis,verblijfsr,dataanvverblijfsr,datvoorzeindeverblijfsr,dataanvaaneenslverblijfsr,verblijfsrstatushis,induitslnlkiesr,dateindeuitslnlkiesr,uitslnlkiesrstatushis,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezi,dateindeuitsleukiesr,euverkiezingenstatushis,verantwoordelijke,BijhverantwoordelijkheidStat,rdnopschortingbijhouding,opschortingstatushis,bijhgem,datinschringem,indonverwdocaanw,bijhgemstatushis,gempk,indpkvollediggeconv,pkstatushis,landvanwaargevestigd,datvestiginginnederland,immigratiestatushis,datinschr,versienr,vorigepers,volgendepers,inschrstatushis,tijdstiplaatstewijz,indgegevensinonderzoek)
VALUES
(2,1,234567891,2345678901,'A',1,'A',null,'Erwin',null,',',null,'Schrödinger',false,false,'A',null,false,false,null,null,null,null,null,'A',18870812,364,null,null,null,32,'52.429222,2.790527','A',null,null,null,null,null,null,null,'A',null,null,null,null,'A',false,null,'A',false,null,null,'A',null,'A',1,'A',364,null,false,'A',null,false,'A',null,null,'A',null,null,null,null,'A',null,false);
INSERT INTO kern.pers (id,srt,bsn,anr,idsstatushis,geslachtsaand,geslachtsaandstatushis,predikaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaam,indnreeksalsgeslnaam,indalgoritmischafgeleid,samengesteldenaamstatushis,gebrgeslnaamegp,indaanschrmetadellijketitel,indaanschralgoritmischafgele,predikaataanschr,voornamenaanschr,voorvoegselaanschr,scheidingstekenaanschr,geslnaamaanschr,aanschrstatushis,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats,blregiogeboorte,landgeboorte,omsgeboorteloc,geboortestatushis,datoverlijden,gemoverlijden,wploverlijden,blplaatsoverlijden,blregiooverlijden,landoverlijden,omslocoverlijden,overlijdenstatushis,verblijfsr,dataanvverblijfsr,datvoorzeindeverblijfsr,dataanvaaneenslverblijfsr,verblijfsrstatushis,induitslnlkiesr,dateindeuitslnlkiesr,uitslnlkiesrstatushis,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezi,dateindeuitsleukiesr,euverkiezingenstatushis,verantwoordelijke,BijhverantwoordelijkheidStat,rdnopschortingbijhouding,opschortingstatushis,bijhgem,datinschringem,indonverwdocaanw,bijhgemstatushis,gempk,indpkvollediggeconv,pkstatushis,landvanwaargevestigd,datvestiginginnederland,immigratiestatushis,datinschr,versienr,vorigepers,volgendepers,inschrstatushis,tijdstiplaatstewijz,indgegevensinonderzoek)
VALUES
(3,1,345678912,3456789012,'A',1,'A',null,'Immanuel',null,',',null,'Kant',false,false,'A',null,false,false,null,null,null,null,null,'A',17240422,364,null,null,null,111,'52.429222,2.790527','A',null,null,null,null,null,null,null,'A',null,null,null,null,'A',false,null,'A',false,null,null,'A',null,'A',1,'A',364,null,false,'A',null,false,'A',null,null,'A',null,null,null,null,'A',null,false);
INSERT INTO kern.pers (id,srt,bsn,anr,idsstatushis,geslachtsaand,geslachtsaandstatushis,predikaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaam,indnreeksalsgeslnaam,indalgoritmischafgeleid,samengesteldenaamstatushis,gebrgeslnaamegp,indaanschrmetadellijketitel,indaanschralgoritmischafgele,predikaataanschr,voornamenaanschr,voorvoegselaanschr,scheidingstekenaanschr,geslnaamaanschr,aanschrstatushis,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats,blregiogeboorte,landgeboorte,omsgeboorteloc,geboortestatushis,datoverlijden,gemoverlijden,wploverlijden,blplaatsoverlijden,blregiooverlijden,landoverlijden,omslocoverlijden,overlijdenstatushis,verblijfsr,dataanvverblijfsr,datvoorzeindeverblijfsr,dataanvaaneenslverblijfsr,verblijfsrstatushis,induitslnlkiesr,dateindeuitslnlkiesr,uitslnlkiesrstatushis,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezi,dateindeuitsleukiesr,euverkiezingenstatushis,verantwoordelijke,BijhverantwoordelijkheidStat,rdnopschortingbijhouding,opschortingstatushis,bijhgem,datinschringem,indonverwdocaanw,bijhgemstatushis,gempk,indpkvollediggeconv,pkstatushis,landvanwaargevestigd,datvestiginginnederland,immigratiestatushis,datinschr,versienr,vorigepers,volgendepers,inschrstatushis,tijdstiplaatstewijz,indgegevensinonderzoek)
VALUES
(4,1,456789123,3456785012,'A',1,'A',null,'Alpha',null,',',null,'PlatoA',false,false,'A',null,false,false,null,null,null,null,null,'A',17240422,364,null,null,null,111,'52.429222,2.790527','A',null,null,null,null,null,null,null,'A',null,null,null,null,'A',false,null,'A',false,null,null,'A',null,'A',1,'A',364,null,false,'A',null,false,'A',null,null,'A',null,null,null,null,'A',null,false);
INSERT INTO kern.pers (id,srt,bsn,anr,idsstatushis,geslachtsaand,geslachtsaandstatushis,predikaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaam,indnreeksalsgeslnaam,indalgoritmischafgeleid,samengesteldenaamstatushis,gebrgeslnaamegp,indaanschrmetadellijketitel,indaanschralgoritmischafgele,predikaataanschr,voornamenaanschr,voorvoegselaanschr,scheidingstekenaanschr,geslnaamaanschr,aanschrstatushis,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats,blregiogeboorte,landgeboorte,omsgeboorteloc,geboortestatushis,datoverlijden,gemoverlijden,wploverlijden,blplaatsoverlijden,blregiooverlijden,landoverlijden,omslocoverlijden,overlijdenstatushis,verblijfsr,dataanvverblijfsr,datvoorzeindeverblijfsr,dataanvaaneenslverblijfsr,verblijfsrstatushis,induitslnlkiesr,dateindeuitslnlkiesr,uitslnlkiesrstatushis,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezi,dateindeuitsleukiesr,euverkiezingenstatushis,verantwoordelijke,BijhverantwoordelijkheidStat,rdnopschortingbijhouding,opschortingstatushis,bijhgem,datinschringem,indonverwdocaanw,bijhgemstatushis,gempk,indpkvollediggeconv,pkstatushis,landvanwaargevestigd,datvestiginginnederland,immigratiestatushis,datinschr,versienr,vorigepers,volgendepers,inschrstatushis,tijdstiplaatstewijz,indgegevensinonderzoek)
VALUES
(5,1,456789123,3256789012,'A',1,'A',null,'Beta',null,',',null,'PlatoB',false,false,'A',null,false,false,null,null,null,null,null,'A',17240422,364,null,null,null,111,'52.429222,2.790527','A',null,null,null,null,null,null,null,'A',null,null,null,null,'A',false,null,'A',false,null,null,'A',null,'A',1,'A',364,null,false,'A',null,false,'A',null,null,'A',null,null,null,null,'A',null,false);
INSERT INTO kern.pers (id,srt,bsn,anr,idsstatushis,geslachtsaand,geslachtsaandstatushis,predikaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaam,indnreeksalsgeslnaam,indalgoritmischafgeleid,samengesteldenaamstatushis,gebrgeslnaamegp,indaanschrmetadellijketitel,indaanschralgoritmischafgele,predikaataanschr,voornamenaanschr,voorvoegselaanschr,scheidingstekenaanschr,geslnaamaanschr,aanschrstatushis,datgeboorte,gemgeboorte,wplgeboorte,blgeboorteplaats,blregiogeboorte,landgeboorte,omsgeboorteloc,geboortestatushis,datoverlijden,gemoverlijden,wploverlijden,blplaatsoverlijden,blregiooverlijden,landoverlijden,omslocoverlijden,overlijdenstatushis,verblijfsr,dataanvverblijfsr,datvoorzeindeverblijfsr,dataanvaaneenslverblijfsr,verblijfsrstatushis,induitslnlkiesr,dateindeuitslnlkiesr,uitslnlkiesrstatushis,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezi,dateindeuitsleukiesr,euverkiezingenstatushis,verantwoordelijke,BijhverantwoordelijkheidStat,rdnopschortingbijhouding,opschortingstatushis,bijhgem,datinschringem,indonverwdocaanw,bijhgemstatushis,gempk,indpkvollediggeconv,pkstatushis,landvanwaargevestigd,datvestiginginnederland,immigratiestatushis,datinschr,versienr,vorigepers,volgendepers,inschrstatushis,tijdstiplaatstewijz,indgegevensinonderzoek)
VALUES
(6,1,787878789,7878787878,'A',1,'A',null,'Desiderius',null,',',null,'Erasmus',false,false,'A',null,false,false,null,null,null,null,null,'A',17240422,364,null,null,null,111,'52.429222,2.790527','A',null,null,null,null,null,null,null,'A',null,null,null,null,'A',false,null,'A',false,null,null,'A',null,'A',1,'A',364,null,false,'A',null,false,'A',null,null,'A',null,null,null,null,'A',null,false);



-- Persoon / adres
INSERT INTO kern.persadres (id,pers,srt,adreshstatushis,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,loctovadres,nladresstatushis,bladresstatushis)
VALUES
(1,1,1,'A',35,'New Yorkweg','New Yorkweg','Almere',73,'A','sous','1334NA','to','A','A');
INSERT INTO kern.persadres (id,pers,srt,adreshstatushis,gem,nor,afgekortenor,gemdeel,huisnr,postcode,nladresstatushis,bladresstatushis)
VALUES
(2,6,2,'A',600,'Kerkstraat','Kerkstraat','Rotterdam',12,'5656XX','A','A');

-- Persoon / indicatie
INSERT INTO kern.persindicatie (id,pers,srt,waarde,persindicatiestatushis) VALUES (1,1,1,true,'A');
INSERT INTO kern.persindicatie (id,pers,srt,waarde,persindicatiestatushis) VALUES (2,1,2,true,'A');
INSERT INTO kern.persindicatie (id,pers,srt,waarde,persindicatiestatushis) VALUES (3,1,3,true,'A');
INSERT INTO kern.persindicatie (id,pers,srt,waarde,persindicatiestatushis) VALUES (4,1,4,false,'A');
INSERT INTO kern.persindicatie (id,pers,srt,waarde,persindicatiestatushis) VALUES (5,1,5,false,'A');
INSERT INTO kern.persindicatie (id,pers,srt,waarde,persindicatiestatushis) VALUES (6,1,6,false,'A');

-- Autorisatiebesluit, doelbindingen en abonnementen
INSERT INTO autaut.autorisatiebesluit (id, srt, besluittekst, autoriseerder, indmodelbesluit, indingetrokken, autorisatiebesluitstatushis) VALUES (1, 1, 'Het mag', 364, false, false, 'A');
INSERT INTO autaut.doelbinding (id, levsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstdoelbinding, doelbindingstatushis) VALUES (1, 1,  364, 1, 'Is goed', 'A');
INSERT INTO autaut.functie (id, naam) VALUES (1, 'test');
INSERT INTO autaut.certificaat (id, subject, serial, signature) VALUES (1, 'CN=serverkey', 1315331010, '070ce9add25272a4111ca55da1b66e692b94e0689e74628fd834cf3001a7ef43972b5674bb66c954beaf16c16317db635ea92973de0d994f7651005be3c29162fd9125b9f425ad6443a8a298221a74fabe774cf8aa3d070b399d93aa4708944a81aa3cee5f080794e975801cf01ca371348cf56e7b9761256fac6950e3baa9af');
INSERT INTO autaut.authenticatiemiddel (id, partij, rol, functie, certificaattbvssl, certificaattbvondertekening, ipadres, authenticatiemiddelstatushis) VALUES (1, 364, 1, 1, 1, 1, '192.168.0.1', 'A');

INSERT INTO lev.abonnement (id, doelbinding, srtabonnement, abonnementstatushis) VALUES (1, 1, 1, 'A');
INSERT INTO lev.abonnementsrtber (id, abonnement, srtber, abonnementsrtberstatushis) VALUES (1, 1, 1, 'A');

-- Historisch abonnement
INSERT INTO autaut.autorisatiebesluit (id, srt, besluittekst, autoriseerder, indmodelbesluit, indingetrokken, autorisatiebesluitstatushis) VALUES (2, 1, 'Het mag', 364, false, false, 'A');
INSERT INTO autaut.doelbinding (id, levsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstdoelbinding, doelbindingstatushis) VALUES (2, 2,  364, 1, 'Is goed', 'A');
INSERT INTO lev.abonnement (id, doelbinding, srtabonnement, abonnementstatushis) VALUES (2, 2, 1, 'M');
INSERT INTO lev.abonnementsrtber (id, abonnement, srtber, abonnementsrtberstatushis) VALUES (2, 2, 1, 'M');



-- delete from autaut.seltaak;

INSERT INTO kern.partijrol (id, partij, rol, datingang, dateinde, indag)
VALUES
(65535, (select id from kern.partij p where p.code = '056901'), 1, 20010101, 20300101, true),
(65534, (select id from kern.partij p where p.code = '048401'), 1, 20010101, 20300101, true),
(65533, (select id from kern.partij p where p.code = '059901'), 1, 20010101, 20300101, true);

INSERT INTO autaut.levsautorisatie (id,stelsel,indmodelautorisatie,naam,protocolleringsniveau,indaliassrtadmhndleveren,datingang,dateinde,populatiebeperking,toelichting,indblok,indag)
VALUES
(1,1,false,'Selectie levsautorisatie',null,null,20010101,20300101,null,null,null,true),
(2,1,false,'Selectie levsautorisatie 2',null,null,20010101,20300101,null,null,null,true);

INSERT INTO autaut.dienstbundel (id,levsautorisatie,naam,datingang,dateinde,naderepopulatiebeperking,indnaderepopbeperkingvolconv,toelichting,indblok,indag)
VALUES
(1,1,'test dienstbundel 1',20010101,20300101,null,null,null,null,true),
(2,2,'test dienstbundel 2',20010101,20300101,null,null,null,null,true);

INSERT INTO autaut.dienst (id,dienstbundel,srt,effectafnemerindicaties,datingang,dateinde,indblok,indag,attenderingscriterium,indagattendering,maxaantalzoekresultaten,indagzoeken,srtsel,eersteseldat,selinterval,eenheidselinterval,nadereselcriterium,selpeilmommaterieelresultaat,selpeilmomformeelresultaat,historievormsel,indselresultaatcontroleren,maxaantalpersperselbestand,maxgrootteselbestand,indverzvolberbijwijzafninase,leverwijzesel,indagsel)
VALUES
(1,1,12,null,20010101,20300101,null,true,null,false,null,false,3,cast(to_char((current_date - INTERVAL '5' DAY), 'YYYYMMDD') as Integer),null,null,'<expressie 1>',null,null,1,true,null,null,null,null,true),
-- Dienst met selectie interval 1 maand, eerste 5 dagen vanaf vandaag.
(2,1,12,null,20010101,20300101,null,true,null,false,null,false,2,cast(to_char((current_date + INTERVAL '5' DAY), 'YYYYMMDD') as Integer),1,2,'<expressie 2>',cast(to_char((current_date), 'YYYYMMDD') as Integer),current_timestamp,2,false,20,100000,true,1,true),
-- Dienst met selectie interval 3 dagen, eerste 35 dagen vanaf vandaag.
(3,2,12,null,20010101,20300101,null,true,null,false,null,false,1,cast(to_char((current_date + INTERVAL '35' DAY), 'YYYYMMDD') as Integer),3,1,'<expressie 3>',cast(to_char((current_date + INTERVAL '35' DAY), 'YYYYMMDD') as Integer),current_timestamp,3,null,10,null,null,2,true);

INSERT INTO autaut.toeganglevsautorisatie (id,geautoriseerde,levsautorisatie,ondertekenaar,transporteur,datingang,dateinde,naderepopulatiebeperking,afleverpunt,indblok,indag)
VALUES
(1,65535,1,null,null,20010101,20300101,null,'dummy',null,true),
(2,65534,1,null,null,20010101,20300101,null,'dummy2',null,true),
(3,65533,2,null,null,20010101,20300101,null,'dummy3',null,true);

INSERT INTO autaut.seltaak (id,dienst,toeganglevsautorisatie,volgnr,datplanning,indag,indagstatus,status)
VALUES
(1,2,1,1,cast(to_char((current_date + INTERVAL '1' MONTH ), 'YYYYMMDD') as Integer),true,true,2),
(2,2,2,1,cast(to_char((current_date - INTERVAL '2' DAY), 'YYYYMMDD') as Integer),true,true,10);
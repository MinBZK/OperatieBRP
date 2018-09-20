
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('1', 'huisletter', 'WAAR', 1,20130101,null,4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('4', 'altijd waar', 'WAAR', 1,20130101,null,4);

insert into autaut.abonnement -- Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('5', 'postcode gebied Haarlem 2000 - 2099','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2000AA" EN a.postcode <= "2099ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('6', 'postcode gebied Heemstede 2100-2129','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2100AA" EN a.postcode <= "2129ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('7', 'postcode gebied Hillegom 2180 - 2182','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('8', 'postcode gebied Bennebroek 2120 - 2121','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2120AA" EN a.postcode <= "2121ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch ABONNEMENT IS NIET MEER GELDIG => Einddatum abo < systeemdatum voor testgeval BRLV0018-TC01.
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('9',  'postcode gebied Hillegom 2180 - 2182 NIET GELDIG ABO Einddate 20130201 KD sysdat','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20130101,20130201,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch ABONNEMENT IS NIET MEER GELDIG => Begindatum abo > systeemdatum voor testgeval BRLV0018-TC02.
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  values ('10', 'postcode gebied Hillegom 2180 - 2182 NIET GELDIG ABO Begindat 20140101 GD sysdat','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20140101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch DIENST IS NIET MEER GELDIG => Einddatum dienst < systeemdatum voor testgeval BRLV0019-TC01.
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  values ('11', 'postcode gebied Hillegom 2180 - 2182 NIET GELDIG DST Einddate 20130201 KD sysdat','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch DIENST IS NOG NIET GELDIG => Begindatum dienst > systeemdatum voor testgeval BRLV0019-TC02.
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('12', 'postcode gebied Hillegom 2180 - 2182 NIET GELDIG DST Begindat 20140101 KD sysdat','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch ABONNEMENT IS NIET DEFINITIEF voor testgeval BRLV0020-TC01 Toestand (in dit geval = 3) <> 4 .
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('13', 'postcode gebied Hillegom 2180 - 2182 TOESTAND ABO OG DEFINITIEF','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20130101,null,3);

insert into autaut.abonnement -- Handmatig of Niet Automatisch DIENST IS NIET DEFINITIEF voor testgeval BRLV0021-TC01 Toestand (in dit geval = 2) <> 4 .
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('14', 'postcode gebied Bennebroek 2120 - 2121 TOESTAND DST OG DEFINITIEF','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "2120AA" EN a.postcode <= "2121ZZ")) > 0', 1,20130101,null,4);


insert into autaut.abonnement (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
select
  999 + p.id,
  concat('Mutaties op personen van wie u de bijhoudingsverantwoording heeft. (', CAST( p.code AS text ), ')'),
  concat('persoon.bijhouding.bijhoudingspartij = ', CAST( p.code AS text ), ''),
  1, -- protocolleringsniveau = geen beperking
  20130101, null,4
from kern.partij p
where p.srt = 3
order by p.id;


insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	1,347, null,null, 1,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	4,177, null,null,	4,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	5,6, null,null,	5,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	6,8, null,null,6,20130101,null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	7,10,	null,null,7,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair,  abonnement, datingang, dateinde)
VALUES(	8,12, null,null,8,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	9,12,	null,null,9,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	10,12, null,null,	10,20140101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	11,12, null,null,11,20130101,null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	12,12, null,null,12,20130101,	null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	13,12,null,null,13,20130101,null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	14,12,null,null,14,20130101,null);

insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
select
  999 + p.id, p.id, null, null, 999 + p.id, 20130101,null
from kern.partij p
where p.srt = 3
order by p.id;

insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	1,1,1,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	4,2,4,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	5,1,5,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	6,1,6,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	7,2,7,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	8,2,8,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	9,2,9,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	10,2,10,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	11,2,11,	null,	20130101,20130201,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	12,2,12,	null,	20140101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	13,2,13,	null,	20130101,null,	4);
insert into autaut.dienst (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	14,2,14,	null,	20130101,null,	2);

insert into autaut.dienst -- Automatisch
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
select 999 + p.id, 1,  999 + p.id,  null,  20130101,  null,  4
from kern.partij p
where p.srt = 3
order by p.id;

insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
VALUES (1, 1, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
VALUES (4, 4, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (5, 5, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (6, 6, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (7, 7, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (8, 8, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (9, 9, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (10, 10, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (11, 11, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (12, 12, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (13, 13, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (14, 14, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);

INSERT INTO autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  select
    999 + p.id, 999 + p.id, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null
  from kern.partij p
  where p.srt = 3
  order by p.id;

UPDATE autaut.afleverwijze SET uri = 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService' WHERE uri IS NULL;

-- Nieuwe Persafnemerindicaties voor test

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('35', 'altijd waar 2', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('36', 'altijd waar 3', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('37', 'altijd waar 4', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('38', 'altijd waar 5', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('39', 'altijd waar 6', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('40', 'altijd waar 7', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('41', 'altijd waar 8', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('42', 'altijd waar 9', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('43', 'altijd waar 10', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('44', 'altijd waar 11', true, 1,20130101,null,4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('45', 'altijd waar 12', true, 1,20130101,null,4);

insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	35, 201, null, null, 35, 20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	36, 202,	null, null, 36, 20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	37, 204,	null, null,37,20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	38, 207,	null, null, 38,20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	39, 208,	null, null, 39,20130101,	null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	40, 209,	null, null,40,20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	41, 210,	null, null,41,20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	42, 211,	null, null,42,20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	43, 213,	null, null,43,20130101, null);
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	44, 214,	null, null, 44, 20130101, null);
		insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	45, 214,	null, null, 45, 20130101, null);

insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	35,2,35,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	36,2,36,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	37,2,37,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	38,2,38,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	39,2,39,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	40,2,40,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	41,2,41,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	42,2,42,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	43,2,43,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	44,2,44,	null,	20130101,null,	4);
insert into autaut.dienst
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	45,2,45,	null,	20130101,null,	4);

INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '177', '35', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '201', '36', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '202', '37', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '204', '38', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '207', '39', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '208', '40', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '209', '41', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '210', '42', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '211', '43', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '213', '44', '20130101'
FROM helper.brppers;
INSERT INTO autaut.persafnemerindicatie (pers, afnemer, abonnement, dataanvmaterieleperiode)
SELECT id, '214', '45', '20130101'
FROM helper.brppers;

insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (35, 35, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (36, 36, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (37, 37, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (38, 38, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (39, 39, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (40, 40, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (41, 41, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (42, 42, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (43, 43, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (44, 44, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (45, 45, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);



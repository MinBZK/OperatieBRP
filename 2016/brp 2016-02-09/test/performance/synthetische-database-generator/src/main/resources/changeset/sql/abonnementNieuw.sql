-- Nieuwe abonnementen voor test

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('15', 'gebdatum na 1970 01 01','persoon.geboorte.datum > #1970/01/01#', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('16', 'gebdatum voor 1970 01 01','persoon.geboorte.datum < #1970/01/01#', 1, 20130101, null, 4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('17', 'geslachtsaanduiding man','persoon.geslachtsaanduiding = "MAN"', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('18', 'geslachtsaanduiding vrouw','persoon.geslachtsaanduiding = "VROUW"', 1, 20130101, null, 4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('19', 'huisnummer onder 25','AANTAL(FILTER(persoon.adressen, a, a.huisnummer >= 0 EN a.huisnummer <= 25)) > 0', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('20', 'huisnummer boven 25','AANTAL(FILTER(persoon.adressen, a, a.huisnummer >= 0 EN a.huisnummer > 25)) > 0', 1, 20130101, null, 4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('21', 'postcode voor 5555MM','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "0000AA" EN a.postcode <= "5555MM")) > 0', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('22', 'postcode na 5555MM','AANTAL(FILTER(persoon.adressen, a, a.postcode >= "5555MN" EN a.postcode <= "9999ZZ")) > 0', 1, 20130101, null, 4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('23', 'bijhoudingspartij onder 1000','persoon.bijhouding.bijhoudingspartij <= 1000', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('24', 'bijhoudingspartij boven 1000','persoon.bijhouding.bijhoudingspartij > 1000', 1, 20130101, null, 4);

-- Deze abonnementen werken niet meer doordat woonplaats veranderd is in woonplaatsnaam
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('25', 'woonplaatsnaam eerste helft alfabet', 'AANTAL(FILTER(persoon.adressen, a, a.woonplaatsnaam >= "A" EN a.woonplaatsnaam < "M")) > 0', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('26', 'woonplaatsnaam tweede helft alfabet', 'AANTAL(FILTER(persoon.adressen, a, a.woonplaatsnaam >= "M" EN a.woonplaatsnaam >= "Z")) > 0', 1, 20130101, null, 4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('27', 'land nederland','AANTAL(FILTER(persoon.adressen, a, a.land_gebied = 6030)) > 0', 1, 20130101, null, 4);
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('28', 'land buitenland','AANTAL(FILTER(persoon.adressen, a, a.land_gebied <> 6030)) > 0', 1, 20130101, null, 4);

insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('29', 'wel kinderen','AANTAL(KINDEREN()) = 0', 1, 20130101, null, 4);
insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('30', 'geen kinderen','AANTAL(KINDEREN()) > 0', 1, 20130101, null, 4);

insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  values ('31', 'wel huwelijken','AANTAL(HUWELIJKEN()) = 0', 1, 20130101, null, 4);
insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  values ('32', 'geen huwelijken','AANTAL(HUWELIJKEN()) > 0', 1, 20130101, null, 4);

insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  values ('33', 'wel partners','AANTAL(PARTNERS()) = 0', 1, 20130101, null, 4);
insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  values ('34', 'geen partners','AANTAL(PARTNERS()) > 0', 1, 20130101, null, 4);

insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	15, 13, null, null,	15, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	16, 13, null, null,	16, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	17, 13, null, null,	17, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	18, 13, null, null,	18, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	19, 13, null, null,	19, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	20, 13, null, null,	20, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	21, 13, null, null,	21, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	22, 13, null, null, 22, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	23, 13, null, null, 23, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	24, 13, null, null,	24, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	25, 13, null, null,	25, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	26, 13, null, null,	26, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	27, 13, null, null, 27, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	28, 13, null, null,	28, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	29, 13, null, null, 29, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	30, 13, null, null,	30, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	31, 13, null, null,	31, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	32, 13, null, null,	32, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	33, 13, null, null,	33, 20130101, null);
insert into autaut.toegangabonnement (id, partij, authenticatiemiddel, intermediair, abonnement, datingang, dateinde)
VALUES(	34, 13, null, null, 34, 20130101, null);

insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	15,1,15,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	16,1,16,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	17,1,17,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	18,1,18,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	19,1,19,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	20,1,20,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	21,1,21,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	22,1,22,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	23,1,23,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	24,1,24,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	25,1,25,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	26,1,26,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	27,1,27,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	28,1,28,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	29,1,29,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	30,1,30,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	31,1,31,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	32,1,32,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	33,1,33,	null,	20130101,null,	4);
insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	34,1,34,	null,	20130101,null,	4);

insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (15, 15, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (16, 16, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (17, 17, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (18, 18, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (19, 19, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (20, 20, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (21, 21, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (22, 22, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (23, 23, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (24, 24, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (25, 25, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (26, 26, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (27, 27, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (28, 28, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (29, 29, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (30, 30, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (31, 31, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (32, 32, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (33, 33, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
  VALUES (34, 34, 2, 'http://pap96.modernodam.nl:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService', 20130101, null);


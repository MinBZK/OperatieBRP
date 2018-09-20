delete from autaut.his_persafnemerindicatie where persafnemerindicatie in (select id from autaut.persafnemerindicatie where pers in (select id from kern.pers));
delete from autaut.persafnemerindicatie where pers in (select id from kern.pers);
delete from autaut.toegangabonnement;
delete from autaut.dienst;
delete from autaut.abonnement;

--(12:56:57 PM) Pepijn de Jong: filter(persoon.nationaliteiten, n, n.nationaliteit = 339)
--(12:57:19 PM) Pepijn de Jong: postcode = ¨1901RE¨)
--'filter(persoon.adressen, a, a.huisnrtoevoeging = "II")
insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
VALUES ('1', 'huisletter' , true, 1, -- geen beperking,
		20130101, null, 4 );-- toestand definitief

--insert into autaut.abonnement
--  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
--values ('2', 'huisnrtoevoeging', 'filter(persoon.adressen, a, a.huisnrtoevoeging = "II")', 1, 20130101,null,4);

--insert into autaut.abonnement
--  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
--values ('3', 'geslachtsaanduiding', 'filter(persoon.geslachtsaanduiding = "V")', 1, 20130101,null,4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('4', 'altijd waar', true, 1,20130101,null,4);

-- User Stories ‘volgen persoon’ op aanvraag (niet en wel automatisch)
insert into autaut.abonnement -- Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('5', 'postcode gebied Haarlem 2000 - 2099','aantal(filter(persoon.adressen, a, a.postcode >= "2000AA" EN a.postcode <= "2099ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('6', 'postcode gebied Heemstede 2100-2129','aantal(filter(persoon.adressen, a, a.postcode >= "2100AA" EN a.postcode <= "2129ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('7', 'postcode gebied Hillegom 2180 - 2182','aantal(filter(persoon.adressen, a, a.postcode >= "2180AA" EN a.postcode <= "2182ZZ")) > 0', 1,20130101,null,4);

insert into autaut.abonnement -- Handmatig of Niet Automatisch
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('8', 'postcode gebied Bennebroek 2120 - 2121','aantal(filter(persoon.adressen, a, a.postcode >= "2120AA" EN a.postcode <= "2121ZZ")) > 0', 1,20130101,null,4);
---<

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
select 
  999 + p.id, 
  concat('Mutaties op personen van wie u de bijhoudingsverantwoording heeft. (', CAST( p.code AS text ), ')'),
  concat('persoon.bijhoudingsgemeente = ', CAST( p.code AS text ), ''),
  1, -- protocolleringsniveau = geen beperking
  20130101, null,4
from kern.partij p
where p.srt = 3
order by p.id;


insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	1,347, --select partij.id from kern.partij where partij.code = 034401
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		1,20130101,
		null);
 
--insert into autaut.toegangabonnement
--  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
--VALUES(	2,177, --select partij.id from kern.partij where partij.code = 017401
---		null,null,
---		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
---		2,20130101,
---		null);

---insert into autaut.toegangabonnement
---  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
---VALUES(	3,177, --select partij.id from kern.partij where partij.code = 017401
---		null,null,
---		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
---		3,20130101,
---		null);
		
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	4,177, --select partij.id from kern.partij where partij.code = 017401
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		4,20130101,
		null);

		insert into autaut.toegangabonnement -- Automatisch
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	5,5003, --select partij.id from kern.partij where partij.code = "SRPUC50151-3-Partij" 505003
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		5,20130101,
		null);

		insert into autaut.toegangabonnement -- Automatisch
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	6,5004, --select partij.id from kern.partij where partij.code = "SRPUC50151-4-Partij"  505004
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		6,20130101,
		null);


		insert into autaut.toegangabonnement -- Handmatig of Niet Automatisch
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	7,5005, --select partij.id from kern.partij where partij.code = "SRPUC50151-5-Partij"  505005
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		7,20130101,
		null);
		
		insert into autaut.toegangabonnement -- Handmatig of Niet Automatisch
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	8,5006, --select partij.id from kern.partij where partij.code = "SRPUC50151-6-Partij"  505006
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		8,20130101,
		null);

		
		
		insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
select
  999 + p.id, p.id, null, null, null, 999 + p.id, 20130101,null 
from kern.partij p
where p.srt = 3
order by p.id;



insert into autaut.dienst -- Automatisch
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	1,1,1,	null,	20130101,null,	4);

---insert into autaut.dienst
---  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
---VALUES(	2,1,2,	null,	20130101,null,	4);

---		insert into autaut.dienst
---  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
---VALUES(	3,1,3,	null,	20130101,null,	4);

insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	4,1,4,	null,	20130101,null,	4);

insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	5,1,5,	null,	20130101,null,	4);

insert into autaut.dienst -- Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	6,1,6,	null,	20130101,null,	4);

insert into autaut.dienst -- Handmatig of Niet Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	7,2,7,	null,	20130101,null,	4);

insert into autaut.dienst -- Handmatig of Niet Automatisch
(id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	8,2,8,	null,	20130101,null,	4);


insert into autaut.dienst -- Automatisch
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
select 999 + p.id, 1,  999 + p.id,  null,  20130101,  null,  4
from kern.partij p
where p.srt = 3
order by p.id;


UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 034401);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 017401);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 185901);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 036101);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 505005);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 505006);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 170201);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 039701);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 505003);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 505004);
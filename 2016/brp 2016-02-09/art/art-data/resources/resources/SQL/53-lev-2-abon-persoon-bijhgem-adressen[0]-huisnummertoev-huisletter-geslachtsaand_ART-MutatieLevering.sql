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

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('2', 'huisnrtoevoeging', filter(persoon.adressen, a, a.huisnrtoevoeging = "II"), 1, 20130101,null,4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('3', 'geslachtsaanduiding', filter(persoon.geslachtsaanduiding = "V"), 1, 20130101,null,4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
values ('4', 'altijd waar', true, 1,20130101,null,4);

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
select 
  999 + p.id, 
  concat('Mutaties op personen van wie u de bijhoudingsverantwoording heeft. (', CAST( p.code AS text ), ')'),
  concat('persoon.bijhoudingsgemeente = "', CAST( p.code AS text ), '"'),
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
 
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	2,177, --select partij.id from kern.partij where partij.code = 017401
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		2,20130101,
		null);

insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	3,177, --select partij.id from kern.partij where partij.code = 017401
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		3,20130101,
		null);
		
insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
VALUES(	4,177, --select partij.id from kern.partij where partij.code = 017401
		null,null,
		'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService',
		4,20130101,
		null);

insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
select
  999 + p.id, p.id, null, null, null, 999 + p.id, 20130101,null 
from kern.partij p
where p.srt = 3
order by p.id;



insert into autaut.dienst
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	1,1,1,	null,	20130101,null,	4);

insert into autaut.dienst
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	2,1,2,	null,	20130101,null,	4);

		insert into autaut.dienst
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	3,1,3,	null,	20130101,null,	4);

		insert into autaut.dienst
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
VALUES(	4,1,4,	null,	20130101,null,	4);


insert into autaut.dienst
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
select 999 + p.id, 1,  999 + p.id,  null,  20130101,  null,  4
from kern.partij p
where p.srt = 3
order by p.id;


UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 034401);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 017401);
UPDATE autaut.toegangabonnement SET wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE partij in (select partij.id from kern.partij where partij.code = 185901);
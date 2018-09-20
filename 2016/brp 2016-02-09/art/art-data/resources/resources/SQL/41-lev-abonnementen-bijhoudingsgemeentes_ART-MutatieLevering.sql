delete from autaut.toegangabonnement;
delete from autaut.dienst;
delete from autaut.abonnement;

insert into autaut.abonnement
  (id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand, abonnementstatushis)
select
  999 + p.id,
  concat('Mutaties op personen van wie u de bijhoudingsverantwoording heeft. (', CAST( p.code AS text ), ')'),
  concat('persoon.bijhoudingsgemeente = "', CAST( p.code AS text ), '"'),
  1, -- geen beperking
  20130101,
  null,
  4, -- Definitief
  'A'
from kern.partij p
where p.srt = 3
order by p.id
;

insert into autaut.toegangabonnement
  (id, partij, authenticatiemiddel, bewerker, wsdlendpoint, abonnement, datingang, dateinde, toegangabonnementstatushis)
select
  999 + p.id,
  p.id,
  null,
  null,
  null,
  999 + p.id,
  20130101,
  null,
  'A'
from kern.partij p
where p.srt = 3
order by p.id
;

insert into autaut.dienst
  (id, catalogusoptie, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand, dienststatushis)
select
  999 + p.id,
  1,
  999 + p.id,
  null,
  20130101,
  null,
  4, -- Definitief
  'A'
from kern.partij p
where p.srt = 3
order by p.id
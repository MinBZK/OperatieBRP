delete from autaut.his_toegangabonnement;
delete from autaut.his_dienst;
delete from autaut.his_abonnement;

delete from autaut.toegangabonnement;
delete from autaut.dienst;
delete from autaut.abonnement;

-- (toegang)abonnement en dienst voor elke partij voor automatisch synchroniseren
insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  select
    1000 + p.id,
    concat('Automatisch synchroniseren populatie ', lpad(CAST( p.code AS text ), 6, '0'), ''),
    concat('persoon.bijhoudingsgemeente = ', CAST( p.code AS text )),
    1, -- geen beperking
    20130101,
    null,
    4 -- Definitief
  from kern.partij p
  where p.srt = 3
  order by p.id
;

insert into autaut.toegangabonnement
(id, partij, authenticatiemiddel, intermediair, wsdlendpoint, abonnement, datingang, dateinde)
  select
    1000 + p.id,
    p.id,
    null,
    null,
    null,
    1000 + p.id,
    20130101,
    null
  from kern.partij p
  where p.srt = 3
  order by p.id
;

insert into autaut.dienst
(id, srtdienst, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
  select
    1000 + p.id,
    1,
    1000 + p.id,
    null,
    20130101,
    null,
    4 -- Definitief
  from kern.partij p
  where p.srt = 3
  order by p.id
;

-- (toegang)abonnement en dienst voor elke afnemer voor volgen op indicatie
insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  select
    5000 + p.id,
    concat('Synchroniseren via indicatie voor partij ', lpad(CAST( p.code AS text ), 6, '0'), ''),
    'TRUE',
    1, -- geen beperking
    20130101,
    null,
    4 -- Definitief
  from kern.partij p
  where p.srt = 3
  order by p.id
;

insert into autaut.toegangabonnement
(id, partij, authenticatiemiddel, Intermediair, wsdlendpoint, abonnement, datingang, dateinde)
  select
    5000 + p.id,
    p.id,
    null,
    null,
    null,
    5000 + p.id,
    20130101,
    null
  from kern.partij p
  where p.srt = 3
  order by p.id
;

insert into autaut.dienst
(id, srtdienst, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
  select
    5000 + p.id,
    2,
    5000 + p.id,
    null,
    20130101,
    null,
    4 -- Definitief
  from kern.partij p
  where p.srt = 3
  order by p.id
;

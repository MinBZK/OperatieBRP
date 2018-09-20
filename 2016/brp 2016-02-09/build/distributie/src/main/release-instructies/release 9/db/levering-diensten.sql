-- PdJ 12-8-2013: Toegevoegd om zowel automatisch volgen (expressies) als handmatig volgen (afnemerindicaties) mogelijk te maken.
-- Dienst voor het automatisch synchroniseren van de eigen populatie (= bijhoudingsgemeente)
update lev.srtdienst set naam = 'Automatisch synchroniseren' where id = 1;
-- Nieuwe dienst voor synchroniseren via het plaatsen van een afnemer indicatie
update lev.srtdienst set naam='Handmatig synchroniseren' where id = 2;

delete from lev.toegangabonnement;
delete from lev.dienst;
delete from lev.abonnement;

insert into lev.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  select
    1000 + p.id,
    concat('Automatisch synchroniseren populatie ', CAST( p.code AS text ), ''),
    concat('persoon.bijhoudingsgemeente = "', CAST( p.code AS text ), '"'),
    1, -- geen beperking
    20130101,
    null,
    4 -- Definitief
  from kern.partij p
  where p.srt = 3
  order by p.id
;

insert into lev.toegangabonnement
(id, partij, authenticatiemiddel, Intermediair, wsdlendpoint, abonnement, datingang, dateinde)
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

-- Dienst voor elke partij voor automatisch synchroniseren
insert into lev.dienst
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

-- Afnemer indicatie abonnementen
insert into lev.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  select
    5000 + p.id,
    concat('Synchroniseren via indicatie voor partij ', CAST( p.code AS text ), ''),
    'TRUE',
    1, -- geen beperking
    20130101,
    null,
    4 -- Definitief
  from kern.partij p
  where p.srt = 3
  order by p.id
;


insert into lev.toegangabonnement
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


-- Dienst voor elke partij voor handmatig synchroniseren (via afnemer indicaties)
insert into lev.dienst
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

-- Gemeente Groningen - PinkRoccade
update lev.toegangabonnement set wsdlendpoint = 'https://92.70.53.29:8444/CGS/StUF/0302/BRP/0100/services/OntvangAsynchroonLevering' where partij = (
  select id from kern.partij where code = 1401
);

-- Gemeente Utrecht - Gemboxx
update lev.toegangabonnement set wsdlendpoint = 'https://gemboxxbrp.grexx.net/leveren/kennisgeving.svc' where partij = (
  select id from kern.partij where code = 34401
);

-- Gemeente Haarlem - Procura
update lev.toegangabonnement set wsdlendpoint = 'https://web.procura.nl/gba-webservice/services/Kennisgeving' where partij = (
  select id from kern.partij where code = 39201
);

-- Gemeente Den Haag - BRP
update lev.toegangabonnement set wsdlendpoint = 'http://127.0.0.1:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' where partij = (
  select id from kern.partij where code = 51801
);

-- Gemeente Rotterdam - Centric
update lev.toegangabonnement set wsdlendpoint = 'https://leverenpiv.centric.eu/test/OntvangKennisgeving.svc' where partij = (
  select id from kern.partij where code = 59901
);

--Gemeente Maastricht -
update lev.toegangabonnement set wsdlendpoint = 'http://srv02.jnc.nl:7080/opentunnel/12332112332112332112/brp/leveren' where partij = (
  select id from kern.partij where code = 93501
);




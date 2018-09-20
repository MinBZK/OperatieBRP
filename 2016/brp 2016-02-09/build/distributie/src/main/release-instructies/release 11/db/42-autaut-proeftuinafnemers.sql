-- Toevoeging voor afnemers op de PROEFTUIN omgeving
insert into autaut.abonnement
(id, naam, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
  select
    10000 + p.id,
    concat('Automatisch synchroniseren populatie NIET', lpad(CAST( p.code AS text ), 6, '0'), ''),
    concat('persoon.bijhoudingsgemeente <> ', CAST( p.code AS text )),
    1, 20130101, null, 4
  from kern.partij p
  where p.naam like 'Gemeente Haarlem';

insert into autaut.toegangabonnement
(id, partij, authenticatiemiddel, Intermediair, wsdlendpoint, abonnement, datingang, dateinde)
  select
    10000 + p.id,
    p.id,
    null, null, null,
    10000 + p.id,
    20130101, null
  from kern.partij p
  where p.naam like 'Gemeente Haarlem';

insert into autaut.dienst
(id, srtdienst, abonnement, naderepopulatiebeperking, datingang, dateinde, toestand)
  select
    10000 + p.id,
    1,
    10000 + p.id,
    'true',
    20130101, null,
    4
  from kern.partij p
  where p.naam like 'Gemeente Haarlem';


update autaut.toegangabonnement
set wsdlendpoint = 'https://92.70.53.29:8444/CGS/StUF/0302/BRP/0100/services/OntvangAsynchroonLevering'
where partij = (select id from kern.partij where naam like 'Gemeente Groningen');

update autaut.toegangabonnement
set wsdlendpoint = 'https://gemboxxbrp.grexx.net/leveren/kennisgeving.svc'
where partij = (select id from kern.partij where naam like 'Gemeente Utrecht');

update autaut.toegangabonnement
set wsdlendpoint = 'https://leverenpiv.centric.eu/test/OntvangKennisgeving.svc'
where partij = (select id from kern.partij where naam like 'Gemeente Rotterdam');

update autaut.toegangabonnement
set wsdlendpoint = 'http://srv02.jnc.nl:7080/opentunnel/12332112332112332112/brp/leveren'
where partij = (select id from kern.partij where naam like 'Gemeente Maastricht');

update autaut.toegangabonnement
set wsdlendpoint = 'https://web.procura.nl/gba-webservice/services/Kennisgeving'
where partij = (select id from kern.partij where naam like 'Gemeente Haarlem');

update autaut.toegangabonnement
set wsdlendpoint = 'http://127.0.0.1:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService'
where partij = (select id from kern.partij where naam like 'Gemeente %Gravenhage');

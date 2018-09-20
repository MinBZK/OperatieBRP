-- insert historie voor alle (toegang)abonnementen en diensten
insert into autaut.his_abonnement
(id, abonnement, populatiebeperking, protocolleringsniveau, toestand, tsreg, datingang, dateinde)
  select
    id,
    id,
    populatiebeperking,
    protocolleringsniveau,
    toestand,
    CURRENT_TIMESTAMP,
    datingang,
    dateinde
  from autaut.abonnement a
  order by a.id
;

insert into autaut.his_toegangabonnement
(id, toegangabonnement, tsreg, datingang, dateinde)
  select
    id,
    id,
    CURRENT_TIMESTAMP,
    datingang,
    dateinde
  from autaut.toegangabonnement t
  order by t.id
;

insert into autaut.his_dienst
(id, dienst, naderepopulatiebeperking, toestand, tsreg, datingang, dateinde)
  select
    id,
    id,
    naderepopulatiebeperking,
    toestand,
    CURRENT_TIMESTAMP,
    datingang,
    dateinde
  from autaut.dienst d
  order by d.id
;
